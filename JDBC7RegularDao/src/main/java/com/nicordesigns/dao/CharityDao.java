package com.nicordesigns.dao;

import com.nicordesigns.model.Charity;

public interface CharityDao {

  int insert(Charity charity);

  void update(Charity charity);

  void delete(Charity charity);

  Charity findByCharityTaxId(String taxId);
}
