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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `article` */

insert  into `article`(`id`,`regDate`,`updateDate`,`title`,`body`,`memberId`,`boardId`,`hit`) values 
(1,'2020-12-07 10:30:14','2020-12-07 10:30:14','제목1','내용1',1,1,0),
(2,'2020-12-07 10:30:14','2020-12-07 10:30:14','제목2','내용2',1,1,0),
(3,'2020-12-07 10:30:14','2020-12-07 10:30:14','제목3','내용3',1,1,0);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `articleReply` */

/*Table structure for table `board` */

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `name` char(20) COLLATE utf8_unicode_ci NOT NULL,
  `code` char(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `board` */

insert  into `board`(`id`,`regDate`,`updateDate`,`name`,`code`) values 
(1,'2020-12-07 10:30:14','2020-12-07 10:30:14','공지사항','notice'),
(2,'2020-12-07 10:30:14','2020-12-07 10:30:14','자유','free');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `member` */

insert  into `member`(`id`,`regDate`,`updateDate`,`loginId`,`loginPw`,`name`) values 
(1,'2020-12-07 10:30:14','2020-12-07 10:30:14','test1','test1','테스터1'),
(2,'2020-12-07 10:30:14','2020-12-07 10:30:14','test2','test2','테스터2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
