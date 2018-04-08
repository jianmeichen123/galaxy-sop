-- 全息报告
-- 本轮计划的融资及估值>希望融资金额
-- 本轮计划的融资及估值>本轮融资希望稀释股权比例
-- 本轮计划的融资及估值>项目估值
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='1916';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='3,5' WHERE `id`='1917';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='1943';

-- 上轮投资估值为
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='1910';
-- 投资金额为
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='1911';
-- 市场同类公司估值参考>估值
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='2039';
-- 确保该项目在成功上市以前需要投入多少资金
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='1923';

-- 下一轮及隔一轮的融资>估值
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='1929';
-- 下一轮及隔一轮的融资>估值(运营报告)
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='2072';

-- 隔轮融资的估值及时间表>隔轮融资估值
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='1939';
-- 隔轮融资的估值及时间表>隔轮融资估值(运营报告)
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='2079';


-- 主要战略投资人、财务投资人投资情况
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='3,5' WHERE `id`='2033';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='2032';
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='2034';


-- 决策报告
-- 估值安排>股权占比
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='3,5' WHERE `id`='3010';
-- 投决会结果>投资金额
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='3004';
-- 估值安排>投前估值
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='9,6' WHERE `id`='3009';
-- 估值安排>项目估值
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='13,6' WHERE `id`='3012';

