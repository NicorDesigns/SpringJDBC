package com.nicordesigns.jdbc12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

@Component
public class DBUtil {

  @Autowired DataSource dataSource;

  public String getCatalogName() {

    Connection conn = createConnection();
    String catalogName;
    try {
      catalogName = Objects.requireNonNull(conn).getCatalog();
      conn.close();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return catalogName;
  }

  private Connection createConnection() {

    try {
      return dataSource.getConnection();
    } catch (Exception exc) {
      exc.printStackTrace();
      return null;
    }
  }
}
