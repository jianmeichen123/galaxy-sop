ALTER TABLE `fx_db`.`sop_project`   
  ADD COLUMN `progress_history` VARCHAR(255) NULL  COMMENT '流程历史记录(逗号分割)';
ALTER TABLE `fx_db`.`sop_project`  
  ADD COLUMN `business_type_code` VARCHAR(20) NULL  COMMENT '业务类型编码：TZ-投资，ST-闪投'; 
  
 