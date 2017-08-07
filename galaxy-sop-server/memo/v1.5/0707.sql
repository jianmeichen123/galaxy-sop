/*投资形式*/
ALTER TABLE `fx_db`.`sop_project` 
ADD COLUMN `finance_mode` VARCHAR(45) NULL COMMENT '投资形式' AFTER `project_time`;
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('212','xhhl','投资形式','30','financeMode','30','投资形式（项目基本信息）','1456382861380','1456382861380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('213','financeMode','独投','0','financeMode:0','0',NULL,'145638261380','145638261380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('214','financeMode','领投','1','financeMode:1','1',NULL,'145638261380','145638261380','0');
insert into `dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `text`, `created_time`, `updated_time`, `is_delete`) values('215','financeMode','合投','2','financeMode:2','2',NULL,'145638261380','145638261380','0');
CREATE TABLE `joint_delivery` (
  `id` bigint(100) NOT NULL AUTO_INCREMENT,
  `delivery_type` varchar(20) DEFAULT NULL COMMENT '0-独投;1-领投;2-合投',
  `delivery_name` varchar(100) DEFAULT NULL COMMENT '合投机构名称',
  `delivery_amount` decimal(15,4) DEFAULT NULL COMMENT '投资金额',
  `delivery_share_ratio` decimal(6,4) DEFAULT NULL COMMENT '股权占比',
  `project_id` bigint(50) DEFAULT NULL COMMENT '项目ID',
  `create_uid` bigint(50) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(30) DEFAULT NULL COMMENT '创建日期',
  `update_time` bigint(30) DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `fx_db`.`platform_index_config`   
  ADD COLUMN `resource_code` VARCHAR(100) NULL  COMMENT '资源标识' AFTER `created_time`;

UPDATE platform_index_config,platform_resource SET platform_index_config.resource_code = platform_resource.resource_mark 
WHERE platform_resource.id = platform_index_config.resource_id;
ALTER TABLE `power`.`resource`   
  CHANGE `resource_type` `resource_type` INT(11) NULL  COMMENT '资源类型1-菜单;2-页面;3-操作;4-其他(div等);5-桌面模块';


  /**
   * 全息图
   */
  /*团队-CEO情况-个人的核心能力是  */
UPDATE `fx_db`.`information_title` SET `type`='13' WHERE `id`='1332';
/*团队-核心团队能力匹配结论-该项目需要核心团队具有的能力  */
UPDATE `fx_db`.`information_title` SET `type`='13' WHERE `id`='1364';
/*团队-核心团队能力匹配结论-核心创始团队已满足的能力  */
UPDATE `fx_db`.`information_title` SET `type`='13' WHERE `id`='1365';
/*团队-核心团队能力匹配结论-核心创始团队尚缺少的能力  */
UPDATE `fx_db`.`information_title` SET `type`='13' WHERE `id`='1366';
/*团队-客户及收入数据（2B类公司）-影响客户和收入高速增长的瓶颈是什么 */
UPDATE `fx_db`.`information_title` SET `type`='13' WHERE `id`='1427';
/*客单价 位数调整 */
UPDATE `fx_db`.`information_title` SET `val_rule_mark`='8,2' WHERE `id`='1422';
/*删除重复题 潜在竞争对手的核心资源*/
UPDATE `fx_db`.`information_title` SET `is_valid`='1' WHERE `id`='1535';
/*融资及估值模块中，现金流量表更改题型，将填写变成上传图片
*/
UPDATE `fx_db`.`information_title` SET `type`='7' WHERE `id`='1933';
/* 团队-CEO情况-背景-是否是本领域接触人才 */
UPDATE `fx_db`.`information_title` SET `type`='12' WHERE `id`='1329';
/* 团队-CEO情况-背景-是否毕业于一流名校 */
UPDATE `fx_db`.`information_title` SET `type`='12' WHERE `id`='1330';

/* 所有加其他内容的题 添加校验条件 */
UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1332';

UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1364';

UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1365';
UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1366';

UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1427';


UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1329';
UPDATE `fx_db`.`information_title` SET `placeholder`='请填写其他内容', `val_rule`='1', `val_rule_mark`='40' WHERE `id`='1330';
/*视图*/
CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `fxuser`@`10.%.%.%` 
    SQL SECURITY DEFINER
VIEW `fx_db`.`v_user` AS
    (SELECT 
		`power`.`user`.`id` AS `id`,
		`power`.`user`.`login_name` AS `login_name`,
		`power`.`user`.`user_name` AS `user_name`,
		`power`.`user`.`telphone` AS `telphone`,
		`power`.`user`.`mobile_phone` AS `mobile_phone`,
		`power`.`user`.`email1` AS `email1`,
		`power`.`user`.`email2` AS `email2`,
		`power`.`user`.`employ_no` AS `employ_no`,
		`power`.`user`.`sex` AS `sex`,
		`power`.`user`.`password` AS `password`,
		`power`.`user`.`origin_password` AS `origin_password`,
		`power`.`user`.`is_show` AS `is_show`,
		`power`.`user`.`is_del` AS `is_del`,
		`power`.`user`.`is_outtage` AS `is_outtage`,
		`power`.`user`.`create_time` AS `create_time`,
		`power`.`user`.`create_id` AS `create_id`,
		`power`.`user`.`update_time` AS `update_time`,
		`power`.`user`.`update_id` AS `update_id`,
		`power`.`user`.`company_id` AS `company_id`,
		`power`.`user`.`address` AS `address`,
		`power`.`user`.`is_admin` AS `is_admin`
    FROM
        `power`.`user`);
        CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `fxuser`@`10.%.%.%` 
    SQL SECURITY DEFINER
VIEW `v_rel_dep_user` AS
    (SELECT 
        `power`.`rel_dep_user`.`id` AS `id`,
        `power`.`rel_dep_user`.`dep_id` AS `dep_id`,
        `power`.`rel_dep_user`.`user_id` AS `user_id`,
        `power`.`rel_dep_user`.`is_del` AS `is_del`,
        `power`.`rel_dep_user`.`is_outtage` AS `is_outtage`,
        `power`.`rel_dep_user`.`create_time` AS `create_time`,
        `power`.`rel_dep_user`.`create_id` AS `create_id`,
        `power`.`rel_dep_user`.`update_time` AS `update_time`,
        `power`.`rel_dep_user`.`update_id` AS `update_id`,
        `power`.`rel_dep_user`.`company_id` AS `company_id`
    FROM
        `power`.`rel_dep_user`);
        CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `fxuser`@`10.%.%.%` 
    SQL SECURITY DEFINER
VIEW `fx_db`.`v_department` AS
    (SELECT 
        `power`.`department`.`id` AS `id`,
        `power`.`department`.`dep_name` AS `name`,
        `power`.`department`.`parent_id` AS `parent_id`,
        `power`.`department`.`index_no` AS `index_no`,
        `power`.`department`.`is_show` AS `is_show`,
        `power`.`department`.`create_time` AS `create_time`,
        `power`.`department`.`create_id` AS `create_id`,
        `power`.`department`.`update_time` AS `update_time`,
        `power`.`department`.`update_id` AS `update_id`,
        `power`.`department`.`is_del` AS `is_del`,
        `power`.`department`.`is_outtage` AS `is_outtage`,
        `power`.`department`.`company_id` AS `company_id`,
        `power`.`department`.`dep_manager` AS `dep_manager`,
        `power`.`department`.`is_careerline` AS `is_careerline`
    FROM
        `power`.`department`);


