--项目表添加是否删除字段
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `id_delete` INT(11) NULL DEFAULT 1 AFTER `business_type_code`;
--项目表添加删除原因字段
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `delete_reason` VARCHAR(225) NULL AFTER `id_delete`;
--项目表添加是否删除字段添加备注
ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `id_delete` `id_delete` INT(11) NULL DEFAULT '1' COMMENT '默认值为1；1：正常数据；0:已删除数据；' ;
--项目表添加删除原因字段添加备注
ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `delete_reason` `delete_reason` VARCHAR(225) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '项目删除原因' ;
--任务表添加是否删除字段
ALTER TABLE `fx_db`.`sop_task` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 1 COMMENT '默认值为1；0：已删除；1：正常数据；' AFTER `created_time`;
