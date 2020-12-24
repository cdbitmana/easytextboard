-- MariaDB dump 10.18  Distrib 10.4.17-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: textBoard
-- ------------------------------------------------------
-- Server version	10.4.17-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `title` char(200) NOT NULL,
  `body` text NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  `boardId` int(10) unsigned NOT NULL,
  `hit` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'2020-12-17 13:28:21','2020-12-17 13:28:21','자바) Switch 구문','# SWITCH\r\n```java\r\nswitch (변수명) {\r\n          case 조건1:\r\n          실행코드1;\r\n\r\n          case 조건2:\r\n          실행코드2;\r\n\r\n          default:\r\n          실행코드3;\r\n}\r\n```\r\n\r\n변수가 \r\n조건1에 참이라면 실행코드1부터 실행코드3까지 순차적으로 실행\r\n조건2에 참이라면 실행코드2부터 실행코드3까지 순차적으로 실행\r\n조건 1과 조건2에 모두 맞지 않으면 실행코드3만 실행\r\n\r\n\r\n조건에 맞는 실행코드만 실행하고 싶다면 break; 를 넣어준다.\r\n# 예시\r\n```java\r\nint value = 1;\r\n\r\nswitch (value) {\r\n          case 1 :\r\n          System.out.println(\"1\");\r\n          break;\r\n\r\n          case 2 :\r\n          System.out.println(\"2\");\r\n          break;\r\n\r\n          case 3 :\r\n          System.out.println(\"3\");\r\n          break;\r\n\r\n         default :\r\n         System.out.println(\"그 외의 숫자\");\r\n}\r\n```\r\n',1,1,0),(2,'2020-12-17 15:25:02','2020-12-17 15:25:59','자바) 배열 정렬하는 식','```java\r\nint[] arr = new int[N];\r\n\r\n  for (int i = 0 ; i < arr.length ; i++) {\r\n    for (int j = 0 ; j < arr.length-i-1 ; j++) {\r\n      if(arr[j] < arr[j+1]){\r\n          int temp = arr[j+1];\r\n          arr[j+1] = arr[j];\r\n          arr[j] = temp;\r\n      }  \r\n    }\r\n  }\r\n```\r\n\r\n배열의 앞 뒤 값을  비교해서 작은 값을 뒤로 보내고, 다시 앞 뒤로 비교한다.\r\n이 과정을 반복해서 제일 작은 값은 마지막 위치로 가게 되고 제일 마지막 값은 이미 제일 작은 값이므로 또다시 비교할 필요가 없다.\r\n이 과정을 다시 반복하면 배열은 내림차순으로 정렬된다.',1,1,0),(3,'2020-12-17 15:25:02','2020-12-17 15:25:59','MySQL) 문자열 합치기 CONCAT','# CONCAT\r\n```MySql\r\nCONCAT(\'문\',\'자\',\'열\')\r\n```\r\n-> \'문자열\'\r\n\r\n',1,1,0),(4,'2020-12-17 15:25:02','2020-12-17 15:25:59','MySQL) INNER JOIN','# INNER JOIN\r\n```mysql\r\nSELECT * FROM 테이블명1\r\nINNER JOIN 테이블명2\r\nON 테이블명1.컬럼명 = 테이블명2.컬럼명;\r\n```\r\n테이블명1.컬럼과 테이블명2.컬럼의 값이 같은 것들만 합쳐져 나온다.',1,1,0),(5,'2020-12-20 09:06:01','2020-12-20 09:06:01','자바) 폴더와 하위파일 삭제하는 메소드','# 폴더와 하위파일 삭제하는 메소드\r\n```java\r\npublic static void deleteDir(String filePath) { \r\n\r\n          File deleteFolder = new File(filePath); \r\n\r\n               if(deleteFolder.exists()) { \r\n                   File[] deleteFolderList = deleteFolder.listFiles();\r\n                           for(int i = 0 ; i < deleteFolderList.length; i++) {\r\n                                   if(deleteFolderList[i].isFile()) { \r\n                                        deleteFolderList[i].delete();\r\n                                    } else {\r\n                                     deleteDir(deleteFolderList[i].getPath()); \r\n                                    } \r\n                                    deleteFolderList[i].delete();\r\n                               } deleteFolder.delete(); \r\n                       } \r\n              }\r\n```\r\n',1,1,0),(6,'2020-12-21 21:45:32','2020-12-21 21:45:34','HTML,CSS) box-sizing 속성','content-box 는 기본으로 적용되는 키워드.\r\n\r\nwidth속성과 height속성이 글자가 들어가는 영역의 크기를 지정하게 만든다.\r\n\r\n(width:100px , height:100px 인 영역에 border,margin,padding등의 요소가 추가되어도 width와 height는 100px로 고정)\r\n\r\n(따라서 태그의 전체 영역은 width,height의 크기에서 border,margin,padding만큼 커지게 된다.)\r\n\r\n \r\n\r\nborder-box 는 width속성과 height속성이 테두리를 포함한 영역의 크기를 지정하게 만든다.\r\n\r\n(width:100px , height:100px 인 영역에 border,margin,padding등의 요소가 추가되면 border와 padding의 크기만큼 width와 height가 줄어든다. margin은 영역의 바깥에 여백을 주는것이기 때문에 상관이 없다.)\r\n\r\n',1,1,0),(7,'2020-12-21 21:55:28','2020-12-21 21:55:30','JAVASCRIPT) replace를 replaceAll처럼 사용하기 😀','자바스크립트에는 replaceAll 이 없다.\r\nreplaceAll처럼 쓰려면 정규식표현을 사용.\r\n```javascript\r\nstring.replace(\"#\" , \"\"); \r\nstring.replace9(/#/gi , \"\");\r\n```\r\n\r\nreplace 함수에서와 같이 사용하되, 따옴표를 / 슬래시로 대체하고, 뒤에 gi 를 붙이면 replaceAll() 과 같은 기능을 한다.\r\n\r\n* g : 발생할 모든 패턴에 대한 전역 검색\r\n\r\n* i : 대/소문자 구분 안함\r\n\r\n* m : 여러 줄 검색\r\n\r\n😀😀',1,1,0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articleRecommend`
--

DROP TABLE IF EXISTS `articleRecommend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articleRecommend` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `articleId` int(10) unsigned NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articleRecommend`
--

LOCK TABLES `articleRecommend` WRITE;
/*!40000 ALTER TABLE `articleRecommend` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleRecommend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articleReply`
--

DROP TABLE IF EXISTS `articleReply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articleReply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `body` text NOT NULL,
  `articleId` int(10) unsigned NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articleReply`
--

LOCK TABLES `articleReply` WRITE;
/*!40000 ALTER TABLE `articleReply` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleReply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `name` char(20) NOT NULL,
  `code` char(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'2020-12-18 10:17:45','2020-12-18 10:17:45','IT','it');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guestBook`
--

DROP TABLE IF EXISTS `guestBook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guestBook` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `title` text NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guestBook`
--

LOCK TABLES `guestBook` WRITE;
/*!40000 ALTER TABLE `guestBook` DISABLE KEYS */;
INSERT INTO `guestBook` VALUES (1,'2020-12-22 13:56:37','2020-12-22 13:56:40','“좋은 프로그래머 대부분은 돈이나 대중에게 받을 찬사를 기대하고 프로그래밍을 하지 않고 프로그래밍이 재미 있어서 한다.”\r\n\r\n- 리누스 베네딕트 토르발스(Linus Benedict Torvalds)',1),(2,'2020-12-22 13:58:06','2020-12-22 13:58:08','\"손으로 10초면 충분히 할 수 있는 일을\r\n컴퓨터로 하루 종일 프로그래밍해서 자동으로 수행할 때,\r\n나는 더할 나위 없이 큰 행복을 느낀다.\"\r\n\r\n- 더글러스 노엘 애덤스(Douglas Noel Adams)',1),(3,'2020-12-22 13:59:14','2020-12-22 13:59:16','\"만약 당신이 코딩을 할 수 있게 된다면, 앉은 자리에서 무언가를 만들어 낼 수 있고, 아무도 당신을 막을 수 없을 것이다.\"\r\n\r\n- 마크 저커버그(Mark Zuckerberg)',1);
/*!40000 ALTER TABLE `guestBook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL,
  `updateDate` datetime NOT NULL,
  `loginId` char(30) NOT NULL,
  `loginPw` varchar(50) NOT NULL,
  `name` char(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'2020-12-17 13:27:07','2020-12-17 13:27:07','admin','admin','이명학');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-23 15:05:54
