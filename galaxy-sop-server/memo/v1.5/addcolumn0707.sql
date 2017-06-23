  ALTER TABLE `fx_db`.`sop_interview_record` 
ADD COLUMN `interview_result` VARCHAR(32) NULL COMMENT '访谈结果' AFTER `schedule_id`;
  ALTER TABLE `fx_db`.`sop_interview_record` 
ADD COLUMN `result_reason` VARCHAR(32) NULL COMMENT '原因' AFTER `interview_result`;
  ALTER TABLE `fx_db`.`sop_interview_record` 
ADD COLUMN `reason_other` VARCHAR(32) NULL COMMENT '其他原因' AFTER `result_reason`;
  ALTER TABLE `fx_db`.`sop_meeting_record` 
ADD COLUMN `result_reason` VARCHAR(32) NULL COMMENT '原因' AFTER `update_time`;
  ALTER TABLE `fx_db`.`sop_meeting_record` 
ADD COLUMN `reason_other` VARCHAR(32) NULL COMMENT '其他原因' AFTER `result_reason`;

ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `finance_mode` VARCHAR(45) NULL COMMENT '投资形式' AFTER `project_time`;

insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('212','xhhl','投资形式','30','financeMode','30','投资形式（项目基本信息）','1456382861380','1456382861380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('213','financeMode','独投','0','financeMode:0','0',NULL,'145638261380','145638261380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('214','financeMode','领投','1','financeMode:1','1',NULL,'145638261380','145638261380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('215','financeMode','合投','2','financeMode:2','2',NULL,'145638261380','145638261380','0');

CREATE TABLE `joint_delivery` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `delivery_type` varchar(20) DEFAULT NULL COMMENT '0-独投;1-领投;2-合投',
  `delivery_name` varchar(100) DEFAULT NULL COMMENT '合投机构名称',
  `delivery_amount` decimal(15,4) DEFAULT NULL COMMENT '投资金额',
  `delivery_share_ratio` decimal(6,4) DEFAULT NULL COMMENT '股权占比',
  `project_id` bigint(50) DEFAULT NULL COMMENT '项目ID',
  `create_uid` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(30) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


ALTER TABLE `fx_db`.`platform_index_config`   
  ADD COLUMN `resource_code` VARCHAR(100) NULL  COMMENT '资源标识' AFTER `created_time`;

UPDATE platform_index_config,platform_resource SET platform_index_config.resource_code = platform_resource.resource_mark 
WHERE platform_resource.id = platform_index_config.resource_id;

ALTER TABLE `power`.`resource`   
  CHANGE `resource_type` `resource_type` INT(11) NULL  COMMENT '资源类型1-菜单;2-页面;3-操作;4-其他(div等);5-桌面模块';
  
 

