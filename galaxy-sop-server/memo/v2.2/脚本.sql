-- 投资方主体调整
ALTER TABLE `fx_db`.`information_dictionary` 
ADD COLUMN `short_name` VARCHAR(100) NULL AFTER `name`;


UPDATE `fx_db`.`information_dictionary` SET `short_name`='天马基金' WHERE `id`='2271';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='星河之光' WHERE `id`='2272';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='诚合基石' WHERE `id`='2278';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='苍穹之下' WHERE `id`='2274';
UPDATE `fx_db`.`information_dictionary` SET `name`='喀什耀灼创业投资有限公司', `short_name`='喀什耀灼' WHERE `id`='2275';
UPDATE `fx_db`.`information_dictionary` SET `name`='霍尔果斯燎原创业投资有限公司', `short_name`='燎原投资' WHERE `id`='2276';
UPDATE `fx_db`.`information_dictionary` SET `is_valid`='1' WHERE `id`='2277';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='天马股份' WHERE `id`='2279';
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2287', '2270', '3020', 'investmentMainpart10', '霍尔果斯市微创之星创业投资有限公司', '微创之星', 'D229_10', '10', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2288', '2270', '3020', 'investmentMainpart11', '日照基石资本股权投资中心（有限合伙）', '日照基石', 'D229_11', '11', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2289', '2270', '3020', 'investmentMainpart12', '日照元鼎股权投资基金（有限合伙）', '日照元鼎', 'D229_12', '12', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2290', '2270', '3020', 'investmentMainpart13', '东方元鼎（北京）投资咨询有限公司', '东方元鼎', 'D229_13', '13', '0');
UPDATE `fx_db`.`information_dictionary` SET `is_valid`='1' WHERE `id`='2273';
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2291', '2270', '3020', 'investmentMainpart14', '喀什星河创业投资有限公司', '喀什星河创业', 'D229_14', '14', '0');
UPDATE `fx_db`.`information_dictionary` SET `short_name`='星河数银' WHERE `id`='2277';
-- 投资主体由 "喀什星河互联创业投资有限公司" 调整为 "喀什诚合基石创业投资有限公司"			
UPDATE `fx_db`.`information_result` SET `content_choose`='2278' WHERE content_choose = 2273;			
			


