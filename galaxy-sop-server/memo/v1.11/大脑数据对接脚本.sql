ALTER TABLE `fx_db`.`information_title_relate` 
ADD COLUMN `danao_info` VARCHAR(50) NULL COMMENT '标识是否从大脑同步数据，为null表示不需要' AFTER `val_rule_formula`;

ALTER TABLE `fx_db`.`information_title` 
ADD COLUMN `danao_info` VARCHAR(50) NULL COMMENT '标识是否从大脑同步数据，为null表示不需要' AFTER `updated_time`;

	 
-- 团队成员 全息报告
UPDATE `fx_db`.`information_title` SET `danao_info`='teamInfo' WHERE `id`='1302';
UPDATE `fx_db`.`information_title` SET `danao_info`='teamInfo' WHERE `id`='1303';
	 
-- 股权结构和融资历史 全息报告
UPDATE `fx_db`.`information_title` SET `danao_info`='financeInfo,equityInfo' WHERE `id`='1902';
UPDATE `fx_db`.`information_title` SET `danao_info`='equityInfo' WHERE `id`='1906';
UPDATE `fx_db`.`information_title` SET `danao_info`='financeInfo' WHERE `id`='1903';


-- 团队成员融资报告、尽调报告
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='teamInfo' WHERE `id`='8302';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='teamInfo' WHERE `id`='5302';


-- 股权结构和融资历史 融资报告、尽调报告
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='financeInfo,equityInfo' WHERE `id`='5902';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='financeInfo,equityInfo' WHERE `id`='8902';


-- 法人信息 尽调报告
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='legalInfo' WHERE `id`='5812';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='legalInfo' WHERE `id`='5813';

