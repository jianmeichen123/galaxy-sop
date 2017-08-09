/*全息报告结果表 增加得分字段*/
ALTER TABLE `fx_db`.`information_result` 
ADD COLUMN `grade1` DOUBLE(10,3) NULL COMMENT '得分1' AFTER `content_describe2`,
ADD COLUMN `grade2` DOUBLE(10,3) NULL COMMENT '得分2' AFTER `grade1`;

/*全息报告标题表 增加自动计算公式的字段，处理题目之间自动计算逻辑*/
ALTER TABLE `fx_db`.`information_title` 
ADD COLUMN `val_rule_formula` VARCHAR(50) NULL COMMENT '验证规则题之间自动计算公式' AFTER `val_rule_mark`;
