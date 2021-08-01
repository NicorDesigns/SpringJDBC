ALTER TABLE charitydb.CHARITY
    DROP INDEX IF EXISTS PK_CHARITY;

DELETE
FROM charitydb.CHARITY;

DROP TABLE IF EXISTS charitydb.CHARITY;

CREATE TABLE charitydb.CHARITY
(
    `CHARITY_ID`               MEDIUMINT    NOT NULL AUTO_INCREMENT,
    `CHARITY_TAX_ID`           VARCHAR(250) NOT NULL UNIQUE, -- https://tax.co.za/what-is-my-tax-number/
    `CHARITY_NAME`             VARCHAR(250),
    `CHARITY_MISSION`          VARCHAR(250),
    `CHARITY_WEB_ADDRESS`      VARCHAR(250),                 -- Need a separate ADDRESS Table
    `CHARITY_FACEBOOK_ADDRESS` VARCHAR(250),                 -- Need a separate ADDRESS Table
    `CHARITY_TWITTER_ADDRESS`  VARCHAR(250),                 -- Need a separate ADDRESS Table
    PRIMARY KEY (`CHARITY_ID`)
) ENGINE = InnoDB;

CREATE UNIQUE INDEX `PK_CHARITY` ON charitydb.CHARITY (`CHARITY_ID` ASC);
