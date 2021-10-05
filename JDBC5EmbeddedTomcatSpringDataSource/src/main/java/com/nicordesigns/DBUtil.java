package com.nicordesigns;

import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

public class DBUtil {
  public static String getCatalogName(WebApplicationContext webApplicationContext) {
    Connection conn = createConnection(webApplicationContext);
    String catalogName;
    try {
      catalogName = Objects.requireNonNull(conn).getCatalog();
      conn.close();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return catalogName;
  }

  private static Connection createConnection(WebApplicationContext webApplicationContext) {

    try {
      DataSource ds = (DataSource) webApplicationContext.getBean("dataSource");
      return ds.getConnection();
    } catch (Exception exc) {
      exc.printStackTrace();
      return null;
    }
  }
}
