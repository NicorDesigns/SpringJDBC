## Using a DAO with Spring JdbcDaoSupport to be able to do full range of CRUD with transactions that can be rolled back.

## Updating the database with a more rigorous relationship entity model.

## Introducing the Spring Service Layer for transactions.

(This is a build out of JDBC8DirectJdbcDao and JDBC9SpringJdbcDaoSupport )

### 1 Using IntelliJ we create a new Java Maven Module (JDBC10JdbcDaoSupportTransactions)

#### File -> New Module -> Maven (No Archetype Selected)

#### ArtifactId : JDBC10JdbcDaoSupportTransactions

### 2 Create package com.nicordesigns

### 3 Update the generated Maven POM.XML files where required

We will use JDBC9SpringJdbcDaoSupport as a guide for setting up our Data Source

Add the mariadb-java-client to the POM

Add all the required Spring Framework dependencies for the Spring Context and DataSource bean

Update the Parent POM and global dependency version numbers

### 4  Create the Charity DAO Interface and Charity DAO Implementation class

First we create the Charity Model Object and its related Model Object Category For this run we will introduce a one to
one mapping between Charity and Category

Then we create the Charity DAO Interface and the Charity DAO Implementation using JdbcDaoSupport

For the Implementation of the DAO calls we will use the Spring JDBC Template for Charities and use the Spring
Transaction Manager to manage updating the relationship tables in the database

We will create a working Create, Read, Update and Delete functionality across the full object graph of the Charity Table
and Object Model

### 5  Create the Spring Service Layer Interface and Implementation

We will wrap the Create Charity and Read Charity functions in a Spring annotated transaction.

### 6  Create the Application with a Main Method that runs the insert and find methods

We define our Transactional Data Source in the Spring beans.xml file

We use the Spring ClassPathXmlApplicationContext to obtain the conext and the Spring Data Source bean We will also now
get the DAO bean from the bean.xml file

We test the DAO Implementation Object in the Main method

Here we will test the full CRUD capability

### 7 Run the App main method

First clear out the DB Charity Table in such a way that we do not have any FK relations and tables set up. Run the Main
method.

In IntelliJ -> Select Main -> Right Click -> Run Main:main -> Look at the Charity Table Entry --> Look at the Charity
Object that was retrieved from the database Look at the Console Output and at the DB Charity Table