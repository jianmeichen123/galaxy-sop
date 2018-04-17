UPDATE `fx_db`.`information_title` SET `type`='8' WHERE `id`='1203';
UPDATE `fx_db`.`information_title` SET `type`='8' WHERE `id`='1241';

INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `val_rule`, `val_rule_mark`) VALUES ('1270', '1202', 'NO2_1_4', '刚需或痛点', '205.500', '8', '2', 'f', '0', '1', '2000');
UPDATE `fx_db`.`information_title_relate` SET `title_id`='1270' WHERE `id`='1006';

