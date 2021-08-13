## JDBC DataSource Spring Connection

        "DataSource objects can provide connection pooling and distributed transactions. 
         This functionality is essential for enterprise database computing."
        
        "Because of its properties, a DataSource object is a better alternative than the DriverManager class for getting
         a connection. 
         Programmers no longer have to hard code the driver name or JDBC URL in their applications, which makes them 
         more portable. 
         
         Also, DataSource properties make maintaining code much simpler. 
         
         If there is a change, the system administrator can update data source properties and not be concerned about 
         changing every application that makes a connection to the data source. 
         
         For example, if the data source were moved to a different server, all the system administrator would have to do 
         is set the serverName property to the new server name."

### Connection URLs

_Slide 1_

### 1 Create a new DataSource Connection Module (JDBC2SpringDataSource)

### 2 Use Maven in IntelliJ

#### File -> New Module -> JDBC2SpringDataSource (Maven no Archetype)

    Create package com.nicordesigns

### 3 Update Maven POMs where required

    Update the Maven POM dependencies with "Spring Context" and "Spring JDBC"
    Also add the MariaDB Client dependency as was used in JDBC1 Module

### 4 Define DataSource Class and its Properties in application.xml

        <bean id="dataSource" class="org.mariadb.jdbc.MariaDbDataSource">
            <property name="serverName" value="localhost"/>
            <property name="portNumber" value="3306"/>
            <property name="url" value="jdbc:mariadb://localhost:3306/charityDB"/>
            <property name="user" value="root"/>
            <property name="password" value="secret"/>
        </bean>

### 4 Write SpringDataSourceApp that gets JDBC Connection from DataSource bean

    Create class SpringDataSourceApp with main() method
    Get ApplicationContext context from Spring XML defined bean
    Get DataSource from ApplicationContext (XML defined Spring bean)
    Run the main method in IntelliJ to test
    Update the SpringDataSourceApp POM and add exec-maven-plugin to the build section to run the main method using 
    mvn:exec

    
     
        

        
    
    



