package com.nicordesigns.jdbc12.service;

import com.nicordesigns.jdbc12.model.Charity;

import java.sql.SQLException;

public interface CharityService {

  int createCharity(Charity charity) throws SQLException;

  Charity findByCharityTaxId(String s);

  int update(Charity charity) throws SQLException;

  int delete(Charity charity) throws SQLException;
}
