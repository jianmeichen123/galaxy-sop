CREATE TABLE `fx_db`.`information_result_grade` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '分数表主键，自增长',
  `project_id` BIGINT(20) NULL COMMENT '项目ID（关联sop_project表ID）',
  `title_relate_id` BIGINT(20) NULL COMMENT '关联relate表ID',
  `grade1` DOUBLE(10,3) NULL COMMENT '得分1',
  `grade2` DOUBLE(10,3) NULL COMMENT '得分2',
  `is_valid` INT(11) NULL COMMENT '是否可用（0:可用、1:禁用）',
  `created_time` BIGINT(20) NULL COMMENT '创建时间',
  `create_id` BIGINT(20) NULL COMMENT '创建人',
  `updated_time` BIGINT(20) NULL COMMENT '更新时间',
  `update_id` BIGINT(20) NULL COMMENT '更新人',
  PRIMARY KEY (`id`));
