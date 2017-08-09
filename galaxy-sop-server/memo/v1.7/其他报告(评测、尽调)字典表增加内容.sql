/*
-- Query: SELECT * FROM fx_db1.information_dictionary where id > 2103 and id < 3000
LIMIT 0, 50000

-- Date: 2017-07-10 14:03
*/
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2121,NULL,NULL,'urgentDispose','是否需要加急签约并付款','D96',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2122,2121,3024,'urgentDispose1','是','D96_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2123,2121,3024,'urgentDispose2','否','D96_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2124,NULL,NULL,'feasibility','可行性','D97',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2125,2124,1255,'feasibility1','可行','D97_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2126,2124,1255,'feasibility2','不可行','D97_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2127,NULL,NULL,'chance','是否有机会','D98',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2128,2127,1259,'chance1','有','D98_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2129,2127,1259,'chance2','没有','D98_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2130,NULL,NULL,'lawBruising','法务方面是否存在上市硬伤','D99',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2131,2130,1972,'lawBruising1','存在','D99_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2132,2130,1972,'lawBruising2','不存在','D99_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2133,NULL,NULL,'financeBruising','财务方面是否存在上市硬伤','D200',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2134,2133,1973,'financeBruising1','存在','D200_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2135,2133,1973,'financeBruising2','不存在','D200_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2136,NULL,NULL,'policyLimiting','上市是否存在政策限制','D201',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2137,2136,1974,'policyLimiting1','存在','D201_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2138,2136,1974,'policyLimiting2','不存在','D201_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2139,NULL,NULL,'chance','是否有机会','D202',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2142,NULL,NULL,'menaceFrom','主要竞争威胁来自','D203',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2143,2142,1566,'menaceFrom1','显在竞争对手','D203_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2144,2142,1566,'menaceFrom2','潜在竞争对手','D203_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2145,2142,1566,'menaceFrom3','行业新技术或新模式淘汰','D203_3',3,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2146,NULL,NULL,'satisfactionDegree','核心团队对核心能力的满足度','D204',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2147,2146,1368,'satisfactionDegree1','满足（90%-100%）','D204_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2148,2146,1368,'satisfactionDegree2','大部分满足（50%-90%）','D204_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2149,2146,1368,'satisfactionDegree3','不满足（0%-70%）','D204_3',3,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2150,NULL,NULL,'currency','币种','D205',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2151,2150,1910,'currency1','人民币','D205_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2152,2150,1910,'currency2','美元','D205_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2153,NULL,NULL,'currency','币种','D206',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2154,2153,1911,'currency1','人民币','D206_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2155,2153,1911,'currency2','美元','D206_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2156,NULL,NULL,'arrogance','性格是否傲慢','D207',NULL,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2157,2156,1373,'arrogance1','是','D207_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2158,2156,1373,'arrogance2','否','D207_2',2,0,NULL,NULL,NULL,NULL);


INSERT INTO `fx_db`.`information_dictionary` (`id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2236', 'currency', '估值币种', 'D223', '0', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2237', '2236', '1929', 'currency1', '人民币', 'D223_1', '1', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2238', '2236', '1929', 'currency2', '美元', 'D223_2', '2', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2239', 'currency', '隔轮融资估值币种', 'D224', '0', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2240', '2239', '1939', 'currency1', '人民币', 'D224_1', '1', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2241', '2239', '1939', 'currency2', '美元', 'D224_2', '2', '0');
