INSERT INTO `fx_db`.`information_dictionary` (`id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2270', 'investmentMainpart', '星河投资方主体', 'D229', '0', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2271', '2270', '3020', 'investmentMainpart1', '杭州天马星河投资合伙企业（有限合伙）', 'D229_1', '1', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2272', '2270', '3020', 'investmentMainpart2', '北京星河之光投资管理有限公司', 'D229_2', '2', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2273', '2270', '3020', 'investmentMainpart3', '喀什星河互联创业投资有限公司', 'D229_3', '3', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2274', '2270', '3020', 'investmentMainpart4', '霍尔果斯苍穹之下创业投资有限公司', 'D229_4', '4', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2275', '2270', '3020', 'investmentMainpart5', '疏勒县耀灼创业投资有限公司', 'D229_5', '5', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2276', '2270', '3020', 'investmentMainpart6', '霍尔果斯市燎原创业投资有限公司', 'D229_6', '6', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2277', '2270', '3020', 'investmentMainpart7', '北京星河数银科技有限公司', 'D229_7', '7', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2278', '2270', '3020', 'investmentMainpart8', '喀什诚合基石创业投资有限公司', 'D229_8', '8', '0');
INSERT INTO `fx_db`.`information_dictionary` (`id`, `parent_id`, `title_id`, `code`, `name`, `value`, `sort`, `is_valid`) VALUES ('2279', '2270', '3020', 'investmentMainpart9', '天马轴承集团股份有限公司', 'D229_9', '9', '0');



UPDATE `fx_db`.`information_title` SET `type`='14' WHERE `id`='3020';







update fx_db.information_result set content_choose = 2271 where title_id = 3020 and content_describe1 like '%杭州天马%' ;
update fx_db.information_result set content_choose = 2272 where title_id = 3020 and content_describe1 like '%北京星河之光%' ;
update fx_db.information_result set content_choose = 2273 where title_id = 3020 and content_describe1 like '%喀什星河%' ;
update fx_db.information_result set content_choose = 2274 where title_id = 3020 and content_describe1 like '%苍穹之下%' ;
update fx_db.information_result set content_choose = 2275 where title_id = 3020 and content_describe1 like '%耀灼%' ;
update fx_db.information_result set content_choose = 2276 where title_id = 3020 and content_describe1 like '%燎原%' ;
update fx_db.information_result set content_choose = 2277 where title_id = 3020 and content_describe1 like '%数银科技%' ;
update fx_db.information_result set content_choose = 2278 where title_id = 3020 and content_describe1 like '%喀什诚合基石%' ;
update fx_db.information_result set content_choose = 2279 where title_id = 3020 and content_describe1 like '%天马轴承%' ;



UPDATE `fx_db`.`information_dictionary` SET `parent_id`='2170' WHERE `id`='2171';
UPDATE `fx_db`.`information_dictionary` SET `parent_id`='2170' WHERE `id`='2172';
UPDATE `fx_db`.`information_dictionary` SET `parent_id`='2170' WHERE `id`='2173';
