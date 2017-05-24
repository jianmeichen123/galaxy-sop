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