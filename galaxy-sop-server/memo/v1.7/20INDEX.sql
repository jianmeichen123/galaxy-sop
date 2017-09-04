

CREATE TABLE `fx_db`.`information_progress` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user项目各报告进度id',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `uid` bigint(20) DEFAULT NULL COMMENT '项目所属人id',
  `NO` double(6,5) DEFAULT NULL COMMENT '全息报告 进度',
  `DN` double(6,5) DEFAULT NULL COMMENT '尽调报告 进度',
  `PN` double(6,5) DEFAULT NULL COMMENT '决策报告 进度',
  `GN` double(6,5) DEFAULT NULL COMMENT '融资报告 进度',
  `ON` double(6,5) DEFAULT NULL COMMENT '运营报告 进度',
  `EN` double(6,5) DEFAULT NULL COMMENT '评测报告 进度',
  `CN` double(6,5) DEFAULT NULL COMMENT '初评报告 进度',
  `created_time` bigint(20) DEFAULT NULL,
  `updated_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pid_uid` (`project_id`,`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

ALTER TABLE `fx_db`.`information_progress`
  ADD  INDEX `pid_uid` (`project_id`, `uid`);
ALTER TABLE `fx_db`.`information_file`
  ADD  INDEX `pid_tid` (`project_id`, `title_id`);
ALTER TABLE `fx_db`.`information_fixed_table`
  ADD  INDEX `pid_tid` (`project_id`, `title_id`);
ALTER TABLE `fx_db`.`information_listdata`
  ADD  INDEX `pid_tid` (`project_id`, `title_id`);
ALTER TABLE `fx_db`.`information_result`
  ADD  INDEX `pid_tid` (`project_id`, `title_id`);
ALTER TABLE `fx_db`.`information_result_grade`
  ADD  INDEX `pid_tid` (`project_id`, `title_relate_id`);                 
