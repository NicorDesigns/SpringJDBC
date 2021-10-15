package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;

public interface CharityDao {

  int insert(Charity charity);

  int update(Charity charity);

  int delete(Charity charity);

  Charity findByCharityTaxId(String taxId);
}
