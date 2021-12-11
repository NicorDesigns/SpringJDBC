## Using Spring Boot to combine Spring Web with embedded Tomcat

### 1 Using IntelliJ we create a new Java Maven Module (JDBC12JdbcSpringBoot)

#### File -> New Module -> Spring Initialzr

Name JDBC12JdbcSpringBoot Group com.nicordesigns Package Name : com.nicordesigns.jdbcspringboot Packaging : WAR Step 2
Spring Dev Tools Spring Web JDBC API MariaDB Driver

Add Spring Boot Services

#### ArtifactId : JDBC12JdbcSpringBoot

### 1 Run "MVN Clean Install" on the generated app -> Will show missing DataSource Bean

Bring Config file from JDBC6 that contains DataSourceBean Run mvn clean install again (test will pass because bean has
been picked up)    