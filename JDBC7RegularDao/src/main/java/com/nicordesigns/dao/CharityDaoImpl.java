package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;

import javax.sql.DataSource;
import java.sql.*;

public class CharityDaoImpl implements CharityDao {

  private final DataSource dataSource;

  public CharityDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public DataSource getDataSource() {
    return dataSource;
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

    ResultSet charitySet;

    int charityIDInt = 0;

    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmtInsertCharity =
            connection.prepareStatement(sqlInsertCharity, Statement.RETURN_GENERATED_KEYS)) {

      stmtInsertCharity.setString(1, charity.getCharityTaxId());
      stmtInsertCharity.setString(2, charity.getCharityName());
      stmtInsertCharity.setString(3, charity.getCharityMission());
      stmtInsertCharity.setString(4, charity.getCharityWebAddress());
      stmtInsertCharity.setString(5, charity.getCharityFacebookAddress());
      stmtInsertCharity.setString(6, charity.getCharityTwitterAddress());

      int rowsAffected = stmtInsertCharity.executeUpdate();

      if (rowsAffected == 1) {
        System.out.println("Successful Insert");
        charitySet = stmtInsertCharity.getGeneratedKeys();
        if (charitySet.next()) {
          charityIDInt = charitySet.getInt(1);
        }
        System.out.println("Returning DB Generated Charity Id : " + charityIDInt);
        return charityIDInt;
      }

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    System.out.println("Failed to insert Charity - returning Charity Id : " + charityIDInt);
    return charityIDInt;
  }

  @Override
  public void update(Charity charity) {}

  @Override
  public void delete(Charity charity) {}

  @Override
  public Charity findByCharityTaxId(String taxId) {
    String sqlQuery = "SELECT * FROM charity WHERE CHARITY_TAX_ID = ?";
    Charity charity = null;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      preparedStatement.setString(1, taxId);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        charity =
            new Charity(
                rs.getString("CHARITY_TAX_ID"),
                rs.getString("CHARITY_NAME"),
                rs.getString("CHARITY_MISSION"),
                rs.getString("CHARITY_WEB_ADDRESS"),
                rs.getString("CHARITY_FACEBOOK_ADDRESS"),
                rs.getString("CHARITY_TWITTER_ADDRESS"));
      }
      rs.close();
      return charity;

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

    return charity;
  }
}
