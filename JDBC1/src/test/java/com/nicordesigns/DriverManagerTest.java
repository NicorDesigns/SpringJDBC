package com.nicordesigns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverManagerTest {

  private final DriverManager driverManager = new DriverManager();

  @BeforeEach
  void setUpDBConnection() {
    driverManager.setDbms("mariadb");
    driverManager.setUserName("root");
    driverManager.setPassword("secret");
    driverManager.setServerName("localhost");
    driverManager.setPortNumber("3306");
    driverManager.setDbName("charityDB");
  }

  @Test
  void getConnection() {

    try (Connection sqlConnection = driverManager.getConnection()) {
      System.out.println("SQL Connection Catalog: " + sqlConnection.getCatalog());
      assertEquals("charityDB", sqlConnection.getCatalog());
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
  }
}
