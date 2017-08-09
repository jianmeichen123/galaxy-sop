/*全息报告列表题表 每个列表的field字段增加对应题目*/
ALTER TABLE `fx_db`.`information_listdata_remark` 
ADD COLUMN `sub_title_id1` BIGINT(20) NULL AFTER `field_1`,
ADD COLUMN `sub_title_id2` BIGINT(20) NULL AFTER `field_2`,
ADD COLUMN `sub_title_id3` BIGINT(20) NULL AFTER `field_3`,
ADD COLUMN `sub_title_id4` BIGINT(20) NULL AFTER `field_4`,
ADD COLUMN `sub_title_id5` BIGINT(20) NULL AFTER `field_5`,
ADD COLUMN `sub_title_id6` BIGINT(20) NULL AFTER `field_6`,
ADD COLUMN `sub_title_id7` BIGINT(20) NULL AFTER `field_7`,
ADD COLUMN `sub_title_id8` BIGINT(20) NULL AFTER `field_8`,
ADD COLUMN `sub_title_id9` BIGINT(20) NULL AFTER `field_9`,
ADD COLUMN `sub_title_id10` BIGINT(20) NULL AFTER `field_10`;


UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='1374', `sub_title_id1`='1370', `sub_title_id1`='1369', `sub_title_id4`='1375' `sub_title_id1`='1371', WHERE `id`='1';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2014', `sub_title_id2`='2015', `sub_title_id3`='2016', `sub_title_id4`='2017', `sub_title_id5`='2018', `sub_title_id6`='2019', `sub_title_id7`='2020' WHERE `id`='4';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2008', `sub_title_id2`='2009', `sub_title_id3`='2010', `sub_title_id4`='2011', `sub_title_id5`='2012' WHERE `id`='3';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2002', `sub_title_id2`='2003', `sub_title_id3`='2004', `sub_title_id4`='2005', `sub_title_id5`='2006' WHERE `id`='2';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2022', `sub_title_id2`='2023' WHERE `id`='5';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2024', `sub_title_id2`='2025', `sub_title_id3`='2026', `sub_title_id4`='2027', `sub_title_id5`='1551' WHERE `id`='6';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2028', `sub_title_id2`='2029', `sub_title_id3`='1941', `field_4`='股东性质', `sub_title_id4`='2030' WHERE `id`='7';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='1942', `sub_title_id2`='2031', `sub_title_id3`='2032', `sub_title_id4`='2033', `sub_title_id5`='2034' WHERE `id`='8';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2035', `sub_title_id2`='2036', `sub_title_id3`='2037' WHERE `id`='9';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2038', `sub_title_id2`='2039' WHERE `id`='10';
UPDATE `fx_db`.`information_listdata_remark` SET `sub_title_id1`='2040', `sub_title_id2`='2041', `sub_title_id3`='2042' WHERE `id`='11';

