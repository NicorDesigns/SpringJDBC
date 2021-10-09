package com.nicordesigns.service;

import com.nicordesigns.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

public class DBUtil {
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

  private static Connection createConnection() {

    AnnotationConfigApplicationContext ctx =
        new AnnotationConfigApplicationContext(AppConfig.class);
    try {
      DataSource ds = ctx.getBean(DataSource.class);
      return ds.getConnection();
    } catch (Exception exc) {
      exc.printStackTrace();
      return null;
    }
  }
}
