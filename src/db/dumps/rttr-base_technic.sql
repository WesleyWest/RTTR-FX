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
-- Table structure for table `technic`
--

DROP TABLE IF EXISTS `technic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `technic` (
  `technic_id` int(11) NOT NULL,
  `technic_name` varchar(128) NOT NULL,
  `technic_details` varchar(256) DEFAULT NULL,
  `technic_status` varchar(20) NOT NULL,
  `technic_type` varchar(20) NOT NULL,
  `technic_owner` int(11) NOT NULL,
  `technic_repairer` int(11) DEFAULT NULL,
  PRIMARY KEY (`technic_id`),
  UNIQUE KEY `id_UNIQUE` (`technic_id`),
  KEY `status_key_idx` (`technic_status`),
  KEY `technic_type_key_idx` (`technic_type`),
  KEY `fk_technic_employees1_idx` (`technic_owner`),
  KEY `fk_technic_employees2_idx` (`technic_repairer`),
  CONSTRAINT `fk_technic_employees1` FOREIGN KEY (`technic_owner`) REFERENCES `employees` (`employee_id`),
  CONSTRAINT `fk_technic_employees2` FOREIGN KEY (`technic_repairer`) REFERENCES `employees` (`employee_id`),
  CONSTRAINT `status_key` FOREIGN KEY (`technic_status`) REFERENCES `technic_statuses` (`technic_status`),
  CONSTRAINT `technic_type_key` FOREIGN KEY (`technic_type`) REFERENCES `technic_types` (`technic_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technic`
--

LOCK TABLES `technic` WRITE;
/*!40000 ALTER TABLE `technic` DISABLE KEYS */;
/*!40000 ALTER TABLE `technic` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-10 12:19:58
