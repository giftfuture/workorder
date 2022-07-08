/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : work_order

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 06/07/2022 00:49:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `raw_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件真实的名称',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `file_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `file_size` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大小',
  `deleted` bit(1) NULL DEFAULT b'0',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '本地存储' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of file_info
-- ----------------------------

-- ----------------------------
-- Table structure for order_dict
-- ----------------------------
DROP TABLE IF EXISTS `order_dict`;
CREATE TABLE `order_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_sort_id` bigint NULL DEFAULT NULL COMMENT '工单组别',
  `status_dict` json NULL COMMENT '状态枚举',
  `ticket_status_dict` json NULL COMMENT '钱票状态枚举',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_dict
-- ----------------------------
INSERT INTO `order_dict` VALUES (1, 1, '{\"1\": \"已打单\", \"2\": \"待发\", \"3\": \"缺货\", \"4\": \"已作退货\", \"5\": \"已调\", \"6\": \"重新打速递单\"}', '{\"1\": \"欠款\", \"2\": \"欠票\", \"3\": \"只收了部分\", \"4\": \"只开了部分\"}', b'0', 0, '2022-06-28 10:34:22', 0, '2022-06-28 10:34:22');
INSERT INTO `order_dict` VALUES (2, 2, '{\"1\": \"已开\", \"2\": \"未开\", \"3\": \"预开\", \"4\": \"今日更新\"}', NULL, b'0', 0, '2022-06-28 10:34:22', 0, '2022-06-28 10:34:22');
INSERT INTO `order_dict` VALUES (3, 3, '{\"1\": \"已入\", \"2\": \"已订\", \"3\": \"已发部分\", \"4\": \"全部完结\", \"5\": \"到货通知\", \"6\": \"意向订单\", \"7\": \"退订\", \"8\": \"缺货疑难\"}', '{\"1\": \"已收部分\", \"2\": \"已收全款\", \"3\": \"欠款\", \"4\": \"预开\"}', b'0', 0, '2022-06-28 10:34:22', 0, '2022-06-28 10:34:22');
INSERT INTO `order_dict` VALUES (4, 4, '{\"1\": \"欠进项发票\", \"2\": \"未分配\", \"3\": \"要付款\", \"4\": \"已付款\", \"5\": \"已分配\"}', NULL, b'0', 0, '2022-06-28 10:34:22', 0, '2022-06-28 10:34:22');
INSERT INTO `order_dict` VALUES (5, 5, '{\"1\": \"有异常\", \"2\": \"已入库\"}', NULL, b'0', 0, '2022-06-28 10:34:22', 0, '2022-06-28 10:34:22');
INSERT INTO `order_dict` VALUES (6, 6, '{\"1\": \"涨价\", \"2\": \"降价\", \"3\": \"已执行\"}', NULL, b'0', 0, '2022-06-28 10:34:22', 0, '2022-06-28 10:34:22');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '序号',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工单号',
  `order_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签(公共颜色:1,个人颜色:2)',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工单内容文本',
  `send_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '发货文本',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工单状态',
  `ticket_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '钱票状态',
  `in_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '输入金额',
  `arrive_notice` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '到货通知',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间 ',
  `account_remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对账备注',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工单备注',
  `pics` json NULL COMMENT '附件图片',
  `order_sort` tinyint NULL DEFAULT NULL COMMENT '工单组别有发货组、开票和资料组、订货组、订货组、打款组、进货组、价格组',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除：0=可用(默认)1=已删除',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for order_log
-- ----------------------------
DROP TABLE IF EXISTS `order_log`;
CREATE TABLE `order_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NULL DEFAULT NULL COMMENT '工单ID',
  `last_update_time` datetime NULL DEFAULT NULL COMMENT '上次修改时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `pre_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改前状态',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改后状态',
  `pre_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修改前文本',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修改后文本',
  `pre_ticket_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改前钱票状态',
  `ticket_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改后钱票状态',
  `pre_in_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '修改前输入金额',
  `in_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '修改后输入金额',
  `pre_arrive_notice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修改前到货通知',
  `arrive_notice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '修改后到货通知',
  `pre_attach` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改前附件',
  `attach` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改后附件',
  `pre_remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改前备注',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改后备注',
  `pre_account_remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改前对账备注',
  `account_remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改后对账备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '修改发生时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_log
-- ----------------------------

