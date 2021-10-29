package com.nicordesigns.service;

import com.nicordesigns.model.Charity;

import java.sql.SQLException;

public interface CharityService {

  int createCharity(Charity charity) throws SQLException;

  Charity findByCharityTaxId(String s);

  int update(Charity charity);

  int delete(Charity charity) throws SQLException;
}
