# HeidiSQL Dump 
#
# --------------------------------------------------------
# Host:                 127.0.0.1
# Database:             soldier_monitoring_db
# Server version:       5.0.24a-community-nt
# Server OS:            Win32
# Target-Compatibility: Standard ANSI SQL
# HeidiSQL version:     3.2 Revision: 1129
# --------------------------------------------------------

/*!40100 SET CHARACTER SET latin1;*/
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';*/
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;*/


#
# Database structure for database 'soldier_monitoring_db'
#

CREATE DATABASE /*!32312 IF NOT EXISTS*/ "soldier_monitoring_db" /*!40100 DEFAULT CHARACTER SET latin1 */;

USE "soldier_monitoring_db";


#
# Table structure for table 'soldier_table'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "soldier_table" (
  "soldier_id" int(10) unsigned NOT NULL auto_increment,
  "soldier_name" varchar(100) default NULL,
  "soldier_bloodgroup" varchar(100) default NULL,
  "soldier_MobileNo" varchar(50) default NULL,
  PRIMARY KEY  ("soldier_id")
) /*!40100 DEFAULT CHARSET=latin1*/;



#
# Table structure for table 'soldier_value_table'
#

CREATE TABLE /*!32312 IF NOT EXISTS*/ "soldier_value_table" (
  "id" int(10) unsigned NOT NULL auto_increment,
  "soldier_lat" varchar(100) default NULL,
  "soldier_long" varchar(100) default NULL,
  "soldier_temp" varchar(100) default NULL,
  "soldier_heartrate" varchar(100) default NULL,
  "date" varchar(100) default NULL,
  "time" varchar(100) default NULL,
  "soldier_id" int(10) unsigned default NULL,
  PRIMARY KEY  ("id")
) /*!40100 DEFAULT CHARSET=latin1*/;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE;*/
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;*/