-- 健康度调整
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('259', 'xhhl', '健康度', '0', 'healthState', '0', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('254', 'healthState', '风险', '0', 'healthState:0', '4', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('255', 'healthState', '优异', '1', 'healthState:1', '1', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('256', 'healthState', '健康', '2', 'healthState:2', '2', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('257', 'healthState', '关注', '3', 'healthState:3', '3', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('258', 'healthState', '损失', '4', 'healthState:4', '5', '1524727556630', '1524727556630', '0');


--健康度添加删除标识
ALTER TABLE `fx_db`.`sop_project_health` 
ADD COLUMN `is_delete` INT(11) NULL DEFAULT 0 COMMENT '删除标识：0，正常；1：删除' AFTER `updated_time`;




UPDATE `power`.`resource` SET `parent_id`='1159' WHERE `Id`='1163';
UPDATE `power`.`resource` SET `parent_id`='1159' WHERE `Id`='1162';
UPDATE `power`.`resource` SET `parent_id`='1159' WHERE `Id`='1161';
UPDATE `power`.`resource` SET `parent_id`='1159' WHERE `Id`='1160';
UPDATE `power`.`resource` SET `parent_id`='1164' WHERE `Id`='1152';
UPDATE `power`.`resource` SET `parent_id`='1164 ' WHERE `Id`='1153';
UPDATE `power`.`resource` SET `parent_id`='1164' WHERE `Id`='1154';
UPDATE `power`.`resource` SET `parent_id`='1164' WHERE `Id`='1155';
UPDATE `power`.`resource` SET `parent_id`='1164' WHERE `Id`='1156';
UPDATE `power`.`resource` SET `parent_id`='1165' WHERE `Id`='1157';
UPDATE `power`.`resource` SET `parent_id`='1165' WHERE `Id`='1158';

UPDATE `power`.`resource` SET `resource_order`='2101' WHERE `Id`='1164';
UPDATE `power`.`resource` SET `resource_order`='2104' WHERE `Id`='1165';
UPDATE `power`.`resource` SET `resource_order`='2105' WHERE `Id`='1158';
UPDATE `power`.`resource` SET `resource_order`='2117' WHERE `Id`='1156';
UPDATE `power`.`resource` SET `parent_id`='1151', `resource_desc`='运营分析模块-运营数据功能' WHERE `Id`='1159';

insert into `resource` (`Id`, `parent_id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `resource_order`, `resource_desc`, `product_type`, `is_del`, `is_outtage`, `create_time`, `create_id`, `update_time`, `update_id`, `company_id`, `style`) 
values
('1164','1151','operationMeeting','运营会议纪要','3',NULL,'2113','运营分析模块-运营会议纪要功能','0','0','0',NULL,NULL,NULL,NULL,'1',NULL);
insert into `resource` (`Id`, `parent_id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `resource_order`, `resource_desc`, `product_type`, `is_del`, `is_outtage`, `create_time`, `create_id`, `update_time`, `update_id`, `company_id`, `style`) 
values
('1165','1151','projectHealth','健康状况','3',NULL,'2114','运营分析模块-健康状况功能','0','0','0',NULL,NULL,NULL,NULL,'1',NULL);
insert into `resource` (`Id`, `parent_id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `resource_order`, `resource_desc`, `product_type`, `is_del`, `is_outtage`, `create_time`, `create_id`, `update_time`, `update_id`, `company_id`, `style`) 
values
('1166','1165','projectHealth_delete','编辑项目健康度','3',NULL,'2115','健康状况-编辑项目健康度','0','0','0',NULL,NULL,NULL,NULL,'1',NULL);
insert into `resource` (`Id`, `parent_id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `resource_order`, `resource_desc`, `product_type`, `is_del`, `is_outtage`, `create_time`, `create_id`, `update_time`, `update_id`, `company_id`, `style`) 
values
('1167','1165','projectHealth_delete','删除项目健康度','3',NULL,'2116','健康状况-删除项目健康度','0','0','0',NULL,NULL,NULL,NULL,'1',NULL);

-- 移动端运营分析权限
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5501,0,'app_operation','运营分析(移动端)',2,NULL,5501,'可查看到运营分析标签内的信息',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5502,5501,'app_operationMeeting','运营会议纪要(移动端)',2,NULL,5502,'运营分析模块-运营会议纪要功能',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5503,5502,'app_operationMeeting_add','添加运营会议',3,NULL,5503,'可以添加运营分析中的运营会议',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5504,5502,'app_operationMeeting_update','编辑运营会议',3,NULL,5504,'可以编辑运营分析中的运营会议',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5505,5502,'app_operationMeeting_delete','删除运营会议',3,NULL,5505,'可以删除运营分析中的运营会议',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5506,5501,'app_projectHealth','健康状况(移动端)',2,NULL,5506,'运营分析模块-健康状况功能',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5507,5506,'app_projectHealth_add','添加健康状态记录',3,NULL,5507,'可以添加运营分析中的健康状况记录',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5508,5506,'app_projectHealth_delete','编辑项目健康度',3,NULL,5508,'可以编辑运营分析中的健康状况记录',0,0,0,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `resource` (`Id`,`parent_id`,`resource_code`,`resource_name`,`resource_type`,`resource_url`,`resource_order`,`resource_desc`,`product_type`,`is_del`,`is_outtage`,`create_time`,`create_id`,`update_time`,`update_id`,`company_id`,`style`) VALUES (5509,5506,'app_projectHealth_delete','删除项目健康度',3,NULL,5509,'可以删除运营分析中的健康状况记录',0,0,0,NULL,NULL,NULL,NULL,1,NULL);




