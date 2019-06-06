-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: heladeria
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `compra` (
  `COM_Codigo` int(11) NOT NULL,
  `COM_Fecha` datetime NOT NULL,
  `USU_Codigo` int(11) NOT NULL,
  `usuario_USU_Codigo` int(11) NOT NULL,
  PRIMARY KEY (`COM_Codigo`,`usuario_USU_Codigo`),
  KEY `fk_compra_usuario1_idx` (`usuario_USU_Codigo`),
  CONSTRAINT `fk_compra_usuario1` FOREIGN KEY (`usuario_USU_Codigo`) REFERENCES `usuario` (`USU_Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `helado`
--

DROP TABLE IF EXISTS `helado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `helado` (
  `HEL_Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `HEL_Tipo` varchar(45) NOT NULL,
  `HEL_Precio` decimal(10,2) NOT NULL,
  `HEL_Sabor` varchar(45) NOT NULL,
  `HEL_Cantidad` int(11) NOT NULL,
  PRIMARY KEY (`HEL_Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `helado`
--

LOCK TABLES `helado` WRITE;
/*!40000 ALTER TABLE `helado` DISABLE KEYS */;
/*!40000 ALTER TABLE `helado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `helado-compra`
--

DROP TABLE IF EXISTS `helado-compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `helado-compra` (
  `COM_Codigo` int(11) NOT NULL,
  `helado_HEL_Codigo` int(11) NOT NULL,
  `compra_COM_Codigo` int(11) NOT NULL,
  PRIMARY KEY (`helado_HEL_Codigo`,`compra_COM_Codigo`),
  KEY `fk_helado-compra_helado1_idx` (`helado_HEL_Codigo`),
  KEY `fk_helado-compra_compra1_idx` (`compra_COM_Codigo`),
  CONSTRAINT `fk_helado-compra_compra1` FOREIGN KEY (`compra_COM_Codigo`) REFERENCES `compra` (`COM_Codigo`),
  CONSTRAINT `fk_helado-compra_helado1` FOREIGN KEY (`helado_HEL_Codigo`) REFERENCES `helado` (`HEL_Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `helado-compra`
--

LOCK TABLES `helado-compra` WRITE;
/*!40000 ALTER TABLE `helado-compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `helado-compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oferta`
--

DROP TABLE IF EXISTS `oferta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `oferta` (
  `OFE_Codigo` int(11) NOT NULL AUTO_INCREMENT,
  `HEL_Duracion` datetime NOT NULL,
  `HEL_Precio` decimal(10,2) NOT NULL,
  `helado_HEL_Codigo` int(11) NOT NULL,
  PRIMARY KEY (`OFE_Codigo`,`helado_HEL_Codigo`),
  KEY `fk_oferta_helado1_idx` (`helado_HEL_Codigo`),
  CONSTRAINT `fk_oferta_helado1` FOREIGN KEY (`helado_HEL_Codigo`) REFERENCES `helado` (`HEL_Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oferta`
--

LOCK TABLES `oferta` WRITE;
/*!40000 ALTER TABLE `oferta` DISABLE KEYS */;
/*!40000 ALTER TABLE `oferta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `USU_Codigo` int(11) NOT NULL,
  `USU_Nombre` varchar(45) NOT NULL,
  `USU_Apellido` varchar(45) NOT NULL,
  `USU_Contraseña` varchar(45) NOT NULL,
  `USU_Dni` int(11) NOT NULL,
  `USU_Email` varchar(45) NOT NULL,
  PRIMARY KEY (`USU_Codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-05 23:16:40
