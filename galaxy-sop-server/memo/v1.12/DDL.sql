/******填写标准******/
CREATE TABLE `fx_db`.`project_standard` (
  `id` BIGINT(20) NOT NULL COMMENT '规范id (表主键）',
  `module_code` VARCHAR(100) NULL COMMENT '模块编号',
  `module_name` VARCHAR(200) NULL COMMENT '模块名称',
  `standard_details` TEXT NULL COMMENT '填写规则详情',
  `status` INT(1) NULL DEFAULT 0 COMMENT '标准状态 默认0 隐藏， 1显示；',
  `is_valid` INT(1) NULL DEFAULT 0 COMMENT '是否可用   默认0：可用，1：禁用',
  `create_time` BIGINT(20) NULL COMMENT '创建时间',
  `create_id` BIGINT(20) NULL COMMENT '创建人ID',
  `update_time` BIGINT(20) NULL COMMENT '更新时间',
  `update_id` BIGINT(20) NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`))
COMMENT = '项目规范';
