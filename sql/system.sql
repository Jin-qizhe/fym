/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : jqfx

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/02/2021 17:03:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `s_dictionary`;
CREATE TABLE `s_dictionary` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `dic_name` varchar(50) DEFAULT NULL,
  `dic_val` varchar(255) DEFAULT NULL,
  `dic_memo` varchar(200) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `pid` int(20) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=721412000000000037 DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- Table structure for s_log
-- ----------------------------
DROP TABLE IF EXISTS `s_log`;
CREATE TABLE `s_log` (
  `id` int(20) NOT NULL,
  `function` varchar(50) DEFAULT NULL COMMENT '功能名称',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `user_id` int(20) DEFAULT NULL COMMENT '操作用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '操作用户名称',
  `log_time` datetime DEFAULT NULL COMMENT '日志打印时间',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志';

-- ----------------------------
-- Table structure for s_power
-- ----------------------------
DROP TABLE IF EXISTS `s_power`;
CREATE TABLE `s_power` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `pid` int(20) DEFAULT NULL COMMENT '上级id',
  `title` varchar(50) DEFAULT NULL COMMENT '菜单标题',
  `type` tinyint(2) DEFAULT NULL COMMENT '类型：0一级菜单，1二级菜单，2三级菜单，99按钮',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `btn` varchar(255) DEFAULT NULL COMMENT '按钮标志',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `pid` int(20) DEFAULT NULL COMMENT '上级id',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- Table structure for s_role_power
-- ----------------------------
DROP TABLE IF EXISTS `s_role_power`;
CREATE TABLE `s_role_power` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL COMMENT '角色id',
  `power_id` int(20) NOT NULL COMMENT '权限id',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='系统角色权限关联表';

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(50) DEFAULT NULL COMMENT '登录名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `id_card` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`),
  KEY `index_login_name` (`login_name`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
-- Table structure for s_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL COMMENT '用户id',
  `role_id` int(20) NOT NULL COMMENT '角色id',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态：0有效，-1无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色关联表';

SET FOREIGN_KEY_CHECKS = 1;
