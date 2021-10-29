package com.nicordesigns.service;

import com.nicordesigns.dao.CharityDao;
import com.nicordesigns.model.Charity;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

public class CharityServiceImpl implements CharityService {

  private CharityDao charityDao;

  public void setCharityDao(CharityDao charityDao) {
    this.charityDao = charityDao;
  }

  @Override
  @Transactional
  public int createCharity(Charity charity) throws SQLException {
    return charityDao.insert(charity);
  }

  @Override
  public Charity findByCharityTaxId(String taxId) {
    return charityDao.findByCharityTaxId(taxId);
  }

  @Override
  @Transactional
  public int update(Charity charity) {
    return charityDao.update(charity);
  }

  @Override
  @Transactional
  public int delete(Charity charity) throws SQLException {
    return charityDao.delete(charity);
  }
}
