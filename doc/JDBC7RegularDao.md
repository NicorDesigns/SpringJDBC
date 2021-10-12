## Using a DAO with JDBC to insert a new Charity and retrieve the Charity from the database

### 1 Using IntelliJ we create a new Java Maven Module (JDBC7RegularDao)

#### File -> New Module -> Maven (No Archetype Selected)

#### ArtifactId : JDBC7RegularDao

    Create package com.nicordesigns

### 3 Update the generated Maven POM.XML files where required

    We will use JDBC2SpringDataSource as a guide for setting up our Data Source    

    Add the mariadb-java-client to the POM     

    Add all the required Spring Framework dependencies for the Spring Context and DataSource bean

    Also add the Maven build plugin that allows us to package and run a Tomcat Web Application from the command line
    
    Update the Parent POM and global dependency version numbers

### 4  Create the Charity DAO Interface and Charity DAO Implementation class

    First we create the Charity Model Object and its related Model Objects Category and Program
    
    Then we create the Charity DAO Interface and the Charity DAO Implementation

    For the Implementation of the DAO we will use regular JAVA JDBC and not the SpringJDBC Template

    We will create a working insert Charity method and a working find Charity by TaxId method, which means we have
    to manage all the database connections open and closing

    Our methods will only work on the Charity Model Object for now and we won't be implementing the full object graph
    of the Charity

### 5  Create the Application with a Main Method that runs the insert and find methods

    We define our Data Source in the Spring beans.xml file

    We use the Spring  ClassPathXmlApplicationContext to obtain the conext and the Spring Data Source bean

    We create the DAO Implementation Object in the Main method

    Then we first create a new Charity Object and insert it using the DAO and then we find the inserted Charity
    using the DAO
    
### 6 Run the App main method

    First in IntelliJ -> Select Main -> Right Click -> Run Main:main -> Look at the Charity Table Entry
    --> Look at the Charity Object that was retrieved from the database
