1. Prerequisite is that you have a MariaDB download and installed on your local machine. (You will need a super user
   username and password)


2. Windows Start -> Select MariaDB -> Select MySQL Client and enter password


3. Once you are logged into MariaDB Client (Run the following SQL scripts)

          1. DROP DATABASE charityDB;
          2. CREATE DATABASE charityDB;
          3. SHOW DATABASES;
          4. DROP USER springjdbcapp@localhost;
          5. CREATE USER springjdbcapp@localhost IDENTIFIED BY 'password1';
          6. SELECT user FROM mysql.user;
          7. GRANT ALL PRIVILEGES ON charitydb.* TO springjdbcapp@localhost;
          8. FLUSH PRIVILEGES;
          9. SHOW GRANTS FOR springjdbcapp@localhost;
          10. EXIT

4. Open up IntelliJ and check out the https://github.com/NicorDesigns/SpringJDBC/tree/main-start-mariadb-setup branch


5. Set up a database Connection in IntelliJ to the charityDB using the 'springjdbcapp' user


6. Inside our Maven Parent POM create a new sub-module named MariaDBConnect (where we will store our SQL Scripts) 

    
     