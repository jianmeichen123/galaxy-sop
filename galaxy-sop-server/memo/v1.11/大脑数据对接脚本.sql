ALTER TABLE `fx_db`.`sop_project`
  ADD COLUMN `danao_proj_code` VARCHAR(50) NULL  COMMENT '大脑项目code' AFTER `delete_reason`;

ALTER TABLE `fx_db`.`information_title` 
ADD COLUMN `danao_info` VARCHAR(50) NULL COMMENT '标识是否从大脑同步数据，为null表示不需要' AFTER `val_rule_formula`;

ALTER TABLE `fx_db`.`information_title_relate` 
ADD COLUMN `danao_info` VARCHAR(50) NULL COMMENT '标识是否从大脑同步数据，为null表示不需要' AFTER `updated_time`;

--搜索历史
CREATE TABLE `sop_search_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `content` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '历史记录，用分割',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备用',
  `type` int(11) DEFAULT '0' COMMENT '类型：0(全局搜索)',
  `is_delete` int(11) DEFAULT '0' COMMENT '0:正常数据；1:已删除数据；',
  `updated_time` bigint(20) DEFAULT NULL,
  `created_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

commit;

-- 团队成员
-- 全息报告
UPDATE `fx_db`.`information_title` SET `danao_info`='teamInfo' WHERE `id`='1302';
UPDATE `fx_db`.`information_title` SET `danao_info`='teamInfo' WHERE `id`='1303';
-- 融资报告、尽调报告
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='teamInfo' WHERE `id`='8302';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='teamInfo' WHERE `id`='8303';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='teamInfo' WHERE `id`='5302';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='teamInfo' WHERE `id`='8303';

-- 股权结构和融资历史
-- 全息报告
UPDATE `fx_db`.`information_title` SET `danao_info`='financeInfo,equityInfo' WHERE `id`='1902';
UPDATE `fx_db`.`information_title` SET `danao_info`='equityInfo' WHERE `id`='1906';
UPDATE `fx_db`.`information_title` SET `danao_info`='financeInfo' WHERE `id`='1903';
-- 融资报告、尽调报告
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='financeInfo,equityInfo' WHERE `id`='5902';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='financeInfo' WHERE `id`='5903';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='equityInfo' WHERE `id`='5906';

UPDATE `fx_db`.`information_title_relate` SET `danao_info`='financeInfo,equityInfo' WHERE `id`='8902';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='financeInfo' WHERE `id`='8903';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='equityInfo' WHERE `id`='5906';

-- 法人信息
-- 尽调报告
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='legalInfo' WHERE `id`='5812';
UPDATE `fx_db`.`information_title_relate` SET `danao_info`='legalInfo' WHERE `id`='5813';




