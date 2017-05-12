/*拜访计划表增加 拜访计划重要性字段*/
ALTER TABLE `fx_db`.`schedule_info` 
ADD COLUMN `significance` TINYINT(4) NULL COMMENT '拜访计划重要性：1 高， 2  中， 3 低 ' AFTER `updated_time`;

/*访谈记录表增加 拜访计划ID*/
ALTER TABLE `fx_db`.`sop_interview_record` 
ADD COLUMN `schedule_id` BIGINT(20) NULL DEFAULT 0 COMMENT '关联拜访计划ID' AFTER `update_time`;

UPDATE `fx_db`.`schedule_info` SET `project_type`='0' WHERE project_type is null;

UPDATE `fx_db`.`schedule_info` SET `project_id`='0' WHERE project_id is null;
ALTER TABLE `fx_db`.`schedule_info` 
ADD COLUMN `callonAddress` VARCHAR(200) NULL COMMENT '拜访地址';

ALTER TABLE `fx_db`.`schedule_info` 
ADD COLUMN `is_del` TINYINT(4) NULL DEFAULT '0' COMMENT '是否删除字段0:未删除,1:已删除' 


ALTER TABLE `fx_db`.`schedule_contacts` 
ADD COLUMN `firstpinyin` VARCHAR(20) NULL COMMENT '用来进行首字母排序' ,
ADD COLUMN `is_del` TINYINT(4) NULL DEFAULT '0' COMMENT '是否删除字段0:未删除,1:已删除' ;
