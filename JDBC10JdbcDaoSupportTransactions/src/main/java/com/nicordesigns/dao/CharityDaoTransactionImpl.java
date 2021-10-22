package com.nicordesigns.dao;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CharityDaoTransactionImpl extends JdbcDaoSupport implements CharityDao {

  public CharityDaoTransactionImpl() {}

  @Override
  @Transactional
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
                      connection.prepareStatement(sqlInsertCharity);
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
    int x = 0 / 0;
    if (charityRowInserted == 1) {
      Category category = findCategoryForCharity(charity);
      var updatedCategory = updateCharityCategoryRow(charity, category);
      System.out.println("updatedCategory = " + updatedCategory);
    }
    return charityRowInserted;
  }

  private Category updateCharityCategoryRow(Charity charity, Category category)
      throws SQLException {

    int charityCategoryRows;

    if (category == null) {
      System.out.println("Charity Category not found in DB " + charity.getCharityCategory());
      int categoryId = insertCategoryForCharity(charity);
      if (categoryId != 0) {
        category = new Category(categoryId, charity.getCharityCategory().getCategoryName());
        category.setCategoryId(categoryId);
        charityCategoryRows = insertCharityCategoryRelationship(charity.getCharityId(), categoryId);
        System.out.println(
            "Charity Category relationship table rows inserted in DB: " + charityCategoryRows);
      }
    } else {
      System.out.println("Charity Category found in DB: " + category);
      // The Category already exists - find it and update the relationship table
      int charityId = charity.getCharityId();
      int categoryId = category.getCategoryId();

      // Update Charity Category Relationship
      charityCategoryRows = updateCharityCategoryRelationship(charityId, categoryId);

      System.out.println(
          "Charity Category relationship table rows inserted in DB: " + charityCategoryRows);
    }
    return category;
  }

  private int updateCharityCategoryRelationship(int charityId, int categoryId) throws SQLException {

    if (charityId == 0 | categoryId == 0) {
      throw new SQLException("charityId or categoryId can not be 0");
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

  private int insertCategoryForCharity(Charity charity) {

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

    String sqlQuery = "SELECT CATEGORY_ID FROM charity_category WHERE CHARITY_ID = ?";
    assert getJdbcTemplate() != null;
    var categoryId =
        getJdbcTemplate()
            .queryForObject(sqlQuery, new Object[] {charity.getCharityId()}, Integer.class);
    System.out.println(charity.getCharityId() + " , " + categoryId);
    Category findCategory = null;
    String sqlQuery2 = "SELECT * FROM category WHERE CATEGORY_ID = ?";
    assert categoryId != null;
    if (categoryId == 1) {
      findCategory =
          getJdbcTemplate().queryForObject(sqlQuery2, new Object[] {categoryId}, Category.class);
    }

    return findCategory;
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
