ALTER TABLE `fx_db`.`sop_project` 
CHANGE COLUMN `fa_flag` `fa_flag` VARCHAR(11) NOT NULL DEFAULT '0' COMMENT '项目是否来源于FA，默认0表示\"否\"，1表示\"是\"' ;
