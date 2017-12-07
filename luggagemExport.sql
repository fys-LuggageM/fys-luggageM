-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 05 dec 2017 om 13:45
-- Serverversie: 10.1.21-MariaDB
-- PHP-versie: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `luggagem`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `account`
--

CREATE TABLE `account` (
  `Employee_code` int(11) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `account`
--

INSERT INTO `account` (`Employee_code`, `email`, `password`, `salt`, `user_level`, `active`) VALUES
(500500, 'pathedude@gmail.com', '6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B', 'F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9', 2, 1),
(500501, 'admin@corendol.nl', '6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B', 'F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9', 2, 1);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `airport`
--

CREATE TABLE `airport` (
  `IATA` varchar(5) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `timezone` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `airport`
--

INSERT INTO `airport` (`IATA`, `name`, `country`, `timezone`, `city`) VALUES
('AMS', 'Amsterdam Schiphol Airport', 'Netherlands', 'GMT+1', 'Amsterdam');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `changes`
--

CREATE TABLE `changes` (
  `date` date NOT NULL,
  `time` time NOT NULL,
  `Employee_code` int(11) NOT NULL,
  `Luggage_registrationnr` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `customer`
--

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
  `email` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `customer`
--

INSERT INTO `customer` (`customernr`, `first_name`, `preposition`, `last_name`, `adres`, `city`, `postal_code`, `country`, `phone`, `email`) VALUES
(1, 'Jan', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `employee`
--

CREATE TABLE `employee` (
  `code` int(11) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `preposition` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `Luchthaven_IATA` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `employee`
--

INSERT INTO `employee` (`code`, `first_name`, `preposition`, `last_name`, `Luchthaven_IATA`) VALUES
(500500, 'Pathe', NULL, 'Dude', 'AMS'),
(500501, 'Admin', NULL, NULL, 'AMS');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `luggage`
--

CREATE TABLE `luggage` (
  `registrationnr` int(11) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `flightnr` varchar(45) DEFAULT NULL,
  `labelnr` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `luggage_type` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `primary_color` varchar(45) DEFAULT NULL,
  `secondary_color` varchar(45) DEFAULT NULL,
  `notes` varchar(45) DEFAULT NULL,
  `case_type` int(11) DEFAULT NULL,
  `traveller_name` varchar(45) DEFAULT NULL,
  `case_status` tinyint(1) NOT NULL DEFAULT '1',
  `airport_IATA` varchar(5) DEFAULT NULL,
  `customer_customernr` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `luggage`
--

INSERT INTO `luggage` (`registrationnr`, `date`, `flightnr`, `labelnr`, `destination`, `luggage_type`, `brand`, `primary_color`, `secondary_color`, `notes`, `case_type`, `traveller_name`, `case_status`, `airport_IATA`, `customer_customernr`) VALUES
(1, '2017-12-05 12:32:27', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 3, NULL, 1, 'AMS', NULL);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `luggage_damaged`
--

CREATE TABLE `luggage_damaged` (
  `image01` longblob,
  `image02` longblob,
  `image03` longblob,
  `Luggage_registrationnr` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `match`
--

CREATE TABLE `match` (
  `Luggage_registrationnr_foud` int(11) NOT NULL,
  `Luggage_registrationnr_lost` int(11) NOT NULL,
  `Employee_code` int(11) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`Employee_code`),
  ADD KEY `fk_Account_Medewerker_idx` (`Employee_code`);

--
-- Indexen voor tabel `airport`
--
ALTER TABLE `airport`
  ADD PRIMARY KEY (`IATA`);

--
-- Indexen voor tabel `changes`
--
ALTER TABLE `changes`
  ADD PRIMARY KEY (`Employee_code`,`Luggage_registrationnr`),
  ADD KEY `fk_Changes_lost_Medewerker1_idx` (`Employee_code`),
  ADD KEY `fk_Changes_lost_Verloren_bagage1_idx` (`Luggage_registrationnr`);

--
-- Indexen voor tabel `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customernr`);

--
-- Indexen voor tabel `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`code`),
  ADD KEY `fk_Medewerker_Luchthaven1_idx` (`Luchthaven_IATA`);

--
-- Indexen voor tabel `luggage`
--
ALTER TABLE `luggage`
  ADD PRIMARY KEY (`registrationnr`),
  ADD KEY `fk_airport_IATA_idx` (`airport_IATA`),
  ADD KEY `fk_luggage_customer1_idx` (`customer_customernr`);

--
-- Indexen voor tabel `luggage_damaged`
--
ALTER TABLE `luggage_damaged`
  ADD PRIMARY KEY (`Luggage_registrationnr`);

--
-- Indexen voor tabel `match`
--
ALTER TABLE `match`
  ADD PRIMARY KEY (`Luggage_registrationnr_foud`,`Luggage_registrationnr_lost`),
  ADD KEY `fk_Match_Luggage1_idx` (`Luggage_registrationnr_foud`),
  ADD KEY `fk_Match_Luggage2_idx` (`Luggage_registrationnr_lost`),
  ADD KEY `fk_Match_Medewerker1_idx` (`Employee_code`);

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_Account_Medewerker` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `changes`
--
ALTER TABLE `changes`
  ADD CONSTRAINT `fk_Changes_lost_Medewerker1` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Changes_lost_Verloren_bagage1` FOREIGN KEY (`Luggage_registrationnr`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `fk_Medewerker_Luchthaven1` FOREIGN KEY (`Luchthaven_IATA`) REFERENCES `airport` (`IATA`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `luggage`
--
ALTER TABLE `luggage`
  ADD CONSTRAINT `fk_airport_IATA` FOREIGN KEY (`airport_IATA`) REFERENCES `airport` (`IATA`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_luggage_customer1` FOREIGN KEY (`customer_customernr`) REFERENCES `customer` (`customernr`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `luggage_damaged`
--
ALTER TABLE `luggage_damaged`
  ADD CONSTRAINT `fk_Luggage_damaged_Luggage1` FOREIGN KEY (`Luggage_registrationnr`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `match`
--
ALTER TABLE `match`
  ADD CONSTRAINT `fk_Match_Luggage1` FOREIGN KEY (`Luggage_registrationnr_foud`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Match_Luggage2` FOREIGN KEY (`Luggage_registrationnr_lost`) REFERENCES `luggage` (`registrationnr`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Match_Medewerker1` FOREIGN KEY (`Employee_code`) REFERENCES `employee` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
