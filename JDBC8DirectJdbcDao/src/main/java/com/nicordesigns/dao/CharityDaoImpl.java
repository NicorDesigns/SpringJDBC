package com.nicordesigns.dao;

import com.nicordesigns.model.Category;
import com.nicordesigns.model.Charity;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class CharityDaoImpl implements CharityDao {

  private DataSource dataSource;

  public CharityDaoImpl() {}

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(MariaDbPoolDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Charity insert(Charity charity) throws SQLException {

    ResultSet charityGeneratedKeySet;

    int insertedCharityId = 0;

    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false); // Use Transactional Commit capability of MariaDB

      String sqlInsertCharity =
          "INSERT INTO charity"
              + "(CHARITY_TAX_ID, "
              + "CHARITY_NAME, "
              + "CHARITY_MISSION, "
              + "CHARITY_WEB_ADDRESS, "
              + "CHARITY_FACEBOOK_ADDRESS, "
              + "CHARITY_TWITTER_ADDRESS) "
              + "VALUES(?,?,?,?,?,?)";

      try (PreparedStatement preparedStatementInsertCharity =
          connection.prepareStatement(sqlInsertCharity, Statement.RETURN_GENERATED_KEYS)) {

        preparedStatementInsertCharity.setString(1, charity.getCharityTaxId());
        preparedStatementInsertCharity.setString(2, charity.getCharityName());
        preparedStatementInsertCharity.setString(3, charity.getCharityMission());
        preparedStatementInsertCharity.setString(4, charity.getCharityWebAddress());
        preparedStatementInsertCharity.setString(5, charity.getCharityFacebookAddress());
        preparedStatementInsertCharity.setString(6, charity.getCharityTwitterAddress());

        int charityRowsInserted = preparedStatementInsertCharity.executeUpdate();

        if (charityRowsInserted == 1) {
          System.out.println("Charity Row Inserted");
          charityGeneratedKeySet = preparedStatementInsertCharity.getGeneratedKeys();

          if (charityGeneratedKeySet.next()) {
            insertedCharityId = charityGeneratedKeySet.getInt(1);
            System.out.println("DB Generated Charity Id: " + insertedCharityId);
            charity.setCharityId(insertedCharityId);
          }

          Category category = findCategoryForCharity(charity, connection);

          Category charityCategory = updateCharityCategoryRow(charity, connection, category);

          if (charityCategory != null) {
            System.out.println(
                "Successful Insert or Update of Charity, Category and CharityCategory Relationship table");
            charity.setCharityCategory(charityCategory);
          }

          connection.commit();
          connection.setAutoCommit(true);
          return charity;
        }

      } catch (SQLException sqlException) {
        connection.rollback();
        connection.setAutoCommit(true);
        throw sqlException;
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      throw sqlException;
    }

    System.out.println("On success > 0 - Returning DB Generated Charity Id : " + insertedCharityId);
    return charity;
  }

  private Category updateCharityCategoryRow(
      Charity charity, Connection connection, Category category) throws SQLException {

    int charityCategoryRows;

    if (category == null) {
      System.out.println("Charity Category not found in DB " + charity.getCharityCategory());
      int categoryId = insertCategoryForCharity(charity, connection);
      if (categoryId != 0) {
        category = new Category(categoryId, charity.getCharityCategory().getCategoryName());
        category.setCategoryId(categoryId);
        charityCategoryRows =
            insertCharityCategoryRelationship(connection, charity.getCharityId(), categoryId);
        System.out.println(
            "Charity Category relationship table rows inserted in DB: " + charityCategoryRows);
      }
    } else {
      System.out.println("Charity Category found in DB: " + category);
      // The Category already exists - find it and update the relationship table
      int charityId = charity.getCharityId();
      int categoryId = category.getCategoryId();

      // Update Charity Category Relationship
      charityCategoryRows = updateCharityCategoryRelationship(connection, charityId, categoryId);

      System.out.println(
          "Charity Category relationship table rows inserted in DB: " + charityCategoryRows);
    }
    return category;
  }

  private int insertCharityCategoryRelationship(
      Connection connection, int charityId, int categoryId) throws SQLException {

    if (categoryId == 0) {
      throw new SQLException("categoryId can not be 0");
    } else if (charityId == 0) {
      throw new SQLException("categoryId can not be 0");
    }

    String sqlInsertCharityCategory =
        "INSERT INTO CHARITY_CATEGORY(CHARITY_ID, CATEGORY_ID) VALUES(?,?)";

    int charityCategoryRelationshipRowsInserted;
    try (PreparedStatement stmtInsertCharityCategoryRelationship =
        connection.prepareStatement(sqlInsertCharityCategory)) {

      stmtInsertCharityCategoryRelationship.setInt(1, charityId);
      stmtInsertCharityCategoryRelationship.setInt(2, categoryId);

      charityCategoryRelationshipRowsInserted =
          stmtInsertCharityCategoryRelationship.executeUpdate();

      if (charityCategoryRelationshipRowsInserted == 1) {
        System.out.println(
            "Successful Insert of CHARITY_CATEGORY ID" + charityId + " " + categoryId);
      }

    } catch (SQLException sqlException) {
      connection.rollback();
      connection.setAutoCommit(true);
      throw sqlException;
    }
    return charityCategoryRelationshipRowsInserted;
  }

  private int updateCharityCategoryRelationship(
      Connection connection, int charityId, int categoryId) throws SQLException {

    if (charityId == 0 | categoryId == 0) {
      throw new SQLException("charityId or categoryId can not be 0");
    }

    String sqlUpdateCharityCategory =
        "UPDATE CHARITY_CATEGORY SET CATEGORY_ID = ? " + "WHERE CHARITY_ID = ?";

    int charityCategoryRelationshipRowsUpdated;

    try (PreparedStatement preparedStatementUpdateCharityCategoryRelationship =
        connection.prepareStatement(sqlUpdateCharityCategory)) {

      preparedStatementUpdateCharityCategoryRelationship.setInt(1, categoryId);
      preparedStatementUpdateCharityCategoryRelationship.setInt(2, charityId);

      charityCategoryRelationshipRowsUpdated =
          preparedStatementUpdateCharityCategoryRelationship.executeUpdate();

      if (charityCategoryRelationshipRowsUpdated == 1) {
        System.out.println(
            "Successful Update of CHARITY_CATEGORY ID" + charityId + " " + categoryId);
      }

    } catch (SQLException sqlException) {
      connection.rollback();
      connection.setAutoCommit(true);
      throw sqlException;
    }
    return charityCategoryRelationshipRowsUpdated;
  }

  private int insertCategoryForCharity(Charity charity, Connection connection) throws SQLException {
    String sqlInsertCategory = "INSERT INTO category (CATEGORY_NAME) VALUES (?)";

    int insertedDBGeneratedCategoryId = 0;

    try (PreparedStatement preparedStatementInsertCategory =
        connection.prepareStatement(sqlInsertCategory, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatementInsertCategory.setString(1, charity.getCharityCategory().getCategoryName());

      int categoryRowsInserted = preparedStatementInsertCategory.executeUpdate();

      if (categoryRowsInserted == 1) {
        ResultSet categorySet;
        System.out.println("Successful Category Insert");
        categorySet = preparedStatementInsertCategory.getGeneratedKeys();
        if (categorySet.next()) {
          insertedDBGeneratedCategoryId = categorySet.getInt(1);
        }
      }
    } catch (SQLException sqlException) {
      connection.rollback();
      connection.setAutoCommit(true);
      throw sqlException;
    }
    return insertedDBGeneratedCategoryId;
  }

  private Category findCategoryForCharity(Charity charity, Connection connection)
      throws SQLException {

    String sqlQuery = "SELECT * FROM charity_category WHERE CHARITY_ID = ?";

    Category findCategory = null;

    try (PreparedStatement preparedStatementFindCategory = connection.prepareStatement(sqlQuery)) {

      System.out.println("findCategoryForCharity :" + charity.getCharityTaxId());
      preparedStatementFindCategory.setInt(1, charity.getCharityId());
      ResultSet rs = preparedStatementFindCategory.executeQuery();

      if (rs.next()) {
        System.out.println(rs.getInt("CHARITY_ID") + " , " + rs.getInt("CATEGORY_ID"));
        String sqlQuery2 = "SELECT * FROM category WHERE CATEGORY_ID = ?";

        try (PreparedStatement preparedStatementFindCategoryById =
            connection.prepareStatement(sqlQuery2)) {
          preparedStatementFindCategoryById.setInt(1, rs.getInt("CATEGORY_ID"));
          ResultSet rs2 = preparedStatementFindCategoryById.executeQuery();
          if (rs2.next()) {
            System.out.println(rs.getInt("CHARITY_ID") + " , " + rs2.getString("CATEGORY_NAME"));
            findCategory = new Category(rs.getInt("CATEGORY_ID"), rs2.getString("CATEGORY_NAME"));
          }
        }
      }
    } catch (SQLException sqlException) {
      connection.rollback();
      connection.setAutoCommit(true);
      throw sqlException;
    }

    return findCategory;
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
    int rowUpdateCount = 0;

    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {

      preparedStatement.setString(1, charity.getCharityName());
      preparedStatement.setString(2, charity.getCharityMission());
      preparedStatement.setString(3, charity.getCharityWebAddress());
      preparedStatement.setString(4, charity.getCharityFacebookAddress());
      preparedStatement.setString(5, charity.getCharityTwitterAddress());
      preparedStatement.setString(6, charity.getCharityTaxId());

      rowUpdateCount = preparedStatement.executeUpdate();
      int charityCategoryRelationshipRowsInserted = 0;
      if (rowUpdateCount > 0) {
        // Update the Charity Category
        var updateCategory = charity.getCharityCategory();
        System.out.println("Category to be updated: " + updateCategory);
        var findCharityCategoryDB = findCategoryForCharity(charity, connection);
        System.out.println("Category retrieved from MariaDB: " + findCharityCategoryDB);

        if (findCharityCategoryDB != null) {
          // Line 290
          if (!updateCategory.equals(findCharityCategoryDB)) {

            if (updateCategory.getCategoryId() != 0) {
              charityCategoryRelationshipRowsInserted =
                  updateCharityCategoryRelationship(
                      connection, updateCategory.getCategoryId(), charity.getCharityId());
            } else {
              // Find Category by name
              var findCategory = findCategoryByName(updateCategory.getCategoryName(), connection);

              // Update Charity Category RelationShip Table
              charityCategoryRelationshipRowsInserted =
                  updateCharityCategoryRelationship(
                      connection, charity.getCharityId(), findCategory.getCategoryId());
            }
          }
        } else {
          // Insert Charity Category into Category table
          var categoryId = insertCategoryForCharity(charity, connection);
          // Insert Charity Category Relationship
          charityCategoryRelationshipRowsInserted =
              insertCharityCategoryRelationship(connection, charity.getCharityId(), categoryId);
        }
        System.out.println(
            "charityCategoryRelationshipRowsInserted = " + charityCategoryRelationshipRowsInserted);
      }

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return rowUpdateCount;
  }

  @Override
  public int delete(Charity charity) throws SQLException {
    // TODO Delete Charity Category relationship if it exists and the Category if there are now
    // other Charities that relates to it
    String sqlQuery = "DELETE FROM charity where CHARITY_TAX_ID = ?";
    int rowDeleteCount;
    try (Connection connection = dataSource.getConnection()) {

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        preparedStatement.setString(1, charity.getCharityTaxId());
        rowDeleteCount = preparedStatement.executeUpdate();

        if (rowDeleteCount == 1) {
          Category categoryFromName =
              findCategoryByName(charity.getCharityCategory().getCategoryName(), connection);
          int categoryCharityRelationshipRowsDeleted =
              deleteCategoryCharityRelationship(
                  charity.getCharityId(), categoryFromName.getCategoryId(), connection);
          System.out.println(
              "Charity Category Relationship Rows Deleted = "
                  + categoryCharityRelationshipRowsDeleted);
        }
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      throw sqlException;
    }

    return rowDeleteCount;
  }

  private int deleteCategory(Category charityCategory, Connection connection) throws SQLException {
    String sqlQuery = "DELETE FROM category where CATEGORY_ID = ?";
    int rowDeleteCount;

    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      preparedStatement.setInt(1, charityCategory.getCategoryId());
      rowDeleteCount = preparedStatement.executeUpdate();

      if (rowDeleteCount == 1) {
        System.out.println(
            "Category Row deleted ["
                + charityCategory.getCategoryId()
                + ", "
                + charityCategory.getCategoryName()
                + " ]");
      }

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      throw sqlException;
    }

    return rowDeleteCount;
  }

  public int deleteCategoryCharityRelationship(int charityId, int categoryId, Connection connection)
      throws SQLException {

    String sqlQuery = "DELETE FROM charity_category where CHARITY_ID = ? AND CATEGORY_ID = ?";
    int rowDeleteCount;

    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      preparedStatement.setInt(1, charityId);
      preparedStatement.setInt(2, categoryId);
      rowDeleteCount = preparedStatement.executeUpdate();

      if (rowDeleteCount == 1) {
        System.out.println(
            "Charity Category Row deleted [" + charityId + " , " + categoryId + " ]");
      } else {
        System.out.println(
            "Charity Category Row not deleted [" + charityId + " , " + categoryId + " ]");
      }

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
      throw sqlException;
    }

    return rowDeleteCount;
  }

  @Override
  public Charity findByCharityTaxId(String taxId) {
    String sqlQuery = "SELECT * FROM charity WHERE CHARITY_TAX_ID = ?";
    Charity charity = null;

    try (Connection connection = dataSource.getConnection()) {

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        preparedStatement.setString(1, taxId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
          charity =
              new Charity(
                  rs.getInt("CHARITY_ID"),
                  rs.getString("CHARITY_TAX_ID"),
                  rs.getString("CHARITY_NAME"),
                  rs.getString("CHARITY_MISSION"),
                  rs.getString("CHARITY_WEB_ADDRESS"),
                  rs.getString("CHARITY_FACEBOOK_ADDRESS"),
                  rs.getString("CHARITY_TWITTER_ADDRESS"));

          Category charityCategory;
          charityCategory = findCategoryForCharity(charity, connection);
          charity.setCharityCategory(charityCategory);
        }
      }

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return charity;
  }

  public Category findCategoryByName(String categoryName, Connection connection) {
    String sqlQuery = "SELECT * FROM category WHERE CATEGORY_NAME = ?";
    Category category = null;

    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      preparedStatement.setString(1, categoryName);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        category = new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"));
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return category;
  }
}
