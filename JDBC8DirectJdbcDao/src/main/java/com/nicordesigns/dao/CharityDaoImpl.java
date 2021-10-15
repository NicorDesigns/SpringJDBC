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
  public int insert(Charity charity) {

    String sqlInsertCharity =
        "INSERT INTO charity"
            + "(CHARITY_TAX_ID, "
            + "CHARITY_NAME, "
            + "CHARITY_MISSION, "
            + "CHARITY_WEB_ADDRESS, "
            + "CHARITY_FACEBOOK_ADDRESS, "
            + "CHARITY_TWITTER_ADDRESS) "
            + "VALUES(?,?,?,?,?,?)";

    ResultSet charityGeneratedKeySet;

    int insertedCharityId = 0;

    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false); // Use Transactional Commit capability of MariaDB

      try (PreparedStatement preparedStatementInsertCharity =
          connection.prepareStatement(sqlInsertCharity, Statement.RETURN_GENERATED_KEYS)) {

        preparedStatementInsertCharity.setString(1, charity.getCharityTaxId());
        preparedStatementInsertCharity.setString(2, charity.getCharityName());
        preparedStatementInsertCharity.setString(3, charity.getCharityMission());
        preparedStatementInsertCharity.setString(4, charity.getCharityWebAddress());
        preparedStatementInsertCharity.setString(5, charity.getCharityFacebookAddress());
        preparedStatementInsertCharity.setString(6, charity.getCharityTwitterAddress());

        int rowsAffected = preparedStatementInsertCharity.executeUpdate();

        if (rowsAffected == 1) {
          System.out.println("Successful Charity Insert");
          charityGeneratedKeySet = preparedStatementInsertCharity.getGeneratedKeys();
          if (charityGeneratedKeySet.next()) {
            insertedCharityId = charityGeneratedKeySet.getInt(1);
          }

          Category category = getCharityCategory(charity, connection);

          int charityCategoryRowsInserted = 0;

          if (category == null) {
            int categoryId = insertCategoryForNewCharity(charity, connection);
            if (categoryId != 0) {
              charityCategoryRowsInserted =
                  getCharityCategoryRowsInserted(connection, charity.getCharityId(), categoryId);
            }
          } else {
            // The Category already exists - find it and update the relationship table
            int charityId = charity.getCharityId();
            int categoryId = category.getCategoryId();

            charityCategoryRowsInserted =
                getCharityCategoryRowsInserted(connection, charityId, categoryId);
          }

          if (charityCategoryRowsInserted > 0) {
            System.out.println(
                "Successful Insert of Charity, Category and CharityCategory Relationship table");
          }
        }
      }

      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    System.out.println("On success > 0 - Returning DB Generated Charity Id : " + insertedCharityId);
    return insertedCharityId;
  }

  private int getCharityCategoryRowsInserted(Connection connection, int charityId, int categoryId)
      throws SQLException {
    int charityCategoryRowsInserted;
    String sqlInsertCharityCategory =
        "INSERT INTO CHARITY_CATEGORY(CHARITY_ID, CATEGORY_ID) VALUES(?,?)";

    int rowsInserted;
    try (PreparedStatement stmtInsertCharityCategoryRelationship =
        connection.prepareStatement(sqlInsertCharityCategory)) {

      stmtInsertCharityCategoryRelationship.setInt(1, charityId);
      stmtInsertCharityCategoryRelationship.setInt(2, categoryId);

      rowsInserted = stmtInsertCharityCategoryRelationship.executeUpdate();

      if (rowsInserted == 1) {
        System.out.println(
            "Successful Insert of CHARITY_CATEGORY ID" + charityId + " " + categoryId);
      }

    } catch (SQLException sqlException) {
      connection.rollback();
      connection.setAutoCommit(true);
      throw sqlException;
    }
    charityCategoryRowsInserted = rowsInserted;
    return charityCategoryRowsInserted;
  }

  private int insertCategoryForNewCharity(Charity charity, Connection connection)
      throws SQLException {
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

  private Category getCharityCategory(Charity charity, Connection connection) throws SQLException {
    String sqlQuery = "SELECT * FROM category WHERE CATEGORY_NAME = ?";
    Category findCategory = null;

    try (PreparedStatement preparedStatementFindCategory = connection.prepareStatement(sqlQuery)) {

      preparedStatementFindCategory.setString(1, charity.getCharityCategory().getCategoryName());
      ResultSet rs = preparedStatementFindCategory.executeQuery();

      if (rs.next()) {
        findCategory = new Category(rs.getString("CATEGORY_NAME"));
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
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return rowUpdateCount;
  }

  @Override
  public int delete(Charity charity) {
    String sqlQuery = "DELETE FROM charity where CHARITY_TAX_ID = ?";
    int rowUpdateCount = 0;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      preparedStatement.setString(1, charity.getCharityTaxId());
      rowUpdateCount = preparedStatement.executeUpdate();
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return rowUpdateCount;
  }

  @Override
  public Charity findByCharityTaxId(String taxId) {
    String sqlQuery = "SELECT * FROM charity WHERE CHARITY_TAX_ID = ?";
    Charity charity = null;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

      preparedStatement.setString(1, taxId);

      try (ResultSet rs = preparedStatement.executeQuery()) {

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
        }
      }

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return charity;
  }
}
