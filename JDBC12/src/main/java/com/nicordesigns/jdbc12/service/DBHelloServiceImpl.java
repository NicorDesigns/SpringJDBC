package com.nicordesigns.jdbc12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBHelloServiceImpl implements DBHelloService {

  @Autowired DBUtil dbUtil;

  @Override
  public String execute(String message) {

    return message + " Powered By Embedded Tomcat ! " + dbUtil.getCatalogName();
  }
}
