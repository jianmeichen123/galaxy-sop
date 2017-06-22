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



