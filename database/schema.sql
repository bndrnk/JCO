CREATE DATABASE  IF NOT EXISTS `JCO` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `JCO`;
-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: JCO
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ROUTE`
--

DROP TABLE IF EXISTS `ROUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROUTE` (
  `ROUTE_ID` bigint(20) NOT NULL DEFAULT '0',
  `ROUTE_TIME` bigint(20) NOT NULL,
  PRIMARY KEY (`ROUTE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LOCATION`
--

DROP TABLE IF EXISTS `LOCATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LOCATION` (
  `LOCATION_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LOCATION_NAME` varchar(250) DEFAULT NULL,
  `LATITUDE` double NOT NULL,
  `LONGITUDE` double NOT NULL,
  `ROUTE_ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`LOCATION_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=6809 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VEHICLE`
--

DROP TABLE IF EXISTS `VEHICLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VEHICLE` (
  `VEHICLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `VEHICLE_NAME` varchar(250) NOT NULL,
  `VEHICLE_TYPE` varchar(250) NOT NULL,
  `ROUTE_ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`VEHICLE_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-10 19:37:38
