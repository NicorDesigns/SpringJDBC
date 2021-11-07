package com.nicordesigns.service;

import com.nicordesigns.model.Charity;

import java.sql.SQLException;
import java.util.List;

public interface CharityService {

  int createCharity(Charity charity) throws SQLException;

  Charity findByCharityTaxId(String s);

  int update(Charity charity) throws SQLException;

  int delete(Charity charity) throws SQLException;

  int[] insertBatch(List<Charity> charityList);

  List<Charity> findAllCharities();
}
