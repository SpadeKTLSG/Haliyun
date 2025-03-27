/*
 Navicat Premium Dump SQL

 Source Server         : tMySQL
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : haliyun_guest

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/02/2025 16:03:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` bigint UNSIGNED NOT NULL,
  `target_id` bigint UNSIGNED NOT NULL COMMENT 'X...',
  `user_id` bigint UNSIGNED NOT NULL COMMENT 'User',
  `type` tinyint UNSIGNED NOT NULL COMMENT '收藏类型',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for level
-- ----------------------------
DROP TABLE IF EXISTS `level`;
CREATE TABLE `level`  (
  `id` bigint UNSIGNED NOT NULL,
  `floor` int NOT NULL COMMENT '等级层级',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '等级名称',
  `cut` float NOT NULL COMMENT '折扣优惠小数倍率',
  `grow` bigint NOT NULL COMMENT '到下一级需要成长值',
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '等级描述',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `level_pk2`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '等级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of level
-- ----------------------------
INSERT INTO `level` VALUES (1894667686932631552, 0, '青铜', 1, 50, '就是个寄吧', '新人入职转生', '2025-02-26 16:33:46', '2025-02-26 16:44:39', 0, NULL);
INSERT INTO `level` VALUES (1894667686974574592, 1, '白银', 1, 200, '太菜了', '最基础的, 几乎没有权限', '2025-02-26 16:45:29', '2025-02-26 16:45:29', 0, NULL);
INSERT INTO `level` VALUES (1894667687003934720, 2, '玄铁', 1, 800, '普普通通', '一般用户, 普通使用', '2025-02-26 16:46:42', '2025-02-26 16:46:42', 0, NULL);
INSERT INTO `level` VALUES (1894667687029100544, 3, '黄金', 0.98, 1600, '比较熟悉流程', '频繁的用户', '2025-02-26 16:47:36', '2025-02-26 16:47:36', 0, NULL);
INSERT INTO `level` VALUES (1894667687054266368, 4, '铂金', 0.96, 4000, '忠实用户', '更多权利', '2025-02-26 16:48:26', '2025-02-26 16:48:26', 0, NULL);
INSERT INTO `level` VALUES (1894667687083626496, 5, '钻石', 0.95, 8000, '大佬之一', '更多', '2025-02-26 16:49:10', '2025-02-26 16:49:10', 0, NULL);
INSERT INTO `level` VALUES (1894671134281334784, 6, '黑耀', 0.92, 20000, '位列仙班', '几乎不可能达到', '2025-02-26 16:50:03', '2025-02-26 16:51:02', 0, NULL);
INSERT INTO `level` VALUES (1894671134331666432, 7, '以太', 0.9, 1145141919, '顶级', '牛的不行', '2025-02-26 16:51:02', '2025-02-26 16:51:02', 0, NULL);

-- ----------------------------
-- Table structure for self_mail
-- ----------------------------
DROP TABLE IF EXISTS `self_mail`;
CREATE TABLE `self_mail`  (
  `id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NOT NULL,
  `sender_id` bigint UNSIGNED NOT NULL COMMENT '发送者',
  `receiver_id` bigint UNSIGNED NOT NULL COMMENT '接收人',
  `header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '标题',
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '正文',
  `status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '状态',
  `drop` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '删除',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '私信' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of self_mail
-- ----------------------------

-- ----------------------------
-- Table structure for statistics
-- ----------------------------
DROP TABLE IF EXISTS `statistics`;
CREATE TABLE `statistics`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `comment` int NULL DEFAULT 0 COMMENT '评论次数',
  `download` int NULL DEFAULT 0 COMMENT '下载次数',
  `upload` int NULL DEFAULT 0 COMMENT '上传次数',
  `outlet` int NULL DEFAULT 0 COMMENT '活动参加次数',
  `mail` int NULL DEFAULT 0 COMMENT '发私信次数',
  `collect` int NULL DEFAULT 0 COMMENT '收藏次数',
  `like` int NULL DEFAULT 0 COMMENT '点赞次数',
  `trick` int NULL DEFAULT 0 COMMENT '干坏事次数',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '统计, 密集交互' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of statistics
-- ----------------------------

-- ----------------------------
-- Table structure for tomb
-- ----------------------------
DROP TABLE IF EXISTS `tomb`;
CREATE TABLE `tomb`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `create_no` int UNSIGNED NULL DEFAULT NULL COMMENT '注册排名',
  `max_coin` bigint NULL DEFAULT NULL COMMENT '最大硬币持有量',
  `longest_group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '群组生存时间',
  `deepest_night` datetime NULL DEFAULT NULL COMMENT '最晚登陆时间',
  `earliest_morning` datetime NULL DEFAULT NULL COMMENT '最早登陆时间',
  `max_size` bigint UNSIGNED NULL DEFAULT NULL COMMENT '下载过的最大文件大小',
  `min_size` bigint UNSIGNED NULL DEFAULT NULL COMMENT '下载过的最小文件大小',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '坟墓, 有趣的拓展记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tomb
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL,
  `admin` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '管理',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态',
  `login_type` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '登陆方式',
  `account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'nobody' COMMENT '账号',
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_pk`(`account` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1889319697313562624, 1, 1, 3, 'Admin', 'LbNtL7xdUsj3bFjuFHNROw==', '2025-02-11 22:24:52', NULL, 0, NULL);
INSERT INTO `user` VALUES (1889320326245351424, 0, 0, 3, 'Guest', 'LbNtL7xdUsj3bFjuFHNROw==', '2025-02-11 22:24:52', '2025-02-22 20:55:51', 0, NULL);

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail`  (
  `id` bigint UNSIGNED NOT NULL,
  `gender` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像照片',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '地区',
  `nickname` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '昵称',
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '介绍',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_detail_pk`(`phone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_detail
-- ----------------------------
INSERT INTO `user_detail` VALUES (1889319697313562624, 2, '15911451419', 'spadekxcwxtlsg@gmail.com', 'default', '地球', '总裁玄桃K', '我是大BOSS', '2025-02-11 22:24:52', '2025-02-27 15:28:34', 0, NULL);
INSERT INTO `user_detail` VALUES (1889320326245351424, 2, '15911451420', 'spadekxcwxtlsg@gmail.com', 'default', '地球', '贵宾玄桃K', '我是大上帝', '2025-02-11 22:24:52', '2025-02-27 15:28:51', 0, NULL);

-- ----------------------------
-- Table structure for user_func
-- ----------------------------
DROP TABLE IF EXISTS `user_func`;
CREATE TABLE `user_func`  (
  `id` bigint UNSIGNED NOT NULL,
  `level_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '等级id',
  `vip` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT 'VIP',
  `create_group_count` int NOT NULL DEFAULT 0 COMMENT '当前创建群组数',
  `create_group_max` int NOT NULL DEFAULT 10 COMMENT '最大创建群组数',
  `join_group_count` int NOT NULL DEFAULT 0 COMMENT '当前加入群组数',
  `join_group_max` int NOT NULL DEFAULT 100 COMMENT '最大加入群组数',
  `coin` bigint NOT NULL DEFAULT 0 COMMENT '硬币数',
  `energy_coin` bigint NOT NULL DEFAULT 0 COMMENT '能量币数',
  `register_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '注册邀请码',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户功能' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_func
-- ----------------------------
INSERT INTO `user_func` VALUES (1889319697313562624, 1894671134331666432, 1, 0, 114514, 0, 1145141919, 1099, 1099, '3662f948d2014bc08b892014b32c8fb0', '2025-02-11 22:24:52', '2025-02-26 16:55:03', 0, NULL);
INSERT INTO `user_func` VALUES (1889320326245351424, 1894671134331666432, 1, 0, 114514, 0, 1145141919, 1099, 1099, 'b8ad36194b9d49f7b3816ab4c754d5eb', '2025-02-11 22:24:52', '2025-02-26 16:55:03', 0, NULL);

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL COMMENT 'User',
  `group_id` bigint UNSIGNED NOT NULL COMMENT 'Cluster',
  `collect` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '默认群组集' COMMENT '集合',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_group_pk2`(`user_id` ASC) USING BTREE,
  UNIQUE INDEX `user_group_pk3`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户群组关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
