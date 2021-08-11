package com.nicordesigns;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DriverManager {

  private String userName;
  private String password;
  private String dbms;
  private String serverName;
  private String portNumber;
  private String dbName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDbms() {
    return dbms;
  }

  public void setDbms(String dbms) {
    this.dbms = dbms;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public String getPortNumber() {
    return portNumber;
  }

  public void setPortNumber(String portNumber) {
    this.portNumber = portNumber;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public Connection getConnection() {

    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.getUserName());
    connectionProps.put("password", this.getPassword());

    if (this.dbms.equals("mariadb")) {
      try {
        conn =
            java.sql.DriverManager.getConnection(
                "jdbc:"
                    + this.getDbms()
                    + "://"
                    + this.getServerName()
                    + ":"
                    + this.getPortNumber()
                    + "/"
                    + this.getDbName(),
                connectionProps);
      } catch (SQLException sqlException) {
        sqlException.printStackTrace();
      }
    }

    System.out.println("Connected to database");
    return conn;
  }
}
