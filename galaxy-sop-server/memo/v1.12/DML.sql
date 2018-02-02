/******添加微信号******/
INSERT INTO `information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `content`, `is_show`, `is_valid`, `placeholder`, `created_time`, `create_id`, `updated_time`, `update_id`, `is_must`, `val_rule`, `val_rule_mark`, `val_rule_formula`, `danao_info`)
 VALUES('1376','1303','NO3_1_1_6','微信号','305.100','1','2',NULL,'f','0',NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL);

 UPDATE `fx_db`.`information_listdata_remark` SET `field_6`='微信号', `sub_title_id6`='1376' WHERE `id`='1';
