package com.nicordesigns.jdbc12jdbcspringboot.dao;

import com.nicordesigns.jdbc12jdbcspringboot.model.Charity;

import java.sql.SQLException;
import java.util.List;

public interface CharityDao {

  int insert(Charity charity) throws SQLException;

  int[] insertBatch(List<Charity> charityList);

  int update(Charity charity) throws SQLException;

  int delete(Charity charity) throws SQLException;

  Charity findByCharityTaxId(String taxId);
}
