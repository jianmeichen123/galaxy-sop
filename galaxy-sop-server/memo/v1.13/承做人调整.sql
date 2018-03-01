INSERT INTO `fx_db`.`information_listdata_remark` (`id`, `title_id`, `code`, `name`, `field_1`, `sub_title_id1`, `field_2`, `sub_title_id2`, `field_3`, `sub_title_id3`, `field_4`, `sub_title_id4`, `sub_title_id5`, `fun_flag`) VALUES ('23', '1103', 'team-person', '承做人', '项目承做人', '1127', '承做比例（%）', '1128', '隶属事业部', '1104', '事业部总经理', '1105', '1129', '0');

ALTER TABLE `fx_db`.`information_title` 
CHANGE COLUMN `type` `type` INT(11) NULL DEFAULT NULL COMMENT '1:文本、2:单选（Radio）、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)、14单选（select）、15一个标题带两个文本域、16多个文本框内容、18单选下拉框（但是下拉选项内容来自另外一个列表题的部分选项）、19：文本框输入题目答案带单位的处理；20：文本框输入题目答案带单位带币种的处理; 21: 下拉选择加备注 22:复选题（选项来自列表题一部分，评分每个选项单独评）23:下拉复选带备注，24：联动数据，自动取出' ;


UPDATE `fx_db`.`information_title` SET `type`='10' WHERE `id`='1103';
UPDATE `fx_db`.`information_title` SET `parent_id`='1103', `index_no`='104.300', `type`='14', `is_show`='f' WHERE `id`='1105';
UPDATE `fx_db`.`information_title` SET `parent_id`='1103', `index_no`='104.400', `type`='24', `is_show`='f' WHERE `id`='1104';
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `type`, `sign`, `index_no`, `is_show`, `is_valid`) VALUES ('1127', '1103', 'NO1_1_2_1', '项目承做人', '14', '2', '104.100', 'f', '0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `type`, `sign`, `index_no`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('1128', '1103', 'NO1_1_2_2', '承做比例', '1', '2', '104.200', 'f', '0', '承做占比（正整数）', '0', '3', '3,0');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `content`, `is_show`, `is_valid`) VALUES ('1129', '1103', 'NO1_1_2_5', '承做人类别', '104.5', '11', '2', '0:主承做人，1：副承做人', 'f', '0');

