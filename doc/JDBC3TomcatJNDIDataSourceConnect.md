## JDBC JNDI DataSource Connection defined inside Tomcat Web Server

#### From https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html

        "java.sql.DriverManager supports the service provider mechanism. This feature is that all the available JDBC 
        drivers that announce themselves by providing a META-INF/services/java.sql.Driver file are automatically 
        discovered, loaded and registered, relieving you from the need to load the database driver explicitly before 
        you create a JDBC connection. 

        **However, the implementation is fundamentally broken in all Java versions for a servlet container environment.** 

        The problem is that java.sql.DriverManager will scan for the drivers only once.

        The JRE Memory Leak Prevention Listener that is included with Apache Tomcat solves this by triggering the
        drivers scan during Tomcat startup. This is enabled by default. It means that only libraries visible to the 
        listener such as the ones in $CATALINA_BASE/lib will be scanned for database drivers. If you are considering 
        disabling this feature, note that the scan would be triggered by the first web application that is using JDBC, 
        leading to failures when this web application is reloaded and for other web applications that rely on this 
        feature.

        Thus, the web applications that have database drivers in their WEB-INF/lib directory cannot rely on the service 
        provider mechanism and should register the drivers explicitly.

        The list of drivers in java.sql.DriverManager is also a known source of memory leaks. Any Drivers registered by 
        a web application must be deregistered when the web application stops. Tomcat will attempt to automatically 
        discover and deregister any JDBC drivers loaded by the web application class loader when the web application 
        stops. However, it is expected that applications do this for themselves via a ServletContextListener."

### Connection URLs

_Slide 1_

### 1 Create a new Java Enterprise Module (JDBC3TomcatJndiDataSource)

### 2 Use IntelliJ

#### File -> New Java Enterprise Module -> JDBC3TomcatJndiDataSource

#### Project Template : Web Application

#### Application Server : Tomcat 8 (Installed previously)

#### ArtifactId : JDBC3TomcatJndiDataSource

    Create package com.nicordesigns

### 3 Update Maven POMs where required

    Update to use Java 11
    Add the MariaDB Client dependency as was used in JDBC1, JDBC2 Module

### 4 Define DataSource Class and its Properties in application.xml resources

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

    
     
        

        
    
    



