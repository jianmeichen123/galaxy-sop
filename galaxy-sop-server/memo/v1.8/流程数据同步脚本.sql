-- 报告关联表增加流程对应关系
INSERT INTO `fx_db`.`information_title_relate` (`id`, `parent_id`, `name`, `index_no`, `report_type`, `is_valid`) VALUES ('7028', '0', '项目流程信息', '1031', '4', '0');

INSERT INTO `fx_db`.`information_title_relate` (`id`, `parent_id`, `code`, `name`, `title_id`, `index_no`, `report_type`, `is_valid`) VALUES ('7029', '7028', 'FNO10_1', '内评会', '1111', '1032', '4', '0');

INSERT INTO `fx_db`.`information_title_relate` (`id`, `parent_id`, `code`, `name`, `title_id`, `index_no`, `report_type`, `is_valid`) VALUES ('7030', '7028', 'FNO10_2', '立项会', '1113', '1033', '4', '0');

INSERT INTO `fx_db`.`information_title_relate` (`id`, `parent_id`, `code`, `name`, `title_id`, `index_no`, `report_type`, `is_valid`) VALUES ('7031', '7028', 'FNO10_3', '投决会', '1114', '1034', '4', '0');
--报告关联表调整
UPDATE `fx_db`.`information_title_relate` SET `title_id`='1916' WHERE `id`='7003';
UPDATE `fx_db`.`information_title_relate` SET `title_id`='3004' WHERE `id`='7007';
UPDATE `fx_db`.`information_title_relate` SET `title_id`='3008' WHERE `id`='7020';
UPDATE `fx_db`.`information_title_relate` SET `code`='FNO10', `title_id`='1111' WHERE `id`='7028';
-- 项目流程中增加内部评审会会议结果
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('217', 'meeting1Result', '闪投', '1', 'meeting1Result:1', '1', '内评会结果-闪投', '145638261380', '145638261380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('218', 'meeting1Result', '投资', '2', 'meeting1Result:2', '2', '内评会结果-投资', '145638261380', '145638261380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('219', 'meeting1Result', '观望', '3', 'meeting1Result:3', '3', '内评会结果-观望', '145638261380', '145638261380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('220', 'meeting1Result', '否决', '4', 'meeting1Result:4', '4', '内评会结果-否决', '145638261380', '145638261380', '0');

-- 项目流程中增加投决会会议结果
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('221', 'meeting4Result', '投资', '1', 'meeting4Result:1', '1', '投决会结果-投资', '145638261380', '145638261380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('222', 'meeting4Result', '补充材料', '2', 'meeting4Result:2', '2', '投决会结果-补充材料', '145638261380', '145638261380', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) VALUES ('223', 'meeting4Result', '否决', '3', 'meeting4Result:3', '3', '投决会结果-否决', '145638261380', '145638261380', '0');

-- 项目流程中内部评审会会议结果历史数据处理
UPDATE `fx_db`.`sop_meeting_record` SET `meeting_result`='meeting1Result:2' WHERE `meeting_type`='meetingType:1' and `meeting_result`='meetingResult:1';
UPDATE `fx_db`.`sop_meeting_record` SET `meeting_result`='meeting1Result:3' WHERE `meeting_type`='meetingType:1' and `meeting_result`='meetingResult:2';
UPDATE `fx_db`.`sop_meeting_record` SET `meeting_result`='meeting1Result:4' WHERE `meeting_type`='meetingType:1' and `meeting_result`='meetingResult:3';

-- 项目流程中投决会会议结果历史数据处理
UPDATE `fx_db`.`sop_meeting_record` SET `meeting_result`='meeting4Result:1' WHERE `meeting_type`='meetingType:4' and `meeting_result`='meetingResult:1';
UPDATE `fx_db`.`sop_meeting_record` SET `meeting_result`='meeting4Result:2' WHERE `meeting_type`='meetingType:4' and `meeting_result`='meetingResult:2';
UPDATE `fx_db`.`sop_meeting_record` SET `meeting_result`='meeting4Result:3' WHERE `meeting_type`='meetingType:4' and `meeting_result`='meetingResult:3';



-- 当项目已过内部评审阶段，则同步到全息报告>事业部意见的数据为投资
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
select id, '1111', '1143', 0 as `is_valid`, project_time, create_uid from sop_project where project_progress in ('projectProgress:3','projectProgress:4','projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10','projectProgress:11');

-- 项目在内部评审阶段否决了，则同步到全息报告>事业部意见的数据为否决
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
select id, '1111', '1145', 0 as `is_valid`, project_time, create_uid from sop_project where project_progress ='projectProgress:2' and project_status = 'projectStatus:2';

-- 当项目在内部评审阶段，有会议记录的，则把最后添加（按会议时间）的会议记录的结果同步到全息报告>事业部意见
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
SELECT B.project_id, '1111', 
(case when(A.meeting_result in ('meeting1Result:2','meetingResult:1')) then 1143    
	when(A.meeting_result in ('meeting1Result:3','meetingResult:2')) then 1144 
	when(A.meeting_result in ('meeting1Result:4','meetingResult:3')) then 1145
	else null end) as content_choose,
'0', A.created_time, A.create_uid FROM fx_db.sop_meeting_record A,
(SELECT project_id, max(meeting_date) max_day FROM fx_db.sop_meeting_record WHERE meeting_type = 'meetingType:1' and project_id in 
(select id from fx_db.sop_project where project_progress ='projectProgress:2' and project_status = 'projectStatus:0') 
group by project_id
) B
WHERE A.meeting_date = B.max_day;

-- 当项目已过立项会阶段，则同步到全息报告>立项委员会意见则为投资
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
select id, '1113', '1163', 0 as `is_valid`, project_time, create_uid from sop_project where project_progress in ('projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10','projectProgress:11');

-- 当项目在立项会阶段否决了，则同步到全息报告>立项委员会意见则为否决
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
select id, '1113', '1167', 0 as `is_valid`, project_time, create_uid from sop_project where project_progress ='projectProgress:4' and project_status = 'projectStatus:2';

-- 当项目在立项会阶段，有会议记录的，则把最后添加（按会议时间）的会议记录的结果同步到全息报告>立项委员会意见
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
SELECT B.project_id, '1113', 
(case when(A.meeting_result = 'meeting3Result:1') then 1164    
	when(A.meeting_result = 'meeting3Result:2') then 1162 
	when(A.meeting_result = 'meeting3Result:3') then 1163
	when(A.meeting_result = 'meeting3Result:4') then 1165
	when(A.meeting_result = 'meeting3Result:5') then 1166
	when(A.meeting_result = 'meeting3Result:6') then 1167
	else null end) as content_choose,
'0', A.created_time, A.create_uid FROM fx_db.sop_meeting_record A,
(SELECT project_id, max(meeting_date) max_day FROM fx_db.sop_meeting_record WHERE meeting_type = 'meetingType:3' and project_id in 
(select id from fx_db.sop_project where project_progress ='projectProgress:4' and project_status = 'projectStatus:0') 
group by project_id
) B
WHERE A.meeting_date = B.max_day;

-- 当项目已过投决会阶段，则同步到全息报告>投决会意见的数据为投资
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
select id, '1114', '1173', 0 as `is_valid`, project_time, create_uid from sop_project where project_progress in ('projectProgress:8','projectProgress:9','projectProgress:10');

-- 当项目在投决会阶段否决了，则同步到全息报告>投决会意见的数据为否决
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
select id, '1114', '1177', 0 as `is_valid`, project_time, create_uid from sop_project where project_progress ='projectProgress:7' and project_status = 'projectStatus:2';


-- 当项目在投决会阶段，有会议记录的，则把最后添加（按会议时间）的会议记录的结果同步到全息报告>投决会意见
insert into fx_db.information_result(`project_id`,`title_id`,`content_choose`,`is_valid`,`created_time`,`create_id`)
SELECT B.project_id, '1114', 
(case when(A.meeting_result in ('meetingResult:1','meeting4Result:1')) then 1173    
	when(A.meeting_result in ('meetingResult:2','meeting4Result:2')) then 1174 
	when(A.meeting_result in ('meetingResult:3','meeting4Result:3')) then 1177
	else null end) as content_choose,
'0', A.created_time, A.create_uid FROM fx_db.sop_meeting_record A,
(SELECT project_id, max(meeting_date) max_day FROM fx_db.sop_meeting_record WHERE meeting_type = 'meetingType:4' and project_id in 
(select id from fx_db.sop_project where project_progress ='projectProgress:7' and project_status = 'projectStatus:0') 
group by project_id
) B
WHERE A.meeting_date = B.max_day;