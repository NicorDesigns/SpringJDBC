DROP USER IF EXISTS springjdbcapp@localhost;
CREATE USER springjdbcapp@localhost IDENTIFIED BY 'password1';
SELECT user
FROM mysql.user;
GRANT ALL PRIVILEGES ON charitydb.* TO springjdbcapp@localhost;
FLUSH PRIVILEGES;
SHOW GRANTS FOR springjdbcapp@localhost;