package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;

import java.sql.SQLException;

public interface CharityDao {

  /**
   * Insert a new Charity object into the database.
   *
   * @param charity Model Object
   * @return Charity Model Object
   * @throws SQLException after first logging it.
   */
  Charity insert(Charity charity) throws SQLException;

  /**
   * @param charity
   * @return
   */
  int update(Charity charity);

  /**
   * @param charity
   * @return
   * @throws SQLException
   */
  int delete(Charity charity) throws SQLException;

  /**
   * @param taxId
   * @return
   */
  Charity findByCharityTaxId(String taxId);
}
