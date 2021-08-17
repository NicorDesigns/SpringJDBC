package com.nicordesigns;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SpringDataSourceApp {

  private DataSource dataSource;

  public static void main(String[] args) {

    ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    SpringDataSourceApp springDataSourceApp = new SpringDataSourceApp();

    DataSource dataSource = context.getBean(DataSource.class);
    System.out.println(dataSource);

    springDataSourceApp.setDataSource(dataSource);

    String catalogName = springDataSourceApp.getCatalogName(dataSource);
    System.out.println("Catalog Name: " + catalogName);
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private String getCatalogName(DataSource dataSource) {
    String catalogName = null;
    try (Connection connection = dataSource.getConnection()) {
      catalogName = connection.getCatalog();
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return catalogName;
  }
}
