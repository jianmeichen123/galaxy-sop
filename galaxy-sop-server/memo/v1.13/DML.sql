INSERT INTO `resource` (`Id`, `parent_id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `resource_order`, `resource_desc`, `product_type`, `is_del`, `is_outtage`, `create_time`, `create_id`, `update_time`, `update_id`, `company_id`, `style`)
 VALUES('1701','0','systemMessage','系统通知','1','sop/galaxy/systemMessage/tabSystemMessage','3900','menu展示项，由此进入填写标准页面','0','0','0',NULL,NULL,NULL,NULL,'1','224');
 
 
 
-- web端主副承做人标签
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (1020,1001,'project_list_tabs','列表是否分Tab标签',2,NULL,1119,'多承做人 - 列表是否分Tab标签',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
-- app端主副承做人标签 
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5029,5021,'app_project_list_my','项目列表（负责）',3,NULL,11208,'创投项目负责列表功能权限',1,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5030,5021,'app_project_list_coop','项目列表（协作）',3,NULL,11209,'创投项目协作列表功能权限',1,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5031,5021,'app_project_list_all','项目列表（全部）',3,NULL,11210,'创投项目全部列表功能权限',1,0,0,NULL,NULL,NULL,NULL,1,NULL);
