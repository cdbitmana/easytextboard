/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 10.4.6-MariaDB : Database - textBoard
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`textBoard` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `textBoard`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `title` char(200) COLLATE utf8_unicode_ci NOT NULL,
  `body` text COLLATE utf8_unicode_ci NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  `boardId` int(10) unsigned NOT NULL,
  `hit` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `article` */

insert  into `article`(`id`,`regDate`,`updateDate`,`title`,`body`,`memberId`,`boardId`,`hit`) values 
(1,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목1','공지사항 내용1',1,1,0),
(2,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목2','공지사항 내용2',1,1,0),
(3,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목3','공지사항 내용3',1,1,0),
(4,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목4','공지사항 내용4',1,1,0),
(5,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목5','공지사항 내용5',1,1,0),
(6,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목6','공지사항 내용6',1,1,0),
(7,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목7','공지사항 내용7',1,1,0),
(8,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목8','공지사항 내용8',1,1,0),
(9,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목9','공지사항 내용9',1,1,0),
(10,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목10','공지사항 내용10',1,1,0),
(11,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목11','공지사항 내용11',1,1,0),
(12,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목12','공지사항 내용12',1,1,0),
(13,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목13','공지사항 내용13',1,1,0),
(14,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목14','공지사항 내용14',1,1,0),
(15,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목15','공지사항 내용15',1,1,0),
(16,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목16','공지사항 내용16',1,1,0),
(17,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목17','공지사항 내용17',1,1,0),
(18,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목18','공지사항 내용18',1,1,0),
(19,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목19','공지사항 내용19',1,1,0),
(20,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목20','공지사항 내용20',1,1,0),
(21,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목21','공지사항 내용21',1,1,0),
(22,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목22','공지사항 내용22',1,1,0),
(23,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목23','공지사항 내용23',1,1,0),
(24,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목24','공지사항 내용24',1,1,0),
(25,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목25','공지사항 내용25',1,1,0),
(26,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목26','공지사항 내용26',1,1,0),
(27,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목27','공지사항 내용27',1,1,0),
(28,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목28','공지사항 내용28',1,1,0),
(29,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목29','공지사항 내용29',1,1,0),
(30,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목30','공지사항 내용30',1,1,0),
(31,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목1','자유 내용1',1,2,0),
(32,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목2','자유 내용2',1,2,0),
(33,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목3','자유 내용3',1,2,0),
(34,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목4','자유 내용4',1,2,0),
(35,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목5','자유 내용5',1,2,0),
(36,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목6','자유 내용6',1,2,0),
(37,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목7','자유 내용7',1,2,0),
(38,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목8','자유 내용8',1,2,0),
(39,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목9','자유 내용9',1,2,0),
(40,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목10','자유 내용10',1,2,0),
(41,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목11','자유 내용11',1,2,0),
(42,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목12','자유 내용12',1,2,0),
(43,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목13','자유 내용13',1,2,0),
(44,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목14','자유 내용14',1,2,0),
(45,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목15','자유 내용15',1,2,0),
(46,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목16','자유 내용16',1,2,0),
(47,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목17','자유 내용17',1,2,0),
(48,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목18','자유 내용18',1,2,0),
(49,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목19','자유 내용19',1,2,0),
(50,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목20','자유 내용20',1,2,0),
(51,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목21','자유 내용21',1,2,0),
(52,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목22','자유 내용22',1,2,0),
(53,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목23','자유 내용23',1,2,0),
(54,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목24','자유 내용24',1,2,0),
(55,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목25','자유 내용25',1,2,0),
(56,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목26','자유 내용26',1,2,0),
(57,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목27','자유 내용27',1,2,0),
(58,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목28','자유 내용28',1,2,0),
(59,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목29','자유 내용29',1,2,0),
(60,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목30','자유 내용30',1,2,0),
(61,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목31','공지사항 내용31',2,1,0),
(62,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목32','공지사항 내용32',2,1,0),
(63,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목33','공지사항 내용33',2,1,0),
(64,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목34','공지사항 내용34',2,1,0),
(65,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목35','공지사항 내용35',2,1,0),
(66,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목36','공지사항 내용36',2,1,0),
(67,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목37','공지사항 내용37',2,1,0),
(68,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목38','공지사항 내용38',2,1,0),
(69,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목39','공지사항 내용39',2,1,0),
(70,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목40','공지사항 내용40',2,1,0),
(71,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목41','공지사항 내용41',2,1,0),
(72,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목42','공지사항 내용42',2,1,0),
(73,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목43','공지사항 내용43',2,1,0),
(74,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목44','공지사항 내용44',2,1,0),
(75,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목45','공지사항 내용45',2,1,0),
(76,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목46','공지사항 내용46',2,1,0),
(77,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목47','공지사항 내용47',2,1,0),
(78,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목48','공지사항 내용48',2,1,0),
(79,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목49','공지사항 내용49',2,1,0),
(80,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목50','공지사항 내용50',2,1,0),
(81,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목51','공지사항 내용51',2,1,0),
(82,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목52','공지사항 내용52',2,1,0),
(83,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목53','공지사항 내용53',2,1,0),
(84,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목54','공지사항 내용54',2,1,0),
(85,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목55','공지사항 내용55',2,1,0),
(86,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목56','공지사항 내용56',2,1,0),
(87,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목57','공지사항 내용57',2,1,0),
(88,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목58','공지사항 내용58',2,1,0),
(89,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목59','공지사항 내용59',2,1,0),
(90,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목60','공지사항 내용60',2,1,0),
(91,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목61','공지사항 내용61',2,1,0),
(92,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목62','공지사항 내용62',2,1,0),
(93,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목63','공지사항 내용63',2,1,0),
(94,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목64','공지사항 내용64',2,1,0),
(95,'2020-12-14 12:17:19','2020-12-14 12:17:19','공지사항 제목65','공지사항 내용65',2,1,0),
(96,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목31','자유 내용31',2,2,0),
(97,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목32','자유 내용32',2,2,0),
(98,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목33','자유 내용33',2,2,0),
(99,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목34','자유 내용34',2,2,0),
(100,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목35','자유 내용35',2,2,0),
(101,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목36','자유 내용36',2,2,0),
(102,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목37','자유 내용37',2,2,0),
(103,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목38','자유 내용38',2,2,0),
(104,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목39','자유 내용39',2,2,0),
(105,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목40','자유 내용40',2,2,0),
(106,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목41','자유 내용41',2,2,0),
(107,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목42','자유 내용42',2,2,0),
(108,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목43','자유 내용43',2,2,0),
(109,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목44','자유 내용44',2,2,0),
(110,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목45','자유 내용45',2,2,0),
(111,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목46','자유 내용46',2,2,0),
(112,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목47','자유 내용47',2,2,0),
(113,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목48','자유 내용48',2,2,0),
(114,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목49','자유 내용49',2,2,0),
(115,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목50','자유 내용50',2,2,0),
(116,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목51','자유 내용51',2,2,0),
(117,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목52','자유 내용52',2,2,0),
(118,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목53','자유 내용53',2,2,0),
(119,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목54','자유 내용54',2,2,0),
(120,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목55','자유 내용55',2,2,0),
(121,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목56','자유 내용56',2,2,0),
(122,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목57','자유 내용57',2,2,0),
(123,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목58','자유 내용58',2,2,0),
(124,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목59','자유 내용59',2,2,0),
(125,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목60','자유 내용60',2,2,0),
(126,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목61','자유 내용61',2,2,0),
(127,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목62','자유 내용62',2,2,0),
(128,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목63','자유 내용63',2,2,0),
(129,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목64','자유 내용64',2,2,0),
(130,'2020-12-14 12:17:19','2020-12-14 12:17:19','자유 제목65','자유 내용65',2,2,0),
(135,'2020-12-14 12:26:00','2020-12-14 12:26:00','긴 내용 게시물 테스트','Lorem ipsum dolor sit amet consectetur, adipisicing elit. Provident dolore vitae laudantium nostrum officia, eum ea iure quae inventore accusantium autem ducimus odio quis accusamus dignissimos possimus alias commodi saepe. Et aperiam nam ab atque. Dolore ratione sint id porro, tempore qui enim quos ducimus aut ut ipsum eum aspernatur totam consectetur ullam culpa officiis quibusdam consequuntur quaerat, ipsa beatae. Natus eos placeat maiores dolorum velit. Hic dolore obcaecati voluptate libero. Laborum nulla ipsam id in earum porro rerum quam, excepturi iusto explicabo molestias ut? Id aspernatur saepe ipsam consequuntur. Deserunt nulla voluptatum nisi exercitationem possimus autem magni illo, architecto iure mollitia animi eos repellendus libero quos tempore neque, dicta id omnis laudantium tenetur facilis vitae atque sed. Sed, suscipit? Error laborum amet consectetur sapiente sunt quia dolor autem distinctio fuga! Distinctio officiis iste assumenda deleniti repellendus nam obcaecati quas ut, iure magni necessitatibus ratione saepe, tempore sunt ex impedit! Repellat quibusdam aperiam error alias aliquid consequatur nam dicta impedit veniam ipsum? Fugit beatae sequi ipsa obcaecati exercitationem quam, dolores quasi dolor cumque eos nostrum aspernatur perspiciatis laudantium alias velit. Et dolorum porro nam, eius quod eligendi laborum quo nesciunt commodi. Cum maiores dignissimos hic adipisci ut veritatis suscipit molestias quas, dolores officiis aliquam temporibus, molestiae vero consequuntur odit facilis! Molestias cum pariatur amet reprehenderit sapiente, aliquid reiciendis quam cumque facere animi distinctio? Magni beatae quo hic architecto nihil ex placeat laboriosam iste nostrum corrupti, optio quae. Esse, explicabo sapiente! Laudantium molestiae reiciendis, voluptatum quidem numquam vero fugiat. Iusto officiis saepe voluptatem, a facilis obcaecati debitis ipsam dolores magnam distinctio beatae. In provident est sunt earum quisquam rerum cumque perspiciatis? Labore esse ea ipsum aspernatur doloremque omnis fugit doloribus inventore perspiciatis repellat officiis, qui, reprehenderit ex nam quod, numquam facilis consectetur quos repudiandae deleniti quia illo. Velit omnis saepe a! Culpa, doloribus aperiam! Officia quae minus voluptatum iste, placeat perspiciatis autem quam beatae odit sit deleniti. Accusamus facere reprehenderit minima architecto amet sunt, maxime soluta voluptatum quasi voluptates in consequuntur. Incidunt velit, quisquam laboriosam perspiciatis, nesciunt est voluptas atque commodi eveniet assumenda deleniti tenetur sed repudiandae quis eaque illum ex consectetur error? Laboriosam, aut qui? Perspiciatis libero possimus similique consectetur. Rerum placeat facere minus atque labore id eligendi, nisi, pariatur officiis, aspernatur accusantium obcaecati excepturi reiciendis! Fuga corporis consectetur error, reiciendis labore accusamus suscipit sunt vitae ut architecto eos assumenda. Voluptatibus aspernatur incidunt earum aut iure, nobis odit? Labore, placeat error iusto animi sint, quibusdam iure, quasi explicabo neque architecto corporis dolor sequi dolorem alias suscipit amet temporibus nobis nulla! Praesentium, hic autem veritatis error placeat, minus atque quod vero nemo deserunt tempora obcaecati maxime eos, eum repellendus? Ex sunt molestias perspiciatis veritatis. A nobis odio iste nostrum, totam nesciunt. Id maxime, dicta quas culpa similique error nisi quaerat delectus cumque beatae voluptatum tenetur fugiat eos quasi optio ab ea magnam aspernatur nostrum in facilis voluptatem. Placeat ex consequatur eveniet! Ex velit doloribus exercitationem dignissimos? Labore possimus inventore eaque libero! Ratione accusamus fuga iusto inventore illo eius ex? Recusandae perferendis aliquid necessitatibus quas blanditiis rem totam quod ut obcaecati deserunt? Sunt nostrum soluta magni atque ab debitis, possimus natus rerum numquam laboriosam harum assumenda corrupti beatae est vel expedita veritatis ipsam labore recusandae veniam nesciunt perferendis? Veritatis corporis neque doloremque? Eligendi consequuntur distinctio nesciunt laborum molestias, perspiciatis minima aliquam excepturi molestiae id, numquam esse? Sequi corporis accusamus cupiditate ipsam labore voluptatem, enim, dolore molestias assumenda aspernatur tenetur voluptate atque mollitia. Amet nam optio dignissimos numquam atque ratione, reprehenderit saepe, nostrum magni doloribus fugiat, architecto tempora? Non excepturi voluptatibus iste dolor? Ex assumenda, excepturi perspiciatis iusto aperiam inventore mollitia reprehenderit et! Praesentium placeat sint quos, a repellat non cumque qui voluptatem, ipsa architecto deserunt sit sequi delectus quae fuga id dignissimos perspiciatis quaerat ratione ipsum dolore, dolor quis. Deserunt, neque iusto? At temporibus aperiam exercitationem doloremque asperiores, praesentium nulla dignissimos et, corrupti repudiandae quam ipsa mollitia alias culpa illo placeat eum dolorum nisi quisquam facere rem! Sunt neque dignissimos sit? Porro. Optio corporis illo sit alias, modi minima soluta ut ab fugit qui sunt adipisci expedita unde id ad dolor consequuntur accusantium assumenda earum debitis nulla quis pariatur. Dolore, blanditiis eius? Sed similique, ad sit veritatis autem amet. Praesentium labore corporis sunt quo nam sequi, officiis dicta perferendis, sapiente perspiciatis commodi. Corporis nobis magni saepe minus consequatur mollitia, inventore at molestias. Quidem cumque reprehenderit facere delectus corrupti sapiente officiis sit est repellat vel quasi, illum facilis neque odio. Dolorem fuga sint accusamus fugiat, aut ipsa incidunt perspiciatis tempore totam, enim nisi. Incidunt tenetur minus eius quos alias! Voluptatum neque saepe eos deleniti possimus nihil quisquam culpa earum consequatur, dolores porro! In, totam inventore temporibus quidem id quam repellendus assumenda recusandae autem. Laboriosam delectus, eum labore ullam nesciunt sapiente reiciendis est totam, sed error necessitatibus! Quo blanditiis eveniet quisquam dicta, in architecto. Possimus nemo facilis neque illum alias facere hic architecto numquam. Iusto ratione quae, repellendus cum ab hic libero quam in ad aliquid maiores fuga temporibus perferendis commodi earum labore velit. Quos dolorem cum ipsa quo recusandae deleniti asperiores est corrupti? Accusantium nam mollitia corporis reprehenderit veniam commodi nesciunt placeat nisi aliquam et repudiandae minus quibusdam illum consequuntur eaque repellat hic, qui vitae perspiciatis voluptatum suscipit! Laborum illum harum aliquid nesciunt. Ut modi explicabo quos laboriosam, voluptates sequi. Magnam iusto, tempora delectus quod ratione dolore sit, repellendus et assumenda corporis consequuntur porro corrupti saepe earum laudantium tempore a maiores animi aut! Repellat soluta earum assumenda ratione odit fuga maxime, facere, eos maiores voluptate facilis omnis laborum! Aut error sunt quos suscipit veritatis nisi consequuntur fuga non enim, facilis distinctio rerum assumenda. Voluptatum, recusandae iste illo in iusto voluptatem dicta nisi, mollitia similique impedit perspiciatis voluptas beatae atque laboriosam quod corrupti veniam dolorum magni assumenda inventore molestias corporis quasi? Velit, aliquam sit. Autem excepturi suscipit exercitationem labore velit nostrum tempora fugit vel. Dolorem inventore asperiores officiis tempora deserunt eius assumenda explicabo dicta reiciendis saepe tempore recusandae, nam cumque veniam alias, eligendi voluptas. Fugit, cumque! Sint dolores voluptas nostrum minus eos nam deserunt ullam quasi ea expedita, architecto eum sunt totam excepturi aspernatur mollitia soluta repellendus assumenda impedit itaque sed laboriosam voluptate. Voluptate. Temporibus quis veniam consequuntur illum fugit tempore quia repellendus minima ratione vel autem dolorem laborum, sed aspernatur? Quasi beatae atque dolor consectetur dolores omnis commodi repudiandae distinctio, possimus explicabo dolore. Libero aperiam quasi ea magni ipsa. Sint tempora earum ipsam numquam velit officiis fuga! Laudantium commodi dicta laborum, laboriosam aliquam, ullam odio architecto illum, at itaque ipsa facilis! Vitae, expedita. Voluptatum veniam consectetur maxime laboriosam harum quo expedita, esse corporis. Doloribus vitae sequi quia enim nobis, et atque, neque veritatis, cum facilis vel architecto illo odio suscipit amet expedita officiis. Optio eos laborum aut in aperiam unde? Corrupti molestias deserunt temporibus accusamus nostrum porro sed ratione facilis quaerat. Totam tempore odio quis facilis vero, optio illo et eius necessitatibus expedita. Dolorem culpa molestias atque error omnis, quae odit voluptatibus architecto? Est corporis, sed esse mollitia laboriosam animi possimus sequi illum exercitationem rerum assumenda unde adipisci dolorum porro enim fuga reiciendis? Dolorem, molestias. Error voluptas a aut consectetur, saepe vitae sint ipsum iste doloremque cumque veritatis. Iure ratione est nam quaerat, ex reiciendis similique odit quis assumenda nobis voluptatem placeat? Ab? Iste et soluta omnis sapiente sint minima inventore eius, tempore earum eaque corrupti! Officia assumenda necessitatibus molestias iusto velit dolorum earum obcaecati asperiores tenetur eius id, sequi consequatur quod nemo? Officia mollitia ex minus reprehenderit porro rem recusandae enim. Id deserunt quisquam recusandae necessitatibus aut deleniti cumque saepe voluptatibus, delectus cupiditate incidunt molestiae officiis suscipit omnis eligendi. Recusandae, blanditiis inventore. Earum amet sequi maiores saepe doloremque cumque vitae omnis, reiciendis aliquam dignissimos magnam ducimus ea facere magni perferendis officia dolores, fugiat in sunt? Autem quas commodi aliquid esse eius maxime! Molestias asperiores magnam veniam alias nostrum aliquam ullam beatae architecto reprehenderit nihil. Soluta quaerat debitis odio nesciunt doloribus, ipsam modi rerum dolorem delectus fugiat repellendus ab, vitae cum vero nam! Asperiores dicta autem atque sit officiis eaque temporibus porro quod, ullam animi. Obcaecati consectetur, eaque doloremque veniam nam iure ratione earum atque molestias velit illo, magnam ducimus praesentium id debitis! Dolorem nesciunt sequi et suscipit. Porro esse tempora exercitationem totam molestias earum dolorum eos necessitatibus, cumque sit perferendis temporibus sequi? Nisi perspiciatis facere aut est sapiente sunt sequi incidunt laudantium? Doloribus perferendis velit delectus deleniti laborum? Necessitatibus laboriosam repudiandae non expedita. Alias laborum placeat at quam porro obcaecati ut corrupti voluptatibus, maiores atque ex ducimus eligendi tempora veniam enim pariatur! Sint deleniti nisi aut est nulla, quisquam commodi laborum alias animi ipsa facilis modi eveniet ab veniam assumenda provident quos minus vero non ullam molestias dolorum delectus? Dolore, voluptatem nostrum! Eveniet consequuntur odio placeat beatae, eius ipsa necessitatibus numquam laboriosam nisi unde sunt mollitia quis quibusdam blanditiis quidem exercitationem? Pariatur deleniti, rem dolorem expedita aliquid similique delectus autem recusandae natus. Nihil eveniet veritatis inventore repellat enim ducimus dolores recusandae vero temporibus omnis? Maiores, quam aliquam. Temporibus, fugiat asperiores. Asperiores, saepe nam excepturi quisquam rerum vel corporis molestiae quidem odio. Corrupti.',1,1,0),
(136,'2020-12-14 12:26:33','2020-12-14 12:26:33','질문 1','질문 1',1,3,0);

/*Table structure for table `articleRecommand` */

DROP TABLE IF EXISTS `articleRecommand`;

CREATE TABLE `articleRecommand` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `articleId` int(10) unsigned NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `articleRecommand` */

/*Table structure for table `articleReply` */

DROP TABLE IF EXISTS `articleReply`;

CREATE TABLE `articleReply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `body` text COLLATE utf8_unicode_ci NOT NULL,
  `articleId` int(10) unsigned NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `articleReply` */

insert  into `articleReply`(`id`,`regDate`,`updateDate`,`body`,`articleId`,`memberId`) values 
(1,'2020-12-11 12:59:13','2020-12-11 12:59:13','공지사항 제목1 댓글1',1,1),
(2,'2020-12-11 13:11:44','2020-12-11 13:11:44','공지사항 제목1 댓글2',1,2),
(3,'2020-12-11 13:19:16','2020-12-11 13:19:16','긴 내용 게시물의 댓글',43,1);

/*Table structure for table `board` */

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `name` char(20) COLLATE utf8_unicode_ci NOT NULL,
  `code` char(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `board` */

insert  into `board`(`id`,`regDate`,`updateDate`,`name`,`code`) values 
(1,'2020-12-08 11:24:38','2020-12-08 11:24:38','공지사항','notice'),
(2,'2020-12-08 11:24:38','2020-12-08 11:24:38','자유','free'),
(3,'2020-12-10 11:49:25','2020-12-10 11:49:25','질문','qna'),
(4,'2020-12-11 10:51:08','2020-12-11 10:51:08','사진','photo');

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `loginId` char(30) COLLATE utf8_unicode_ci NOT NULL,
  `loginPw` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `name` char(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `member` */

insert  into `member`(`id`,`regDate`,`updateDate`,`loginId`,`loginPw`,`name`) values 
(1,'2020-12-08 11:24:38','2020-12-08 11:24:38','test1','test1','테스터1'),
(2,'2020-12-08 11:24:38','2020-12-08 11:24:38','test2','test2','테스터2'),
(3,'2020-12-11 12:44:37','2020-12-11 12:44:37','test3','test3','테스터3');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
