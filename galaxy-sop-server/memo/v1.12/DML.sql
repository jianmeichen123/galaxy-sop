/******添加微信号******/
INSERT INTO `information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `content`, `is_show`, `is_valid`, `placeholder`, `created_time`, `create_id`, `updated_time`, `update_id`, `is_must`, `val_rule`, `val_rule_mark`, `val_rule_formula`, `danao_info`)
 VALUES('1376','1303','NO3_1_1_6','微信号','305.100','1','2',NULL,'f','0',NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL);

 UPDATE `fx_db`.`information_listdata_remark` SET `field_6`='微信号', `sub_title_id6`='1376' WHERE `id`='1';
 
 
 
 /******承揽人历史数据处理 ******/
update fx_db.information_result a set a.content_choose = (select b.code from fx_db.information_dictionary b where b.id = a.content_choose) where a.title_id = 1118 and a.content_choose != 10072;
update fx_db.information_result set content_choose = '非投资线员工' where title_id = 1118 and content_choose = 10072;


INSERT INTO `resource` (`Id`, `parent_id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `resource_order`, `resource_desc`, `product_type`, `is_del`, `is_outtage`, `create_time`, `create_id`, `update_time`, `update_id`, `company_id`, `style`)
 VALUES('1601','0','standard','填写标准','1','sop/galaxy/writePage/','3900','menu展示项，由此进入填写标准页面','0','0','0',NULL,NULL,NULL,NULL,'1','224');
 
 
 UPDATE `fx_db`.`information_title` SET `name`='成立日期' WHERE `id`='1816';
