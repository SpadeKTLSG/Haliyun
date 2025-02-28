/*
 Navicat Premium Dump SQL

 Source Server         : tMySQL
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : haliyun_money

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/02/2025 16:03:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for currency
-- ----------------------------
DROP TABLE IF EXISTS `currency`;
CREATE TABLE `currency`  (
  `id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `exchange_rate` float NULL DEFAULT NULL COMMENT '和能量币的换算倍率',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '货币' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of currency
-- ----------------------------

-- ----------------------------
-- Table structure for donate
-- ----------------------------
DROP TABLE IF EXISTS `donate`;
CREATE TABLE `donate`  (
  `id` bigint UNSIGNED NOT NULL,
  `nickname` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `money` int NULL DEFAULT NULL COMMENT '献金数',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '留言',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '捐赠' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of donate
-- ----------------------------

-- ----------------------------
-- Table structure for outlet
-- ----------------------------
DROP TABLE IF EXISTS `outlet`;
CREATE TABLE `outlet`  (
  `id` bigint UNSIGNED NOT NULL,
  `status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '状态',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '介绍',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注',
  `type` int NULL DEFAULT NULL COMMENT '活动类型',
  `bonus_exp` bigint NULL DEFAULT NULL COMMENT '奖励经验数值',
  `bonus_coin` bigint NULL DEFAULT NULL COMMENT '奖励硬币数值',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `outlet_pk2`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outlet
-- ----------------------------

-- ----------------------------
-- Table structure for prop
-- ----------------------------
DROP TABLE IF EXISTS `prop`;
CREATE TABLE `prop`  (
  `id` bigint UNSIGNED NOT NULL,
  `type` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '类型',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `price` int NULL DEFAULT NULL COMMENT '价格',
  `stock` int NULL DEFAULT NULL COMMENT '库存',
  `pic` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `attribute` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '属性 (JSON)',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '道具' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prop
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
