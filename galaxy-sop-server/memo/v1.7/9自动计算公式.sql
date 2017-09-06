ALTER TABLE `fx_db`.`information_title` 
ADD COLUMN `val_rule_formula` VARCHAR(50) NULL COMMENT '验证规则题之间自动计算公式' AFTER `val_rule_mark`;


/*项目估值自动计算公式*/
UPDATE `fx_db`.`information_title` SET `val_rule_formula`='1943=1916/1917' WHERE `id`='1916';
UPDATE `fx_db`.`information_title` SET `val_rule_formula`='1943=1916/1917' WHERE `id`='1917';

/*主要战略投资人、财务投资人投资情况 增加币种及自动计算逻辑*/
UPDATE `fx_db`.`information_title` SET `type`='19', `content`='万元', `val_rule_formula`='2034=2032/2033' WHERE `id`='2032';
UPDATE `fx_db`.`information_title` SET `type`='19', `content`='%', `val_rule_formula`='2034=2032/2033' WHERE `id`='2033';
UPDATE `fx_db`.`information_title` SET `type`='19', `content`='万元' WHERE `id`='2034';
INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `is_must`) VALUES ('2081', '1908', 'NO9_1_6_6', '币种', '910.15', '14', '2', 'f', '0', '0');

INSERT INTO `fx_db`.`information_dictionary` (`id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2242', 'currency', '主要战略投资人、财务投资人投资情况', 'D225', '0', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2243', '2242', '2081', 'currency1', '人民币', 'D225_1', '1', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2244', '2242', '2081', 'currency2', '美元', 'D225_2', '2', '0');



/*决策报告项目估值自动计算公式*/
UPDATE `fx_db`.`information_title` SET `val_rule_formula`='3012=3004/3010' WHERE `id`='3010';
UPDATE `fx_db`.`information_title` SET `val_rule_formula`='3012=3004/3010' WHERE `id`='3004';