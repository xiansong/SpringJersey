delimiter $$

CREATE DATABASE `pwz_utility` /*!40100 DEFAULT CHARACTER SET utf8 */$$

delimiter $$

CREATE TABLE `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `user_role` (
  `userRoleId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `authority` varchar(45) NOT NULL,
  PRIMARY KEY (`userRoleId`),
  UNIQUE KEY `userId_authority_UNIQUE` (`userId`,`authority`),
  KEY `fk_user_role_user_userId_idx` (`userId`),
  CONSTRAINT `fk_user_role_user_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `elec_account` (
  `elecAcctId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `companyName` varchar(45) NOT NULL,
  `acctNumber` varchar(45) NOT NULL,
  `MPRN` bigint(11) NOT NULL,
  PRIMARY KEY (`elecAcctId`),
  UNIQUE KEY `company_acctNumber_UNIQUE` (`companyName`,`acctNumber`),
  KEY `fk_elec_account_user_userId_idx` (`userId`),
  CONSTRAINT `fk_elec_account_user_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `elec_reading` (
  `readingId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `elecAcctId` int(10) unsigned NOT NULL,
  `dayReading` int(10) unsigned NOT NULL,
  `nightReading` int(10) unsigned NOT NULL,
  `readDate` date NOT NULL,
  PRIMARY KEY (`readingId`),
  KEY `fk_elec_reading_elec_account_elecAcctId_idx` (`elecAcctId`),
  CONSTRAINT `fk_elec_reading_elec_account_elecAcctId` FOREIGN KEY (`elecAcctId`) REFERENCES `elec_account` (`elecAcctId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `elec_bill` (
  `billId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `elecAcctId` int(10) unsigned NOT NULL,
  `dayBill` decimal(8,2) NOT NULL,
  `nightBill` decimal(8,2) NOT NULL,
  `issuedDate` date NOT NULL,
  PRIMARY KEY (`billId`),
  KEY `fk_elec_bill_elec_account_elecAcctId_idx` (`elecAcctId`),
  CONSTRAINT `fk_elec_bill_elec_account_elecAcctId` FOREIGN KEY (`elecAcctId`) REFERENCES `elec_account` (`elecAcctId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `gas_account` (
  `gasAcctId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `companyName` varchar(45) NOT NULL,
  `acctNumber` varchar(45) NOT NULL,
  `GPRN` int(7) NOT NULL,
  PRIMARY KEY (`gasAcctId`),
  UNIQUE KEY `companyName_acctNumber_UNIQUE` (`companyName`,`acctNumber`),
  KEY `fk_gas_account_user_userId_idx` (`userId`),
  CONSTRAINT `fk_gas_account_user_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `gas_reading` (
  `readingId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gasAcctId` int(10) unsigned NOT NULL,
  `reading` int(10) unsigned NOT NULL,
  `readDate` date NOT NULL,
  PRIMARY KEY (`readingId`),
  KEY `fk_gas_reading_gas_account_gasAcctId_idx` (`gasAcctId`),
  CONSTRAINT `fk_gas_reading_gas_account_gasAcctId` FOREIGN KEY (`gasAcctId`) REFERENCES `gas_account` (`gasAcctId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `gas_bill` (
  `billId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gasAcctId` int(10) unsigned NOT NULL,
  `bill` decimal(8,2) NOT NULL,
  `issuedDate` date NOT NULL,
  PRIMARY KEY (`billId`),
  KEY `fk_gas_bill_gas_account_gasAcctId_idx` (`gasAcctId`),
  CONSTRAINT `fk_gas_bill_gas_account_gasAcctId` FOREIGN KEY (`gasAcctId`) REFERENCES `gas_account` (`gasAcctId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
