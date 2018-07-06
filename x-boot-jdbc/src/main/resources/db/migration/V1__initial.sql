/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : xboot

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-07-03 10:11:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(48) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', null, 'app', 'app', 'password,refresh_token', null, null, null, null, null, null);
INSERT INTO `oauth_client_details` VALUES ('backUser', null, '$2a$10$yOTEOVIiDnnxKGyg898iXur2X92Z4kjTx9WXh0Dc3x2lVYen12pX.', 'backUser', 'password,refresh_token', null, null, '1800', '86400', null, null);
INSERT INTO `oauth_client_details` VALUES ('inner', null, 'inner', 'inner', 'implicit,,refresh_token', null, null, null, null, null, null);
INSERT INTO `oauth_client_details` VALUES ('webApp', null, 'webApp', 'webApp', 'password,refresh_token', null, null, '86400', '86400', null, null);
INSERT INTO `oauth_client_details` VALUES ('webApp2', null, 'webApp2', 'app', 'authorization_code,password,refresh_token,client_credentials', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(48) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------
INSERT INTO `oauth_refresh_token` VALUES ('b96e057b4e1c4920428e833db48d4c15', 0xACED00057372004C6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744578706972696E674F417574683252656672657368546F6B656E2FDF47639DD0C9B70200014C000A65787069726174696F6E7400104C6A6176612F7574696C2F446174653B787200446F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E636F6D6D6F6E2E44656661756C744F417574683252656672657368546F6B656E73E10E0A6354D45E0200014C000576616C75657400124C6A6176612F6C616E672F537472696E673B787074002462303132643438372D613930382D343361662D613865662D3533633533393963386264367372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001613B74C98E78, 0xACED0005737200416F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F417574683241757468656E7469636174696F6EBD400B02166252130200024C000D73746F7265645265717565737474003C4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F4F4175746832526571756573743B4C00127573657241757468656E7469636174696F6E7400324C6F72672F737072696E676672616D65776F726B2F73656375726974792F636F72652F41757468656E7469636174696F6E3B787200476F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E416273747261637441757468656E7469636174696F6E546F6B656ED3AA287E6E47640E0200035A000D61757468656E746963617465644C000B617574686F7269746965737400164C6A6176612F7574696C2F436F6C6C656374696F6E3B4C000764657461696C737400124C6A6176612F6C616E672F4F626A6563743B787000737200266A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654C697374FC0F2531B5EC8E100200014C00046C6973747400104C6A6176612F7574696C2F4C6973743B7872002C6A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65436F6C6C656374696F6E19420080CB5EF71E0200014C00016371007E00047870737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A65787000000009770400000009737200426F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E617574686F726974792E53696D706C654772616E746564417574686F7269747900000000000001A40200014C0004726F6C657400124C6A6176612F6C616E672F537472696E673B787074000A524F4C455F61646D696E7371007E000D740006617069646F637371007E000D74000C64617461626173652F6C6F677371007E000D74000673797374656D7371007E000D740008757365722F6164647371007E000D74000B757365722F64656C6574657371007E000D740009757365722F656469747371007E000D740009757365722F766965777371007E000D740008757365724C6973747871007E000C707372003A6F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E4F41757468325265717565737400000000000000010200075A0008617070726F7665644C000B617574686F72697469657371007E00044C000A657874656E73696F6E7374000F4C6A6176612F7574696C2F4D61703B4C000B726564697265637455726971007E000E4C00077265667265736874003B4C6F72672F737072696E676672616D65776F726B2F73656375726974792F6F61757468322F70726F76696465722F546F6B656E526571756573743B4C000B7265736F7572636549647374000F4C6A6176612F7574696C2F5365743B4C000D726573706F6E7365547970657371007E0024787200386F72672E737072696E676672616D65776F726B2E73656375726974792E6F61757468322E70726F76696465722E426173655265717565737436287A3EA37169BD0200034C0008636C69656E74496471007E000E4C001172657175657374506172616D657465727371007E00224C000573636F706571007E00247870740006776562417070737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C654D6170F1A5A8FE74F507420200014C00016D71007E00227870737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000037708000000040000000274000A6772616E745F7479706574000870617373776F7264740008757365726E616D6574000561646D696E78737200256A6176612E7574696C2E436F6C6C656374696F6E7324556E6D6F6469666961626C65536574801D92D18F9B80550200007871007E0009737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F4000000000000174000361707078017371007E0033770C000000103F40000000000000787371007E002A3F40000000000000770800000010000000007870707371007E0033770C000000103F40000000000000787371007E0033770C000000103F40000000000000787372004F6F72672E737072696E676672616D65776F726B2E73656375726974792E61757468656E7469636174696F6E2E557365726E616D6550617373776F726441757468656E7469636174696F6E546F6B656E00000000000001A40200024C000B63726564656E7469616C7371007E00054C00097072696E636970616C71007E00057871007E0003017371007E00077371007E000B0000000977040000000971007E000F71007E001171007E001371007E001571007E001771007E001971007E001B71007E001D71007E001F7871007E003D737200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F726465727871007E002A3F400000000000067708000000080000000271007E002C71007E002D71007E002E71007E002F780070737200326F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E7573657264657461696C732E5573657200000000000001A40200075A00116163636F756E744E6F6E457870697265645A00106163636F756E744E6F6E4C6F636B65645A001563726564656E7469616C734E6F6E457870697265645A0007656E61626C65644C000B617574686F72697469657371007E00244C000870617373776F726471007E000E4C0008757365726E616D6571007E000E7870010101017371007E0030737200116A6176612E7574696C2E54726565536574DD98509395ED875B0300007870737200466F72672E737072696E676672616D65776F726B2E73656375726974792E636F72652E7573657264657461696C732E5573657224417574686F72697479436F6D70617261746F7200000000000001A4020000787077040000000971007E000F71007E001171007E001371007E001571007E001771007E001971007E001B71007E001D71007E001F787074000561646D696E);

-- ----------------------------
-- Table structure for t_cli_client
-- ----------------------------
DROP TABLE IF EXISTS `t_cli_client`;
CREATE TABLE `t_cli_client` (
  `id` bigint(20) NOT NULL,
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `account_non_expired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否未过期',
  `credentials_non_expired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '登录凭据是否未过期',
  `account_non_Locked` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否未锁定',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `status` int(11) DEFAULT '1' COMMENT '用户状态 1 正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_by_id` int(11) NOT NULL DEFAULT '0' COMMENT '修改者',
  `lock_time` datetime DEFAULT NULL,
  `private_key` varchar(255) DEFAULT NULL,
  `public_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_login_name` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台会员表';

-- ----------------------------
-- Records of t_cli_client
-- ----------------------------

-- ----------------------------
-- Table structure for t_cli_client_attempts
-- ----------------------------
DROP TABLE IF EXISTS `t_cli_client_attempts`;
CREATE TABLE `t_cli_client_attempts` (
  `id` bigint(20) NOT NULL,
  `username` varchar(30) NOT NULL,
  `attempts` int(11) NOT NULL,
  `last_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员用户尝试登陆次数记录表';

-- ----------------------------
-- Records of t_cli_client_attempts
-- ----------------------------

-- ----------------------------
-- Table structure for t_pf_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_pf_notice`;
CREATE TABLE `t_pf_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sort_order` int(11) DEFAULT NULL,
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(60) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_by` varchar(60) DEFAULT NULL,
  `readed` int(11) DEFAULT NULL COMMENT '是否已读',
  `del_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1013733924275163138 DEFAULT CHARSET=utf8 COMMENT='平台通知';

-- ----------------------------
-- Records of t_pf_notice
-- ----------------------------
INSERT INTO `t_pf_notice` VALUES ('1013732935849705474', '0', '欢迎登录x-boot后台管理系统，来了解他的用途吧', null, '这是您点击的《欢迎登录x-boot后台管理系统，来了解他的用途吧》的相关内容。', '2018-07-02 18:35:40', '0', '2018-07-02 18:35:40', '0', '0', '0');
INSERT INTO `t_pf_notice` VALUES ('1013733375580450818', '0', '使用iView-admin和iView-ui组件库快速搭建你的后台系统吧', null, '这是您点击的《使用iView-admin和iView-ui组件库快速搭建你的后台系统吧》的相关内容。', '2018-07-02 18:37:25', '0', '2018-07-02 18:37:25', '0', '0', '0');
INSERT INTO `t_pf_notice` VALUES ('1013733523052257281', '0', '喜欢x-boot的话，欢迎到github主页给个star吧', null, '这是您点击的《喜欢x-boot的话，欢迎到github主页给个star吧》的相关内容。', '2018-07-02 18:38:00', '0', '2018-07-02 18:38:00', '0', '1', '0');
INSERT INTO `t_pf_notice` VALUES ('1013733756863684610', '0', '这是一条您已经读过的消息', null, '这是您点击的《这是一条您已经读过的消息》的相关内容。', '2018-07-02 18:38:56', '0', '2018-07-02 18:38:56', '0', '1', '0');
INSERT INTO `t_pf_notice` VALUES ('1013733924275163137', '0', '这是一条被删除的消息', null, '这是您点击的《这是一条被删除的消息》的相关内容。', '2018-07-02 18:39:36', '0', '2018-07-02 18:39:36', '0', '1', '1');

-- ----------------------------
-- Table structure for t_pf_push
-- ----------------------------
DROP TABLE IF EXISTS `t_pf_push`;
CREATE TABLE `t_pf_push` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `type` tinyint(4) DEFAULT '1' COMMENT '1系统公告2我的消息3优选活动',
  `content` varchar(512) DEFAULT NULL COMMENT '消息内容',
  `sender` int(11) DEFAULT NULL COMMENT '消息发送人',
  `from_name` varchar(64) DEFAULT NULL COMMENT '消息发送人名称',
  `target` int(11) DEFAULT NULL COMMENT '消息接收人',
  `target_name` varchar(64) DEFAULT NULL COMMENT '消息接收人名称',
  `is_read` tinyint(4) DEFAULT '0' COMMENT '消息阅读状态（0未读 1已读）',
  `href` varchar(256) DEFAULT NULL COMMENT ' 优选活动地址',
  `create_time` datetime DEFAULT NULL,
  `create_by_id` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_by_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户消息';

-- ----------------------------
-- Records of t_pf_push
-- ----------------------------

-- ----------------------------
-- Table structure for t_pf_sms
-- ----------------------------
DROP TABLE IF EXISTS `t_pf_sms`;
CREATE TABLE `t_pf_sms` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `template_code` varchar(64) DEFAULT NULL,
  `content` varchar(128) DEFAULT NULL,
  `del_flag` int(11) DEFAULT '0' COMMENT '1标识删除   0否',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(60) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_by` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='短信模板';

-- ----------------------------
-- Records of t_pf_sms
-- ----------------------------

-- ----------------------------
-- Table structure for t_pf_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `t_pf_sms_log`;
CREATE TABLE `t_pf_sms_log` (
  `id` int(11) NOT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `template_code` varchar(30) DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  `send_status` int(11) DEFAULT NULL,
  `cur_send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `send_time` datetime DEFAULT NULL,
  `receive_time` datetime DEFAULT NULL,
  `out_id` int(11) DEFAULT NULL,
  `err_code` varchar(255) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pf_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` varchar(255) NOT NULL,
  `del_flag` int(11) DEFAULT '0',
  `create_by` varchar(60) NOT NULL,
  `create_time` datetime NOT NULL,
  `modify_by` varchar(60) NOT NULL,
  `modify_time` datetime NOT NULL,
  `cost_time` int(11) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_param` text,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------
INSERT INTO `t_sys_log` VALUES ('1013588781471567873', '1', '0', '2018-07-02 09:02:52', '0', '2018-07-02 09:02:52', '24', '115.193.164.155', '未知', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"4444\"],\"enabled\":[\"true\"],\"_rowKey\":[\"58\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:00:36\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013589507291721730', '1', '0', '2018-07-02 09:05:45', '0', '2018-07-02 09:05:45', '208', '115.193.164.155', '未知', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"5555\"],\"enabled\":[\"true\"],\"_rowKey\":[\"87\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:02:52\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013600149998354434', '1', '0', '2018-07-02 09:48:17', '0', '2018-07-02 09:48:17', '95', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"54444\"],\"enabled\":[\"true\"],\"_rowKey\":[\"26\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:05:44\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013600586474405890', '1', '0', '2018-07-02 09:49:46', '0', '2018-07-02 09:49:46', '83293', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"44444\"],\"enabled\":[\"true\"],\"_rowKey\":[\"29\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:48:02\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013600926053597185', '1', '0', '2018-07-02 09:51:15', '0', '2018-07-02 09:51:15', '244', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"6666\"],\"enabled\":[\"true\"],\"_rowKey\":[\"55\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:49:46\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013601171181305857', '1', '0', '2018-07-02 09:52:47', '0', '2018-07-02 09:52:47', '40503', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"3333\"],\"enabled\":[\"true\"],\"_rowKey\":[\"58\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:51:07\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013601438924701698', '1', '0', '2018-07-02 09:53:40', '0', '2018-07-02 09:53:40', '122', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"5555\"],\"enabled\":[\"true\"],\"_rowKey\":[\"84\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:52:04\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013602020829888513', '1', '0', '2018-07-02 09:56:03', '0', '2018-07-02 09:56:03', '76473', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"44444\"],\"enabled\":[\"true\"],\"_rowKey\":[\"110\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:53:09\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013602446455275521', '1', '0', '2018-07-02 09:57:10', '0', '2018-07-02 09:57:10', '54530', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"5555\"],\"enabled\":[\"true\"],\"_rowKey\":[\"113\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:55:25\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013603567945031681', '1', '1009722428004200450', '2018-07-02 10:01:37', '1009722428004200450', '2018-07-02 10:01:37', '308', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"3333\"],\"enabled\":[\"true\"],\"_rowKey\":[\"116\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 09:57:10\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013615013856706561', '1', '1009722428004200450', '2018-07-02 10:47:06', '1009722428004200450', '2018-07-02 10:47:06', '389', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"44444\"],\"enabled\":[\"true\"],\"_rowKey\":[\"118\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 10:43:15\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013627727245094914', '1', '', '2018-07-02 11:37:37', '', '2018-07-02 11:37:37', '782', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013641457550901249', '1', '', '2018-07-02 12:32:10', '', '2018-07-02 12:32:10', '743', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013649110318702594', '1', '', '2018-07-02 13:02:35', '', '2018-07-02 13:02:35', '812', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013651081389842434', '1', '', '2018-07-02 13:10:25', '', '2018-07-02 13:10:25', '983', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013667887538130946', '0', '', '2018-07-02 14:17:12', '', '2018-07-02 14:17:12', '688', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013672247139753986', '0', '', '2018-07-02 14:34:31', '', '2018-07-02 14:34:31', '589', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013672841795592193', '0', '1009722428004200450', '2018-07-02 14:36:53', '1009722428004200450', '2018-07-02 14:36:53', '66', '115.193.164.155', '浙江 杭州', '更新用户角色', '{\"modifyBy\":[\"1009722428004200450\"],\"_index\":[\"2\"],\"description\":[\"\"],\"sort\":[\"0\"],\"title\":[\"5555\"],\"enabled\":[\"true\"],\"_rowKey\":[\"6\"],\"createBy\":[\"1009722428004200450\"],\"modifyTime\":[\"2018-07-02 10:47:05\"],\"createTime\":[\"2018-06-27 09:29:00\"],\"permissions\":[\"[object Object],[object Object],[object Object]\"],\"name\":[\"ROLE_12313\"],\"id\":[\"1011783419818897409\"]}', 'POST', '/role/edit', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013678305581150210', '0', '', '2018-07-02 14:58:35', '', '2018-07-02 14:58:35', '693', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013686043556167681', '0', '', '2018-07-02 15:29:20', '', '2018-07-02 15:29:20', '704', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013694525286023169', '0', '', '2018-07-02 16:03:03', '', '2018-07-02 16:03:03', '681', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013697061804032002', '0', '', '2018-07-02 16:13:07', '', '2018-07-02 16:13:07', '753', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013698736463794178', '0', '', '2018-07-02 16:19:47', '', '2018-07-02 16:19:47', '52352', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013698784857673729', '0', '', '2018-07-02 16:19:59', '', '2018-07-02 16:19:59', '8306', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013698979813117954', '0', '', '2018-07-02 16:20:45', '', '2018-07-02 16:20:45', '36659', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013699193688961026', '0', '', '2018-07-02 16:21:36', '', '2018-07-02 16:21:36', '13074', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013699207525969922', '0', '', '2018-07-02 16:21:39', '', '2018-07-02 16:21:39', '248', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013709580534800385', '0', '', '2018-07-02 17:02:52', '', '2018-07-02 17:02:52', '399', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013712188053880833', '0', '', '2018-07-02 17:13:14', '', '2018-07-02 17:13:14', '938', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013712229485215745', '0', '', '2018-07-02 17:13:24', '', '2018-07-02 17:13:24', '302', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013712859834560514', '0', '', '2018-07-02 17:15:54', '', '2018-07-02 17:15:54', '616', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013713013518053377', '0', '', '2018-07-02 17:16:31', '', '2018-07-02 17:16:31', '309', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/login', null);
INSERT INTO `t_sys_log` VALUES ('1013716624432119809', '0', '1009722428004200450', '2018-07-02 17:30:51', '1009722428004200450', '2018-07-02 17:30:51', '141', '115.193.164.155', '浙江 杭州', '用户登出', '{\"accessToken\":[\"null\"]}', 'POST', '/oauth/revoke', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013717112858816514', '0', '1009722428004200450', '2018-07-02 17:32:48', '1009722428004200450', '2018-07-02 17:32:48', '116', '115.193.164.155', '浙江 杭州', '用户登出', '{\"accessToken\":[\"d840d246-7258-4150-a43f-a7249cd5e824\"]}', 'POST', '/oauth/logout', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013717198040936449', '0', '', '2018-07-02 17:33:09', '', '2018-07-02 17:33:09', '551', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013717251887411201', '0', '1009722428004200450', '2018-07-02 17:33:21', '1009722428004200450', '2018-07-02 17:33:21', '93', '115.193.164.155', '浙江 杭州', '用户登出', '{\"accessToken\":[\"d9901dc5-782a-4ecc-8f92-e52030842089\"]}', 'POST', '/oauth/logout', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013717326818652162', '0', '', '2018-07-02 17:33:39', '', '2018-07-02 17:33:39', '343', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013717533073551361', '0', '1009722428004200450', '2018-07-02 17:34:29', '1009722428004200450', '2018-07-02 17:34:29', '109', '115.193.164.155', '浙江 杭州', '用户登出', '{\"accessToken\":[\"1edef437-b9d0-483b-a3b0-c070fecc242b\"]}', 'POST', '/oauth/logout', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013717566422462466', '0', '', '2018-07-02 17:34:36', '', '2018-07-02 17:34:36', '246', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013734293789143041', '0', '', '2018-07-02 18:41:04', '', '2018-07-02 18:41:04', '715', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013746290429718530', '0', '', '2018-07-02 19:28:45', '', '2018-07-02 19:28:45', '369', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013754192498421761', '0', '', '2018-07-02 20:00:08', '', '2018-07-02 20:00:08', '695', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013763075258372098', '0', '', '2018-07-02 20:35:26', '', '2018-07-02 20:35:26', '642', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013764978574807042', '0', '1009722428004200450', '2018-07-02 20:43:00', '1009722428004200450', '2018-07-02 20:43:00', '203', '115.193.164.155', '浙江 杭州', '用户登出', '{\"accessToken\":[\"80983697-614a-42a5-8bb5-277f356ce1a5\"]}', 'POST', '/oauth/logout', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013765001379237890', '0', '', '2018-07-02 20:43:06', '', '2018-07-02 20:43:06', '623', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);
INSERT INTO `t_sys_log` VALUES ('1013767160946671618', '0', '1009722428004200450', '2018-07-02 20:51:40', '1009722428004200450', '2018-07-02 20:51:40', '96', '115.193.164.155', '浙江 杭州', '用户登出', '{\"accessToken\":[\"316a0afa-6040-4267-8ce6-5682a679747b\"]}', 'POST', '/oauth/logout', 'xukk');
INSERT INTO `t_sys_log` VALUES ('1013767184992616450', '0', '', '2018-07-02 20:51:46', '', '2018-07-02 20:51:46', '556', '115.193.164.155', '浙江 杭州', '用户登录', '{\"password\":[\"123456\"],\"grant_type\":[\"password\"],\"username\":[\"xukk\"]}', 'POST', '/oauth/login', null);

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission` (
  `id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '资源代号',
  `permission` varchar(255) NOT NULL DEFAULT '',
  `title` varchar(30) NOT NULL DEFAULT '' COMMENT '资源名称',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '资源路径',
  `level` int(11) NOT NULL DEFAULT '0',
  `sort` decimal(10,2) NOT NULL DEFAULT '0.00',
  `icon` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(30) NOT NULL DEFAULT '' COMMENT '资源类型',
  `button_type` varchar(30) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(30) NOT NULL DEFAULT '0',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(30) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------
INSERT INTO `t_sys_permission` VALUES ('1009726998067458050', '0', 'sys', '', '系统管理', '/form', '1', '1.00', 'ios-gear', 'MENU', null, 'Main', '', '1', '2018-06-21 17:17:31', '0', '2018-06-25 19:25:51', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1009730375975387137', '1009726998067458050', 'user-manage', '', '用户管理', 'user-manage', '2', '1.10', 'android-person', 'MENU', null, 'sys/user-manage/userManage', '', '1', '2018-06-21 17:30:56', '0', '2018-06-25 19:25:01', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1009730748542828546', '1009726998067458050', 'role-manage', '', '角色管理', 'role-manage', '2', '1.20', 'android-contacts', 'MENU', null, 'sys/role-manage/roleManage', '', '1', '2018-06-21 17:32:25', '0', '2018-06-21 17:32:25', '0');
INSERT INTO `t_sys_permission` VALUES ('1009731306804678658', '1009726998067458050', 'menu-manage', '', '权限管理', 'menu-manage', '2', '1.30', 'navicon-round', 'MENU', null, 'sys/menu-manage/menuManage', '', '1', '2018-06-21 17:34:38', '0', '2018-06-21 17:34:38', '0');
INSERT INTO `t_sys_permission` VALUES ('1009731306884370433', '1009726998067458050', 'log-manage', '', '日志管理', 'log-manage', '2', '1.40', 'android-list', 'MENU', null, 'sys/log-manage/logManage', '', '1', '2018-06-21 17:34:38', '0', '2018-06-21 17:34:38', '0');
INSERT INTO `t_sys_permission` VALUES ('1009733861018411009', '1009730375975387137', 'user-add', '', '添加用户', '', '3', '1.11', '', 'BUTTON', 'add', null, '', '1', '2018-06-21 17:44:47', '0', '2018-06-21 17:44:47', '0');
INSERT INTO `t_sys_permission` VALUES ('1009733861077131266', '1009730375975387137', 'user-edit', '', '编辑用户', '/xboot/user/admin/edit', '3', '1.12', '', 'BUTTON', 'edit', null, '', '1', '2018-06-21 17:44:47', '0', '2018-06-21 17:44:47', '0');
INSERT INTO `t_sys_permission` VALUES ('1009733861110685697', '1009730375975387137', 'user-disable', '', '禁用用户', '/xboot/user/admin/disable/**', '3', '1.13', '', 'BUTTON', 'disable', null, '', '1', '2018-06-21 17:44:47', '0', '2018-06-21 17:44:47', '0');
INSERT INTO `t_sys_permission` VALUES ('1009733861161017345', '1009730375975387137', 'user-enable', '', '启用用户', '/xboot/user/admin/enable/**', '3', '1.14', '', 'BUTTON', 'enable', null, '', '1', '2018-06-21 17:44:47', '0', '2018-06-21 17:44:47', '0');
INSERT INTO `t_sys_permission` VALUES ('1009734016585105410', '1009730375975387137', 'user-delete', '', '删除用户', '/xboot/user/delByIds**', '3', '1.15', '', 'BUTTON', 'delete', null, '', '1', '2018-06-21 17:45:24', '0', '2018-06-21 17:45:24', '0');
INSERT INTO `t_sys_permission` VALUES ('1009734795245051906', '1009730748542828546', 'role-add', '', '添加角色', '', '3', '1.21', '', 'BUTTON', 'add', null, '', '1', '2018-06-21 17:48:30', '0', '2018-06-21 17:48:30', '0');
INSERT INTO `t_sys_permission` VALUES ('1009734795328937985', '1009730748542828546', 'role-edit', '', '编辑角色', '', '3', '1.22', '', 'BUTTON', 'edit', null, '', '1', '2018-06-21 17:48:30', '0', '2018-06-21 17:48:30', '0');
INSERT INTO `t_sys_permission` VALUES ('1009734795362492417', '1009730748542828546', 'role-delete', '', '删除角色', '', '3', '1.23', '', 'BUTTON', 'delete', null, '', '1', '2018-06-21 17:48:30', '0', '2018-06-21 17:48:30', '0');
INSERT INTO `t_sys_permission` VALUES ('1009736199670030337', '1009731306804678658', 'permission-add', '', '添加菜单', '', '3', '1.31', '', 'BUTTON', 'add', null, '', '1', '2018-06-21 17:54:05', '0', '2018-06-21 17:54:05', '0');
INSERT INTO `t_sys_permission` VALUES ('1009736199728750594', '1009731306804678658', 'permission-edit', '', '编辑菜单', '', '3', '1.32', '', 'BUTTON', 'edit', null, '', '1', '2018-06-21 17:54:05', '0', '2018-06-21 17:54:05', '0');
INSERT INTO `t_sys_permission` VALUES ('1009736199766499330', '1009731306804678658', 'permission-delete', '', '删除菜单', '', '3', '1.33', '', 'BUTTON', 'delete', null, '', '1', '2018-06-21 17:54:05', '0', '2018-06-21 17:54:05', '0');
INSERT INTO `t_sys_permission` VALUES ('1009736502515568641', '1009731306804678658', 'log-delete', '', '删除日志', '', '3', '1.41', '', 'BUTTON', 'delete', null, '', '1', '2018-06-21 17:55:17', '0', '2018-06-21 17:55:17', '0');
INSERT INTO `t_sys_permission` VALUES ('1009736502561705985', '1009731306804678658', 'log-clearAll', '', '清空日志', '', '3', '1.42', '', 'BUTTON', 'clearAll', null, '', '1', '2018-06-21 17:55:17', '0', '2018-06-21 17:55:17', '0');
INSERT INTO `t_sys_permission` VALUES ('1009736951746494465', '1009730748542828546', 'role-editPerm', '', '分配权限', '', '3', '1.24', '', 'BUTTON', 'editPerm', null, '', '1', '2018-06-21 17:57:04', '0', '2018-06-21 17:57:04', '0');
INSERT INTO `t_sys_permission` VALUES ('1011215397043482625', '0', 'access', '', '权限按钮测试页', '/access', '1', '2.00', 'locked', 'MENU', null, 'Main', '', '1', '2018-06-25 19:51:53', '1009722428004200450', '2018-06-25 19:52:02', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1011216190417055745', '1011215397043482625', 'access_index', '', '权限按钮测试页', 'index', '2', '2.10', 'locked', 'MENU', null, 'access/access', '', '1', '2018-06-25 19:55:02', '1009722428004200450', '2018-06-27 09:28:33', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1011219365517766657', '1011216190417055745', 'edit-test', '', '编辑按钮测试', 'edit-test', '3', '2.11', '', 'BUTTON', 'edit', null, '', '1', '2018-06-25 20:07:39', '1009722428004200450', '2018-06-25 20:09:08', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1011427762544472065', '1009726998067458050', 'document-manage', '', '接口文档', 'document-manage', '2', '1.50', 'document', 'MENU', null, 'sys/document-manage/documentManage', '', '1', '2018-06-26 09:55:45', '1009722428004200450', '2018-06-26 14:43:09', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1011784823417565186', '1011216190417055745', 'access_index', '', 'test', '1313', '3', '2.40', '', '1', null, null, '', '1', '2018-06-27 09:34:35', '1009722428004200450', '2018-06-27 09:34:35', '1009722428004200450');
INSERT INTO `t_sys_permission` VALUES ('1012501686703271938', '1009726998067458050', 'job-manage', '', '任务管理', 'job-manage', '2', '1.60', 'hammer', 'MENU', null, 'sys/job-manage/jobManage', '', '1', '2018-06-29 09:03:08', '1009722428004200450', '2018-06-29 09:13:53', '1009722428004200450');

-- ----------------------------
-- Table structure for t_sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_privilege`;
CREATE TABLE `t_sys_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `privilege_role` (`role_id`) USING BTREE,
  KEY `privilege_permission_id` (`permission_id`),
  CONSTRAINT `privilege_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `t_sys_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `privilege_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1013717502182502403 DEFAULT CHARSET=utf8 COMMENT='角色资源表';

-- ----------------------------
-- Records of t_sys_privilege
-- ----------------------------
INSERT INTO `t_sys_privilege` VALUES ('1011784446114754561', '1011783419818897409', '1011215397043482625');
INSERT INTO `t_sys_privilege` VALUES ('1011784446144114690', '1011783419818897409', '1011216190417055745');
INSERT INTO `t_sys_privilege` VALUES ('1011784446169280514', '1011783419818897409', '1011219365517766657');
INSERT INTO `t_sys_privilege` VALUES ('1013717501624659970', '1009712269710225409', '1009726998067458050');
INSERT INTO `t_sys_privilege` VALUES ('1013717501649825793', '1009712269710225409', '1009730375975387137');
INSERT INTO `t_sys_privilege` VALUES ('1013717501683380225', '1009712269710225409', '1009733861018411009');
INSERT INTO `t_sys_privilege` VALUES ('1013717501708546049', '1009712269710225409', '1009733861077131266');
INSERT INTO `t_sys_privilege` VALUES ('1013717501733711874', '1009712269710225409', '1009733861110685697');
INSERT INTO `t_sys_privilege` VALUES ('1013717501758877697', '1009712269710225409', '1009733861161017345');
INSERT INTO `t_sys_privilege` VALUES ('1013717501792432130', '1009712269710225409', '1009734016585105410');
INSERT INTO `t_sys_privilege` VALUES ('1013717501817597954', '1009712269710225409', '1009730748542828546');
INSERT INTO `t_sys_privilege` VALUES ('1013717501846958081', '1009712269710225409', '1009734795245051906');
INSERT INTO `t_sys_privilege` VALUES ('1013717501872123906', '1009712269710225409', '1009734795328937985');
INSERT INTO `t_sys_privilege` VALUES ('1013717501914066946', '1009712269710225409', '1009734795362492417');
INSERT INTO `t_sys_privilege` VALUES ('1013717501935038465', '1009712269710225409', '1009736951746494465');
INSERT INTO `t_sys_privilege` VALUES ('1013717501964398594', '1009712269710225409', '1009731306804678658');
INSERT INTO `t_sys_privilege` VALUES ('1013717501997953026', '1009712269710225409', '1009736199670030337');
INSERT INTO `t_sys_privilege` VALUES ('1013717502023118850', '1009712269710225409', '1009736199728750594');
INSERT INTO `t_sys_privilege` VALUES ('1013717502052478977', '1009712269710225409', '1009736199766499330');
INSERT INTO `t_sys_privilege` VALUES ('1013717502073450497', '1009712269710225409', '1009736502515568641');
INSERT INTO `t_sys_privilege` VALUES ('1013717502098616322', '1009712269710225409', '1009736502561705985');
INSERT INTO `t_sys_privilege` VALUES ('1013717502127976449', '1009712269710225409', '1009731306884370433');
INSERT INTO `t_sys_privilege` VALUES ('1013717502153142274', '1009712269710225409', '1011427762544472065');
INSERT INTO `t_sys_privilege` VALUES ('1013717502182502402', '1009712269710225409', '1012501686703271938');

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '代号',
  `title` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(255) NOT NULL DEFAULT '',
  `sort` int(11) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(30) NOT NULL DEFAULT '0',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_by` varchar(30) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `roel_code` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('1009712269710225409', 'ROLE_ADMIN', '管理员', '管理员', '0', '1', '2018-06-21 16:18:59', '0', '2018-06-21 16:18:59', '0');
INSERT INTO `t_sys_role` VALUES ('1009715933875228673', 'ROLE_TEST', '测试人员', '测试人员', '0', '1', '2018-06-21 16:33:33', '0', '2018-06-21 16:33:33', '0');
INSERT INTO `t_sys_role` VALUES ('1011783419818897409', 'ROLE_12313', '5555', '', '0', '1', '2018-06-27 09:29:00', '1009722428004200450', '2018-07-02 14:36:53', '1009722428004200450');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `uuid` varchar(64) NOT NULL DEFAULT '' COMMENT 'UUID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `account_non_expired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否未过期',
  `credentials_non_expired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '登录凭据是否未过期',
  `account_non_locked` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否未锁定',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态 1 正常 -1 软删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(30) NOT NULL DEFAULT '0' COMMENT '创建者',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_by` varchar(30) NOT NULL DEFAULT '0' COMMENT '修改者',
  `type` int(11) NOT NULL DEFAULT '0',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_login_name` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1009722428004200450', '6d0174ac-3e2b-4963-8cb0-46c5b40c7a16', 'xukk', '$2a$10$NwGklH5KLsA2CQFkPga8e.3awaTcMlaaNWY5YGmtyA1l6qjdvsQm6', '1', '1', '1', '1', '1', '2018-06-21 16:59:21', '0', '2018-06-27 14:38:07', '1009722428004200450', '1', '2018-07-02 18:02:07');
INSERT INTO `t_sys_user` VALUES ('1011804738283286530', '', 'test', '$2a$10$fyH2tpIIk5BR/Sxqh0c/zuLKObhjtVa/7Q8aXtuxLlT7FJjJRUY2C', '1', '1', '1', '0', '1', '2018-06-27 10:53:43', '1009722428004200450', '2018-07-02 17:38:02', '1009722428004200450', '0', null);

-- ----------------------------
-- Table structure for t_sys_user_attempts
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_attempts`;
CREATE TABLE `t_sys_user_attempts` (
  `id` bigint(20) NOT NULL,
  `username` varchar(60) NOT NULL COMMENT '用户标识',
  `attempts` int(11) NOT NULL COMMENT '尝试次数',
  `last_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录尝试次数记录表';

-- ----------------------------
-- Records of t_sys_user_attempts
-- ----------------------------
INSERT INTO `t_sys_user_attempts` VALUES ('1010062486410354689', 'xukk', '0', '2018-07-02 20:51:46');

-- ----------------------------
-- Table structure for t_sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_group`;
CREATE TABLE `t_sys_user_group` (
  `id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- Records of t_sys_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_group_role_ship
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_group_role_ship`;
CREATE TABLE `t_sys_user_group_role_ship` (
  `id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user_group_role_ship
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_group_ship
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_group_ship`;
CREATE TABLE `t_sys_user_group_ship` (
  `group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user_group_ship
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_info`;
CREATE TABLE `t_sys_user_info` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID',
  `user_no` varchar(30) NOT NULL DEFAULT '' COMMENT '员工编号',
  `nick_name` varchar(255) NOT NULL DEFAULT '',
  `real_name` varchar(30) NOT NULL DEFAULT '' COMMENT '真实名字',
  `idcard` varchar(50) NOT NULL DEFAULT '',
  `mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '手机',
  `fixed_phone` varchar(50) NOT NULL DEFAULT '' COMMENT '固话',
  `gender` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
  `avatar` varchar(255) NOT NULL DEFAULT '',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `email` varchar(100) NOT NULL DEFAULT '',
  `version` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(30) NOT NULL DEFAULT '0' COMMENT '创建者',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `modify_by` varchar(30) NOT NULL DEFAULT '0' COMMENT '修改者',
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`user_no`) USING BTREE,
  UNIQUE KEY `user_mobile` (`mobile`) USING BTREE,
  KEY `info_user_id` (`user_id`),
  CONSTRAINT `info_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_sys_user_info
-- ----------------------------
INSERT INTO `t_sys_user_info` VALUES ('1010059818711293954', '1009722428004200450', '0', '1529652000930', '', '徐康康', '', '18268213185', '0576-85621474', '0', 'http://chedai.oss-cn-shanghai.aliyuncs.com/2018/06/28/e8f2e5ab72d14afea3e7878338a54f48.jpeg', '2018-06-22 15:20:01', '410551654@qq.com', '1', '2018-06-22 15:20:01', '0', '2018-06-28 11:08:02', '1009722428004200450', '330000', '330100', '330106', '130000,130200,130203');
INSERT INTO `t_sys_user_info` VALUES ('1011804738358784002', '1011804738283286530', '0', '123456', '', 'test', '', '18268213184', '', '1', '', null, '3213@qq.com', '0', '2018-06-27 10:53:43', '1009722428004200450', '2018-06-27 10:53:43', '1009722428004200450', null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_user_id` (`user_id`),
  CONSTRAINT `user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('1011804738388144130', '1011804738283286530', '1009715933875228673');
INSERT INTO `t_sys_user_role` VALUES ('1011861213387091969', '1009722428004200450', '1009712269710225409');

-- ----------------------------
-- Table structure for xxl_job_qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_blob_triggers`;
CREATE TABLE `xxl_job_qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `xxl_job_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_calendars`;
CREATE TABLE `xxl_job_qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_cron_triggers`;
CREATE TABLE `xxl_job_qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `xxl_job_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_cron_triggers
-- ----------------------------
INSERT INTO `xxl_job_qrtz_cron_triggers` VALUES ('schedulerFactoryBean', '4', '5', '0/5 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for xxl_job_qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_fired_triggers`;
CREATE TABLE `xxl_job_qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_job_details`;
CREATE TABLE `xxl_job_qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_job_details
-- ----------------------------
INSERT INTO `xxl_job_qrtz_job_details` VALUES ('schedulerFactoryBean', '4', '5', null, 'com.xxl.job.admin.core.jobbean.RemoteHttpJobBean', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);

-- ----------------------------
-- Table structure for xxl_job_qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_locks`;
CREATE TABLE `xxl_job_qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_locks
-- ----------------------------
INSERT INTO `xxl_job_qrtz_locks` VALUES ('schedulerFactoryBean', 'STATE_ACCESS');
INSERT INTO `xxl_job_qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for xxl_job_qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_paused_trigger_grps`;
CREATE TABLE `xxl_job_qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_scheduler_state`;
CREATE TABLE `xxl_job_qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_scheduler_state
-- ----------------------------
INSERT INTO `xxl_job_qrtz_scheduler_state` VALUES ('schedulerFactoryBean', 'DESKTOP-48IFFV51530581548525', '1530581557724', '5000');

-- ----------------------------
-- Table structure for xxl_job_qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_simple_triggers`;
CREATE TABLE `xxl_job_qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `xxl_job_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_simprop_triggers`;
CREATE TABLE `xxl_job_qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `xxl_job_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `xxl_job_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_triggers`;
CREATE TABLE `xxl_job_qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  CONSTRAINT `xxl_job_qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `xxl_job_qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_triggers
-- ----------------------------
INSERT INTO `xxl_job_qrtz_triggers` VALUES ('schedulerFactoryBean', '4', '5', '4', '5', null, '1530242505000', '1530242500000', '5', 'PAUSED', 'CRON', '1530242443000', '0', null, '2', '');

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_group`;
CREATE TABLE `xxl_job_qrtz_trigger_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_group
-- ----------------------------
INSERT INTO `xxl_job_qrtz_trigger_group` VALUES ('5', 'xxl-job-executor-app', 'test', '1', '0', '127.0.0.1:9988');

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_info`;
CREATE TABLE `xxl_job_qrtz_trigger_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_fail_strategy` varchar(50) DEFAULT NULL COMMENT '失败处理策略',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` text COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_info
-- ----------------------------
INSERT INTO `xxl_job_qrtz_trigger_info` VALUES ('4', '5', '0/5 * * * * ?', '测试任务01', '2018-06-29 11:20:43', '2018-06-29 11:20:43', 'xukk', '', 'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 'FAIL_ALARM', 'BEAN', '', 'GLUE代码初始化', '2018-06-29 11:20:43', null);

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_log`;
CREATE TABLE `xxl_job_qrtz_trigger_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` varchar(2048) DEFAULT NULL COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` varchar(2048) DEFAULT NULL COMMENT '执行-日志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_log
-- ----------------------------
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('14', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:20:45', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:20:55', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('15', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:20:50', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:21:05', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('16', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:20:50', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:21:15', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('17', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:20:55', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:21:25', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('18', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:00', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:21:35', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('19', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:05', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:21:45', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('20', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:10', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:21:55', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('21', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:15', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:22:05', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('22', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:20', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:22:15', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('23', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:25', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:22:25', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('24', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:30', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:22:35', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('25', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:35', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:22:45', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('26', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:21:40', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:22:55', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('27', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:22:32', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:23:05', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('28', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-06-29 11:36:58', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-06-29 11:37:08', '200', '');
INSERT INTO `xxl_job_qrtz_trigger_log` VALUES ('29', '5', '4', 'BEAN', '127.0.0.1:9988', 'demoJobHandler', '', '2018-07-02 14:24:49', '200', '调度机器：172.30.96.1<br>执行器-注册方式：自动注册<br>执行器-地址列表：[127.0.0.1:9988]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>失败处理策略：失败告警<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：127.0.0.1:9988<br>code：200<br>msg：null', '2018-07-02 14:24:59', '200', '');

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_logglue`;
CREATE TABLE `xxl_job_qrtz_trigger_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` text COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_qrtz_trigger_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_qrtz_trigger_registry`;
CREATE TABLE `xxl_job_qrtz_trigger_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(255) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_job_qrtz_trigger_registry
-- ----------------------------
INSERT INTO `xxl_job_qrtz_trigger_registry` VALUES ('75', 'EXECUTOR', 'xxl-job-executor-app', '127.0.0.1:9988', '2018-07-03 09:32:33');
