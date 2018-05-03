
DROP VIEW IF EXISTS `v_role`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`fxuser`@`10.%.%.%` SQL SECURITY DEFINER VIEW `v_role` AS (
SELECT
  `power`.`role`.`id`          AS `id`,
  `power`.`role`.`role_code`   AS `role_code`,
  `power`.`role`.`role_name`   AS `role_name`,
  `power`.`role`.`role_demo`   AS `role_demo`,
  `power`.`role`.`is_del`      AS `is_del`,
  `power`.`role`.`is_outtage`  AS `is_outtage`,
  `power`.`role`.`create_time` AS `create_time`,
  `power`.`role`.`create_id`   AS `create_id`,
  `power`.`role`.`update_time` AS `update_time`,
  `power`.`role`.`update_id`   AS `update_id`,
  `power`.`role`.`company_id`  AS `company_id`
FROM `power`.`role`)$$

DELIMITER ;


DELIMITER $$

USE `fx_db`$$

DROP VIEW IF EXISTS `v_rel_role_user`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`fxuser`@`10.%.%.%` SQL SECURITY DEFINER VIEW `v_rel_role_user` AS (
SELECT
  `power`.`rel_role_user`.`id`          AS `id`,
  `power`.`rel_role_user`.`role_id`     AS `dep_id`,
  `power`.`rel_role_user`.`user_id`     AS `user_id`,
  `power`.`rel_role_user`.`is_del`      AS `is_del`,
  `power`.`rel_role_user`.`is_outtage`  AS `is_outtage`,
  `power`.`rel_role_user`.`create_time` AS `create_time`,
  `power`.`rel_role_user`.`create_id`   AS `create_id`,
  `power`.`rel_role_user`.`update_time` AS `update_time`,
  `power`.`rel_role_user`.`update_id`   AS `update_id`,
  `power`.`rel_role_user`.`company_id`  AS `company_id`
FROM `power`.`rel_role_user`)$$

DELIMITER ;