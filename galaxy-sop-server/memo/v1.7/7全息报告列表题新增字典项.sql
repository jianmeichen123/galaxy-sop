INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2159,NULL,NULL,'education','学历','D208',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2160,2159,2005,'education1','高中','D208_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2161,2159,2005,'education2','大专','D208_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2162,2159,2005,'education3','本科','D208_3',3,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2163,2159,2005,'education4','硕士','D208_4',4,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2164,2159,2005,'education5','MBA','D208_5',5,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2165,2159,2005,'education6','博士','D208_6',6,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2166,2159,2005,'education7','其他','D208_7',7,0,NULL,NULL,NULL,NULL);


INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2167,NULL,NULL,'founder','是否是核心创始人','D209',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2168,2167,2017,'founder1','是','D209_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2169,2167,2017,'founder','否','D209_2',2,0,NULL,NULL,NULL,NULL);


INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2170,NULL,NULL,'shareholderNature','股东性质','D210',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2171,2167,2030,'shareholderNature1','自然人','D210_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2172,2167,2030,'shareholderNature2','法人','D210_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2173,2167,2030,'shareholderNature3','其他','D210_3',3,0,NULL,NULL,NULL,NULL);


INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2174,NULL,NULL,'itemsBeforeStatus','交割前事项状态','D211',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2175,2174,2051,'itemsBeforeStatus1','未完成','D211_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2176,2174,2051,'itemsBeforeStatus2','已完成','D211_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2177,NULL,NULL,'itemsAfterStatus','交割后事项状态','D212',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2178,2177,2055,'itemsAfterStatus1','未完成','D212_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2179,2177,2055,'itemsAfterStatus2','已完成','D212_2',2,0,NULL,NULL,NULL,NULL);

INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2180,NULL,NULL,'currency','融资历史币种','D213',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2181,2180,2062,'currency1','人民币','D213_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2182,2180,2062,'currency2','美元','D213_2',2,0,NULL,NULL,NULL,NULL);



INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2183,NULL,NULL,'financingStatus','融资轮次','D214',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2184,2183,2063,'financingStatus1','尚未获投','D214_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2185,2183,2063,'financingStatus2','种子轮','D214_2',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2186,2183,2063,'financingStatus3','天使轮','D214_3',3,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2187,2183,2063,'financingStatus4','Pre-A轮','D214_4',4,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2188,2183,2063,'financingStatus5','A轮','D214_5',5,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2189,2183,2063,'financingStatus6','A+轮','D214_6',6,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2190,2183,2063,'financingStatus7','Pre-B轮','D214_7',7,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2191,2183,2063,'financingStatus8','B轮','D214_8',8,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2192,2183,2063,'financingStatus9','B+轮','D214_9',9,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2193,2183,2063,'financingStatus10','C轮','D214_10',10,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2194,2183,2063,'financingStatus11','D轮','D214_11',11,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2195,2183,2063,'financingStatus12','E轮','D214_12',12,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2196,2183,2063,'financingStatus13','Pre-IPO','D214_13',13,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2197,2183,2063,'financingStatus14','已上市','D214_14',14,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2198,2183,2063,'financingStatus15','新三版','D214_15',15,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2199,2183,2063,'financingStatus16','战略投资','D214_16',16,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2200,2183,2063,'financingStatus17','已被收购','D214_17',17,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2201,2183,2063,'financingStatus18','不明确','D214_18',18,0,NULL,NULL,NULL,NULL);


INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2202,NULL,NULL,'stockRight','新老股','D215',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2203,2202,2064,'stockRight1','新股','D215_1',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `fx_db`.`information_dictionary` (`id`,`parent_id`,`title_id`,`code`,`name`,`value`,`sort`,`is_valid`,`created_time`,`create_id`,`updated_time`,`update_id`) VALUES (2204,2202,2064,'stockRight2','老股','D215_2',2,0,NULL,NULL,NULL,NULL);
