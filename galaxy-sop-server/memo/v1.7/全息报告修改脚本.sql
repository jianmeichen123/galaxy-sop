
/*全息报告标题表字段备注更新*/
ALTER TABLE `fx_db`.`information_title` 
CHANGE COLUMN `type` `type` INT(11) NULL DEFAULT NULL COMMENT '1:文本、2:单选（Radio）、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)、14单选（select）、15一个标题带两个文本域、16多个文本框内容、18单选下拉框（但是下拉选项内容来自另外一个列表题的部分选项）、19：文本框输入题目答案带单位的处理；20：文本框输入题目答案带单位带币种的处理' ;


/*全息报告标题表 题目类型修改 希望融资金额*/
UPDATE `fx_db`.`information_title` SET `name`='希望融资金额', `type`='19', `content`='万元', `val_rule_mark`='9,4'  WHERE `id`='1916';
/*全息报告标题表 题目类型修改 确保该项目在成功上市以前需要投入多少资金*/
UPDATE `fx_db`.`information_title` SET `name`='确保该项目在成功上市以前需要投入多少资金', `type`='19', `content`='万元', `val_rule_mark`='9,4' WHERE `id`='1923';
/*全息报告标题表 题目类型修改 本轮融资希望稀释股权比例*/
UPDATE `fx_db`.`information_title` SET `name`='本轮融资希望稀释股权比例', `type`='19', `content`='%', `val_rule_mark`='3,2' WHERE `id`='1917';
/*全息报告标题表 题目类型修改 上轮投资估值为*/
UPDATE `fx_db`.`information_title` SET `name`='上轮投资估值为', `type`='20', `content`='万元', `val_rule_mark`='9,4' WHERE `id`='1910';
/*全息报告标题表 题目类型修改 投资金额为*/
UPDATE `fx_db`.`information_title` SET `name`='投资金额为', `type`='20', `content`='万元', `val_rule_mark`='9,4' WHERE `id`='1911';
UPDATE `fx_db`.`information_title` SET `name`='估值', `type`='20', `content`='万元', `val_rule_mark`='9,4' WHERE `id`='1929';
UPDATE `fx_db`.`information_title` SET `type`='20', `content`='万元', `val_rule_mark`='9,4' WHERE `id`='1939';



/*全息报告标题表 题目类型修改 核心团队对核心能力的满足度*/
UPDATE `fx_db`.`information_title` SET `type`='2', `placeholder`='', `val_rule`='', `val_rule_mark`='' WHERE `id`='1368';

/*全息报告标题表 新增题目 项目估值*/
INSERT INTO `information_title` (`id`,`parent_id`,`code`,`name`,`index_no`,`type`,`sign`,`content`,`is_show`,`is_valid`,`placeholder`,`created_time`,`create_id`,`updated_time`,`update_id`,`is_must`,`val_rule`,`val_rule_mark`) VALUES (1943,'1915','NO9_2_11','项目估值',919.500,19,2,'万元','t',0,NULL,NULL,NULL,NULL,NULL,0,'2','9,4');
/*全息报告标题表 新增题目 性格是否傲慢*/
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`) VALUES ('1373', '1354', 'NO3_9_10_9', '性格是否傲慢', '363.5', '2', '2', 't', '0');


/*全息报告列表题目表 增加字段 团队成员-学习经历-开始时间*/
UPDATE `fx_db`.`information_listdata_remark` SET `field_5`='开始时间' WHERE `id`='2';


/*全息报告标题表 题目类型修改 需要在交割前解决的事项*/
UPDATE `fx_db`.`information_title` SET `type`='10' WHERE `id`='1810';
/*全息报告标题表 题目类型修改 需要在交割后解决的事项*/
UPDATE `fx_db`.`information_title` SET `type`='10' WHERE `id`='1811';
/*全息报告标题表 题目类型修改 融资历史*/
UPDATE `fx_db`.`information_title` SET `name`='融资历史', `type`='10' WHERE `id`='1903';


/*全息报告字典表  运营数据  通过运营数据该项目所处的阶段*/
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1809', '1801', '1406', 'operationStage8', '已上市', 'D64_8', '8', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1810', '1801', '1406', 'operationStage9', '新三板挂牌', 'D64_9', '9', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1819', '1801', '1406', 'operationStage10', '并购', 'D64_10', '10', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1820', '1801', '1406', 'operationStage11', '战略投资', 'D64_11', '11', '0');
UPDATE `fx_db`.`information_dictionary` SET `name`='IPO' WHERE `id`='1808';
/*全息报告字典表  基础信息  项目阶段*/
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1129', '1121', '1108', 'projectStage8', '已上市', 'D3_8', '8', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1130', '1121', '1108', 'projectStage9', '新三板挂牌', 'D3_9', '9', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1139', '1121', '1108', 'projectStage10', '并购', 'D3_10', '10', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('1140', '1121', '1108', 'projectStage11', '战略投资', 'D3_11', '11', '0');
UPDATE `fx_db`.`information_dictionary` SET `name`='IPO' WHERE `id`='1128';
/*全息报告字典表  基础信息  事业部意见 将转向改为观望*/
UPDATE `fx_db`.`information_dictionary` SET `name`='观望' WHERE `id`='1144';
UPDATE `fx_db`.`information_dictionary` SET `name`='否决' WHERE `id`='1145';
UPDATE `fx_db`.`information_dictionary` SET `name`='否决' WHERE `id`='1167';

/*全息报告字典表  基础信息  投决会意见 */
DELETE FROM `fx_db`.`information_dictionary` WHERE `id`='1172';
DELETE FROM `fx_db`.`information_dictionary` WHERE `id`='1175';
DELETE FROM `fx_db`.`information_dictionary` WHERE `id`='1176';
UPDATE `fx_db`.`information_dictionary` SET `name`='否决' WHERE `id`='1177';

/*投决会意见 历史数据处理 闪投 - 投资 观望、转向 - 补充材料*/
UPDATE `fx_db`.`information_result` SET `content_choose`='1173' WHERE title_id = 1114 and content_choose = 1172;
UPDATE `fx_db`.`information_result` SET `content_choose`='1174' WHERE title_id = 1114 and content_choose = 1175;
UPDATE `fx_db`.`information_result` SET `content_choose`='1174' WHERE title_id = 1114 and content_choose = 1176;


UPDATE `fx_db`.`information_title` SET `code`='NO2_1_2' WHERE `id`='1204';
UPDATE `fx_db`.`information_title` SET `code`='NO2_1_3' WHERE `id`='1205';

/*综合竞争比较提变成显示*/
UPDATE `fx_db`.`information_title` SET `is_show`='t' WHERE `id`='1548';


UPDATE `fx_db`.`information_title` SET `code`='NO9_3_12_2' WHERE `id`='1940';


