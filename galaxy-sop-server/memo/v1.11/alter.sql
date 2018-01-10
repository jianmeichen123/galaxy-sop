
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 1 AFTER `business_type_code`;

ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `delete_reason` VARCHAR(225) NULL AFTER `is_delete`;

ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `is_delete` `is_delete` INT(11) NULL DEFAULT '0' COMMENT '默认值为0；0：正常数据；1:已删除数据；' ;

ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `delete_reason` `delete_reason` VARCHAR(225) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '项目删除原因' ;
--任务表添加是否删除字段
ALTER TABLE `fx_db`.`sop_task` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 0 COMMENT '默认值为0；1：已删除；0：正常数据；' AFTER `created_time`;


