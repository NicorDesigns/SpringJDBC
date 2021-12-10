package com.nicordesigns.jdbc12.dao;

import com.nicordesigns.jdbc12.model.Charity;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCharityStatementCreator implements PreparedStatementCreator {
  private final Charity charity;

  public InsertCharityStatementCreator(Charity charity) {
    this.charity = charity;
  }

  @Override
  public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
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

    PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCharity);
    preparedStatement.setString(1, charity.getCharityTaxId());
    preparedStatement.setString(2, charity.getCharityName());
    preparedStatement.setString(3, charity.getCharityMission());
    preparedStatement.setString(4, charity.getCharityWebAddress());
    preparedStatement.setString(5, charity.getCharityFacebookAddress());
    preparedStatement.setString(6, charity.getCharityTwitterAddress());

    return preparedStatement;
  }
}
