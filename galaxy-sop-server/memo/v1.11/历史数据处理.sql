-- 操作日志对象数据变更
UPDATE `fx_db`.`sop_operation_logs` SET `operation_content`='拨付凭证' WHERE `operation_content`='打款凭证';
UPDATE `fx_db`.`sop_operation_logs` SET `operation_content`='工商转让' WHERE `operation_content`='工商变更';

UPDATE `fx_db`.`sop_task` SET `is_delete`='1' where task_name in('完善简历');