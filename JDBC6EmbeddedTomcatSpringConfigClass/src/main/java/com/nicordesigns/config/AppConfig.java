package com.nicordesigns.config;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = {"com.nicordesigns.service"})
public class AppConfig {

  @Bean
  public DataSource dataSource() throws SQLException {

    MariaDbDataSource dataSource = new MariaDbDataSource();
    dataSource.setServerName("localhost");
    dataSource.setPortNumber(Integer.parseInt("3306"));
    dataSource.setUser("root");
    dataSource.setPassword("secret");
    dataSource.setDatabaseName("charityDB");
    dataSource.setUrl("jdbc:mariadb://localhost:3306/charityDB");
    return dataSource;
  }
}