/*
全息报告题型调整，文本录入改为列表题
*/
INSERT INTO `information_listdata_remark` (`id`,`title_id`,`code`,`sub_code`,`name`,`field_1`,`sub_title_id1`,`field_2`,`sub_title_id2`,`field_3`,`sub_title_id3`,`field_4`,`sub_title_id4`,`field_5`,`sub_title_id5`,`field_6`,`sub_title_id6`,`field_7`,`sub_title_id7`,`field_8`,`sub_title_id8`,`field_9`,`sub_title_id9`,`field_10`,`sub_title_id10`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`,`fun_flag`) VALUES (15,3022,'grant-part',NULL,'分期注资计划','分拨名称',2043,'计划注资时间',2044,'计划注资金额（万元）',2045,'付款条件',2046,'拨款状态',2047,'文件个数',2048,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'1');
INSERT INTO `information_listdata_remark` (`id`,`title_id`,`code`,`sub_code`,`name`,`field_1`,`sub_title_id1`,`field_2`,`sub_title_id2`,`field_3`,`sub_title_id3`,`field_4`,`sub_title_id4`,`field_5`,`sub_title_id5`,`field_6`,`sub_title_id6`,`field_7`,`sub_title_id7`,`field_8`,`sub_title_id8`,`field_9`,`sub_title_id9`,`field_10`,`sub_title_id10`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`,`fun_flag`) VALUES (16,1810,'delivery-before',NULL,'交割前事项','事项简述',2049,'详细内容',2050,NULL,2051,NULL,2052,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'1');
INSERT INTO `information_listdata_remark` (`id`,`title_id`,`code`,`sub_code`,`name`,`field_1`,`sub_title_id1`,`field_2`,`sub_title_id2`,`field_3`,`sub_title_id3`,`field_4`,`sub_title_id4`,`field_5`,`sub_title_id5`,`field_6`,`sub_title_id6`,`field_7`,`sub_title_id7`,`field_8`,`sub_title_id8`,`field_9`,`sub_title_id9`,`field_10`,`sub_title_id10`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`,`fun_flag`) VALUES (17,1811,'delivery-after',NULL,'交割后事项','事项简述',2053,'详细内容',2054,NULL,2055,NULL,2056,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'1');
INSERT INTO `information_listdata_remark` (`id`,`title_id`,`code`,`sub_code`,`name`,`field_1`,`sub_title_id1`,`field_2`,`sub_title_id2`,`field_3`,`sub_title_id3`,`field_4`,`sub_title_id4`,`field_5`,`sub_title_id5`,`field_6`,`sub_title_id6`,`field_7`,`sub_title_id7`,`field_8`,`sub_title_id8`,`field_9`,`sub_title_id9`,`field_10`,`sub_title_id10`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`,`fun_flag`) VALUES (18,1903,'finance-history',NULL,'融资历史','融资时间',2057,'投资方(机构或个人)',2058,'投资金额',2059,'股权占比',2060,'估值金额',2061,'币种',2062,'融资轮次',2063,'新老股',2064,'合同关键条款',2065,'对赌或业绩承诺条款',2066,0,NULL,NULL,NULL,NULL,'1');
INSERT INTO `information_listdata_remark` (`id`,`title_id`,`code`,`sub_code`,`name`,`field_1`,`sub_title_id1`,`field_2`,`sub_title_id2`,`field_3`,`sub_title_id3`,`field_4`,`sub_title_id4`,`field_5`,`sub_title_id5`,`field_6`,`sub_title_id6`,`field_7`,`sub_title_id7`,`field_8`,`sub_title_id8`,`field_9`,`sub_title_id9`,`field_10`,`sub_title_id10`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`,`fun_flag`) VALUES (19,2067,'operation-indices',NULL,'上一轮融资后关键运营指标变化','指标名称',2035,'融资时指标值',2036,'目前指标值',2037,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'0');







INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('1374', '1303', 'NO3_1_1_5', '姓名', '303.9', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('1375', '1303', 'NO3_1_1_6', '联系电话', '305.1', '1', '2', 'f', '0', '0');

INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `sign`, `is_show`, `is_must`) VALUES ('2001', '1303', 'NO3_1_1_7', '学习经历', '308', '1', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2002', '2001', 'NO3_1_1_7_1', '毕业时间', '308.2', '1', '2', 'f', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`) VALUES ('2003', '2001', 'NO3_1_1_7_2', '毕业院校', '308.3', '1', '2', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`) VALUES ('2004', '2001', 'NO3_1_1_7_3', '学习专业', '308.4', '1', '2', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`) VALUES ('2005', '2001', 'NO3_1_1_7_4', '学历', '308.5', '14', '2', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2006', '2001', 'NO3_1_1_7_5', '开始时间', '308.1', '1', '2', 'f', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2007', '1303', 'NO3_1_1_8', '工作经历', '309', '1', 'f', '0', '');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2008', '2007', 'NO3_1_1_8_1', '起始年月', '309.1', '1', '2', 'f', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2009', '2007', 'NO3_1_1_8_2', '结束年月', '309.2', '1', '2', 'f', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2010', '2007', 'NO3_1_1_8_3', '任职公司', '309.3', '1', '2', 'f', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2011', '2007', 'NO3_1_1_8_4', '任职职位', '309.4', '1', '2', 'f', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2012', '2007', 'NO3_1_1_8_5', '工作内容简述', '309.5', '8', '2', 'f', '0', '1', '1000');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2013', '1303', 'NO3_1_1_9', '创业经历', '310', '1', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2014', '2013', 'NO3_1_1_9_1', '起始年月', '310.1', '1', '2', 'f', '0', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2015', '2013', 'NO3_1_1_9_2', '结束年月', '310.2', '1', '2', 'f', '0', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2016', '2013', 'NO3_1_1_9_3', '担任职务', '310.3', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2017', '2013', 'NO3_1_1_9_4', '是否是核心创始人（5%以上创始股权）', '310.4', '2', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2018', '2013', 'NO3_1_1_9_5', '创立时股权比例', '310.5', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2019', '2013', 'NO3_1_1_9_6', '融资机构及金额、成功或失败或离职原因', '310.6', '8', '2', 'f', '0', '0', '1', '1000');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2020', '2013', 'NO3_1_1_9_7', '项目概述', '310.7', '8', '2', 'f', '0', '0', '1', '1000');

INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2022', '1325', 'NO3_8_1_1', '持股人', '329.1', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`) VALUES ('2023', '2021', 'NO3_8_1_2', '持股比例', '329.2', '1', '2', 'f', '0', '持股比例', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2024', '1548', 'NO5_7_1_2', '主要竞争对手', '549.1', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2025', '1548', 'NO5_7_1_3', '胜算度', '549.2', '1', '2', 'f', '0', '0', '2', '2,0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2026', '1548', 'NO5_7_1_4', '威胁系数', '549.3', '1', '2', 'f', '0', '0', '2', '2,0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2027', '1548', 'NO5_7_1_5', '应对竞争的最有效措施', '549.4', '8', '2', 'f', '0', '0', '1', '200');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2028', '1906', 'NO9_1_4_2', '股东名称', '906.1', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2029', '1906', 'NO9_1_4_3', '占股比例', '906.2', '1', '2', 'f', '0', '0', '3', '3,2');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2030', '1906', 'NO9_1_4_4', '股东性质', '907.1', '14', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2031', '1908', 'NO9_1_6_2', '投资人名称', '910.1', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`) VALUES ('2032', '1908', 'NO9_1_6_3', '投资金额', '910.2', '1', '2', 'f', '0', '', '0', '2');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`) VALUES ('2033', '1908', 'NO9_1_6_4', '股权占比%', '910.3', '1', '2', 'f', '0', '股权占比', '0', '3');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`) VALUES ('2034', '1908', 'NO9_1_6_5', '投后估值', '910.4', '1', '2', 'f', '0', '投后估值', '0', '2');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2035', '1912', 'NO9_1_8_1', '指标名称', '914.1', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2036', '1912', 'NO9_1_8_2', '融资时指标值', '914.2', '8', '2', 'f', '0', '0', '1', '1000');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2037', '1912', 'NO9_1_8_3', '目前指标值', '914.3', '8', '2', 'f', '0', '0', '1', '1000');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2038', '1920', 'NO9_2_5_1', '同类公司名称', '922.1', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `content`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2039', '1920', 'NO9_2_5_2', '估值', '922.2', '19', '2', '万元', 'f', '0', '估值', '0', '2', '9,4');

