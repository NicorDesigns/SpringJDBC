package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;

import java.sql.SQLException;

public interface CharityDao {

  int insert(Charity charity) throws SQLException;

  int update(Charity charity);

  int delete(Charity charity) throws SQLException;

  Charity findByCharityTaxId(String taxId);
}
