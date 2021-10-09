package com.nicordesigns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBHelloServiceImpl implements DBHelloService {

  @Autowired private DBHelloService DBHelloService;

  @Override
  public String execute(String message) {

    return message + " Powered By Embedded Tomcat ! " + DBUtil.getCatalogName();
  }
}
