
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 1 AFTER `business_type_code`;

ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `delete_reason` VARCHAR(225) NULL AFTER `is_delete`;

ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `is_delete` `is_delete` INT(11) NULL DEFAULT '1' COMMENT '默认值为1；1：正常数据；0:已删除数据；' ;

ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `delete_reason` `delete_reason` VARCHAR(225) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '项目删除原因' ;
--任务表添加是否删除字段
ALTER TABLE `fx_db`.`sop_task` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 1 COMMENT '默认值为1；0：已删除；1：正常数据；' AFTER `created_time`;


ALTER TABLE `fx_db`.`information_title_relate`   
  ADD COLUMN `danao_info` VARCHAR(50) NULL  COMMENT '标识是否从大脑同步数据，为null表示不需要' AFTER `val_rule_formula`;

ALTER TABLE `fx_db`.`sop_project`   
  ADD COLUMN `danao_proj_code` VARCHAR(50) NULL  COMMENT '大脑项目code' AFTER `delete_reason`,
  ADD COLUMN `danao_comp_code` VARCHAR(50) NULL  COMMENT '大脑项目公司code' AFTER `danao_proj_code`;