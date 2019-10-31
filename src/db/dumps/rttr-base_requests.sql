-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: rttr-base
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'If 0 then the request is active. If 1 then the request is closed.',
  `request_technic_id` int(11) NOT NULL,
  `request_open_date` datetime NOT NULL,
  `request_close_date` datetime DEFAULT NULL,
  `request_problem_description` varchar(512) NOT NULL,
  `request_decision_description` varchar(512) DEFAULT NULL,
  `request_author_id` int(11) NOT NULL,
  `request_closer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `requests_id_UNIQUE` (`request_id`),
  KEY `fk_technic_idx` (`request_technic_id`),
  CONSTRAINT `fk_technic` FOREIGN KEY (`request_technic_id`) REFERENCES `technic` (`technic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (1,0,1,'2019-10-17 10:15:00',NULL,'Не включается, идёт дым',NULL,10,NULL),(2,1,2,'2019-10-14 13:25:00','2019-10-15 09:22:00','Изображение плывёт','Изображение приплыло',11,12),(3,0,5,'2019-10-11 11:32:00',NULL,'Оборван провод',NULL,10,NULL),(4,1,3,'2019-10-21 11:21:00','2019-10-31 17:32:00','Нехорошо пахнет','Побрызгали дезодорантом',12,11);
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-31 17:41:14
