package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;

import javax.sql.DataSource;
import java.sql.*;

public class CharityDaoImpl implements CharityDao {

  private DataSource dataSource;

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
      stmtInsertCharity.setString(4, charity.getCharityWebAddress().toString());
      stmtInsertCharity.setString(5, charity.getCharityFacebookAddress().toString());
      stmtInsertCharity.setString(6, charity.getCharityTwitterAddress().toString());

      int rowsAffected = stmtInsertCharity.executeUpdate();

      charitySet = stmtInsertCharity.getGeneratedKeys();

      if (charitySet.next()) {
        charityIDInt = charitySet.getInt(1);
      }

      return charityIDInt;

    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return charityIDInt;
  }

  @Override
  public void update(Charity charity) {}

  @Override
  public void delete(Charity charity) {}

  @Override
  public Charity findByCharityTaxId(String taxId) {
    return null;
  }
}
