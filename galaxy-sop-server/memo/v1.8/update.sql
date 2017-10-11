--表格增加查看功能（综合竞争比较，上一轮融资后关键运营指标变化，每次融资的里程碑和时间节点如何）
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='6';
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='9';
UPDATE `fx_db`.`information_listdata_remark` SET `fun_flag`='1' WHERE `id`='11';


UPDATE `fx_db`.`information_title_relate` SET `title_id`='1101' WHERE `id`='7001';
UPDATE `fx_db`.`information_title_relate` SET `title_id`='3008' WHERE `id`='7007';
UPDATE `fx_db`.`information_title_relate` SET `title_id`='1915' WHERE `id`='7003';
UPDATE `fx_db`.`information_title_relate` SET `title_id`='3001' WHERE `id`='7020';



update  fx_db.information_title_relate set code = REPLACE(code, 'FNO', 'FNO1_') where `report_type`=4;
UPDATE `fx_db`.`information_title_relate` SET `code`='FNO1' WHERE `id`='7001';

UPDATE `fx_db`.`dict` SET `is_delete`='1' WHERE `id`='209';
UPDATE `fx_db`.`dict` SET `is_delete`='1' WHERE `id`='211';