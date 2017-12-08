-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: luggagem
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `Employee_code` int(11) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`Employee_code`),
  KEY `fk_Account_Medewerker_idx` (`Employee_code`),
  CONSTRAINT `fk_Account_Medewerker` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (500500,'pathedude@gmail.com','6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B','F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9',3,1),(500501,'admin@corendol.nl','6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B','F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9',3,1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `airport`
--

DROP TABLE IF EXISTS `airport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `airport` (
  `IATA` varchar(5) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `timezone` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IATA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airport`
--

LOCK TABLES `airport` WRITE;
/*!40000 ALTER TABLE `airport` DISABLE KEYS */;
INSERT INTO `airport` VALUES ('AMS','Amsterdam Schiphol Airport','Netherlands','GMT+1','Amsterdam');
/*!40000 ALTER TABLE `airport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changes`
--

DROP TABLE IF EXISTS `changes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `changes` (
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Employee_code` int(11) NOT NULL,
  `Luggage_registrationnr` int(11) NOT NULL,
  `changeid` tinyint(1) NOT NULL,
  PRIMARY KEY (`Employee_code`,`Luggage_registrationnr`),
  KEY `fk_Changes_lost_Medewerker1_idx` (`Employee_code`),
  KEY `fk_Changes_lost_Verloren_bagage1_idx` (`Luggage_registrationnr`),
  CONSTRAINT `fk_Changes_lost_Medewerker1` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Changes_lost_Verloren_bagage1` FOREIGN KEY (`Luggage_registrationnr`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changes`
--

LOCK TABLES `changes` WRITE;
/*!40000 ALTER TABLE `changes` DISABLE KEYS */;
INSERT INTO `changes` VALUES ('2017-12-08 16:38:17',500500,14,0);
/*!40000 ALTER TABLE `changes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customernr` int(11) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `preposition` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `adres` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `postal_code` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`customernr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Jan',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'Mees','de','Noob','q','q','q','q','q','q'),(3,'Joris','','Ebbelaar','Deutzhof 67','Assendelft','1566CP','Nederland','06-23189112','joris-ebbelaar@hotmail.nl');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `code` int(11) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `preposition` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `Luchthaven_IATA` varchar(5) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_Medewerker_Luchthaven1_idx` (`Luchthaven_IATA`),
  CONSTRAINT `fk_Medewerker_Luchthaven1` FOREIGN KEY (`Luchthaven_IATA`) REFERENCES `airport` (`IATA`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (500500,'Pathe',NULL,'Dude','AMS'),(500501,'Admin',NULL,NULL,'AMS');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luggage`
--

DROP TABLE IF EXISTS `luggage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `luggage` (
  `registrationnr` int(11) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `flightnr` varchar(45) DEFAULT NULL,
  `labelnr` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `luggage_type` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `location_found` varchar(45) DEFAULT NULL,
  `primary_color` varchar(45) DEFAULT NULL,
  `secondary_color` varchar(45) DEFAULT NULL,
  `notes` varchar(45) DEFAULT NULL,
  `size` varchar(45) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  `case_type` int(11) DEFAULT NULL,
  `customer_firstname` varchar(45) DEFAULT NULL,
  `customer_preposition` varchar(45) DEFAULT NULL,
  `customer_lastname` varchar(45) DEFAULT NULL,
  `case_status` tinyint(1) NOT NULL DEFAULT '1',
  `airport_IATA` varchar(5) DEFAULT NULL,
  `customer_customernr` int(11) DEFAULT NULL,
  PRIMARY KEY (`registrationnr`),
  KEY `fk_airport_IATA_idx` (`airport_IATA`),
  KEY `fk_luggage_customer1_idx` (`customer_customernr`),
  CONSTRAINT `fk_airport_IATA` FOREIGN KEY (`airport_IATA`) REFERENCES `airport` (`IATA`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_luggage_customer1` FOREIGN KEY (`customer_customernr`) REFERENCES `customer` (`customernr`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luggage`
--

LOCK TABLES `luggage` WRITE;
/*!40000 ALTER TABLE `luggage` DISABLE KEYS */;
INSERT INTO `luggage` VALUES (1,'2017-12-05 12:32:27',NULL,'1',NULL,'hard case','samsonite',NULL,'red','white',NULL,NULL,NULL,3,NULL,NULL,'',1,'AMS',NULL),(2,'2017-12-06 17:31:18',NULL,'2',NULL,'weekend bag','louis vuitton',NULL,'brown','black','',NULL,NULL,3,'Mees','de','Noob',1,'AMS',2),(3,'2017-12-06 17:32:03',NULL,'3',NULL,'suitcase','northface',NULL,'black','black','',NULL,NULL,3,'Mees','de','Noob',1,'AMS',2),(4,'2017-12-06 23:14:33',NULL,'4','','weekend bag','tommy hilfiger',NULL,'black','brown','q',NULL,NULL,0,'q',NULL,NULL,1,NULL,NULL),(5,'2017-12-06 23:32:20',NULL,'5',NULL,'hard case','samsonite',NULL,'red','white','',NULL,NULL,3,'Joris','','Ebbelaar',0,'AMS',3),(6,'2017-12-08 12:22:36',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,1,NULL,NULL),(7,'2017-12-08 15:58:18','','','','','','','','','','','',1,'','','',1,NULL,NULL),(8,'2017-12-08 15:58:21','AAy3184','83q8o4q89','Alkmaar','Suitcase','BrandinHetBos','Toilet','Geel','Tood','Een zeer gekke koffer','873x839x93','201kg',1,'A.Y.','van','Toaster',1,NULL,NULL),(9,'2017-12-08 16:00:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,1,NULL,NULL),(10,'2017-12-08 16:00:57',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,1,NULL,NULL),(11,'2017-12-08 16:18:05','','','','','','','','','','','',1,'','','',1,'AMS',NULL),(12,'2017-12-08 16:24:46','','','','','','','','','','','',1,'','','',1,'AMS',NULL),(13,'2017-12-08 16:38:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,1,NULL,NULL),(14,'2017-12-08 16:38:17',NULL,NULL,NULL,'YA',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'Joris','','Ebbelaar',1,NULL,NULL);
/*!40000 ALTER TABLE `luggage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luggage_damaged`
--

DROP TABLE IF EXISTS `luggage_damaged`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `luggage_damaged` (
  `image01` longblob,
  `image02` longblob,
  `image03` longblob,
  `Luggage_registrationnr` int(11) NOT NULL,
  PRIMARY KEY (`Luggage_registrationnr`),
  CONSTRAINT `fk_Luggage_damaged_Luggage1` FOREIGN KEY (`Luggage_registrationnr`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luggage_damaged`
--

LOCK TABLES `luggage_damaged` WRITE;
/*!40000 ALTER TABLE `luggage_damaged` DISABLE KEYS */;
/*!40000 ALTER TABLE `luggage_damaged` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matches` (
  `registrationnr` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `case_status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`registrationnr`),
  CONSTRAINT `FK_matchingNr` FOREIGN KEY (`registrationnr`) REFERENCES `luggage` (`registrationnr`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-08 16:42:03
