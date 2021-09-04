## JDBC JNDI DataSource Connection defined inside Tomcat Web Server

#### From https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html

        "java.sql.DriverManager supports the service provider mechanism. This feature is that all the available JDBC 
        drivers that announce themselves by providing a META-INF/services/java.sql.Driver file are automatically 
        discovered, loaded and registered, relieving you from the need to load the database driver explicitly before 
        you create a JDBC connection. 

        _However, the implementation is fundamentally broken in all Java versions for a servlet container environment._ 

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

### JNDI Connection defined in Tomcat context.xml

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
    Update the Parent POM

### 4 Define DataSource Class and its Properties in the Tomcat 8.5 CONTEXT.XML resource element

         <Resource name="jdbc/charityDB" auth="Container" type="javax.sql.DataSource"
               maxTotal="100" maxIdle="30" maxWaitMillis="10000"
               username="root" password="secret" driverClassName="org.mariadb.jdbc.Driver"
               url="jdbc:mariadb://localhost:3306/charityDB"/>

### 5 Update the generated HelloServlet.java to get the CharityDB CatalogName from the JNDI defined DB

    Update web.xml

        <description>MariaDB Test App</description>
        <resource-ref>
            <description>DB Connection</description>
            <res-ref-name>jdbc/charityDB</res-ref-name>
            <res-type>javax.sql.DataSource</res-type>
            <res-auth>Container</res-auth>
        </resource-ref>
    
    
    Create a DBUtil class where we:

    Get ApplicationContext context from the Tomcate Web Server
    Get DataSource from Tomcat ApplicationContext
        
        InitialContext ctx = new InitialContext();
        MariaDbDataSource ds = (MariaDbDataSource) ctx.lookup("java:comp/env/jdbc/charityDB");

    Get the Catalog Name from the DB like we did in JDBC1 & JDBC2
			
    Change the generated HelloServlet.java to display the CatalogName
    
    
     
        

        
    
    



