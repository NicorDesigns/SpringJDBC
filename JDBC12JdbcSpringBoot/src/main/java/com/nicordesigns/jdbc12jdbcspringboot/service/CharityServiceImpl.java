package com.nicordesigns.jdbc12jdbcspringboot.service;

import com.nicordesigns.jdbc12jdbcspringboot.dao.CharityDao;
import com.nicordesigns.jdbc12jdbcspringboot.model.Charity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Component
public class CharityServiceImpl implements CharityService {

  @Autowired private CharityDao charityDao;

  @PostConstruct
  private void initialize() {
    setCharityDao(charityDao);
  }

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
}
