ALTER TABLE `fx_db`.`information_title` 
CHANGE COLUMN `type` `type` INT(11) NULL DEFAULT NULL COMMENT '1:文本、2:单选（Radio）、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)、14单选（select）、15一个标题带两个文本域、16多个文本框内容' ;
ALTER TABLE `fx_db`.`information_title` 
CHANGE COLUMN `sign` `sign` INT(11) NULL DEFAULT NULL COMMENT '标记（1:纯标题、2:有内容的标题、3:标识为区域标题,下面还有题）、4:内容拆分出来的标题' ;

INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('1238', '1203', 'NO2_1_1_1', '该项目是一个通过或基于', '203.1', '16', '4', 't', '0', '技术或模式&请填写三级以下分类&具体品类:平台、运营商、服务商、技术提供商、解决方案提供商、工具', '0', '1', '200');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('1239', '1203', 'NO2_1_1_2', '连接的服务两端是（填写服务的两端）', '203.2', '16', '4', 't', '0', '服务一端&服务另一端', '0', '1', '200');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('1240', '1203', 'NO2_1_1_3', '为谁提供了什么样的产品或服务（填写用户、产品服务即内容）', '203.3', '16', '4', 't', '0', '用户&产品服务即内容', '0', '1', '200');
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('1241', '1203', 'NO2_1_1_4', '满足了什么的刚需或解决了什么（填写需求、痛点）', '203.4', '16', '4', 't', '0', '如有需求请填写&如有痛点请填写', '0', '1', '200');


UPDATE `fx_db`.`information_title` SET `type`='16', `content`='该项目是一个通过或基于<sitg>技术或模式</sitg>的<sitg>选择三级以下分类</sitg> 的<sitg>具体品类：平台、运营商、服务商、技术提供商、解决方案提供商、工具</sitg>，连接<sitg>服务一端</sitg>和<sitg>服务另一端</sitg>，为<sitg>用户</sitg>提供<sitg>产品服务即内容</sitg>的产品或服务，满足了<sitg>需求，如有</sitg>的刚需或解决了<sitg>痛点，如有</sitg>。' WHERE `id`='1203';

