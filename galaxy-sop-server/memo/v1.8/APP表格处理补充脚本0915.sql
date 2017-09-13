ALTER TABLE `fx_db`.`information_title` 
CHANGE COLUMN `val_rule_mark` `val_rule_mark` VARCHAR(100) NULL DEFAULT NULL COMMENT '验证规则补充描述（字符长度……）' ;


UPDATE `fx_db`.`information_title` SET `val_rule_mark`='2,isHeader,field1/field2' WHERE `id`='1325';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='4,isHeader,field1/field2/field3/field4' WHERE `id`='1906';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='6,isHeader,field1/field2/field3/field4/field5/field6' WHERE `id`='1908';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='3,isHeader,field1/field2/field3' WHERE `id`='1912';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='2,isHeader,field1/field2' WHERE `id`='1920';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='3,isHeader,field1/field2/field3' WHERE `id`='1925';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='3,isHeader,field1/field2/field3' WHERE `id`='2067';
