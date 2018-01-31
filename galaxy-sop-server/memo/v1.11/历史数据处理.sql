-- 操作日志对象数据变更
UPDATE `fx_db`.`sop_operation_logs` SET `operation_content`='拨付凭证' WHERE `operation_content`='打款凭证';
UPDATE `fx_db`.`sop_operation_logs` SET `operation_content`='工商转让' WHERE `operation_content`='工商变更';

UPDATE `fx_db`.`sop_task` SET `is_delete`='1' where task_name in('完善简历');


UPDATE `power`.`resource` SET `resource_name`='拨付凭证', `resource_desc`='menu展示项，由此进入拨付凭证页面' WHERE `Id`='1331';
UPDATE `power`.`resource` SET `resource_name`='认领拨付凭证', `resource_desc`='可认领拨付凭证任务（原系统权限）' WHERE `Id`='1332';
UPDATE `power`.`resource` SET `resource_name`='处理拨付凭证', `resource_desc`='可处理拨付凭证任务' WHERE `Id`='1333';
UPDATE `power`.`resource` SET `resource_desc`='可在处理拨付凭证的时候查看项目基本信息' WHERE `Id`='1334';
UPDATE `power`.`resource` SET `resource_desc`='可在处理拨付凭证的时候上传/更新文件' WHERE `Id`='1335';
UPDATE `power`.`resource` SET `resource_desc`='可在处理拨付凭证的时候提交完成任务' WHERE `Id`='1336';