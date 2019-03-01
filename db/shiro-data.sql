/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-03-01 20:21:18
*/

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions` VALUES ('31', 'user:create', '用户新增', '23', '0');
INSERT INTO `sys_permissions` VALUES ('32', 'user:update', '用户修改', '23', '0');
INSERT INTO `sys_permissions` VALUES ('33', 'user:delete', '用户删除', '23', '0');
INSERT INTO `sys_permissions` VALUES ('34', 'user:view', '用户查看', '23', '0');
INSERT INTO `sys_permissions` VALUES ('35', 'role:update', '角色更新', '21', '0');
INSERT INTO `sys_permissions` VALUES ('36', 'role:delete', '角色删除', '21', '0');
INSERT INTO `sys_permissions` VALUES ('37', 'role:create', '角色创建', '21', '0');
INSERT INTO `sys_permissions` VALUES ('38', 'role:view', '角色查看', '21', '0');
INSERT INTO `sys_permissions` VALUES ('39', 'permission:delete', '权限删除', '21', '0');
INSERT INTO `sys_permissions` VALUES ('40', 'permission:create', '权限创建', '21', '0');
INSERT INTO `sys_permissions` VALUES ('41', 'permission:view', '权限查看', '21', '0');
INSERT INTO `sys_permissions` VALUES ('42', 'project:manage', '项目管理', '27', '0');
INSERT INTO `sys_permissions` VALUES ('43', 'project:distribution', '项目任务分配', '27', '0');
INSERT INTO `sys_permissions` VALUES ('44', 'project:develop', '项目开发', '28', '0');
INSERT INTO `sys_permissions` VALUES ('45', 'project:maintain', '项目维护', '28', '0');
INSERT INTO `sys_permissions` VALUES ('46', 'security:maintain', '安全维护', '30', '0');
INSERT INTO `sys_permissions` VALUES ('47', 'security:develop', '安全功能设计', '30', '0');
INSERT INTO `sys_permissions` VALUES ('48', 'security:test', '安全测试', '31', '0');
INSERT INTO `sys_permissions` VALUES ('49', 'security:bug-test', 'BUG检测', '31', '0');
INSERT INTO `sys_permissions` VALUES ('86', 'propaganda:arrange', '策略宣传', '48', '0');

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES ('21', 'admin', '总经理', '0', '0');
INSERT INTO `sys_roles` VALUES ('22', 'personnel', '人事部', '0', '0');
INSERT INTO `sys_roles` VALUES ('23', 'personnel-resource', '人力资源部部长', '22', '0');
INSERT INTO `sys_roles` VALUES ('24', 'personnel-administration', '行政部部长', '22', '0');
INSERT INTO `sys_roles` VALUES ('26', 'technical', '技术部', '0', '0');
INSERT INTO `sys_roles` VALUES ('27', 'technical-development', '项目经理', '26', '0');
INSERT INTO `sys_roles` VALUES ('28', 'technical-maintenance', '项目组组长', '26', '0');
INSERT INTO `sys_roles` VALUES ('29', 'security', '安全部', '0', '0');
INSERT INTO `sys_roles` VALUES ('30', 'security-net', '网络安全部负责人', '29', '0');
INSERT INTO `sys_roles` VALUES ('31', 'security-test', '项目安全测试人员', '29', '0');
INSERT INTO `sys_roles` VALUES ('47', 'propaganda-department', '宣传部', '0', '0');
INSERT INTO `sys_roles` VALUES ('48', 'propaganda-Minister', '宣传部部长', '47', '0');
INSERT INTO `sys_roles` VALUES ('52', 'propaganda-employee', '宣传部部员', '47', '0');

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
INSERT INTO `sys_roles_permissions` VALUES ('9', '23', '32');
INSERT INTO `sys_roles_permissions` VALUES ('10', '23', '33');
INSERT INTO `sys_roles_permissions` VALUES ('11', '23', '34');
INSERT INTO `sys_roles_permissions` VALUES ('12', '27', '42');
INSERT INTO `sys_roles_permissions` VALUES ('13', '27', '43');
INSERT INTO `sys_roles_permissions` VALUES ('14', '28', '44');
INSERT INTO `sys_roles_permissions` VALUES ('15', '28', '45');
INSERT INTO `sys_roles_permissions` VALUES ('16', '30', '46');
INSERT INTO `sys_roles_permissions` VALUES ('17', '30', '47');
INSERT INTO `sys_roles_permissions` VALUES ('18', '31', '48');
INSERT INTO `sys_roles_permissions` VALUES ('19', '31', '49');
INSERT INTO `sys_roles_permissions` VALUES ('20', '21', '35');
INSERT INTO `sys_roles_permissions` VALUES ('21', '21', '36');
INSERT INTO `sys_roles_permissions` VALUES ('22', '21', '37');
INSERT INTO `sys_roles_permissions` VALUES ('23', '21', '38');
INSERT INTO `sys_roles_permissions` VALUES ('24', '21', '39');
INSERT INTO `sys_roles_permissions` VALUES ('25', '21', '40');
INSERT INTO `sys_roles_permissions` VALUES ('26', '21', '41');
INSERT INTO `sys_roles_permissions` VALUES ('27', '0', null);
INSERT INTO `sys_roles_permissions` VALUES ('28', '0', null);
INSERT INTO `sys_roles_permissions` VALUES ('29', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('36', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('37', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('38', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('39', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('40', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('41', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('42', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('43', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('44', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('45', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('46', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('47', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('48', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('49', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('50', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('51', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('52', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('53', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('54', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('55', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('56', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('57', '21', null);
INSERT INTO `sys_roles_permissions` VALUES ('61', '41', null);
INSERT INTO `sys_roles_permissions` VALUES ('62', '41', null);
INSERT INTO `sys_roles_permissions` VALUES ('63', '46', '84');
INSERT INTO `sys_roles_permissions` VALUES ('64', '0', '85');
INSERT INTO `sys_roles_permissions` VALUES ('68', '30', '86');
INSERT INTO `sys_roles_permissions` VALUES ('69', '31', '86');
INSERT INTO `sys_roles_permissions` VALUES ('70', '48', '86');
INSERT INTO `sys_roles_permissions` VALUES ('71', '52', '86');
INSERT INTO `sys_roles_permissions` VALUES ('72', '23', '31');
INSERT INTO `sys_roles_permissions` VALUES ('73', '24', '31');

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES ('1', 'admin', '62d725e493aacf6295d9dbcb53124c56', 'b9408525da32539f4788ee497b767d68', '[行政部部长]', '0');
INSERT INTO `sys_users` VALUES ('2', 'tycoding', '2ef47409f49a2a4946c1d0026d233b3c', '89231f8e54d1b0e8a3e5777e2ff84f07', '[项目经理][网络安全部负责人]', '0');
INSERT INTO `sys_users` VALUES ('3', '涂陌', 'b8d54b004de358746d6d482df5bd49d9', 'fcb4c36274e2e007371eb49ad3c2663c', '[项目经理][项目组组长]', '0');
INSERT INTO `sys_users` VALUES ('4', 'xjk', 'b466f127f65b972a46a2cde8e28094c5', '37d7f253a08c4d10620cc2b1a432ae98', '[总经理]', '0');

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES ('72', '3', '27');
INSERT INTO `sys_users_roles` VALUES ('73', '3', '28');
INSERT INTO `sys_users_roles` VALUES ('74', '2', '27');
INSERT INTO `sys_users_roles` VALUES ('75', '2', '30');
INSERT INTO `sys_users_roles` VALUES ('79', '1', '24');
INSERT INTO `sys_users_roles` VALUES ('82', '4', '21');
