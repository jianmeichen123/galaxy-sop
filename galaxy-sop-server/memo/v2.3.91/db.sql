use fx_db;

/**创建总拨款计划记录表**/
CREATE TABLE `sop_total_grant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '总拨款计划ID',
  `project_id` bigint(20) DEFAULT NULL COMMENT '关联的项目ID',
  `grant_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '总拨款计划名称',
  `grant_money` decimal(15,2) DEFAULT NULL COMMENT '总拨款计划金额',
  `create_uid` bigint(20) DEFAULT NULL COMMENT '创建者的用户ID',
  `create_uname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者名',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '项目的最近一次修改日期',
  `created_time` bigint(20) NOT NULL COMMENT '项目的创建日期',
  PRIMARY KEY (`id`),
  KEY `sop_total_grant_index` (`project_id`,`create_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/**创建分期拨款计划记录表**/
CREATE TABLE `sop_part_grant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分期拨款计划ID',
  `total_grant_id` bigint(20) DEFAULT NULL COMMENT '关联的总拨款计划ID',
  `grant_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '分期拨款计划名称',
  `grant_detail` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '分期拨款时间备注信息',
  `grant_money` decimal(15,2) DEFAULT NULL COMMENT '总拨款计划金额',
  `create_uid` bigint(20) DEFAULT NULL COMMENT '创建者的用户ID',
  `create_uname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者名',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '项目的最近一次修改日期',
  `created_time` bigint(20) NOT NULL COMMENT '项目的创建日期',
  PRIMARY KEY (`id`),
  KEY `sop_part_grant_index` (`total_grant_id`,`create_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/**创建实际拨款计划记录表**/
CREATE TABLE `sop_actual_grant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实际拨款计划ID',
  `part_grant_id` bigint(20) DEFAULT NULL COMMENT '关联的总拨款计划ID',
  `grant_money` decimal(15,2) DEFAULT NULL COMMENT '总拨款计划金额',
  `create_uid` bigint(20) DEFAULT NULL COMMENT '创建者的用户ID',
  `create_uname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者名',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '项目的最近一次修改日期',
  `created_time` bigint(20) NOT NULL COMMENT '项目的创建日期',
  PRIMARY KEY (`id`),
  KEY `sop_actual_grant_index` (`part_grant_id`,`create_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;