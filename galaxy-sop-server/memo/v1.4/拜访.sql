/*拜访计划表增加 拜访计划重要性字段*/
ALTER TABLE `fx_db`.`schedule_info` 
ADD COLUMN `significance` TINYINT(4) NULL COMMENT '拜访计划重要性：1 高， 2  中， 3 低 ' AFTER `updated_time`;

/*访谈记录表增加 拜访计划ID*/
ALTER TABLE `fx_db`.`sop_interview_record` 
ADD COLUMN `schedule_id` BIGINT(20) NULL DEFAULT 0 COMMENT '关联拜访计划ID' AFTER `update_time`;
