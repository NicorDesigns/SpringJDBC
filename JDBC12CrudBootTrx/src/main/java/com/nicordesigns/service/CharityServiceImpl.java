package com.nicordesigns.service;

import com.nicordesigns.dao.CharityDao;
import com.nicordesigns.model.Charity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Component
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
  @Transactional
  public Charity findByCharityTaxId(String taxId) {
    return charityDao.findByCharityTaxId(taxId);
  }

  @Override
  @Transactional
  public int update(Charity charity) throws SQLException {
    return charityDao.update(charity);
  }

  @Override
  @Transactional
  public int delete(Charity charity) throws SQLException {
    return charityDao.delete(charity);
  }

  @Override
  @Transactional
  public int[] insertBatch(List<Charity> charityList) {
    return charityDao.insertBatch(charityList);
  }

  @Override
  public List<Charity> findAllCharities() {
    return charityDao.findAllCharities();
  }
}
