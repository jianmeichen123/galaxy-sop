INSERT INTO `fx_db`.`information_title` (`id`, `parent_id`, `code`, `name`, `index_no`, `type`, `sign`, `is_show`, `is_valid`, `placeholder`, `is_must`, `val_rule`, `val_rule_mark`) VALUES ('1119', '1117', 'NO1_2_6', '投资经理意见', '112.5', '8', '2', 't', '0', '简要表述下怎么看此项目、为什么推荐、估值逻辑等', '0', '1', '500');

-- 项目来源“事业部”调整成“自开发”
UPDATE `fx_db`.`dict` SET `name`='自开发' WHERE `id`='234';

CREATE TABLE sop_file_history LIKE sop_file;
ALTER TABLE sop_file_history ADD COLUMN file_id BIGINT(20) NOT NULL;