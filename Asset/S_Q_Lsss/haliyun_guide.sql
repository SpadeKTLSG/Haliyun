/*
 Navicat Premium Dump SQL

 Source Server         : tMySQL
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : haliyun_guide

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/02/2025 16:03:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_notice
-- ----------------------------
DROP TABLE IF EXISTS `admin_notice`;
CREATE TABLE `admin_notice`  (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `top` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '置顶',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '管理员公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_notice
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '反馈' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for file_op_log
-- ----------------------------
DROP TABLE IF EXISTS `file_op_log`;
CREATE TABLE `file_op_log`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `file_id` bigint UNSIGNED NOT NULL,
  `op` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '操作类型',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'User账号',
  `file_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件名称',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文件操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_op_log
-- ----------------------------

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'User账号',
  `ipv4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'IPv4的记录',
  `ipv6` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'IPv6的记录',
  `login_type` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '登陆方式',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '登陆日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for platform_status
-- ----------------------------
DROP TABLE IF EXISTS `platform_status`;
CREATE TABLE `platform_status`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_count` bigint NULL DEFAULT NULL COMMENT '用户数统计',
  `group_count` bigint NULL DEFAULT NULL COMMENT '群组数统计',
  `file_count` bigint NULL DEFAULT NULL COMMENT '文件数统计',
  `risk_count` bigint NULL DEFAULT NULL COMMENT '风控数统计',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '平台状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform_status
-- ----------------------------

-- ----------------------------
-- Table structure for req_log
-- ----------------------------
DROP TABLE IF EXISTS `req_log`;
CREATE TABLE `req_log`  (
  `id` bigint UNSIGNED NOT NULL,
  `success` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '是否成功',
  `user_account` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户账户',
  `query_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求类型',
  `route` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求路径',
  `execute_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '计时',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '请求日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_log
-- ----------------------------

-- ----------------------------
-- Table structure for risklog
-- ----------------------------
DROP TABLE IF EXISTS `risklog`;
CREATE TABLE `risklog`  (
  `id` bigint UNSIGNED NOT NULL,
  `header` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `lines` varchar(2068) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '条目id JSON',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '风控' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risklog
-- ----------------------------

-- ----------------------------
-- Table structure for risklog_line
-- ----------------------------
DROP TABLE IF EXISTS `risklog_line`;
CREATE TABLE `risklog_line`  (
  `id` bigint UNSIGNED NOT NULL,
  `header` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '风控条目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risklog_line
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint UNSIGNED NOT NULL,
  `conf` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '配置',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '值',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
