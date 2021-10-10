## Embedded Tomcat JDBC Spring DataSource Connection bean defined in Spring Config Class using Annotations.

### 1 Using IntelliJ we create a new Java Maven Module (JDBC6EmbeddedTomcatSpringConfigClass)

#### File -> New Module -> Maven (No Archetype Selected)

#### ArtifactId : JDBC6EmbeddedTomcatSpringConfigClass

    Create package com.nicordesigns

### 3 Update the generated Maven POM.XML files where required

    Update the package to be a JAR

    Add all the required Tomcat dependencies that will enable running it from the command line
    
    Add all the required Spring Framework dependencies for the Spring Context and DataSource bean

    Add log4j and jstl dependencies
    
    Also add the Maven build plugin that allows us to package and run a Tomcat Web Application from the command line
    
    Update the Parent POM and global dependency version numbers

### 4  Create the Application with a Main Method that does all the setup and configuration

    The template for this module is a basic Spring MVC Application.
    We will use Spring Annotations and Spring config classes
    This module will use Spring App Config Java Classe to define the datasource bean
    It will use a Spring Web Config class to define the MVC ViewResolver Bean
    We will run Embedded Tomcat from a Spring Component
    We will use a DBHelloService Spring Component to present the Hello World Screen and
    the MariaDB Catalog Name
    The WelcomeController is where all the logic is tied together

### 5 Run the App main method

    First in IntelliJ -> Select Main -> Right Click -> Run Main:main -> show the JSP Page with the Model that 
    uses the Dispacher Servlet

http://localhost:8080/hello

    Using the Maven Execute plugin: 

    
    First add the plugin to the modules Maven pom.xml
    Then execute it from the command line:  mvn exec:java

    https://www.mojohaus.org/exec-maven-plugin/usage.html

    http://localhost:8080/hello

    





