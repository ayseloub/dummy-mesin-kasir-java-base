/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 10.4.27-MariaDB : Database - restoran
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`restoran` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `restoran`;

/*Table structure for table `detailpemesanan` */

DROP TABLE IF EXISTS `detailpemesanan`;

CREATE TABLE `detailpemesanan` (
  `idDetailPemesanan` int(11) NOT NULL AUTO_INCREMENT,
  `idPemesanan` int(11) NOT NULL,
  `idMenu` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  PRIMARY KEY (`idDetailPemesanan`),
  KEY `idPemesanan` (`idPemesanan`),
  KEY `idMenu` (`idMenu`),
  CONSTRAINT `idMenu` FOREIGN KEY (`idMenu`) REFERENCES `menu` (`idMenu`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `idPemesanan` FOREIGN KEY (`idPemesanan`) REFERENCES `pemesanan` (`idPemesanan`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `detailpemesanan` */

insert  into `detailpemesanan`(`idDetailPemesanan`,`idPemesanan`,`idMenu`,`jumlah`) values 
(13,19,1,2),
(14,19,3,2),
(15,20,2,3);

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `idMenu` int(11) NOT NULL AUTO_INCREMENT,
  `NamaMenu` varchar(50) NOT NULL,
  `JenisMenu` enum('Makanan','Minuman') NOT NULL,
  `Harga` int(11) NOT NULL,
  `JumlahStock` int(11) DEFAULT NULL,
  PRIMARY KEY (`idMenu`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `menu` */

insert  into `menu`(`idMenu`,`NamaMenu`,`JenisMenu`,`Harga`,`JumlahStock`) values 
(1,'Nasi Goreng','Makanan',27000,43),
(2,'Nasi Goreng Spesial','Makanan',30000,43),
(3,'Es teh manis','Minuman',4000,67),
(4,'teh manis panas','Minuman',3000,45);

/*Table structure for table `pelanggan` */

DROP TABLE IF EXISTS `pelanggan`;

CREATE TABLE `pelanggan` (
  `idPelanggan` int(11) NOT NULL AUTO_INCREMENT,
  `NamaPelanggan` varchar(50) DEFAULT NULL,
  `noTelepon` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPelanggan`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `pelanggan` */

insert  into `pelanggan`(`idPelanggan`,`NamaPelanggan`,`noTelepon`) values 
(20,'Azarya',81211),
(21,'Budi',9999);

/*Table structure for table `pemesanan` */

DROP TABLE IF EXISTS `pemesanan`;

CREATE TABLE `pemesanan` (
  `idPemesanan` int(11) NOT NULL AUTO_INCREMENT,
  `idPelanggan` int(11) NOT NULL,
  PRIMARY KEY (`idPemesanan`),
  KEY `idPelanggan` (`idPelanggan`),
  CONSTRAINT `idPelanggan` FOREIGN KEY (`idPelanggan`) REFERENCES `pelanggan` (`idPelanggan`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `pemesanan` */

insert  into `pemesanan`(`idPemesanan`,`idPelanggan`) values 
(19,20),
(20,21);

/* Trigger structure for table `detailpemesanan` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `sistemStock` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `sistemStock` AFTER INSERT ON `detailpemesanan` FOR EACH ROW BEGIN

	update restoran.`Menu` set JumlahStock = JumlahStock - new.jumlah where idMenu = new.idMenu;

    END */$$


DELIMITER ;

/* Procedure structure for procedure `Cetak_Struk` */

/*!50003 DROP PROCEDURE IF EXISTS  `Cetak_Struk` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Cetak_Struk`(IN Nama VARCHAR(255))
BEGIN
	
SELECT * FROM struk WHERE `Nama Pelanggan` = Nama;
	
	END */$$
DELIMITER ;

/* Procedure structure for procedure `Lihat Menu` */

/*!50003 DROP PROCEDURE IF EXISTS  `Lihat Menu` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Lihat Menu`()
BEGIN
	
		SELECT * FROM `Daftar Menu`;
	
	END */$$
DELIMITER ;

/* Procedure structure for procedure `masukan pesanan pelanggan` */

/*!50003 DROP PROCEDURE IF EXISTS  `masukan pesanan pelanggan` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `masukan pesanan pelanggan`(in Nama varchar(255), in NamaMenu varchar(255), in Jumlah int)
BEGIN

-- Start a transaction
START TRANSACTION;

-- Step 1: Retrieve idPemesanan from pemesanan table
SELECT p.idPemesanan INTO @pemesanan_id
FROM pemesanan p
JOIN pelanggan pl ON p.idPelanggan = pl.idPelanggan
WHERE pl.NamaPelanggan = Nama;

-- Step 2: Retrieve idMenu from menu table
SELECT idMenu INTO @menu_id FROM menu WHERE `NamaMenu` = NamaMenu;

-- Step 3: Insert into detailpemesanan table
INSERT INTO restoran.detailpemesanan (idPemesanan, idMenu, jumlah)
VALUES (@pemesanan_id, @menu_id, Jumlah);

-- Commit the transaction
COMMIT;


	END */$$
DELIMITER ;

/* Procedure structure for procedure `Pelanggan_Baru` */

/*!50003 DROP PROCEDURE IF EXISTS  `Pelanggan_Baru` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Pelanggan_Baru`(in Nama Varchar(255), in nohp int)
BEGIN
	INSERT INTO restoran.`pelanggan`(NamaPelanggan, noTelepon)  
VALUE (Nama, nohp);
	END */$$
DELIMITER ;

/* Procedure structure for procedure `Tambah pesanan pelanggan` */

/*!50003 DROP PROCEDURE IF EXISTS  `Tambah pesanan pelanggan` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Tambah pesanan pelanggan`(in nama varchar(255))
BEGIN
-- Step 1: Retrieve idPelanggan from pelanggan table
SELECT idPelanggan INTO @pelanggan_id FROM pelanggan WHERE `namaPelanggan` = nama;

-- Step 2: Insert into pemesanan table
INSERT INTO restoran.pemesanan (idPelanggan) VALUES (@pelanggan_id);


	END */$$
DELIMITER ;

/*Table structure for table `daftarmenu` */

DROP TABLE IF EXISTS `daftarmenu`;

/*!50001 DROP VIEW IF EXISTS `daftarmenu` */;
/*!50001 DROP TABLE IF EXISTS `daftarmenu` */;

/*!50001 CREATE TABLE  `daftarmenu`(
 `Jenis` enum('Makanan','Minuman') ,
 `sisa Barang` int(11) ,
 `Nama Menu` varchar(50) ,
 `Harga` int(11) 
)*/;

/*Table structure for table `struk` */

DROP TABLE IF EXISTS `struk`;

/*!50001 DROP VIEW IF EXISTS `struk` */;
/*!50001 DROP TABLE IF EXISTS `struk` */;

/*!50001 CREATE TABLE  `struk`(
 `idPemesanan` int(11) ,
 `idDetailPemesanan` int(11) ,
 `Nama Pelanggan` varchar(50) ,
 `Item` varchar(50) ,
 `jumlah` int(11) ,
 `Harga` varchar(15) ,
 `TotHarga` varchar(25) ,
 `TOTAlALL` varbinary(47) 
)*/;

/*Table structure for table `totalbelanjapelanggan` */

DROP TABLE IF EXISTS `totalbelanjapelanggan`;

/*!50001 DROP VIEW IF EXISTS `totalbelanjapelanggan` */;
/*!50001 DROP TABLE IF EXISTS `totalbelanjapelanggan` */;

/*!50001 CREATE TABLE  `totalbelanjapelanggan`(
 `idPemesanan` int(11) ,
 `idPelanggan` int(11) ,
 `NamaPelanggan` varchar(50) ,
 `Total Belanja` varbinary(47) 
)*/;

/*View structure for view daftarmenu */

/*!50001 DROP TABLE IF EXISTS `daftarmenu` */;
/*!50001 DROP VIEW IF EXISTS `daftarmenu` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `daftarmenu` AS (select `menu`.`JenisMenu` AS `Jenis`,`menu`.`JumlahStock` AS `sisa Barang`,`menu`.`NamaMenu` AS `Nama Menu`,`menu`.`Harga` AS `Harga` from `menu`) */;

/*View structure for view struk */

/*!50001 DROP TABLE IF EXISTS `struk` */;
/*!50001 DROP VIEW IF EXISTS `struk` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `struk` AS (select `pemesanan`.`idPemesanan` AS `idPemesanan`,`detailpemesanan`.`idDetailPemesanan` AS `idDetailPemesanan`,`pelanggan`.`NamaPelanggan` AS `Nama Pelanggan`,`menu`.`NamaMenu` AS `Item`,`detailpemesanan`.`jumlah` AS `jumlah`,concat('RP. ',`menu`.`Harga`) AS `Harga`,concat('Rp. ',`menu`.`Harga` * `detailpemesanan`.`jumlah`) AS `TotHarga`,concat('Rp. ',(select sum(`menu`.`Harga` * `detailpemesanan`.`jumlah`) from ((`pemesanan` join `detailpemesanan` on(`pemesanan`.`idPemesanan` = `detailpemesanan`.`idPemesanan`)) join `menu` on(`detailpemesanan`.`idMenu` = `menu`.`idMenu`)))) AS `TOTAlALL` from (((`pemesanan` join `pelanggan` on(`pemesanan`.`idPelanggan` = `pelanggan`.`idPelanggan`)) join `detailpemesanan` on(`pemesanan`.`idPemesanan` = `detailpemesanan`.`idPemesanan`)) join `menu` on(`detailpemesanan`.`idMenu` = `menu`.`idMenu`))) */;

/*View structure for view totalbelanjapelanggan */

/*!50001 DROP TABLE IF EXISTS `totalbelanjapelanggan` */;
/*!50001 DROP VIEW IF EXISTS `totalbelanjapelanggan` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `totalbelanjapelanggan` AS select `pemesanan`.`idPemesanan` AS `idPemesanan`,`pelanggan`.`idPelanggan` AS `idPelanggan`,`pelanggan`.`NamaPelanggan` AS `NamaPelanggan`,concat('Rp. ',(select sum(`menu`.`Harga` * `detailpemesanan`.`jumlah`) from (`detailpemesanan` join `menu` on(`detailpemesanan`.`idMenu` = `menu`.`idMenu`)) where `detailpemesanan`.`idPemesanan` = `pemesanan`.`idPemesanan`)) AS `Total Belanja` from (`pemesanan` join `pelanggan` on(`pemesanan`.`idPelanggan` = `pelanggan`.`idPelanggan`)) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
