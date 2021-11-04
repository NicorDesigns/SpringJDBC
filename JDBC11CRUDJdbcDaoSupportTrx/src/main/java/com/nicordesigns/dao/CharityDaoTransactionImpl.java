package com.nicordesigns.dao;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import com.nicordesigns.model.Program;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

      var charityProgramsInDb = findProgramsListInDB(charity.getCharityPrograms());
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

  private List<Program> findProgramsListInDB(List<Program> charityProgramList) {
    System.out.println("findProgramsForCharity " + charityProgramList);

    List<Program> charityPrograms = new ArrayList<>();

    assert getJdbcTemplate() != null;
    String sqlQueryPrograms = "SELECT * FROM program WHERE PROGRAM_DESCRIPTION = ?";
    for (Program program : charityProgramList) {
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

  public void charityCategoryUpdate(Charity charity, int charityRowInserted) throws SQLException {

    if (charityRowInserted == 1) {

      System.out.println(
          "Finding Charity Category for Charity Category  " + charity.getCharityCategory());

      var category = findCategoryByName(charity.getCharityCategory().getCategoryName());

      Integer categoryId;
      if (category == null) {
        categoryId = insertCategoryForCharity(charity.getCharityCategory().getCategoryName());
      } else {
        categoryId = category.getCategoryId();
      }

      System.out.println(
          "Finding Charity Category Relationship for Charity Category  "
              + charity.getCharityCategory());

      Integer charityCategoryRelId =
          CharityCategorySupportDao.findCategoryIdInCharityRelationshipRow(
              Objects.requireNonNull(this.getJdbcTemplate()), charity.getCharityId());

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

  public int updateCharityCategoryRelationship(int charityId, Integer categoryId)
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

  public int insertCharityCategoryRelationship(int charityId, int categoryId) throws SQLException {

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

  public int insertCategoryForCharity(String categoryName) {
    String sqlInsertCategory = "INSERT INTO category (CATEGORY_NAME) VALUES (?)";
    assert getJdbcTemplate() != null;
    KeyHolder categoryKeyHolder = new GeneratedKeyHolder();
    getJdbcTemplate()
        .update(
            connection -> {
              PreparedStatement preparedStatement =
                  connection.prepareStatement(sqlInsertCategory, Statement.RETURN_GENERATED_KEYS);
              preparedStatement.setString(1, categoryName);
              return preparedStatement;
            },
            categoryKeyHolder);
    return Objects.requireNonNull(categoryKeyHolder.getKey()).intValue();
  }

  public Category findCategoryByName(String categoryName) {
    assert getJdbcTemplate() != null;

    BeanPropertyRowMapper<Category> charityRowMapper =
        BeanPropertyRowMapper.newInstance(Category.class);

    assert getJdbcTemplate() != null;

    String sqlQueryCategory = "SELECT * FROM category WHERE CATEGORY_NAME = ?";
    var category_id =
        getJdbcTemplate()
            .query(
                sqlQueryCategory,
                new Object[] {categoryName},
                resultSet -> resultSet.next() ? resultSet.getInt("CATEGORY_ID") : null);
    if (category_id != null) {
      return CharityCategorySupportDao.findCategoryById(getJdbcTemplate(), category_id);
    }
    return null;
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
  public int update(Charity charity) throws SQLException {
    String sqlUpdate =
        "UPDATE charity SET CHARITY_NAME = ?,"
            + " CHARITY_MISSION = ?, "
            + " CHARITY_WEB_ADDRESS = ?, "
            + " CHARITY_FACEBOOK_ADDRESS = ?, "
            + " CHARITY_TWITTER_ADDRESS = ?"
            + " WHERE CHARITY_TAX_ID = ?";

    assert getJdbcTemplate() != null;
    var charityRow =
        getJdbcTemplate()
            .update(
                sqlUpdate,
                charity.getCharityName(),
                charity.getCharityMission(),
                charity.getCharityWebAddress(),
                charity.getCharityFacebookAddress(),
                charity.getCharityTwitterAddress(),
                charity.getCharityTaxId());

    var charityCategoryUpdate = charity.getCharityCategory();
    // find Category by Charity Tax Id in DB
    var charityCategoryInDb = findCategoryForCharity(charity);

    // Update Category Name in DB with Charity Category Name
    if (charityCategoryInDb != null && !charityCategoryUpdate.equals(charityCategoryInDb)) {
      var doesCategoryExistInDB =
          findCategoryByName(charity.getCharityCategory().getCategoryName());
      int updateCharityCategoryRow;
      if (doesCategoryExistInDB != null) {
        updateCharityCategoryRow =
            updateCharityCategoryRelationship(
                charity.getCharityId(), doesCategoryExistInDB.getCategoryId());
        System.out.println("Update CharityCategory Row Count" + updateCharityCategoryRow);
      } else {
        var insertCharityCategoryId =
            insertCategoryForCharity(charity.getCharityCategory().getCategoryName());
        System.out.println("Insert CharityCategory Id : " + insertCharityCategoryId);
        updateCharityCategoryRow =
            updateCharityCategoryRelationship(charity.getCharityId(), insertCharityCategoryId);
        System.out.println(
            "Update CharityCategory Relationship (Row Total) : " + updateCharityCategoryRow);
      }
      System.out.println("Charity Category Relationship Row Updated");
    }

    var charityProgramsToUpdate = charity.getCharityPrograms();
    var charityProgramsInDB = findProgramsForCharityIdInDB(charity.getCharityId());
    System.out.println("charityProgramsInDB" + charityProgramsInDB);
    var charityPrograms = new ArrayList<>(charityProgramsInDB);

    for (Program program : charityProgramsToUpdate) {
      System.out.println("Program to Update" + program);
      if (charityProgramsInDB.contains(program)) {
        charityPrograms.remove(program);
      } else {
        var insertedProgramId = insertProgramForCharity(program);
        System.out.println("Inserted ProgramId : " + insertedProgramId);
        var insertProgramIdCharityIdRelation =
            insertCharityProgramRelationship(charity.getCharityId(), insertedProgramId);
        System.out.println(
            "Insert ProgramId CharityId Relation Row(s) : " + insertProgramIdCharityIdRelation);
      }
    }

    for (Program program : charityPrograms) {
      var rowsDeleted = deleteProgramForCharity(charity.getCharityId(), program.getProgramId());
      System.out.println(rowsDeleted);
    }

    return charityRow;
  }

  private int deleteProgramForCharity(int charityId, int programId) {

    String sqlDelete = "DELETE FROM charity_program where CHARITY_ID = ? AND PROGRAM_ID = ?";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate().update(sqlDelete, charityId, programId);

    // TODO Delete Orphan Programs in DB

  }

  @Override
  public int delete(Charity charity) {
    Map<String, Object> args = new HashMap<>();
    args.put("charityTaxId", charity.getCharityTaxId());

    String sqlQuery = "DELETE FROM charity where CHARITY_TAX_ID = :charityTaxId";

    assert getJdbcTemplate() != null;
    return getJdbcTemplate().update(sqlQuery, args);
    // TODO Delete Category if there are no other Charities left with the same Category
    // TODO Delete Program if there are no other Charities left with the same Program (for every
    // Program)
  }

  @Override
  public Charity findByCharityTaxId(String charityTaxId) {
    String sqlQuery = "SELECT * FROM charity WHERE CHARITY_TAX_ID = ?";
    BeanPropertyRowMapper<Charity> charityRowMapper =
        BeanPropertyRowMapper.newInstance(Charity.class);

    assert getJdbcTemplate() != null;

    var charity = getJdbcTemplate().queryForObject(sqlQuery, charityRowMapper, charityTaxId);
    var category = findCategoryForCharity(Objects.requireNonNull(charity));
    charity.setCharityCategory(category);
    var charityProgramsInDb = findProgramsForCharityIdInDB(charity.getCharityId());
    charity.setCharityPrograms(charityProgramsInDb);

    return charity;
  }

  private List<Program> findProgramsForCharityIdInDB(int charityId) {
    // Get List of Program Ids from charity_program table
    assert getJdbcTemplate() != null;
    List<Program> programList = new ArrayList<>();
    String sqlQueryProgramIdList = "SELECT * FROM charity_program WHERE CHARITY_ID = ?";
    var programIdRows = getJdbcTemplate().queryForList(sqlQueryProgramIdList, charityId);

    // Get List of Programs from program table
    for (Map<String, Object> programIdRow : programIdRows) {
      var programId = (Integer) programIdRow.get("PROGRAM_ID");
      String sqlQueryProgramList = "SELECT * FROM program WHERE PROGRAM_ID = ?";
      var programRows = getJdbcTemplate().queryForList(sqlQueryProgramList, programId);
      for (Map<String, Object> programRow : programRows) {
        Program foundProgram = new Program();
        foundProgram.setProgramId((Integer) programRow.get("PROGRAM_ID"));
        foundProgram.setProgramType((String) programRow.get("PROGRAM_DESCRIPTION"));
        programList.add(foundProgram);
      }
    }
    return programList;
  }

  public Category findCategoryForCharity(Charity charity) {
    Category category = null;
    var categoryId =
        CharityCategorySupportDao.findCategoryIdInCharityRelationshipRow(
            Objects.requireNonNull(this.getJdbcTemplate()), charity.getCharityId());
    if (categoryId != null) {
      category = CharityCategorySupportDao.findCategoryById(getJdbcTemplate(), categoryId);
    }
    return category;
  }
}
