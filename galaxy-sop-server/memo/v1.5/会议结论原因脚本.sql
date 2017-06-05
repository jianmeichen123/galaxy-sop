INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('193', 'xhhl', '会议结论原因', '28', 'meetingResultReason', '28', '会议结论原因', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('194', 'meetingResultReason', '否决原因', '1', 'meetingVetoReason', '1', '会议否决原因', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('195', 'meetingVetoReason', '签约其他投资机构', '1', 'meetingVetoReason:1', '1', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('196', 'meetingVetoReason', '签约其他投资机构', '2', 'meetingVetoReason:2', '2', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('197', 'meetingVetoReason', '签约其他投资机构', '3', 'meetingVetoReason:3', '3', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('198', 'meetingVetoReason', '决策改变等', '4', 'meetingVetoReason:4', '4', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('199', 'meetingVetoReason', '其他原因', '5', 'meetingVetoReason:5', '5', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('200', 'meetingResultReason', '待定原因', '2', 'meetingUndeterminedReason', '2', '会议待定原因', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('201', 'meetingUndeterminedReason', '信息未获取全面', '1', 'meetingUndeterminedReason:1', '1', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('202', 'meetingUndeterminedReason', '其他原因', '2', 'meetingUndeterminedReason:2', '2', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('203', 'meetingResultReason', '跟进中原因', '3', 'meetingFollowingReason', '3', '会议跟进中原因', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('204', 'meetingFollowingReason', '未到下一轮融资期', '1', 'meetingFollowingReason:1', '1', '1456382861380', '1456382861380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('205', 'meetingFollowingReason', '其他原因', '2', 'meetingFollowingReason:2', '2', '1456382861380', '1456382861380', '0');


/*调整投资流程显示顺序*/
UPDATE `fx_db`.`dict` SET `dict_sort`='5' WHERE `id`='192';
UPDATE `fx_db`.`dict` SET `dict_sort`='6' WHERE `id`='32';
UPDATE `fx_db`.`dict` SET `dict_sort`='7' WHERE `id`='33';
UPDATE `fx_db`.`dict` SET `dict_sort`='8' WHERE `id`='34';
UPDATE `fx_db`.`dict` SET `dict_sort`='9' WHERE `id`='35';
UPDATE `fx_db`.`dict` SET `dict_sort`='10' WHERE `id`='36';
UPDATE `fx_db`.`dict` SET `dict_sort`='11' WHERE `id`='37';

UPDATE `fx_db`.`dict` SET `name`='投资估值未达成一致' WHERE `id`='196';
UPDATE `fx_db`.`dict` SET `name`='投资方式未达成一致（如并购、财务投资，服务费等）' WHERE `id`='197';