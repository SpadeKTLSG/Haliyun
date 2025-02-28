/*
 Navicat Premium Dump SQL

 Source Server         : tMySQL
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : haliyun_pub

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 28/02/2025 16:03:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '代码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '(now())',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '适配, 必须自增长, 不可新增',
  `parent_id` int UNSIGNED NULL DEFAULT NULL COMMENT '父菜单id',
  `type` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '类型 0：目录 1：菜单 2：按钮',
  `admin` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否仅管理可见',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单动态路由(页面路径)',
  `score` int NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '前端菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, 1, 0, '主页', 'Guest/users', 0, '仪表盘目录', '2025-02-16 18:39:34', '2025-02-19 10:36:22', 0, NULL);
INSERT INTO `menu` VALUES (2, 0, 1, 0, '测试', 'panel/notice', 0, '测试', '2025-02-16 18:40:54', '2025-02-19 10:36:22', 0, NULL);
INSERT INTO `menu` VALUES (3, 0, 1, 0, '系统', 'sys/menu', 0, '测试', '2025-02-16 22:23:14', '2025-02-19 10:36:22', 0, NULL);
INSERT INTO `menu` VALUES (4, 0, 1, 0, '用户管理', 'user/admin', 0, NULL, '2025-02-19 00:06:53', '2025-02-19 10:36:22', 0, NULL);
INSERT INTO `menu` VALUES (5, 0, 1, 0, '用户用户', 'user/user', 0, NULL, '2025-02-19 00:06:53', '2025-02-19 10:36:22', 0, NULL);
INSERT INTO `menu` VALUES (6, 0, 1, 0, '测试2', 'temp', 0, NULL, '2025-02-19 10:30:31', '2025-02-19 10:36:22', 0, NULL);

-- ----------------------------
-- Table structure for menu_conf
-- ----------------------------
DROP TABLE IF EXISTS `menu_conf`;
CREATE TABLE `menu_conf`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `menu_id` bigint UNSIGNED NOT NULL,
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '状态',
  `value` int NULL DEFAULT NULL COMMENT '配置值',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '前端菜单配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_conf
-- ----------------------------

-- ----------------------------
-- Table structure for navi_compo
-- ----------------------------
DROP TABLE IF EXISTS `navi_compo`;
CREATE TABLE `navi_compo`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '导航名称',
  `target` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'URL',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT (now()),
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint UNSIGNED NULL DEFAULT 0,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '导航组件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of navi_compo
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
