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
INSERT INTO `account` VALUES (500500,'test@gmail.com','6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B','F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9',1,1),(500501,'jordanvanbeijnhem@gmail.com','E1D7422236CC9ABE8B96335C5B3F7C85EBBCB101C71FA9D67C40D1232C7A9A48','C8B2187E351CF2AB6A7922A94EB55072A4259CC741B84BA8D937C727CF24077F',0,1);
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
INSERT INTO `airport` VALUES ('AMS','Schiphol','Nederland','1','Amsterdam');
/*!40000 ALTER TABLE `airport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changes`
--

DROP TABLE IF EXISTS `changes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `changes` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `Employee_code` int(11) NOT NULL,
  `Luggage_registrationnr` int(11) NOT NULL,
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
INSERT INTO `customer` VALUES (545,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
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
INSERT INTO `employee` VALUES (500500,'Pathe','','Dude','AMS'),(500501,'Jordan','van','Beijnhem','AMS');
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
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `flightnr` varchar(45) DEFAULT NULL,
  `labelnr` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `luggage_type` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `primary_color` varchar(45) DEFAULT NULL,
  `secondary_color` varchar(45) DEFAULT NULL,
  `notes` varchar(45) DEFAULT NULL,
  `Klant_klantnr` int(11) DEFAULT NULL,
  `case_type` int(11) DEFAULT NULL,
  `traveller_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`registrationnr`),
  KEY `fk_Verloren_bagage_Klant1_idx` (`Klant_klantnr`),
  CONSTRAINT `fk_Verloren_bagage_Klant1` FOREIGN KEY (`Klant_klantnr`) REFERENCES `customer` (`customernr`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `luggage`
--

LOCK TABLES `luggage` WRITE;
/*!40000 ALTER TABLE `luggage` DISABLE KEYS */;
INSERT INTO `luggage` VALUES (500,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,545,NULL,NULL);
/*!40000 ALTER TABLE `luggage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `luggage_damaged`
--

DROP TABLE IF EXISTS `luggage_damaged`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `luggage_damaged` (
  `image01` blob,
  `image02` blob,
  `image03` blob,
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
-- Table structure for table `match`
--

DROP TABLE IF EXISTS `match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `match` (
  `Luggage_registrationnr_foud` int(11) NOT NULL,
  `Luggage_registrationnr_lost` int(11) NOT NULL,
  `Employee_code` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`Luggage_registrationnr_foud`,`Luggage_registrationnr_lost`),
  KEY `fk_Match_Luggage1_idx` (`Luggage_registrationnr_foud`),
  KEY `fk_Match_Luggage2_idx` (`Luggage_registrationnr_lost`),
  KEY `fk_Match_Medewerker1_idx` (`Employee_code`),
  CONSTRAINT `fk_Match_Luggage1` FOREIGN KEY (`Luggage_registrationnr_foud`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Match_Luggage2` FOREIGN KEY (`Luggage_registrationnr_lost`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Match_Medewerker1` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match`
--

LOCK TABLES `match` WRITE;
/*!40000 ALTER TABLE `match` DISABLE KEYS */;
/*!40000 ALTER TABLE `match` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-01 13:51:32
