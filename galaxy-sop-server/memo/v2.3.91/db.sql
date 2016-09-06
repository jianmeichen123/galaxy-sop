use fx_db;

/**创建总拨款计划记录表**/
CREATE TABLE `sop_grant_total` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '总拨款计划ID',
  `project_id` bigint(20) DEFAULT NULL COMMENT '关联的项目ID',
  `grant_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '总拨款计划名称',
  `grant_money` decimal(15,2) DEFAULT NULL COMMENT '总拨款计划金额',
  `create_uid` bigint(20) DEFAULT NULL COMMENT '创建者的用户ID',
  `create_uname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者名',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '项目的最近一次修改日期',
  `created_time` bigint(20) NOT NULL COMMENT '项目的创建日期',
  PRIMARY KEY (`id`),
  KEY `sop_grant_total_index` (`project_id`,`create_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/**创建分期拨款计划记录表**/
CREATE TABLE `sop_grant_part` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分期拨款计划ID',
  `total_grant_id` bigint(20) DEFAULT NULL COMMENT '关联的总拨款计划ID',
  `grant_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '分期拨款计划名称',
  `grant_detail` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '分期拨款时间备注信息',
  `grant_money` decimal(15,2) DEFAULT NULL COMMENT '总拨款计划金额',
  `create_uid` bigint(20) DEFAULT NULL COMMENT '创建者的用户ID',
  `create_uname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者名',
  `part_status` int(11) DEFAULT 0 COMMENT '分期拨款记录状态：默认0表示正常',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '项目的最近一次修改日期',
  `created_time` bigint(20) NOT NULL COMMENT '项目的创建日期',
  `file_num` tinyint(4) DEFAULT NULL COMMENT '文件个数',
  PRIMARY KEY (`id`),
  KEY `sop_grant_part_index` (`total_grant_id`,`create_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/**创建实际拨款计划记录表**/
CREATE TABLE `sop_grant_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实际拨款计划ID',
  `part_grant_id` bigint(20) DEFAULT NULL COMMENT '关联的总拨款计划ID',
  `grant_money` decimal(15,2) DEFAULT NULL COMMENT '总拨款计划金额',
  `create_uid` bigint(20) DEFAULT NULL COMMENT '创建者的用户ID',
  `create_uname` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者名',
  `updated_time` bigint(20) DEFAULT NULL COMMENT '项目的最近一次修改日期',
  `created_time` bigint(20) NOT NULL COMMENT '项目的创建日期',
  PRIMARY KEY (`id`),
  KEY `sop_grant_actual_index` (`part_grant_id`,`create_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/**添加投后运营角色，id=20**/
INSERT INTO `fx_db`.`platform_role`
            (`id`,
             `name`,
             `role_code`,
             `user_id`,
             `description`,
             `disabled`,
             `sort`,
             `created_time`,
             `updated_time`,
             `application_platform`)
VALUES (20,
        '投后运营',
        'THYY',
        NULL,
        '负责投成的项目打款及运营情况收集。',
        NULL,
        0,
        1469153154195,
        NULL,
        0);
/**拨款相关资源**/
INSERT INTO `fx_db`.`platform_resource`(`parent_id`,`resource_mark`,`resource_name`,`resource_url`,`resource_type`,`resource_order`,`resource_desc`,`resource_status`,`product_mark`,`created_uid`,`created_time`,`updated_uid`,`updated_time`,`application_platform`,`style`)
VALUES ((SELECT id FROM ((SELECT * FROM platform_resource WHERE resource_mark='project_view')  t)),'add_appr_actual','添加实际拨款信息',NULL,'3',1,'添加实际拨款信息','1','sop',999,1451577600000,999,1451577600000,'1',NULL);
INSERT INTO `fx_db`.`platform_resource`(`parent_id`,`resource_mark`,`resource_name`,`resource_url`,`resource_type`,`resource_order`,`resource_desc`,`resource_status`,`product_mark`,`created_uid`,`created_time`,`updated_uid`,`updated_time`,`application_platform`,`style`)
VALUES ((SELECT id FROM ((SELECT * FROM platform_resource WHERE resource_mark='project_view')  t)),'edit_appr_actual','编辑实际拨款信息',NULL,'3',2,'编辑实际拨款信息','1','sop',999,1451577600000,999,1451577600000,'1',NULL);
INSERT INTO `fx_db`.`platform_resource`(`parent_id`,`resource_mark`,`resource_name`,`resource_url`,`resource_type`,`resource_order`,`resource_desc`,`resource_status`,`product_mark`,`created_uid`,`created_time`,`updated_uid`,`updated_time`,`application_platform`,`style`)
VALUES ((SELECT id FROM ((SELECT * FROM platform_resource WHERE resource_mark='project_view')  t)),'delete_appr_actual','删除实际拨款信息',NULL,'3',3,'删除实际拨款信息','1','sop',999,1451577600000,999,1451577600000,'1',NULL);
/***分拨款文件关系表**/
CREATE TABLE `sop_grant_file` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '拨款信息\\文档关联id',
   `grant_id` bigint(20) DEFAULT NULL COMMENT '拨款信息id',
   `file_id` bigint(20) DEFAULT NULL COMMENT '文档id',
   `created_time` bigint(20) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=417 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/**数据字典-立项报告**/ 
INSERT INTO `fx_db`.`dict` (`parent_code`,`name`,`dict_value`,`dict_code`,`dict_sort`,`created_time`,`updated_time`,`is_delete`)
VALUES ('fileWorktype','立项报告',17,'fileWorktype:17','17',1456818150913,1456818150913,'0');
/**数据字典-尽职调查启动会报告**/ 
INSERT INTO `fx_db`.`dict` (`parent_code`,`name`,`dict_value`,`dict_code`,`dict_sort`,`created_time`,`updated_time`,`is_delete`)
VALUES ('fileWorktype','尽职调查启动会报告',18,'fileWorktype:18','18',1456818150913,1456818150913,'0');
/**数据字典-尽职调查总结会报告**/ 
INSERT INTO `fx_db`.`dict` (`parent_code`,`name`,`dict_value`,`dict_code`,`dict_sort`,`created_time`,`updated_time`,`is_delete`)
VALUES ('fileWorktype','尽职调查总结会报告',19,'fileWorktype:19','19',1456818150913,1456818150913,'0');

/**菜单数据修正**/
UPDATE `fx_db`.`platform_resource` SET `resource_url` = NULL WHERE `resource_mark` = 'task_into_view';
