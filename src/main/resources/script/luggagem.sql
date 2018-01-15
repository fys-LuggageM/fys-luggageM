-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Gegenereerd op: 16 jan 2018 om 00:10
-- Serverversie: 10.1.9-MariaDB
-- PHP-versie: 5.6.15

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
(500500, 'pathedude@gmail.com', '6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B', 'F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9', 3, 1),
(500501, 'admin@corendol.nl', '6F71EF86333B48BDAD9CC03E697A58FEC0E53E7BA4B2C7A33593D34D1263775B', 'F9064373F9B3C8F0A1C610DA2346341F89062E58055B10480A19AD965273DEE9', 3, 1);

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
('AMS', 'Amsterdam Schiphol Airport', 'Netherlands', 'GMT+1', 'Amsterdam'),
('RTM', 'Rotterdam Airport', 'Netherlands', 'GMT+1', 'Rotterdam');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `changes`
--

CREATE TABLE `changes` (
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Employee_code` int(11) NOT NULL,
  `Luggage_registrationnr` int(11) NOT NULL,
  `changeid` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `changes`
--

INSERT INTO `changes` (`date`, `Employee_code`, `Luggage_registrationnr`, `changeid`) VALUES
('2018-01-15 23:47:57', 500500, 1, 0),
('2018-01-15 23:49:15', 500500, 2, 0),
('2018-01-15 23:50:38', 500500, 3, 0),
('2018-01-15 23:53:15', 500500, 4, 0),
('2018-01-15 23:57:49', 500500, 5, 0),
('2018-01-16 00:00:00', 500500, 6, 0),
('2018-01-16 00:04:28', 500500, 7, 0),
('2018-01-16 00:05:24', 500500, 8, 0),
('2018-01-16 00:05:49', 500500, 9, 0),
('2018-01-16 00:07:29', 500500, 10, 0),
('2018-01-16 00:08:28', 500500, 11, 0),
('2018-01-16 00:09:27', 500500, 12, 0);

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
(1, 'Joris', '', 'Ebbelaar', 'Pleasantpark', 'Assendelft', '0420XD', 'Netherlands', '0612345678', 'joris-ebbelaar@hotmail.nl'),
(2, 'Jan', 'van', 'Test', 'Lonelylodge', 'Amserdam', '1234CP', 'Netherlands', '0612345678', 'joris-ebbelaar@hotmail.nl'),
(3, 'Loes', 'van', 'Ackema', 'Greasygroove', 'Haarlem', '1532GT', 'Netherlands', '0612345678', 'joris-ebbelaar@hotmail.nl');

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
  `location_found` varchar(45) DEFAULT NULL,
  `destination` varchar(45) DEFAULT NULL,
  `luggage_type` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `primary_color` varchar(45) DEFAULT NULL,
  `secondary_color` varchar(45) DEFAULT NULL,
  `notes` longtext,
  `size` varchar(45) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  `case_type` int(11) DEFAULT NULL,
  `customer_firstname` varchar(45) DEFAULT NULL,
  `customer_preposition` varchar(45) DEFAULT NULL,
  `customer_lastname` varchar(45) DEFAULT NULL,
  `case_status` tinyint(1) NOT NULL DEFAULT '1',
  `airport_IATA` varchar(5) DEFAULT NULL,
  `customer_customernr` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Gegevens worden geëxporteerd voor tabel `luggage`
--

INSERT INTO `luggage` (`registrationnr`, `date`, `flightnr`, `labelnr`, `location_found`, `destination`, `luggage_type`, `brand`, `primary_color`, `secondary_color`, `notes`, `size`, `weight`, `case_type`, `customer_firstname`, `customer_preposition`, `customer_lastname`, `case_status`, `airport_IATA`, `customer_customernr`) VALUES
(1, '2018-01-15 23:47:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Overduidelijke schade!', NULL, NULL, 3, 'Joris', '', 'Ebbelaar', 1, 'AMS', 1),
(2, '2018-01-15 23:49:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Geen opmerkingen.', NULL, NULL, 3, 'Jan', 'van', 'Test', 1, 'RTM', 2),
(3, '2018-01-15 23:50:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Schade haast niet zichtbaar.', NULL, NULL, 3, 'Loes', 'van', 'Ackema', 1, 'AMS', 3),
(4, '2018-01-15 23:53:15', '30122', '23145662', 'Toilet', 'Amsterdam', 'Bag', 'Eastpack', 'Black', 'Orange', '', '100x80x20', '5', 1, 'Jan', 'van', 'Test', 1, 'AMS', NULL),
(5, '2018-01-15 23:57:49', '43123', '562716238', 'Belt-03', 'Haarlem', 'Suitcase', 'louis vuitton', 'Black', 'Yellow', '', '50x50x50', '15', 1, 'Loes', 'van', 'Ackema', 1, 'AMS', NULL),
(6, '2018-01-16 00:00:00', '321356', '34256745', NULL, 'Madrid', 'Suitcase', 'samsonite', 'Black', 'Black', 'Rood lint om het handvat.', NULL, NULL, 2, 'Joris', '', 'Ebbelaar', 1, 'AMS', NULL),
(7, '2018-01-16 00:04:28', '32145', '123451523', 'Belt-02', 'Amsterdam', 'Suitcase', 'samsonite', 'Black', 'Black', '', '50x50x50', '25', 1, 'Jan', 'van', 'Straaten', 1, 'AMS', NULL),
(8, '2018-01-16 00:05:24', '', '', 'Belt-01', '', 'Suitcase', 'samsonite', 'Black', 'Black', '', '10x10x10', '10', 1, '', '', '', 1, 'AMS', NULL),
(9, '2018-01-16 00:05:49', '', '', 'Belt-01', '', 'Bagpack', 'eastpack', 'Black', 'Darkblue', '', '100x50x20', '20', 1, '', '', '', 1, 'RTM', NULL),
(10, '2018-01-16 00:07:29', '12345', '12345678', 'Belt-01', '', 'Bag', 'samsonite', 'Darkbrown', 'Darkbrown', '', '10x10x10', '10', 1, '', '', '', 1, 'AMS', NULL),
(11, '2018-01-16 00:08:28', '12345', '12345678', 'Belt-01', '', 'Suitcase', 'samsonite', 'Darkbrown', 'Darkbrown', '', '10x10x10', '10', 1, '', '', '', 1, 'AMS', NULL),
(12, '2018-01-16 00:09:27', '12345', '8674325', NULL, 'Portugal', 'Bagpack', 'samsonite', 'Blue', 'Blue', NULL, NULL, NULL, 2, 'Jan', 'van', 'Test', 1, 'RTM', NULL);

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

--
-- Gegevens worden geëxporteerd voor tabel `luggage_damaged`
--

INSERT INTO `luggage_damaged` (`image01`, `image02`, `image03`, `Luggage_registrationnr`) VALUES
(NULL, NULL, NULL, 1),
(NULL, NULL, NULL, 2),
(NULL, NULL, NULL, 3);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `matches`
--

CREATE TABLE `matches` (
  `registrationnr` int(11) NOT NULL,
  `selectedRegNr` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `case_status` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
-- Indexen voor tabel `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`registrationnr`);

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
-- Beperkingen voor tabel `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `FK_matchingNr` FOREIGN KEY (`registrationnr`) REFERENCES `luggage` (`registrationnr`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
