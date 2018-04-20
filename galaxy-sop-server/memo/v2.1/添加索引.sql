UPDATE `fx_db`.`information_title` SET `type`='8' WHERE `id`='1203';
UPDATE `fx_db`.`information_title` SET `type`='8' WHERE `id`='1241';

INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `val_rule`, `val_rule_mark`) VALUES ('1270', '1202', 'NO2_1_4', '刚需或痛点', '205.500', '8', '2', 'f', '0', '1', '2000');
UPDATE `fx_db`.`information_title_relate` SET `title_id`='1270' WHERE `id`='1006';

-- 修改融资轮次
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2287', '1121', '1108', 'projectStage12', 'A+', 'D3_12', '5', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2288', '1121', '1108', 'projectStage13', 'B+', 'D3_13', '7', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2289', '1121', '1108', 'projectStage14', 'C+', 'D3_14', '9', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2290', '1121', '1108', 'projectStage15', 'D', 'D3_15', '10', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2291', '1121', '1108', 'projectStage16', 'D+', 'D3_16', '11', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2292', '1121', '1108', 'projectStage17', 'E', 'D3_17', '12', '0');

UPDATE `fx_db`.`information_dictionary` SET `name`='B', `sort`='6' WHERE `id`='1126';
UPDATE `fx_db`.`information_dictionary` SET `name`='C', `sort`='8' WHERE `id`='1127';
UPDATE `fx_db`.`information_dictionary` SET `name`='Pre-IPO', `sort`='13' WHERE `id`='1128';
UPDATE `fx_db`.`information_dictionary` SET `sort`='14' WHERE `id`='1129';
UPDATE `fx_db`.`information_dictionary` SET `name`='新三板', `sort`='15' WHERE `id`='1130';
UPDATE `fx_db`.`information_dictionary` SET `sort`='16' WHERE `id`='1139';
UPDATE `fx_db`.`information_dictionary` SET `sort`='17' WHERE `id`='1140';
UPDATE `fx_db`.`information_dictionary` SET `name`='Pre-A' WHERE `id`='1124';
UPDATE `fx_db`.`information_dictionary` SET `name`='A' WHERE `id`='1125';

-- 修改否决原因
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('252', 'meetingVetoReason', '公司定位、商业模式有问题', '6', 'meetingVetoReason:6', '5', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('245', 'meetingVetoReason', '创新性不足', '7', 'meetingVetoReason:7', '6', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('246', 'meetingVetoReason', '市场地位不具有领先性', '8', 'meetingVetoReason:8', '7', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('247', 'meetingVetoReason', '市场规模和市场机会不大', '9', 'meetingVetoReason:9', '8', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('248', 'meetingVetoReason', '产品和服务不够好', '10', 'meetingVetoReason:10', '9', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('249', 'meetingVetoReason', '收费模式不清晰有问题', '11', 'meetingVetoReason:11', '10', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('250', 'meetingVetoReason', '技术达不到要求', '12', 'meetingVetoReason:12', '11', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('251', 'meetingVetoReason', '团队能力有限', '13', 'meetingVetoReason:13', '12', '1456382861380', '1456382861380', '0');
UPDATE `fx_db`.`dict` SET `name`='已经或意向签约其他投资机构' WHERE `id`='195';
UPDATE `fx_db`.`dict` SET `name`='决策改变' WHERE `id`='198';
UPDATE `fx_db`.`dict` SET `dict_sort`='13' WHERE `id`='199';


INSERT INTO `fx_db`.`project_standard` (`id`, `module_code`, `module_name`, `standard_details`, `status`, `is_valid`) VALUES ('13', '12', '项目简介', '该项目是一个通过或基于（VR定制屏和核心延时算法技术）的（提供全球最清晰的VR体验）的（头显一体化解决方案提供商），连接（优质内容）和（消费者），为（消费者）提供（显示清晰不眩晕的VR产品）的产品或服务，满足了（使用优质体验VR设备的需求）的刚需或解决了（VR设备不清晰、眩晕的痛点）。', '1', '0');

INSERT INTO `dict` VALUES ('252', 'projectType', '直营', '3', 'projectType:3', '3', NULL, '1456817970774', '1456817970774', '0');

UPDATE dict SET is_delete=1 WHERE dict_code='projectType:2';