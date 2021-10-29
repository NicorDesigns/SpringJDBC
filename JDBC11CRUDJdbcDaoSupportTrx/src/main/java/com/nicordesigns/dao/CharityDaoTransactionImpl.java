package com.nicordesigns.dao;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import com.nicordesigns.model.Program;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CharityDaoTransactionImpl extends JdbcDaoSupport implements CharityDao {

  public CharityDaoTransactionImpl() {}

  @Override
  public int insert(Charity charity) throws SQLException {

    assert getJdbcTemplate() != null;

    String sqlInsertCharity =
        "INSERT INTO charity"
            + "(CHARITY_TAX_ID, "
            + "CHARITY_NAME, "
            + "CHARITY_MISSION, "
            + "CHARITY_WEB_ADDRESS, "
            + "CHARITY_FACEBOOK_ADDRESS, "
            + "CHARITY_TWITTER_ADDRESS) "
            + "VALUES("
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "?"
            + ")";

    KeyHolder charityKeyHolder = new GeneratedKeyHolder();
    int charityRowInserted =
        getJdbcTemplate()
            .update(
                connection -> {
                  PreparedStatement preparedStatement =
                      connection.prepareStatement(
                          sqlInsertCharity, Statement.RETURN_GENERATED_KEYS);
                  preparedStatement.setString(1, charity.getCharityTaxId());
                  preparedStatement.setString(2, charity.getCharityName());
                  preparedStatement.setString(3, charity.getCharityMission());
                  preparedStatement.setString(4, charity.getCharityWebAddress());
                  preparedStatement.setString(5, charity.getCharityFacebookAddress());
                  preparedStatement.setString(6, charity.getCharityTwitterAddress());
                  return preparedStatement;
                },
                charityKeyHolder);

    System.out.println(
        "charityKeyHolder.getKey() : " + Objects.requireNonNull(charityKeyHolder.getKey()));

    charity.setCharityId(Objects.requireNonNull(charityKeyHolder.getKey()).intValue());

    charityCategoryUpdate(charity, charityRowInserted);
    charityProgramsUpdate(charity, charityRowInserted);

    return charityRowInserted;
  }

  private void charityProgramsUpdate(Charity charity, int charityRowInserted) throws SQLException {

    if (charityRowInserted == 1) {

      System.out.println("Finding Charity Programs for Charity " + charity.getCharityPrograms());

      var charityProgramsInDb = findProgramsForCharity(charity);
      System.out.println("charityProgramsInDb = " + charityProgramsInDb);

      for (Program program : charity.getCharityPrograms()) {
        System.out.println("Charity Program = " + program);

        var findProgramInDB = findProgramByName(program.getProgramType());

        Integer programIdInCharityProgramTable = null;

        if (findProgramInDB != null) {
          programIdInCharityProgramTable =
              findCharityProgramRelationshipRow(findProgramInDB, charity.getCharityId());
        }

        System.out.println(
            "Program Id retrieved from Charity Program Relationship table = "
                + programIdInCharityProgramTable);
        if (programIdInCharityProgramTable == null) {
          var programForCharityId = insertProgramForCharity(program);
          System.out.println(
              "Program Id generated from Inserting Program into Program table = "
                  + programForCharityId);
          var insertCharityProgramRelationshipRow =
              insertCharityProgramRelationship(charity.getCharityId(), programForCharityId);
          System.out.println(
              "Inserted Charity Program Relationship Table row for new Program (ROWS) = "
                  + insertCharityProgramRelationshipRow);
        } else {
          var updateCharityProgramRelationship =
              updateCharityProgramRelationship(
                  charity.getCharityId(), programIdInCharityProgramTable);
          System.out.println(
              "updateCharityProgramRelationship (ROWS): " + updateCharityProgramRelationship);
        }
      }
    }
  }

  private Integer findProgramByName(String programType) {
    assert getJdbcTemplate() != null;
    String sqlQueryPrograms = "SELECT * FROM program WHERE PROGRAM_DESCRIPTION = ?";
    return getJdbcTemplate()
        .query(
            sqlQueryPrograms,
            new Object[] {programType},
            resultSet -> resultSet.next() ? resultSet.getInt("PROGRAM_ID") : null);
  }

  private List<Program> findProgramsForCharity(Charity charity) {
    System.out.println("findProgramsForCharity " + charity.getCharityPrograms());

    List<Program> charityPrograms = new ArrayList<>();

    assert getJdbcTemplate() != null;
    String sqlQueryPrograms = "SELECT * FROM program WHERE PROGRAM_DESCRIPTION = ?";
    for (Program program : charity.getCharityPrograms()) {
      var rows = getJdbcTemplate().queryForList(sqlQueryPrograms, program.getProgramType());

      for (Map<String, Object> row : rows) {
        Program foundProgram = new Program();
        foundProgram.setProgramId((Integer) row.get("PROGRAM_ID"));
        foundProgram.setProgramType((String) row.get("PROGRAM_DESCRIPTION"));
        charityPrograms.add(foundProgram);
      }
    }

    return charityPrograms;
  }

  private void charityCategoryUpdate(Charity charity, int charityRowInserted) throws SQLException {

    if (charityRowInserted == 1) {

      System.out.println(
          "Finding Charity Category for Charity Category  " + charity.getCharityCategory());

      var category = findCategoryForCharity(charity);

      Integer categoryId;
      if (category == null) {
        categoryId = insertCategoryForCharity(charity);
      } else {
        categoryId = category.getCategoryId();
      }

      System.out.println(
          "Finding Charity Category Relationship for Charity Category  "
              + charity.getCharityCategory());

      Integer charityCategoryRelId = findCategoryCharityRelationshipRow(charity);

      System.out.println("Charity Category Relationship Category Id =  " + charityCategoryRelId);

      int charityCategoryRelationshipRowsChanged;

      if (charityCategoryRelId == null) {
        charityCategoryRelationshipRowsChanged =
            insertCharityCategoryRelationship(charity.getCharityId(), categoryId);
      } else {
        charityCategoryRelationshipRowsChanged =
            updateCharityCategoryRelationship(charity.getCharityId(), categoryId);
      }

      System.out.println("updatedCategory = " + categoryId);
      System.out.println(
          "charityCategoryRelationshipRowsChanged = " + charityCategoryRelationshipRowsChanged);
    }
  }

  private int updateCharityCategoryRelationship(int charityId, Integer categoryId)
      throws SQLException {

    if (charityId == 0) {
      throw new SQLException("charityId can not be 0");
    }

    String sqlUpdateCharityCategory =
        "UPDATE CHARITY_CATEGORY SET CATEGORY_ID = ? " + "WHERE CHARITY_ID = ?";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate()
        .update(
            sqlUpdateCharityCategory,
            preparedStatement -> {
              preparedStatement.setInt(1, categoryId);
              preparedStatement.setInt(2, charityId);
            });
  }

  private int updateCharityProgramRelationship(int charityId, Integer programId)
      throws SQLException {

    if (charityId == 0) {
      throw new SQLException("charityId can not be 0");
    }

    String sqlUpdateCharityCategory =
        "UPDATE CHARITY_PROGRAM SET PROGRAM_ID = ? " + "WHERE PROGRAM_ID = ?";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate()
        .update(
            sqlUpdateCharityCategory,
            preparedStatement -> {
              preparedStatement.setInt(1, programId);
              preparedStatement.setInt(2, charityId);
            });
  }

  private int insertCharityCategoryRelationship(int charityId, int categoryId) throws SQLException {

    if (categoryId == 0) {
      throw new SQLException("categoryId can not be 0");
    } else if (charityId == 0) {
      throw new SQLException("categoryId can not be 0");
    }

    String sqlInsertCharityCategory =
        "INSERT INTO CHARITY_CATEGORY(CHARITY_ID, CATEGORY_ID) VALUES(?,?)";

    assert getJdbcTemplate() != null;
    int charityCategoryRelationshipRowsInserted =
        getJdbcTemplate()
            .update(
                sqlInsertCharityCategory,
                preparedStatement -> {
                  preparedStatement.setInt(1, charityId);
                  preparedStatement.setInt(2, categoryId);
                });

    if (charityCategoryRelationshipRowsInserted == 1) {
      System.out.println("Successful Insert of CHARITY_CATEGORY ID" + charityId + " " + categoryId);
    }

    return charityCategoryRelationshipRowsInserted;
  }

  private int insertCharityProgramRelationship(int charityId, int programId) throws SQLException {

    if (programId == 0) {
      throw new SQLException("programId can not be 0");
    } else if (charityId == 0) {
      throw new SQLException("charityId can not be 0");
    }

    String sqlInsertCharityProgram =
        "INSERT INTO charity_program(CHARITY_ID, PROGRAM_ID) VALUES(?,?)";

    assert getJdbcTemplate() != null;
    int charityProgramRelationshipRowsInserted =
        getJdbcTemplate()
            .update(
                sqlInsertCharityProgram,
                preparedStatement -> {
                  preparedStatement.setInt(1, charityId);
                  preparedStatement.setInt(2, programId);
                });

    if (charityProgramRelationshipRowsInserted == 1) {
      System.out.println(
          "Successful Insert of CHARITY_PROGRAM CHARITY_ID"
              + charityId
              + " PROGRAM_ID "
              + programId);
    }

    return charityProgramRelationshipRowsInserted;
  }

  private int insertProgramForCharity(Program program) {

    System.out.println(
        "INSERT INTO program (PROGRAM_DESCRIPTION) VALUES (?) " + program.getProgramType());
    String sqlInsertProgram = "INSERT INTO PROGRAM (PROGRAM_DESCRIPTION) VALUES (?)";
    assert getJdbcTemplate() != null;
    KeyHolder programKeyHolder = new GeneratedKeyHolder();
    int charityProgramRowInserted =
        getJdbcTemplate()
            .update(
                connection -> {
                  PreparedStatement preparedStatement =
                      connection.prepareStatement(
                          sqlInsertProgram, Statement.RETURN_GENERATED_KEYS);
                  preparedStatement.setString(1, program.getProgramType());
                  return preparedStatement;
                },
                programKeyHolder);

    System.out.println("charityProgramRowInserted : " + charityProgramRowInserted);
    return Objects.requireNonNull(programKeyHolder.getKey()).intValue();
  }

  private int insertCategoryForCharity(Charity charity) {
    System.out.println(
        "INSERT INTO category (CATEGORY_NAME) VALUES (?) "
            + charity.getCharityCategory().getCategoryName());
    String sqlInsertCategory = "INSERT INTO category (CATEGORY_NAME) VALUES (?)";
    assert getJdbcTemplate() != null;
    KeyHolder categoryKeyHolder = new GeneratedKeyHolder();
    getJdbcTemplate()
        .update(
            sqlInsertCategory,
            (PreparedStatementSetter)
                preparedStatement ->
                    preparedStatement.setString(1, charity.getCharityCategory().getCategoryName()),
            categoryKeyHolder);
    return Objects.requireNonNull(categoryKeyHolder.getKey()).intValue();
  }

  private Category findCategoryForCharity(Charity charity) {
    System.out.println("findCategoryForCharity " + charity.getCharityCategory().getCategoryName());

    assert getJdbcTemplate() != null;

    BeanPropertyRowMapper<Category> charityRowMapper =
        BeanPropertyRowMapper.newInstance(Category.class);

    assert getJdbcTemplate() != null;

    String sqlQueryCategory = "SELECT * FROM category WHERE CATEGORY_NAME = ?";
    return getJdbcTemplate()
        .queryForObject(
            sqlQueryCategory, charityRowMapper, charity.getCharityCategory().getCategoryName());
  }

  private Integer findCategoryCharityRelationshipRow(Charity charity) {

    assert getJdbcTemplate() != null;

    System.out.println(
        "SELECT CATEGORY_ID FROM charity_category WHERE CHARITY_ID =   " + charity.getCharityId());
    String sqlQuery = "SELECT CATEGORY_ID FROM charity_category WHERE CHARITY_ID = ?";
    return getJdbcTemplate()
        .query(
            sqlQuery,
            new Object[] {charity.getCharityId()},
            resultSet -> resultSet.next() ? resultSet.getInt("CATEGORY_ID") : null);
  }

  private Integer findCharityProgramRelationshipRow(int programId, int charityId) {

    assert getJdbcTemplate() != null;

    System.out.println("SELECT * FROM charity_program WHERE PROGRAM_ID =   " + programId);
    String sqlQuery = "SELECT * FROM charity_program WHERE CHARITY_ID = ? AND PROGRAM_ID = ?";
    return getJdbcTemplate()
        .query(
            sqlQuery,
            new Object[] {charityId, programId},
            resultSet -> resultSet.next() ? resultSet.getInt("PROGRAM_ID") : null);
  }

  // Step 6 is to demo batch update
  @Override
  public int[] insertBatch(final List<Charity> charityList) {
    String sqlInsertCharity =
        "INSERT INTO charity"
            + "(CHARITY_TAX_ID, "
            + "CHARITY_NAME, "
            + "CHARITY_MISSION, "
            + "CHARITY_WEB_ADDRESS, "
            + "CHARITY_FACEBOOK_ADDRESS, "
            + "CHARITY_TWITTER_ADDRESS) "
            + "VALUES("
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "?, "
            + "?"
            + ")";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate()
        .batchUpdate(
            sqlInsertCharity,
            new BatchPreparedStatementSetter() {
              @Override
              public void setValues(PreparedStatement preparedStatement, int i)
                  throws SQLException {
                Charity charity = charityList.get(i);
                preparedStatement.setString(1, charity.getCharityTaxId());
                preparedStatement.setString(2, charity.getCharityName());
                preparedStatement.setString(3, charity.getCharityMission());
                preparedStatement.setString(4, charity.getCharityWebAddress());
                preparedStatement.setString(5, charity.getCharityFacebookAddress());
                preparedStatement.setString(6, charity.getCharityTwitterAddress());
              }

              @Override
              public int getBatchSize() {
                return charityList.size();
              }
            });
  }

  @Override
  public int update(Charity charity) {
    String sqlUpdate =
        "UPDATE charity SET CHARITY_NAME = ?,"
            + " CHARITY_MISSION = ?, "
            + " CHARITY_WEB_ADDRESS = ?, "
            + " CHARITY_FACEBOOK_ADDRESS = ?, "
            + " CHARITY_TWITTER_ADDRESS = ?"
            + " WHERE CHARITY_TAX_ID = ?";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate()
        .update(
            sqlUpdate,
            charity.getCharityName(),
            charity.getCharityMission(),
            charity.getCharityWebAddress(),
            charity.getCharityFacebookAddress(),
            charity.getCharityTwitterAddress(),
            charity.getCharityTaxId());
  }

  @Override
  public int delete(Charity charity) {
    Map<String, Object> args = new HashMap<>();
    args.put("charityTaxId", charity.getCharityTaxId());

    String sqlQuery = "DELETE FROM charity where CHARITY_TAX_ID = :charityTaxId";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate().update(sqlQuery, args);
  }

  @Override
  public Charity findByCharityTaxId(String charityTaxId) {
    String sqlQuery = "SELECT * FROM charity WHERE CHARITY_TAX_ID = ?";
    BeanPropertyRowMapper<Charity> charityRowMapper =
        BeanPropertyRowMapper.newInstance(Charity.class);

    assert getJdbcTemplate() != null;
    return getJdbcTemplate().queryForObject(sqlQuery, charityRowMapper, charityTaxId);
  }
}
