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
