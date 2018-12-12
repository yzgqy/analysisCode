/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost:3306
 Source Schema         : db_code

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : 65001

 Date: 12/12/2018 16:24:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classnode
-- ----------------------------
DROP TABLE IF EXISTS `classnode`;
CREATE TABLE `classnode` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `calleeTimes` int(255) DEFAULT NULL,
  `callerTimes` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for edge
-- ----------------------------
DROP TABLE IF EXISTS `edge`;
CREATE TABLE `edge` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `sourceid` int(255) DEFAULT NULL,
  `targetid` int(255) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
