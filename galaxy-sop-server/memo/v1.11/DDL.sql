CREATE TABLE `fx_db`.`sop_message`(  
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `message_type` VARCHAR(10) COMMENT '消息类型',
  `related_type` INT(10) COMMENT '业务类型：1-项目；2-任务',
  `related_id` BIGINT(20) COMMENT '对应业务ID：项目ID；任务ID',
  `is_published` INT(1) DEFAULT 0 COMMENT '默认值为0；1：已发送；0：为发送；',
  `published_time` BIGINT(20) COMMENT '发送时间',
  `retry_times` INT(10) DEFAULT 0 COMMENT '重试次数',
  `created_id` BIGINT(20) COMMENT '创建者ID',
  `created_time` BIGINT(20) COMMENT '创建时间',
  `updated_id` BIGINT(20) COMMENT '最后更新者ID',
  `updated_time` BIGINT(20) COMMENT '最后更新时间',
  `is_valid` INT(1) DEFAULT 1 COMMENT '默认值为1；1：正常；0：无效；',
  `is_deleted` INT(1) DEFAULT 0 COMMENT '默认值为0；1：已删除；0：正常；',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci
COMMENT='系统消息';
