/*
 Navicat Premium Dump SQL

 Source Server         : tMySQL
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : haliyun_group

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/02/2025 16:03:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_lock
-- ----------------------------
DROP TABLE IF EXISTS `file_lock`;
CREATE TABLE `file_lock`  (
  `id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NOT NULL COMMENT '群组',
  `owner_id` bigint UNSIGNED NOT NULL COMMENT '拥有者',
  `owner_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '拥有者账号',
  `currency_type` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '货币类型',
  `lock_type` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '锁类型',
  `level_limit` int NULL DEFAULT NULL COMMENT '等级限制',
  `cost` int NULL DEFAULT NULL COMMENT '花费货币个数',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文件锁' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_lock
-- ----------------------------

-- ----------------------------
-- Table structure for file_status
-- ----------------------------
DROP TABLE IF EXISTS `file_status`;
CREATE TABLE `file_status`  (
  `id` bigint UNSIGNED NOT NULL,
  `file_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件类型',
  `type_remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件类型介绍',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `count` bigint NULL DEFAULT NULL COMMENT '计数',
  `space` bigint NULL DEFAULT NULL COMMENT '占用容量(字节)',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群组文件状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_status
-- ----------------------------

-- ----------------------------
-- Table structure for cluster
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '群组名称',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '群组昵称',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片',
  `pop_volume` int NULL DEFAULT 32 COMMENT '群组容量',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `group_pk2`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cluster
-- ----------------------------

-- ----------------------------
-- Table structure for group_auth
-- ----------------------------
DROP TABLE IF EXISTS `group_auth`;
CREATE TABLE `group_auth`  (
  `id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NULL DEFAULT NULL,
  `user_id` bigint UNSIGNED NULL DEFAULT NULL,
  `can_kick` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '踢人权限',
  `can_invite` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '邀请权限',
  `can_upload` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '上传权限',
  `can_download` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '下载权限',
  `status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '此权限启用情况',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群组成员权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_auth
-- ----------------------------

-- ----------------------------
-- Table structure for group_detail
-- ----------------------------
DROP TABLE IF EXISTS `group_detail`;
CREATE TABLE `group_detail`  (
  `id` bigint UNSIGNED NOT NULL,
  `share_link` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分享链接',
  `album` varchar(4068) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '相册JSON',
  `used_space` bigint NULL DEFAULT NULL COMMENT '使用容量',
  `total_space` bigint NULL DEFAULT NULL COMMENT '总容量',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群组详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_detail
-- ----------------------------

-- ----------------------------
-- Table structure for group_func
-- ----------------------------
DROP TABLE IF EXISTS `group_func`;
CREATE TABLE `group_func`  (
  `id` bigint UNSIGNED NOT NULL,
  `notice_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '通知ids',
  `currency_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '流通的货币类型',
  `allow_invite` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '允许加入',
  `currency_stock` bigint NULL DEFAULT 0 COMMENT '货币储备',
  `coin_stock` bigint NULL DEFAULT 0 COMMENT '硬币储备',
  `remarks` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '群组评论集合',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群组功能' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_func
-- ----------------------------

-- ----------------------------
-- Table structure for group_level_prefix
-- ----------------------------
DROP TABLE IF EXISTS `group_level_prefix`;
CREATE TABLE `group_level_prefix`  (
  `id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NOT NULL COMMENT '对象群组',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '等级配置名称',
  `level_start` int NOT NULL COMMENT '对应开始等级',
  `level_end` int NOT NULL COMMENT '对应结束等级',
  `polish` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '美化的名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '群组等级前缀' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_level_prefix
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `read_count` int NULL DEFAULT NULL COMMENT '阅读次数',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NULL DEFAULT NULL,
  `group_id` bigint UNSIGNED NULL DEFAULT NULL,
  `person_show` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '同步个人主页',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `pics` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片对象(,)',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT NULL,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '动态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for remark
-- ----------------------------
DROP TABLE IF EXISTS `remark`;
CREATE TABLE `remark`  (
  `id` bigint UNSIGNED NOT NULL,
  `type` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '类型',
  `target_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '具体类型id',
  `likes` bigint NULL DEFAULT NULL COMMENT '点赞数',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of remark
-- ----------------------------

-- ----------------------------
-- Table structure for share_link
-- ----------------------------
DROP TABLE IF EXISTS `share_link`;
CREATE TABLE `share_link`  (
  `id` bigint UNSIGNED NOT NULL,
  `file_id` bigint UNSIGNED NOT NULL,
  `god_account` bigint UNSIGNED NOT NULL COMMENT '分享者账号',
  `expire_date` date NULL DEFAULT NULL COMMENT '过期日期',
  `visit_count` bigint NULL DEFAULT NULL COMMENT '阅读数',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '分享链接' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of share_link
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