-- ----------------------------
-- Table structure for order_sort
-- ----------------------------
DROP TABLE IF EXISTS `order_sort`;
CREATE TABLE `order_sort`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sort_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工单组名',
  `sort_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工单标签',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_sort
-- ----------------------------
INSERT INTO `order_sort` VALUES (1, '发货组', NULL, b'0', 0, '2022-06-28 10:32:02', 0, '2022-06-28 10:32:02');
INSERT INTO `order_sort` VALUES (2, '开票和资料组', NULL, b'0', 0, '2022-06-28 10:32:02', 0, '2022-06-28 10:32:02');
INSERT INTO `order_sort` VALUES (3, '订货组', NULL, b'0', 0, '2022-06-28 10:32:02', 0, '2022-06-28 10:32:02');
INSERT INTO `order_sort` VALUES (4, '打款组', NULL, b'0', 0, '2022-06-28 10:32:02', 0, '2022-06-28 10:32:02');
INSERT INTO `order_sort` VALUES (5, '进货组', NULL, b'0', 0, '2022-06-28 10:32:02', 0, '2022-06-28 10:32:02');
INSERT INTO `order_sort` VALUES (6, '价格组', NULL, b'0', 0, '2022-06-28 10:32:02', 0, '2022-06-28 10:32:02');

-- ----------------------------
-- Table structure for order_tab
-- ----------------------------
DROP TABLE IF EXISTS `order_tab`;
CREATE TABLE `order_tab`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tab_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Tab标题',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_tab
-- ----------------------------
INSERT INTO `order_tab` VALUES (1, '发货组', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');
INSERT INTO `order_tab` VALUES (2, '开票和资料组', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');
INSERT INTO `order_tab` VALUES (3, '订货组', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');
INSERT INTO `order_tab` VALUES (4, '打款组', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');
INSERT INTO `order_tab` VALUES (5, '进货组', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');
INSERT INTO `order_tab` VALUES (6, '价格组', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');
INSERT INTO `order_tab` VALUES (7, '综合搜索', b'0', 0, '2022-06-28 10:31:22', 0, '2022-06-28 10:31:22');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录名',
  `staff_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `passwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `en_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ' 英文名',
  `id_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ' 身份证号',
  `staff_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工编号',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别0:女,1:男',
  `birth` date NULL DEFAULT NULL COMMENT ' 出生年月',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ' 手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态0:在职,1:离职',
  `login_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最近登录时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uqi_loginname_idx`(`login_name` ASC) USING BTREE,
  INDEX `uqi_phone_idx`(`phone` ASC) USING BTREE,
  INDEX `uqi_email_idx`(`email` ASC) USING BTREE,
  INDEX `uqi_staffno_idx`(`staff_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (11, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (13, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (14, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (15, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (18, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');
INSERT INTO `staff` VALUES (19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, b'0', 0, '2022-06-28 10:30:41', 0, '2022-06-28 10:30:41');

-- ----------------------------
-- Table structure for staff_order
-- ----------------------------
DROP TABLE IF EXISTS `staff_order`;
CREATE TABLE `staff_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `staff_id` bigint NULL DEFAULT NULL COMMENT '员工编号',
  `order_sort_id` bigint NULL DEFAULT NULL COMMENT '工单组编号',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff_order
-- ----------------------------
INSERT INTO `staff_order` VALUES (1, 1, 1, b'0', 0, '2022-06-28 10:29:13', 0, '2022-06-28 10:29:13');
INSERT INTO `staff_order` VALUES (2, 1, 2, b'0', 0, '2022-06-28 10:29:13', 0, '2022-06-28 10:29:13');
INSERT INTO `staff_order` VALUES (3, 1, 3, b'0', 0, '2022-06-28 10:29:13', 0, '2022-06-28 10:29:13');
INSERT INTO `staff_order` VALUES (4, 1, 4, b'0', 0, '2022-06-28 10:29:13', 0, '2022-06-28 10:29:13');
INSERT INTO `staff_order` VALUES (5, 1, 5, b'0', 0, '2022-06-28 10:29:13', 0, '2022-06-28 10:29:13');
INSERT INTO `staff_order` VALUES (6, 1, 6, b'0', 0, '2022-06-28 10:29:13', 0, '2022-06-28 10:29:13');

-- ----------------------------
-- Table structure for staff_role
-- ----------------------------
DROP TABLE IF EXISTS `staff_role`;
CREATE TABLE `staff_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `staff_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 338 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff_role
-- ----------------------------
INSERT INTO `staff_role` VALUES (1, 1, 1, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (2, 1, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (3, 2, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (4, 3, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (5, 4, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (6, 5, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (7, 6, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (8, 7, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (9, 8, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (10, 9, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (11, 10, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (12, 11, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (13, 12, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (14, 13, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (15, 14, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (16, 15, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (17, 16, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (18, 17, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');
INSERT INTO `staff_role` VALUES (19, 18, 2, b'0', 1, '2022-06-28 20:56:56', 1, '2022-06-28 20:56:56');

-- ----------------------------
-- Table structure for staff_session
-- ----------------------------
DROP TABLE IF EXISTS `staff_session`;
CREATE TABLE `staff_session`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `session_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户会话id',
  `staff_id` int NULL DEFAULT 0 COMMENT '用户id',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  `online_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'session创建时间',
  `last_access_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'session最后访问时间',
  `expire_time` int NULL DEFAULT 0 COMMENT '超时时间，单位为分钟',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5665 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '在线用户记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff_session
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `perm_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `perm_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限URL',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_c47qrsguwc5qhsl3sqlrxmwpr`(`perm_name`) USING BTREE,
  UNIQUE INDEX `UK_88ikw8276ch7dlivtlvl2yglg`(`perm_url`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '管理员权限', 'sysback', '/sysback', b'0', 0, '2022-07-05 21:32:02', 0, '2022-07-05 21:32:02');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色权限字符串',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'sys_usr', b'0', '', 1, '2019-12-12 20:56:56', 0, '2019-12-12 20:56:56');
INSERT INTO `sys_role` VALUES (2, '员工', 'staf', b'0', '', 2, '2019-12-12 21:25:40', 0, '2019-12-12 21:25:40');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int NOT NULL,
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NOT NULL DEFAULT 0 COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NOT NULL DEFAULT 0 COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKbcw322i3a5p5h7oxkj1np3num`(`permission_id`) USING BTREE,
  INDEX `FKid5g3kh3jhoj05a1jbt3gwxe9`(`role_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1, b'0', 1, '2022-07-05 21:29:35', 1, '2022-07-05 21:29:50');

SET FOREIGN_KEY_CHECKS = 1;
