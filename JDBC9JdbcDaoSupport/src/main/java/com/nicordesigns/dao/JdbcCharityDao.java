package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcCharityDao extends JdbcDaoSupport implements CharityDao {

  @Override
  public int insert(Charity charity) {

    // Step 5 will demo update Batch Inserting of Charities
    // Step 4 of Insert with Template Tutorial will use PreparedStatementSetter
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
        .update(
            sqlInsertCharity,
            new PreparedStatementSetter() {
              @Override
              public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, charity.getCharityTaxId());
                preparedStatement.setString(2, charity.getCharityName());
                preparedStatement.setString(3, charity.getCharityMission());
                preparedStatement.setString(4, charity.getCharityWebAddress());
                preparedStatement.setString(5, charity.getCharityFacebookAddress());
                preparedStatement.setString(6, charity.getCharityTwitterAddress());
              }
            });

    // Step 3 of Insert with Template Tutorial
    //    return getJdbcTemplate().update(
    //            new PreparedStatementCreator() {
    //              @Override
    //              public PreparedStatement createPreparedStatement(Connection connection) throws
    // SQLException {
    //                String sqlInsertCharity =
    //                        "INSERT INTO charity"
    //                                + "(CHARITY_TAX_ID, "
    //                                + "CHARITY_NAME, "
    //                                + "CHARITY_MISSION, "
    //                                + "CHARITY_WEB_ADDRESS, "
    //                                + "CHARITY_FACEBOOK_ADDRESS, "
    //                                + "CHARITY_TWITTER_ADDRESS) "
    //                                + "VALUES("
    //                                + "?, "
    //                                + "?, "
    //                                + "?, "
    //                                + "?, "
    //                                + "?, "
    //                                + "?"
    //                                + ")";
    //
    //                PreparedStatement preparedStatement =
    // connection.prepareStatement(sqlInsertCharity);
    //                preparedStatement.setString(1, charity.getCharityTaxId());
    //                preparedStatement.setString(2, charity.getCharityName());
    //                preparedStatement.setString(3, charity.getCharityMission());
    //                preparedStatement.setString(4, charity.getCharityWebAddress());
    //                preparedStatement.setString(5, charity.getCharityFacebookAddress());
    //                preparedStatement.setString(6, charity.getCharityTwitterAddress());
    //
    //
    //                return preparedStatement;
    //
    //            }}
    //    );
    // Step 2 of Insert Tutorial
    // return getJdbcTemplate()
    //    .update(new InsertCharityStatementCreator(charity));

    //   Step 1 of Insert Tutorial
    //    String sqlInsertCharity =
    //        "INSERT INTO charity"
    //            + "(CHARITY_TAX_ID, "
    //            + "CHARITY_NAME, "
    //            + "CHARITY_MISSION, "
    //            + "CHARITY_WEB_ADDRESS, "
    //            + "CHARITY_FACEBOOK_ADDRESS, "
    //            + "CHARITY_TWITTER_ADDRESS) "
    //            + "VALUES("
    //            + "?, "
    //            + "?, "
    //            + "?, "
    //            + "?, "
    //            + "?, "
    //            + "?"
    //            + ")";
    //
    //    return getJdbcTemplate()
    //        .update(
    //            sqlInsertCharity,
    //            charity.getCharityTaxId(),
    //            charity.getCharityName(),
    //            charity.getCharityMission(),
    //            charity.getCharityWebAddress(),
    //            charity.getCharityFacebookAddress(),
    //            charity.getCharityTwitterAddress());
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