INSERT INTO `information_title` (`id`,`parent_id`,`code`,`name`,`index_no`,`type`,`sign`,`content`,`is_show`,`is_valid`,`placeholder`,`created_time`,`create_id`,`updated_time`,`update_id`,`is_must`,`val_rule`,`val_rule_mark`) VALUES (2040,'1925','NO9_2_10_1','次数',927.100,1,2,NULL,'f',0,'次数',NULL,NULL,NULL,NULL,0,'1',NULL);
INSERT INTO `information_title` (`id`,`parent_id`,`code`,`name`,`index_no`,`type`,`sign`,`content`,`is_show`,`is_valid`,`placeholder`,`created_time`,`create_id`,`updated_time`,`update_id`,`is_must`,`val_rule`,`val_rule_mark`) VALUES (2041,'1925','NO9_2_10_2','里程碑',927.200,8,2,NULL,'f',0,NULL,NULL,NULL,NULL,NULL,0,'1','1000');
INSERT INTO `information_title` (`id`,`parent_id`,`code`,`name`,`index_no`,`type`,`sign`,`content`,`is_show`,`is_valid`,`placeholder`,`created_time`,`create_id`,`updated_time`,`update_id`,`is_must`,`val_rule`,`val_rule_mark`) VALUES (2042,'1925','NO9_2_10_3','时间',927.300,1,2,NULL,'f',0,NULL,NULL,NULL,NULL,NULL,0,'5',NULL);


INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `name`, `index_no`, `sign`, `is_show`, `is_must`) VALUES ('2043', '3022', '分拨名称', '3062.1', '2', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2044', '3022', '计划注资时间', '3062.2', '1', '2', 'f', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2045', '3022', '计划注资金额（万元）', '3062.3', '2', '2', 'f', '0', '2');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2046', '3022', '付款条件', '3062.4', '3', '2', 'f', '0', '1', '2000');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`) VALUES ('2047', '3022', '拨款状态', '3062.5', '14', '2', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_must`, `val_rule`) VALUES ('2048', '3022', '文件个数', '3062.6', '1', '2', 'f', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2049', '1810', 'NO8_1_9_1', '事项简述', '811.1', '1', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2050', '1810', 'NO8_1_9_2', '详细内容', '811.2', '8', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2051', '1810', 'NO8_1_9_3', '状态', '811.3', '14', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2052', '1810', 'NO8_1_9_4', '文档个数', '811.4', '1', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2053', '1811', 'NO8_1_10_1', '事项简述', '812.1', '1', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2054', '1811', 'NO8_1_10_2', '详细内容', '812.2', '8', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2055', '1811', 'NO8_1_10_3', '状态', '812.3', '14', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2056', '1811', 'NO8_1_10_4', '文档个数', '812.4', '1', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2057', '1903', 'NO9_1_1_1', '融资时间', '903.11', '1', '2', 'f', '0', '0', '5');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2058', '1903', 'NO9_1_1_2', '投资方(机构或个人)', '903.12', '1', '2', 'f', '0', '0', '1');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2059', '1903', 'NO9_1_1_3', '投资金额', '903.13', '1', '2', 'f', '0', '0', '2');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2060', '1903', 'NO9_1_1_4', '股权占比', '903.14', '1', '2', 'f', '0', '0', '3');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`) VALUES ('2061', '1903', 'NO9_1_1_5', '估值金额', '903.15', '1', '2', 'f', '0', '0', '4');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2062', '1903', 'NO9_1_1_6', '币种', '903.16', '14', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2063', '1903', 'NO9_1_1_7', '融资轮次', '903.17', '14', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2064', '1903', 'NO9_1_1_8', '新老股', '903.18', '2', '2', 'f', '0', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2065', '1903', 'NO9_1_1_9', '合同关键条款', '903.19', '8', '2', 'f', '0', '0', '1', '2000');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('2066', '1903', 'NO9_1_1_10', '对赌或业绩承诺条款', '903.20', '8', '2', 'f', '0', '0', '1', '2000');


/*判断列表题是否显示查看按钮*/
ALTER TABLE `fx_db`.`information_listdata_remark` 
ADD COLUMN `fun_flag` VARCHAR(20) NULL DEFAULT '0' AFTER `update_id`;
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='1';
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='15';
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='16';
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='17';
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='18';
