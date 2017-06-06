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
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('214','financeMode','合投','1','financeMode:1','1',NULL,'145638261380','145638261380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('215','financeMode','领投','2','financeMode:2','2',NULL,'145638261380','145638261380','0');

CREATE TABLE `joint_delivery` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `delivery_type` tinyint(1) DEFAULT NULL COMMENT '0-独投;1-领投;2-合投',
  `delivery_name` varchar(20) DEFAULT NULL COMMENT '合投机构名称',
  `delivery_amount` decimal(15,4) DEFAULT NULL COMMENT '投资金额',
  `delivery_share_ratio` decimal(6,4) DEFAULT NULL COMMENT '股权占比',
  `project_id` bigint(50) DEFAULT NULL COMMENT '项目ID',
  `create_uid` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(30) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
