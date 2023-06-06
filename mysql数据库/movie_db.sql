/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : movie_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-06-27 23:08:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `area`
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `areaId` int(11) NOT NULL auto_increment,
  `areaName` varchar(20) default NULL,
  PRIMARY KEY  (`areaId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES ('1', '中国大陆');
INSERT INTO `area` VALUES ('2', '中国香港');

-- ----------------------------
-- Table structure for `movie`
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `movieId` int(11) NOT NULL auto_increment,
  `movieTypeObj` int(11) default NULL,
  `movieName` varchar(40) default NULL,
  `moviePhoto` varchar(50) default NULL,
  `director` varchar(20) default NULL,
  `mainPerformer` varchar(40) default NULL,
  `duration` varchar(20) default NULL,
  `areaObj` int(11) default NULL,
  `playTime` varchar(30) default NULL,
  `price` float default NULL,
  `opera` longtext,
  `recommendFlag` varchar(20) default NULL,
  `hitNum` int(11) default NULL,
  PRIMARY KEY  (`movieId`),
  KEY `FK4714F10CFF02515` (`movieTypeObj`),
  KEY `FK4714F10F0A702D9` (`areaObj`),
  CONSTRAINT `FK4714F10CFF02515` FOREIGN KEY (`movieTypeObj`) REFERENCES `movietype` (`typeId`),
  CONSTRAINT `FK4714F10F0A702D9` FOREIGN KEY (`areaObj`) REFERENCES `area` (`areaId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES ('1', '3', '大话西游之大圣娶亲', 'upload/89C178B20B343251A5C1E61AA914C700.jpg', '刘镇伟', '周星驰,朱茵,莫文蔚,蔡少芬,陆树铭,吴孟达', '110分钟', '1', '2018-03-22 19:00', '55', '至尊宝（周星驰 饰）被月光宝盒带回到五百年前，遇见紫霞仙子（朱茵 饰），被对方打上烙印成为对方的人，并发觉自己已变成孙悟空。紫霞与青霞（朱茵 饰）本是如来佛祖座前日月神灯的灯芯（白天是紫霞，晚上是青霞），二人虽然同一肉身却仇恨颇深，因此紫霞立下誓言，谁能拔出她手中的紫青宝剑，谁就是她的意中人。紫青宝剑被至尊宝于不经意间拔出，紫霞决定以身相许，却遭一心记挂白晶晶（莫文蔚 饰）的至尊宝拒绝。后牛魔王救下迷失在沙漠中的紫霞，并逼紫霞与他成婚，关键时刻，至尊宝现身。', '是', '4');
INSERT INTO `movie` VALUES ('2', '2', '水浒传之行者武松', 'upload/A6EB24CB58FE9842D691BC3E2E366150.jpg', '鞠觉亮,王艺', '张涵予,陈龙,杜淳,甘婷婷,刘子赫', '93分钟', '1', '2018-03-24 20:34', '89', '本片主要讲述武松在景阳冈上空手打死一只吊睛白额虎，被乡领奉为打虎英雄，后巧遇兄长武大郎，原来武大娶妻潘金莲，潘金莲试图勾引武松，被拒绝，被当地富户西门庆勾引，奸情败露后，两人毒死了武大郎。为报仇，武松先杀潘金莲再杀西门庆，杀人后，武松到阳谷县县衙自首，判了个刺配孟州。在孟州，武松为报恩结仇于蒋门神，被迫大开杀戒，大闹飞云浦 ，血溅鸳鸯楼，并书“杀人者，打虎武松也”。血溅鸳鸯楼后，为躲避官府抓捕，改作头陀打扮，江湖人称“行者武松”。', '是', '8');

-- ----------------------------
-- Table structure for `movietype`
-- ----------------------------
DROP TABLE IF EXISTS `movietype`;
CREATE TABLE `movietype` (
  `typeId` int(11) NOT NULL auto_increment,
  `typeName` varchar(20) default NULL,
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of movietype
-- ----------------------------
INSERT INTO `movietype` VALUES ('1', '喜剧片');
INSERT INTO `movietype` VALUES ('2', '动作片');
INSERT INTO `movietype` VALUES ('3', '武侠片');

-- ----------------------------
-- Table structure for `orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `orderNo` varchar(20) NOT NULL,
  `movieObj` int(11) default NULL,
  `moviePrice` float default NULL,
  `orderNum` int(11) default NULL,
  `orderPrice` float default NULL,
  `userObj` varchar(20) default NULL,
  `orderTime` varchar(20) default NULL,
  `receiveName` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  `orderStateObj` int(11) default NULL,
  `orderMemo` longtext,
  PRIMARY KEY  (`orderNo`),
  KEY `FK601628FC822B8235` (`movieObj`),
  KEY `FK601628FCC80FC67` (`userObj`),
  KEY `FK601628FC71554D79` (`orderStateObj`),
  CONSTRAINT `FK601628FC71554D79` FOREIGN KEY (`orderStateObj`) REFERENCES `orderstate` (`orderStateId`),
  CONSTRAINT `FK601628FC822B8235` FOREIGN KEY (`movieObj`) REFERENCES `movie` (`movieId`),
  CONSTRAINT `FK601628FCC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES ('user120180124232942', '2', '89', '1', '89', 'user1', '2018-01-24 23:29:42', '双鱼林', '13573598343', '四川成都红星路13号', '2', '快点处理下吧');
INSERT INTO `orderinfo` VALUES ('user120180124233436', '1', '55', '2', '110', 'user1', '2018-01-24 23:34:36', '双鱼林', '13573598343', '四川成都红星路13号', '2', '想看这个');
INSERT INTO `orderinfo` VALUES ('user120180305155822', '2', '89', '2', '178', 'user1', '2018-03-05 15:58:22', '双鱼林', '13573598343', '四川成都红星路13号', '2', '和女朋友看');

-- ----------------------------
-- Table structure for `orderstate`
-- ----------------------------
DROP TABLE IF EXISTS `orderstate`;
CREATE TABLE `orderstate` (
  `orderStateId` int(11) NOT NULL auto_increment,
  `orderStateName` varchar(20) default NULL,
  PRIMARY KEY  (`orderStateId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderstate
-- ----------------------------
INSERT INTO `orderstate` VALUES ('1', '未处理');
INSERT INTO `orderstate` VALUES ('2', '已处理');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', '双鱼林', '男', '2018-01-03 00:00:00', 'upload/B608FE140AAF8B08BDEE89A939B73200.jpg', '13573598343', 'dashen@163.com', '四川成都红星路13号哈', '2017-12-29 14:15:15');
INSERT INTO `userinfo` VALUES ('user2', '123', '王萌萌', '女', '2018-01-14 00:00:00', 'upload/76AA5995F96725C0708BB186B894655C.jpg', '13598308083', 'wagnmm@163.com', '福建福州南平市', '2018-01-24 23:39:18');
