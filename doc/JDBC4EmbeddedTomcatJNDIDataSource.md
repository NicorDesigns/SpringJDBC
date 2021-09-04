## Embedded Tomcat JDBC JNDI DataSource Connection defined in a regular java main method.

### 1 Create a new Java Maven Module (JDBC4EmbeddedTomcatJndiDataSource)

### 2 Use IntelliJ

#### File -> New Module -> Maven (No Archetype Selected)

#### ArtifactId : JDBC4EmbeddedTomcatJndiDataSource

    Create package com.nicordesigns

### 3 Update the generated Maven POM.XML files where required

    Update the package to be a JAR
    Add all the required Tomcat dependencies that will enable running it from the command line
    Also add the Maven build plugin that allows us to package and run a Tomcat Web Application from the command line
    Update the Perent POM and global dependency version numbers

### 4  Create the Application with a Main Method that does all the setup and configuration in code

    Where previosly our DataSource was definied in Spring application.xml or Tomcat context.xml we now set the
    datasource properties in the Main method of our code

### 5 Modify the generated HelloServlet.java to get the CharityDB CatalogName from the main method JNDI

    Re-use DatabaseUtil class from previous tutorial 
    Add modified HelloServlet 
    Create new web app directory underneath main

### 6 Run the App main method

    First in IntelliJ
    





