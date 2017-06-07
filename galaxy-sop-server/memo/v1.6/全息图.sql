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
