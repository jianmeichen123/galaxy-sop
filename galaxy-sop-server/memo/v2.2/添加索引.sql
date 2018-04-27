-- 投资方主体调整
UPDATE `fx_db`.`information_dictionary` SET `short_name`='天马基金' WHERE `id`='2271';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='星河之光' WHERE `id`='2272';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='诚合基石' WHERE `id`='2278';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='苍穹之下' WHERE `id`='2274';
UPDATE `fx_db`.`information_dictionary` SET `name`='喀什耀灼创业投资有限公司', `short_name`='喀什耀灼' WHERE `id`='2275';
UPDATE `fx_db`.`information_dictionary` SET `name`='霍尔果斯燎原创业投资有限公司', `short_name`='燎原投资' WHERE `id`='2276';
UPDATE `fx_db`.`information_dictionary` SET `is_valid`='1' WHERE `id`='2277';
UPDATE `fx_db`.`information_dictionary` SET `short_name`='天马股份' WHERE `id`='2279';
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2287', '2270', '3020', 'investmentMainpart10', '霍尔果斯市微创之星创业投资有限公司', '微创之星', 'D229_10', '10', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2288', '2270', '3020', 'investmentMainpart11', '日照基石资本股权投资中心（有限合伙）', '日照基石', 'D229_11', '11', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2289', '2270', '3020', 'investmentMainpart12', '日照元鼎股权投资基金（有限合伙）', '日照元鼎', 'D229_12', '12', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2290', '2270', '3020', 'investmentMainpart13', '东方元鼎（北京）投资咨询有限公司', '东方元鼎', 'D229_13', '13', '0');
UPDATE `fx_db`.`information_dictionary` SET `is_valid`='1' WHERE `id`='2273';
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `short_name`, `value`, `sort`, `is_valid`) VALUES ('2291', '2270', '3020', 'investmentMainpart14', '喀什星河创业投资有限公司', '喀什星河创业', 'D229_14', '14', '0');


-- 健康度调整
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('253', 'xhhl', '健康度', '0', 'healthState', '0', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('254', 'healthState', '关注', '0', 'healthState:0', '3', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('255', 'healthState', '优异', '1', 'healthState:1', '1', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('256', 'healthState', '健康', '2', 'healthState:2', '2', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('257', 'healthState', '风险', '3', 'healthState:3', '4', '1524727556630', '1524727556630', '0');
INSERT INTO `fx_db`.`dict` (`id`, `parent_code`, `name`, `dict_value`, `dict_code`, `dict_sort`, `created_time`, `updated_time`, `is_delete`) VALUES ('258', 'healthState', '损失', '4', 'healthState:4', '5', '1524727556630', '1524727556630', '0');
