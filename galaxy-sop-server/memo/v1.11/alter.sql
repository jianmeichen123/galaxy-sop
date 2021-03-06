
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 0 AFTER `business_type_code`;

ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `delete_reason` VARCHAR(225) NULL AFTER `is_delete`;

ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `is_delete` `is_delete` INT(11) NULL DEFAULT '0' COMMENT '默认值为0；0：正常数据；1:已删除数据；' ;

ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `delete_reason` `delete_reason` VARCHAR(225) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '项目删除原因' ;
--任务表添加是否删除字段
ALTER TABLE `fx_db`.`sop_task` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 0 COMMENT '默认值为0；1：已删除；0：正常数据；' AFTER `created_time`;


  
ALTER TABLE `fx_db`.`sop_transfer_record` 
ADD COLUMN `operate_id` BIGINT(20) NULL COMMENT '操作人id（也是该条记录的创建人）  移交为投资经理本人；指派为：当前操作人 注意：历史数据处理该字段为 before_uid的值）' AFTER `updated_time`,
ADD COLUMN `operate_type` VARCHAR(11) NULL DEFAULT NULL COMMENT '操作类型：0:移交，1：指派 ' AFTER `operate_id`;


ALTER TABLE `fx_db`.`sop_meeting_scheduling` 
ADD COLUMN `is_delete` INT(5) NULL DEFAULT 0 COMMENT '删除标识： 0：正常；1：删除' AFTER `created_time`;

alter table  schedule_message modify remrk_id varchar(100);