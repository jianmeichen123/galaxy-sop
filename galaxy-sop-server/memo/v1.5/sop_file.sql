
ALTER TABLE `fx_db`.`sop_file`   
	  CHANGE `fil_uri` `fil_uri` VARCHAR(300) CHARSET utf8 COLLATE utf8_bin NULL  COMMENT '存储地址';
	  
	  
ALTER TABLE `fx_db`.`sop_file` ADD COLUMN `interview_record_id` bigint(20) NULL COMMENT '关联访谈ID';
ALTER TABLE `fx_db`.`sop_file` ADD COLUMN `meeting_record_id` bigint(20) NULL COMMENT '关联会议ID';
