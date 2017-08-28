/**
 * 添加和修改字段名称
 */
ALTER TABLE `fx_db`.`information_operation_time`   
  ADD COLUMN `report_type` TINYINT(1) NULL  COMMENT '报告分类';
ALTER TABLE `fx_db`.`information_operation_time`   
  CHANGE `time1` `exit_evaluation_time` datetime COMMENT '退出评测时间';
ALTER TABLE `fx_db`.`information_operation_time`   
  CHANGE `time2` `investment_program_time` datetime COMMENT '投资方案时间';
ALTER TABLE `fx_db`.`information_operation_time`   
  CHANGE `time3` `other_business_time` datetime COMMENT '其他事宜时间';
ALTER TABLE `fx_db`.`information_operation_time`   
  CHANGE `time4` `market_development_time` datetime COMMENT '市场与开发时间';
ALTER TABLE `fx_db`.`information_operation_time` 