## Using Spring Boot to combine Spring Web with embedded Tomcat

[https://spring.io/guides/gs/relational-data-access/](https://spring.io/guides/gs/relational-data-access/)
[https://spring.io/guides/gs/serving-web-content/](https://spring.io/guides/gs/serving-web-content/)
JDBC6 & JDBC11

### 1 Using IntelliJ we create a new Spring Initialzr Module (JDBC12JdbcSpringBoot)

#### File -> New Module -> Spring Initialzr

Name JDBC12JdbcSpringBoot Group com.nicordesigns Package Name : com.nicordesigns.jdbcspringboot Packaging : JAR

Step 2

Developer Tools -> Spring Dev Tools Web -> Spring Web Template Engines -> Thyme Leaf SQL -> JDBC API SQL -> MariaDB
Driver

Finish

Add Spring Boot Services Add all files to Git should IntelliJ ask

### 2 Update the generated POM dependencies

mariadb-java-client should not have only runtime scope because it is not provided Load Maven Changes

### 3 Run "MVN Clean Install" on the generated JDBC12JdbcSpringBoot app -> Will show missing DataSource Bean

Context can not be loaded for JDBC because of missing datasource bean So we will create the bean - Look at the AppConfig
file from JDBC6 that contains DataSourceBean

Create or Paste in Jdbc12JdbcSpringBootConfig

Run mvn clean install again (test will pass because bean has been picked up)

### 4 Copy over the following Packages

Model -> Same as used in JDBC11   
DAO -> JDBC11 with elaborations on Component, Post Construct and Autowired Annotations

### 5 Add the landing page, thymeleaf charites template page and CharityController

index.html landing page in the resources/static directory  
charities.hrml in resources/templates CharityController in the com.nicordesigns.jdbc12jdbcspringboot package

  
