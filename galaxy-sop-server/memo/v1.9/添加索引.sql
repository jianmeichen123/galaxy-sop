ALTER TABLE `information_title_relate` ADD INDEX index_title_id ( `title_id` );
ALTER TABLE `information_title_relate` ADD INDEX index_report_type ( `report_type` );
ALTER TABLE `information_result` ADD INDEX index_title_id ( `title_id` );

-- 客单价限制条件调整
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,2' WHERE `id`='1422';

-- 权限资源表更新
UPDATE `power`.`resource` SET `resource_code`='meetingRecord_delete', `resource_name`='删除会议记录', `resource_desc`='可删除会议记录功能' WHERE `Id`='1094';
UPDATE `power`.`resource` SET `resource_desc`='可编辑会议记录功能' WHERE `Id`='1093';
UPDATE `power`.`resource` SET `resource_desc`='可新增会议记录功能' WHERE `Id`='1092';
