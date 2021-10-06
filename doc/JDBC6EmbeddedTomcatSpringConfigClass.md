## Embedded Tomcat JDBC Spring DataSource Connection bean defined in Spring Config Class using Annotations.

### 1 Using IntelliJ we create a new Java Maven Module (JDBC6EmbeddedTomcatSpringConfigClass)

#### File -> New Module -> Maven (No Archetype Selected)

#### ArtifactId : JDBC6EmbeddedTomcatSpringConfigClass

    Create package com.nicordesigns

### 3 Update the generated Maven POM.XML files where required

    Update the package to be a JAR

    Add all the required Tomcat dependencies that will enable running it from the command line
    
    Add all the required Spring Framework dependencies for the Spring Context and DataSource bean
    
    Also add the Maven build plugin that allows us to package and run a Tomcat Web Application from the command line
    
    Update the Parent POM and global dependency version numbers

### 4  Create the Application with a Main Method that does all the setup and configuration

### 5 Modify the generated HelloServlet.java to get the CharityDB CatalogName from the main method JNDI

    Re-use DatabaseUtil class from previous tutorial 
    Add modified HelloServlet 

### 6 Run the App main method

    This time we will use the Maven Execute plugin since we already know how to launc the App from within
    IntelliJ 

    Then using the Maven Execute plugin: 

    
    First add the plugin to the modules Maven pom.xml
    Then execute it from the command line:  mvn exec:java

    https://www.mojohaus.org/exec-maven-plugin/usage.html

    http://localhost:8080/hello

    





