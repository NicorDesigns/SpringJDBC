### Update the DAO with JDBC for full CRUD capability, using Connection Pooling to get the Data Source and expanding the Charity Object Model to include its Category Types

### 1 Using IntelliJ we create a new Java Maven Module (JDBC8DirectJdbcDao)

#### File -> New Module -> Maven (No Archetype Selected)

#### ArtifactId : JDBC8DirectJdbcDao

### 2 Create package com.nicordesigns

### 3 Update the generated Maven POM.XML files where required

    We will use JDBC2SpringDataSource as a guide for setting up our Data Source    

    Add the mariadb-java-client to the POM     

    Add all the required Spring Framework dependencies for the Spring Context and DataSource bean

    Update the Parent POM and global dependency version numbers

### 4 create the Charity Model Object and its related Model Objects Program

    For this run we will be implementing the relationships between the Charity and Category in our Charity Object Model Graph

### 5  Create the Charity DAO Interface and Charity DAO Implementation class

    Then we create the Charity DAO Interface and the Charity DAO Implementation

    For the Implementation of the DAO we will still use JAVA JDBC directly and not the SpringJDBC Template

    We will expand on our previous insert and find method to get a full CRUD DAO Implementation
    We will add a Category Object to our Charity Object Model 
    We will use try with resources so that Java will manage the Open and Closing of DB Resources

### 6  Create the Application with a Main Method that runs the insert and find methods

    We define our MariaDB Pool Data Source in the Spring beans.xml file
    [https://mariadb.com/kb/en/pool-datasource-implementation/](https://mariadb.com/kb/en/pool-datasource-implementation/)

    We now also define our DAO class in the Spring beans.xml file

    We use the Spring  ClassPathXmlApplicationContext to obtain the conext and the Spring Data Source bean

    We create the DAO Implementation Object in the Main method

    Then we first create a new Charity Object and insert it using the DAO and then we find the inserted Charity
    using the DAO

### 7 Run the App main method

    First clear out the DB Charity Table in such a way that we do not have any FK relations and tables set up.
    Run the Main method.

#### In IntelliJ -> Select Main -> Right Click -> Run Main:main -> Look at the Charity Table Entry

    --> Look at the Charity Object that was retrieved from the database
    Look at the Console Output and at the DB Charity Table