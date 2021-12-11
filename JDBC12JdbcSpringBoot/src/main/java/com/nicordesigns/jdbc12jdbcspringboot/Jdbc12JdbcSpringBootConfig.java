package com.nicordesigns.jdbc12jdbcspringboot;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class Jdbc12JdbcSpringBootConfig {

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
