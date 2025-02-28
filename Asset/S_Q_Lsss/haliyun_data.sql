/*
 Navicat Premium Dump SQL

 Source Server         : tMySQL
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : haliyun_data

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/02/2025 16:03:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for download_task
-- ----------------------------
DROP TABLE IF EXISTS `download_task`;
CREATE TABLE `download_task`  (
  `id` bigint UNSIGNED NOT NULL,
  `file_id` bigint UNSIGNED NOT NULL,
  `file_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pid` bigint UNSIGNED NOT NULL COMMENT '对应文件目标位置',
  `user_id` bigint UNSIGNED NOT NULL,
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `file_size_total` bigint NULL DEFAULT NULL COMMENT '总共需要下载的大小',
  `file_size_ok` bigint NULL DEFAULT NULL COMMENT '已经下载的大小',
  `executor` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责节点',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '下载任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of download_task
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` bigint UNSIGNED NOT NULL,
  `pid` bigint UNSIGNED NOT NULL COMMENT '父项id',
  `user_id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NOT NULL,
  `name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件类型',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for file_detail
-- ----------------------------
DROP TABLE IF EXISTS `file_detail`;
CREATE TABLE `file_detail`  (
  `id` bigint UNSIGNED NOT NULL,
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '描述',
  `download_time` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '下载次数',
  `size` bigint UNSIGNED NULL DEFAULT NULL COMMENT '文件大小',
  `path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径',
  `disk_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'HDFS磁盘Path',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_detail
-- ----------------------------

-- ----------------------------
-- Table structure for file_func
-- ----------------------------
DROP TABLE IF EXISTS `file_func`;
CREATE TABLE `file_func`  (
  `id` bigint UNSIGNED NOT NULL,
  `tag` bigint UNSIGNED NULL DEFAULT NULL COMMENT '标签',
  `lock` bigint UNSIGNED NULL DEFAULT NULL COMMENT '锁',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '状态',
  `valid_date_type` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '有效期类型',
  `valid_date` date NULL DEFAULT NULL COMMENT '有效期至',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件功能' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_func
-- ----------------------------

-- ----------------------------
-- Table structure for file_tag
-- ----------------------------
DROP TABLE IF EXISTS `file_tag`;
CREATE TABLE `file_tag`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_tag
-- ----------------------------

-- ----------------------------
-- Table structure for upload_task
-- ----------------------------
DROP TABLE IF EXISTS `upload_task`;
CREATE TABLE `upload_task`  (
  `id` bigint UNSIGNED NOT NULL,
  `file_id` bigint UNSIGNED NOT NULL,
  `file_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pid` bigint UNSIGNED NOT NULL COMMENT '对应文件目标位置',
  `user_id` bigint UNSIGNED NOT NULL,
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `file_size_total` bigint NULL DEFAULT NULL COMMENT '总共需要上传的大小',
  `file_size_ok` bigint NULL DEFAULT NULL COMMENT '已经上传的大小',
  `executor` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责节点',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '上传任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of upload_task
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
