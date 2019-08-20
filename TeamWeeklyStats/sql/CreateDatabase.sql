#Create database

CREATE DATABASE IF NOT EXISTS `NBA_2019`;

USE `NBA_2019`

#Create tables
CREATE TABLE `players` (
  `RK` nvarchar(50) NOT NULL,
  `Player` nvarchar(50) DEFAULT NULL,
  `POS` nvarchar(50) DEFAULT NULL,
  `AGE` nvarchar(50) DEFAULT NULL,
  `TM` nvarchar(50) DEFAULT NULL,
  `G` date DEFAULT NULL,
  `GS` nvarchar(50) DEFAULT NULL,
  `MP` nvarchar(255) DEFAULT NULL,
  `FG` nvarchar(50) DEFAULT NULL,
  `FGA` nvarchar(50) DEFAULT NULL,
  `FG%` nvarchar(50) NULL,
  `3P` date DEFAULT NULL,
  `3PA` nvarchar(150) DEFAULT NULL,
  `3P%` date DEFAULT NULL,
  `2P` date DEFAULT NULL,
  `2PA` date DEFAULT NULL,
  `2P%` date DEFAULT NULL,
  `eFG%` nvarchar(50) NULL,
  `FT` date DEFAULT NULL,
  `FTA` nvarchar(150) DEFAULT NULL,
  `FT%` date DEFAULT NULL,
  `ORB` date DEFAULT NULL,
  `DRB%` nvarchar(50) NULL,
  `TRB` date DEFAULT NULL,
  `AST` nvarchar(150) DEFAULT NULL,
  `STL` date DEFAULT NULL,
  `BLK` date DEFAULT NULL,
  `TOV` nvarchar(50) NULL,
  `PF` date DEFAULT NULL,
  `PTS` nvarchar(150) DEFAULT NULL,
  PRIMARY KEY (`RK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
