-- 更新fx_db.information_result.content_describe1,元更新为万元，四舍五入保留四位小数
update fx_db.information_result set `content_describe1`=round(`content_describe1`/10000,4) where `title_id` in (1916,1923,1929,1939,1910,1911);

-- 新增title_id=1943,用content_describe1计算, 结果四舍五入保留四位小数 
insert into fx_db.information_result(`project_id`,`title_id`,`content_describe1`,`is_valid`)
select  
	a.`project_id`,
	1943 as `title_id`,
	case when b.`content_describe1`!=0  then a.`content_describe1`/ (b.`content_describe1`/100) else 0 end as `content_describe1`,
	0 as `is_valid`
from 
( select `project_id`,`title_id`,`content_describe1` from fx_db.information_result where `title_id`=1916 ) a left join 
( select `project_id`,`title_id`,`content_describe1` from fx_db.information_result where `title_id`=1917 ) b on a.`project_id` = b.`project_id`
where a.`project_id` not in (select `project_id` from fx_db.information_result where `title_id`=1943)
and b.`project_id` not in (select `project_id` from fx_db.information_result where `title_id`=1943);
 
-- 更新fx_db.information_listdata.field_2,元更新为万元，四舍五入保留四位小数 
update fx_db.information_listdata set `field_2`=round(`field_2`/10000,4) where `title_id`=1920;

 
-- 更新投资人情况 fx_db.information_listdata.field_3,field_5,field_6元更新为万元，四舍五入保留四位小数 
update fx_db.information_listdata set `field_3`=round(`field_3`/10000,4), `field_5`=round(`field_5`/10000,4),`field_5`= '2243' where `title_id`=1908;
