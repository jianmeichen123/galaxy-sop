--项目表添加是否删除字段
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `id_delete` INT(11) NULL DEFAULT 1 AFTER `business_type_code`;