-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: dasauto
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `car_suv`
--

DROP TABLE IF EXISTS `car_suv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_suv` (
  `CAR_ID` varchar(7) NOT NULL,
  `CAR_NO` varchar(10) NOT NULL,
  `CAR_NAME` varchar(15) NOT NULL,
  `MILAGE` decimal(5,2) NOT NULL,
  `ENGINE_CAP` varchar(10) NOT NULL,
  `PRICE` decimal(12,2) NOT NULL,
  `YEAR_MNFCTRE` varchar(256) NOT NULL,
  `STAT_US` varchar(50) NOT NULL,
  `CAR_RENTAL_RATE` decimal(7,2) NOT NULL,
  `QUANTITY` varchar(35) DEFAULT '0',
  PRIMARY KEY (`CAR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_suv`
--

LOCK TABLES `car_suv` WRITE;
/*!40000 ALTER TABLE `car_suv` DISABLE KEYS */;
INSERT INTO `car_suv` VALUES ('02','0001','FORTUNER',20.00,'2000',15000.00,'2022','avaliable',1500.00,'1'),('14','1234','aaaa',12.00,'122',1222.00,'12212','12',12121.00,'0'),('32','gj09','creta',35.00,'900',2024.00,'1200','1',20000.00,'0'),('42','678','ghh',99.00,'344',20119.00,'233','3',122.00,'0'),('55','23','compass',44.00,'22',200.00,'2024','1',566.00,'0'),('78','gj01','jeep',24.00,'135',2012.00,'345','1',500.00,'0'),('89','233','ee',333.00,'2332',2019.00,'133','2',122.00,'0'),('90','344','fgh',455.00,'888',2019.00,'7587','5',2000.00,'0'),('99','MH04FZ5981','Maruti Suzuki',20.00,'125',2012.00,'2','1',5000.00,'0');
/*!40000 ALTER TABLE `car_suv` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-09 12:46:42
