ALTER TABLE `fx_db`.`information_title` 
CHANGE COLUMN `val_rule` `val_rule` VARCHAR(50) NULL DEFAULT NULL COMMENT '验证规则\n1：文本框-不限制输入（中英文、数字、特殊字符），val_rule_mark 里存字符长度\n2：金额或次数校验（数字、有小数的允许输入点）val_rule_mark里整数位和小数位允许的长度\n3：百分比（0~100的数字、有小数的允许输入点）val_rule_mark里整数位和小数位允许的长度\n4：特殊（0~168的数字）\n5：时间控件\n11：正常表格（APP样式使用）\n12：横行展示带推进的表格（APP样式使用）' ;


UPDATE `fx_db`.`information_title` SET `placeholder`=null, `is_must`='0', `val_rule`='11', `val_rule_mark`='2,noHeader,field1/updateTime' WHERE `id`='1810';
UPDATE `fx_db`.`information_title` SET `placeholder`=null, `is_must`='0', `val_rule`='11', `val_rule_mark`='2,noHeader,field1/updateTime' WHERE `id`='1811';
UPDATE `fx_db`.`information_title` SET `placeholder`=null, `val_rule`='11', `val_rule_mark`='2,noHeader,field1/field2' WHERE `id`='1903';
UPDATE `fx_db`.`information_title` SET `val_rule`='11', `val_rule_mark`='3,isHeader,field1/field2/field3' WHERE `id`='1548';
UPDATE `fx_db`.`information_title` SET `val_rule`='11', `val_rule_mark`='3,noHeader,field1/field2/field3' WHERE `id`='1303';
UPDATE `fx_db`.`information_title` SET `val_rule`='11', `val_rule_mark`='3,noHeader,field1/field2/field3' WHERE `id`='3022';

UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='1325';
UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='1906';
UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='1908';
UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='1912';
UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='1920';
UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='2067';
UPDATE `fx_db`.`information_title` SET `val_rule`='10' WHERE `id`='1925';

