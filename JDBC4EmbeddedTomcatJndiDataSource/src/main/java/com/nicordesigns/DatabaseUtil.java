package com.nicordesigns;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

public class DatabaseUtil {

  private static Connection createConnection() {
    try {
      InitialContext ctx = new InitialContext();
      DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/charityDB");
      return ds.getConnection();
    } catch (Exception exc) {
      exc.printStackTrace();
      return null;
    }
  }

  public static String getCatalogName() {

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
}
