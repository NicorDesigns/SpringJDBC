## JDBC DriverManager Connection

### Connection URLs

_Slide 1_

### 1 Create a new Connection Module (JDBC1)

### 2 Use Maven in IntelliJ

#### File -> New Module -> JDBC1 (Maven no Archetype)

    Create package com.nicordesigns

### 3 Write DriverManager App that gets JDBC Connection

    Create new class DriverManager in package
    Stub new method: public Connection getConnection()
    
    Use the same DriverManager as is used in IntelliJ
    Update the Maven POM dependencies for the driver

### 4 Use Junit 5 Test Driver

    Create Junit5 Unit Test for Stubbed method
    Fill out the logic to use same DriverManager used in IntelliJ
    Add the Junit5 dependency to the POM
    Update Maven Build to the latest for Junit 5

##### 5 Update Maven POMs where required

    Ensure we are using Java 11 (OpenJDK 11)
    Add the required Junit5 Build Plugins to the parent POM
    Add the JUNIT 5 API and Engine dependencies to JDBC1 POM

