-- 系统升级消息表
CREATE TABLE `fx_db`.`system_message` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '消息id (表主键)',
  `os_type` VARCHAR(45) NULL COMMENT '发送平台类型  复选 web，ios，android',
  `message_content` TEXT NULL COMMENT '消息内容',
  `is_now_send` TINYINT(4) NULL COMMENT '是否及时发送  0：立即， 1：定时',
  `send_time` BIGINT(20) NULL COMMENT '发送时间',
  `send_status` VARCHAR(45) NULL COMMENT '发送状态 （对应字典项） messageStatus_1  未发送 ，  messageStatus_2 已发送， messageStatus_3 已关闭',
  `is_del` TINYINT(4) NULL COMMENT '是否删除  0：正常， 1：删除',
  `create_id` BIGINT(20) NULL COMMENT '创建人',
  `create_time` BIGINT(20) NULL COMMENT '创建时间',
  `update_id` BIGINT(20) NULL COMMENT '编辑人',
  `update_time` BIGINT(20) NULL COMMENT '编辑时间',
  `upgrade_time` bigint(20) DEFAULT NULL COMMENT '升级时间',
  PRIMARY KEY (`id`))
COMMENT = '系统升级消息表';


-- 消息用户表
CREATE TABLE `fx_db`.`system_message_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '消息用户id (表主键)',
  `message_id` BIGINT(20) NULL COMMENT '系统升级消息表id  关联system_message表主键',
  `message_os` VARCHAR(45) NULL COMMENT '发送平台类型 web，ios，android',
  `user_id` BIGINT(20) NULL COMMENT '用户id  关联power-user 表主键',
  `is_read` TINYINT(4) NULL COMMENT '是否已读  0：未读， 1：已读',
  `is_del` TINYINT(4) NULL COMMENT '是否删除  0：正常， 1：删除',
  `create_id` BIGINT(20) NULL COMMENT '创建人',
  `create_time` BIGINT(20) NULL COMMENT '创建时间',
  `update_id` BIGINT(20) NULL COMMENT '更新人',
  `update_time` BIGINT(20) NULL COMMENT '更新时间',
  PRIMARY KEY (`id`))
COMMENT = '消息用户表';


insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('241','messageStatus','未发送','0','messageStatus:1','0',NULL,'1456382861380','1456382861380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('242','messageStatus','已发送','1','messageStatus:2','0',NULL,'1456382861380','1456382861380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('243','messageStatus','已关闭','2','messageStatus:3','0',NULL,'1456382861380','1456382861380','0');
