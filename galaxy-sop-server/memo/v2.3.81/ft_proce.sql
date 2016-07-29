use fx_db;


DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `clean_user_data`(IN uids VARCHAR(500))
    COMMENT '清理线上测试用户相关数据'
BEGIN
    /**事务**/
    DECLARE t_error INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
	START TRANSACTION;
	    /**删除测试项目附属数据**/
	    DELETE FROM `sop_file` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `sop_interview_record` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `sop_meeting_record` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `sop_meeting_scheduling` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    /** DELETE FROM `sop_operation_logs` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids)); **/
	    DELETE FROM `sop_project_person` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `sop_project_shares` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `sop_task` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `sop_voucher_file` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    DELETE FROM `t_operation_message` WHERE project_id IN (SELECT id FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids));
	    /**删除测试项目**/
	    DELETE FROM `sop_project` WHERE FIND_IN_SET(create_uid, uids);
	    /**删除测试创意附属数据**/
	    DELETE FROM `sop_abandoned` WHERE idea_id IN (SELECT id FROM `sop_idea` WHERE FIND_IN_SET(created_uid, uids));
	    /**删除测试创意**/
	    DELETE FROM `sop_idea` WHERE FIND_IN_SET(created_uid, uids);
	    /**删除测试用户附属数据**/
	    DELETE FROM `sop_target_y` WHERE FIND_IN_SET(user_id, uids);
	    DELETE FROM `sop_pass_rate` WHERE FIND_IN_SET(uid, uids);
	    DELETE FROM `sop_user_schedule` WHERE FIND_IN_SET(user_id, uids);
	    /**删除测试用户**/
	    DELETE FROM `platform_user` WHERE FIND_IN_SET(id, uids);
	    DELETE FROM `platform_user_role` WHERE FIND_IN_SET(user_id, uids);
	IF t_error = 1 THEN
	    ROLLBACK;
	ELSE
	    COMMIT;
	END IF;
    SELECT t_error;/**返回标识位的结果集**/
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `create_default_targert_y`(IN `v_year` int,IN `v_target` int)
    COMMENT '重新生成投资经理年目标记录'
begin
delete from sop_target_y where year=v_year;
insert into sop_target_y(year,user_id,number)
select v_year,a.id,v_target
from platform_user a
left join platform_user_role b on a.id=b.user_id
left join platform_role c on b.role_id=c.id
left join t_department d on a.department_id=d.id
where c.role_code='TZJL' and a.status=0;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `create_records`(IN num int)
begin
	###自动创建测试数据脚本###
  # 参数num ： 项目数                 
  # 项目创建日期 ： 20160101~20160226 
  # 会议记录或次数限制 ： 10次
  declare i int default 0;
	declare th int default 10; # 会议记录或次数限制,(上限)，单位次
  declare el int default 6; # 访谈记录时间间隔（下限），单位小时
	declare eh int default 48; # 访谈记录时间间隔（上限），单位小时
  declare ml int default 1; # 会议记录时间间隔（下限），单位天
	declare mh int default 3; # 会议记录时间间隔（上限） ，单位天
	
	
	## 项目阶段
	declare dict_jcft varchar(30) default 'projectProgress:1';  # 接触访谈
	declare dict_nbps varchar(30) default 'projectProgress:2';  # 内部评审
	declare dict_ceops varchar(30) default 'projectProgress:3'; # ceo评审
	declare dict_lxh varchar(30) default 'projectProgress:4';   # 立项会 
	declare dict_tzyxs varchar(30) default 'projectProgress:5'; # 投资意向书 
	declare dict_jzdc varchar(30) default 'projectProgress:6'; # 尽职调查 
	declare dict_tjh varchar(30) default 'projectProgress:7'; # 投决会 
  declare dict_tzxy varchar(30) default 'projectProgress:8'; # 投资协议
	declare dict_gqjg varchar(30) default 'projectProgress:9'; # 股权交割
	declare dict_thyy varchar(30) default 'projectProgress:10'; # 投后运营
  ## 会议
  declare np  varchar(30) default 'meetingType:1';# 会议类型，内评会
	declare ceo varchar(30) default 'meetingType:2';# 会议类型，CEO评审
	declare lx  varchar(30) default 'meetingType:3';# 会议类型，立项会
	declare tj  varchar(30) default 'meetingType:4';# 会议类型，投决会
	#删除
	delete from sop_project;
	delete from sop_interview_record;
	delete from sop_meeting_record;
	#自增重置
	alter table `sop_project` AUTO_INCREMENT=1;
	alter table sop_interview_record AUTO_INCREMENT=1;
	alter table sop_meeting_record AUTO_INCREMENT=1;
  
  #创建项目信息
  while i< num do 
		#随机获取用户
		select id into @user_id from platform_user order by RAND() limit 1;
    #部门id
		select b.id into @deptartment_id from platform_user a left join t_department b on a.department_id=b.id where a.id=@user_id;
    #部门(投资线)
		select b.name into @department_name from platform_user a left join t_department b on a.department_id=b.id where a.id=@user_id;
		#随机获取项目类型
    select dict_code into @project_type from dict where parent_code='projectType' order by RAND() limit 1;
		#随机获取10W - 5000W 的数值,初始估值
    select floor(100000 + (rand() * 49000001)) into @project_valuations;
		#随机获取10W - 5000W 的数值,初始投资额
    select floor(100000 + (rand() * 49000001)) into @project_contribution;
		#随机获取货币单位
    #select id into @currency_unit from dict where parent_code='' order by RAND() limit 1;
		#随机获取10 - 50 的数值,股份占比
    select floor(10 + (rand() * 41)) into @project_share_ratio;
		#随机获取项目阶段
		select dict_code into @project_progress from dict where parent_code='projectProgress' order by RAND() limit 1;
		#随机会议结论
		select dict_code into @project_status from dict where parent_code='meetingResult' order by RAND() limit 1;
    #获取一个随机日期2015年10月~12月
		select UNIX_TIMESTAMP( get_rand_datetime() ) * 1000 into @rand_unix_timestamp;
		select FROM_UNIXTIME(@rand_unix_timestamp/1000) into @rand_datetime;
		
		set autocommit=0; #取消自动提交
		#new project
		insert into sop_project(project_name,project_code,project_type,project_careerline,project_departId,project_valuations,project_contribution,currency_unit,project_share_ratio,create_uid,project_progress,project_status,created_time,updated_time)
		values(concat('测试_',i) ,'XHPE000201601080012',@project_type,@department_name,@deptartment_id,@project_valuations,@project_contribution,@currency_unit,@project_share_ratio,@user_id,@project_progress,@project_status,@rand_unix_timestamp,@rand_unix_timestamp);
		
		#new project id 
		select LAST_INSERT_ID() into @pro_id;
		#select i+1 into @pro_id;
		
		#接触访谈
		if @project_progress=dict_jcft then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh);
		end if;
		
		#内部评审
		if @project_progress=dict_nbps then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh);
		end if;
		
		#CEO评审
		if @project_progress=dict_ceops then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh);
		end if;
		#立项会
		if @project_progress=dict_lxh then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) ;
		end if;
		#投资意向书
		if @project_progress=dict_tzyxs then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) ;
		end if;
		#尽职调查
		if @project_progress=dict_jzdc then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) ;
		end if;
		#投资决策会
		if @project_progress=dict_tjh then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) into @return_value4;
			select create_meeting_records(@pro_id,tj,th,@return_value4,ml,mh) into @return_value4;
		end if;
		#投资协议
		if @project_progress=dict_tzxy then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) into @return_value4;
			select create_meeting_records(@pro_id,tj,th,@return_value4,ml,mh) into @return_value4;
		end if;
		#股权交割
		if @project_progress=dict_gqjg then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) into @return_value4;
			select create_meeting_records(@pro_id,tj,th,@return_value4,ml,mh) into @return_value4;
		end if;
		#投后运营
		if @project_progress=dict_thyy then 
			select create_interview_records(@pro_id,th,@rand_datetime,el,eh) into @return_value;
			select create_meeting_records(@pro_id,np,th,@return_value,ml,mh) into @return_value2;
			select create_meeting_records(@pro_id,ceo,th,@return_value2,ml,mh) into @return_value3;
			select create_meeting_records(@pro_id,lx,th,@return_value3,ml,mh) into @return_value4;
			select create_meeting_records(@pro_id,tj,th,@return_value4,ml,mh) into @return_value4;
		end if;
		commit; #提交
	set i = i+1;
  end while;	
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`10.%.%.%` PROCEDURE `create_user_data`(OUT result VARCHAR(10))
    COMMENT '创建线上测试用户'
BEGIN
    /**事务**/
    DECLARE t_error INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
	START TRANSACTION;
	    INSERT  INTO `platform_user`
		(`id`,`employ_no`,`real_name`,`nick_name`,`status`,`type`,`department_id`,`department_name`,
		`role`,`email`,`password`,`origin_password`,`gender`,`is_admin`,`birth`,`mobile`,`telephone`,`address`,
		`created_time`,`updated_time`) 
	    VALUES 
		(101,'xhhl-00001','董事长','dsz','0',NULL,1,NULL,NULL,'dsz',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477775816,1460978937037),
		(102,'xhhl-00002','CEO','ceo','0',NULL,2,NULL,NULL,'ceo',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477775899,1461546050661),
		(103,'xhhl-00003','人工智能-合伙人','hhr','0',NULL,13,NULL,NULL,'hhr',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477775950,1461545241821),
		(104,'xhhl-00004','人工智能-投资经理one','tzjl1','0',NULL,13,NULL,NULL,'tzjl1',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477775992,1461554347454),
		(105,'xhhl-00005','人工智能-投资经理two','tzjl2','0',NULL,13,NULL,NULL,'tzjl2',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776041,1461546492326),
		(106,'xhhl-00006','财务总监','cwzj','0',NULL,21,NULL,NULL,'cwzj',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776091,1461733558514),
		(107,'xhhl-00007','财务经理one','cwjl1','0',NULL,21,NULL,NULL,'cwjl1',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776141,1460979133396),
		(108,'xhhl-00008','财务经理two','cwjl2','0',NULL,21,NULL,NULL,'cwjl2',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776200,NULL),
		(109,'xhhl-00009','人事总监','rszj','0',NULL,23,NULL,NULL,'rszj',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776249,NULL),
		(110,'xhhl-00010','人事经理one','rsjl1','0',NULL,23,NULL,NULL,'rsjl1',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776291,NULL),
		(111,'xhhl-00011','人事经理two','rsjl2','0',NULL,23,NULL,NULL,'rsjl2',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776349,1459504597876),
		(112,'xhhl-00012','法务总监','fwzj','0',NULL,20,NULL,NULL,'fwzj',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776399,NULL),
		(113,'xhhl-00013','法务经理one','fwjl1','0',NULL,20,NULL,NULL,'fwjl1',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776457,NULL),
		(114,'xhhl-00014','法务经理two','fwjl2','0',NULL,20,NULL,NULL,'fwjl2',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776508,1459504620484),
		(115,'xhhl-00015','董秘','dm','0',NULL,100,NULL,NULL,'dm',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','\0',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776558,NULL),
		(116,'xhhl-00016','CEO秘书','ceoms','0',NULL,100,NULL,NULL,'ceoms',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776617,1460978929144),
		(117,'xhhl-00017','档案管理员','fm','0',NULL,100,NULL,NULL,'fm',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776617,1460978929144),
		(118,'xhhl-00018','系统管理员','sm','0',NULL,100,NULL,NULL,'sm',
		'03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','123','',NULL,NULL,NULL,NULL,
		'北京市海淀区上地创新大厦',1459477776617,1460978929144);
	    INSERT INTO `platform_user_role` 
		(`id`,`user_id`,`role_id`,`created_time`,`updated_time`) 
	    VALUES 
		(100,101,1,1459477776617,1459477776617),
		(101,102,2,1459477776617,1459477776617),
		(102,103,3,1459477776617,1459477776617),
		(103,104,4,1459477776617,1459477776617),
		(104,105,4,1459477776617,1459477776617),
		(105,106,11,1459477776617,1459477776617),
		(106,107,12,1459477776617,1459477776617),
		(107,108,12,1459477776617,1459477776617),
		(108,109,7,1459477776617,1459477776617),
		(109,110,8,1459477776617,1459477776617),
		(110,111,8,1459477776617,1459477776617),
		(111,112,9,1459477776617,1459477776617),
		(112,113,10,1459477776617,1459477776617),
		(113,114,10,1459477776617,1459477776617),
		(114,115,18,1459477776617,1459477776617),
		(115,116,19,1459477776617,1459477776617),
		(116,117,17,1459477776617,1459477776617),
		(117,118,16,1459477776617,1459477776617);
	IF t_error = 1 THEN
	    ROLLBACK;
	ELSE
	    COMMIT;
	END IF;
    SELECT t_error;/**返回标识位的结果集**/
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`10.%.%.%` PROCEDURE `p_clean_user_data`(in uid bigint)
    COMMENT '清理线上用户相关数据'
BEGIN
    declare _realName varchar(12); -- 用户名
    declare _dateDay datetime; --  日期变量
    declare done int DEFAULT FALSE; -- 遍历数据结束标志
    
    -- 定义游标
    DECLARE rs_cursor CURSOR FOR SELECT id,real_name from `platform_user` where id=uid;
    -- 如果没数据，则将结束标志绑定到游标
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=TRUE;
    -- 获取昨天的日期
    if _dateDay is null then
        set _dateDay = date_add(now(),interval -1 day);
    end if;
    
    -- 打开游标
    open rs_cursor; 
    -- 开始循环
    cursor_loop: LOOP
        -- 提取游标里的数据
        FETCH rs_cursor into uid, _realName; -- 取数据,与select查询结果先后顺序保存一致即可
        if done then
            leave cursor_loop;
        end if;
    
        -- 这里做你想做的循环的事件
        select uid;
        select * from `sop_project` where create_uid=uid;
    end loop cursor_loop;
    -- 关闭游标
    close rs_cursor;
    
    select _dateDay;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `test_1`(
  IN `v_sys` VARCHAR (32),
  IN `v_type` VARCHAR (32),
  IN `v_sdate` VARCHAR (32),
  IN `v_edate` VARCHAR (32)
)
BEGIN
  DECLARE v_sql VARCHAR (5000) DEFAULT '' ;
  DECLARE v_where VARCHAR (1000) DEFAULT '' ;
  IF v_sys <> - 1 THEN 
	SET v_where = CONCAT(v_where," and sop.project_departId='",v_sys,"'") ;
	  END IF ;
  IF v_type <> - 1 THEN 
	SET v_where = CONCAT(v_where," and sop.project_type='",v_type,"'") ;
  END IF ;
  SET v_sql = CONCAT(
    "
SELECT 
  sop.`project_name` '项目名称',
  sop.`created_time` '创建时间',
  ct.name,
  sop.`project_type`'项目类型',
  sop.`create_uname` '投资经理',
  u.`real_name` '合伙人',
  d.`name` '投资事业线',
  (UNIX_TIMESTAMP() - sop.`created_time` / 1000)/60/60/24 '历时时间'
FROM 
  sop_project sop 
  LEFT JOIN `t_department` d 
    ON sop.`project_departId` = d.`id` 
  LEFT JOIN platform_user u 
    ON u.`id` = d.`manager_id` 
    LEFT JOIN  dict ct ON  sop.`project_progress`=ct.dict_code 
  WHERE FROM_UNIXTIME(
      sop.`created_time` / 1000,
      '%Y-%m-%d'
    ) >=  '",v_sdate,"'
    AND FROM_UNIXTIME(
      sop.`created_time` / 1000,
      '%Y-%m-%d'
    ) <= '",v_edate,"'
     ",v_where,"
     
  "
  ) ;
  SET @s = v_sql ;
  
	
	SELECT @s;
		
  #PREPARE stmt FROM @s ;
  
 #EXECUTE stmt ;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_app_brief`(IN  `v_dept_id` int,IN  `v_user_id` int)
    COMMENT 'app数据快览'
begin
select sum(number) into @target 
from  sop_target_y a
left join platform_user b on a.user_id=b.id
where a.year=year(now())
and (case when v_dept_id<>-1 then  b.department_id=v_dept_id else 1=1 end)
and (case when v_user_id<>-1 then  a.user_id=v_user_id else 1=1 end);
select  
	case when num>0 then round(pnum/num,6) else 0 end as prate into @lxh_rate
from 
(
	select 
		count(distinct b.id) as num,
		sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
		sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
	from sop_meeting_record a
	left join sop_project b on a.project_id=b.id
	left join platform_user c on b.create_uid=c.id
	left join t_department d on c.department_id=d.id
	where d.type=1 and a.meeting_type='meetingType:3'
    and (case when v_dept_id<>-1 then  c.department_id=v_dept_id else 1=1 end) 
    and (case when v_user_id<>-1 then  c.id=v_user_id else 1=1 end)
) a;
select 
	count(distinct a.id) as project_count,
    sum(case when from_unixtime(a.created_time/1000,'%Y')=year(now()) then 1 else 0 end) as curryear_project_count,
	sum(case when a.project_progress='projecProgress:10' or a.project_status='meetingResult:3' then a.project_valuations else 0 end) as valuations_amount,
    sum(case when a.project_progress='projectProgress:6' then 1 else 0 end) as jzdc_count,
    sum(case when a.project_progress='projectProgress:10' then 1 else 0 end) as thyy_count,
    ifnull(@target,0) as target,
    ifnull(@lxh_rate,0) as lxh_rate
from sop_project a
left join platform_user b on a.create_uid=b.id
where 1=1 
and (case when v_dept_id<>-1 then  b.department_id=v_dept_id else 1=1 end)
and (case when v_user_id<>-1 then  b.id=v_user_id else 1=1 end);
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_data_brief_chart`(IN  `v_year` int,IN  `v_type` int,IN  `v_deptid` int)
    COMMENT '项目数目标追踪'
begin
declare year_start_mi bigint(20) default ''; 
declare currday_start_mi bigint(20) default unix_timestamp()*1000; 
set v_year=year(now());
set year_start_mi=unix_timestamp(concat(v_year,'-01-01 00:00:00'))*1000;
if v_type=1 then 
	select 
		v_year as year,
		sum(a.target) as target,
		sum(a.completed) as completed,
		case when sum(a.target-a.completed)>0 then sum(a.target-a.completed) else 0 end as notcompleted,
		case when sum(a.completed-a.target)>0 then sum(a.completed-a.target) else 0 end as above
	from 
	(
		select 0 as target,count(*) as completed 
		from sop_project a 
		left join platform_user b on a.create_uid=b.id
		where a.created_time>=year_start_mi
        and a.created_time<=currday_start_mi  
		and (case when v_deptid<>-1 then  b.department_id=v_deptid else 1=1 end) 
		
		union all 
		select sum(b.number) as target,0 as completed 
		from sop_target_y b 
		left join platform_user c on b.user_id=c.id
		where b.`year`=v_year
        and (case when v_deptid<>-1 then  c.department_id=v_deptid else 1=1 end) 
	) a group by 1;
end if;
if v_type=3 then    
    select 
		a.id,
		a.name,
		sum(a.completed) as completed,
		sum(a.target) as target,
		case when sum(a.target)=0 then 0 else round(sum(a.completed)/sum(a.target),4) end as rate
	from (
		select id,name,0 as target,0 as completed from t_department where type=1
        
        union all
        
		select c.id,c.name,0 as target,count(distinct a.id) as completed
		from sop_project a
		left join platform_user b on a.create_uid=b.id
		left join t_department c on b.department_id=c.id
		where  c.type=1 
        and a.created_time>=year_start_mi
        and a.created_time<=currday_start_mi
        and (case when v_deptid<>-1 then  c.id=v_deptid else 1=1 end)
		group by c.id
		union all 
		select c.id,c.name,sum(a.number) as target,0 as completed 
		from sop_target_y a
		left join platform_user b on a.user_id=b.id
		left join t_department c on b.department_id=c.id
		where c.type=1 
        and a.`year`=v_year
        and (case when v_deptid<>-1 then  c.id=v_deptid else 1=1 end)
		group by c.id
	) a 
	group by a.id
	order by 3 desc,4 desc;
end if;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_department_list`(IN  `v_id` long,IN  `v_name` varchar(50),IN `v_parent_id` long,IN `v_type` int,IN `v_manager_id` long)
    COMMENT '获取部门列表'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(5000) default '';
if v_id<>-1 then 
	set v_where = concat(v_where," and a.id=" ,v_id," ");
end if;
if v_name<>"-1" then 
	set v_where = concat(v_where," and a.name like '%",v_name,"%' ");
end if;
if v_parent_id<>-1 then 
	set v_where = concat(v_where," and a.parent_id=" ,v_parent_id," ");
end if;
if v_type<>-1 then 
	set v_where = concat(v_where," and a.type=" ,v_type," ");
end if;
if v_manager_id<>-1 then 
	set v_where = concat(v_where," and a.manager_id=" ,v_manager_id," ");
end if;
set v_sql = concat("select * from t_department a where 1=1 ",v_where);
set @s = v_sql;
prepare stmt from @s;
execute stmt;
 
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_dept_kpi`(IN  `v_sdate` varchar(20),IN  `v_edate` varchar(20),IN `v_year` int,IN `v_deptid` int,IN `v_projectType` varchar(50),IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '部门kpi'
begin
declare v_where varchar(1000) default '';
declare v_where2 varchar(1000) default '';
declare v_where3 varchar(1000) default '';
declare v_where4 varchar(1000) default '';
declare v_sql varchar(5000) default '';
declare v_sql_count varchar(5000) default '';
declare v_lxh_str varchar(200) default "('projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10')";
declare v_tjh_str varchar(200) default "('projectProgress:8','projectProgress:9','projectProgress:10')";
declare v_wb_code varchar(30) default 'projectType:1'; 
declare v_zj_code varchar(30) default 'projectType:2'; 
declare v_sdate_mi bigint(20) default 0;
declare v_edate_mi bigint(20) default 0;
if v_sdate<>'-1' then 
	set v_sdate_mi=unix_timestamp(concat(v_sdate,' 00:00:00'))*1000;
end if;
if v_edate<>'-1' then 
	set v_edate_mi=unix_timestamp(concat(v_edate,' 23:59:59'))*1000;
end if;
create temporary  table 
if not exists DEPT_BASE_DATA (
	hhr varchar(50),
	dept_id int(11),
	dept_name varchar(50),
	dept_target int(11),
    total_target int(11),
	completed int(11),
    completed_all int(11),
    zj_completed int(11),
    company_rank int(5),
    zj_rate decimal(18,4),
	total_rate decimal(18,4),
	lxh_pnumber int(11),
	tjh_pnumber int(11),
	ghl_rate decimal(18,4),
    tjl_rate decimal(18,4)
);
create temporary  table 
if not exists DEPT_BASE_DATA2 (
	hhr varchar(50),
	dept_id int(11),
	dept_name varchar(50),
	dept_target int(11),
    total_target int(11),
	completed int(11),
    completed_all int(11),
    zj_completed int(11),
    company_rank int(5),
    zj_rate decimal(18,4),
	total_rate decimal(18,4),
	lxh_pnumber int(11),
	tjh_pnumber int(11),
	ghl_rate decimal(18,4),
    tjl_rate decimal(18,4)
);
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and d.created_time>=",v_sdate_mi," ");
    set v_where3 =concat(v_where3," and b.created_time>=",v_sdate_mi," ");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and d.created_time<=",v_edate_mi," ");
    set v_where3 =concat(v_where3," and b.created_time<=",v_edate_mi," ");
end if;
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.id=",v_deptid," ");
	set v_where2=concat(v_where2," and c.id=",v_deptid," ");
    set v_where3 = concat(v_where3," and c.department_id=" ,v_deptid," ");
end if;
if v_projectType<>"-1" then 
	set v_where=concat(v_where," and d.project_type='",v_projectType,"' ");
    set v_where3 = concat(v_where3," and b.project_type='",v_projectType,"' ");
end if;
if v_projectType<>"-1" then 
	set v_where4=concat(v_where4," and a.project_type='",v_projectType,"' ");
end if;
set v_sql=concat("
insert into DEPT_BASE_DATA(hhr,dept_id,dept_name,dept_target,total_target,completed,completed_all,zj_completed,company_rank,zj_rate,total_rate,lxh_pnumber,tjh_pnumber,ghl_rate,tjl_rate)
select  
	ifnull(a.hhr,'') as hhr, #合伙人 
	ifnull(a.department_id,0) as dept_id,#部门编号
	ifnull(a.department_name,'') as dept_name, #部门
	sum(ifnull(a.dept_target_number,0)) as dept_target, #部门目标数
	ifnull(a.total_target,0) as total_target, #公司目标数
	ifnull(a.completed,0) as completed, #项目数
    a.completed_all,#累计完成数
	ifnull(a.zj_completed,0) as zj_completed, #自建项目完成数
	0 as company_rank, #公司排名
	(case when a.completed=0 then 0 else round(a.zj_completed/a.completed,4) end) as zj_rate, # 自建项目占比（自建/部门目标）
	(case when a.company_completed=0 then 0 else round(a.completed/a.company_completed,4) end) as total_rate,#公司完成占比
	ifnull(a.lxh_pnum,0) as lxh_pnumber,# 立项会通过数
	ifnull(a.tjh_pnum,0) as tjh_pnumber,# 投决会通过数
	ifnull(a.ghl_rate,0) as ghl_rate, # 过会率
	ifnull(a.tjl_rate,0) as tjl_rate  # 投决率
from (
	select 
		b.id as department_id,
		b.`name` as department_name,
		h.real_name as hhr,
		count(distinct d.id) as completed, # 累计完成数（时间段内）
		sum(case when d.project_type='",v_zj_code,"' then 1 else 0 end) as zj_completed,#自建项目完成数
		(select sum(number) from sop_target_y where `year`=",v_year,") as total_target, # 公司目标数	
        o.company_completed,
        e.number as dept_target_number,
        x.completed_all,
        f.pnum as lxh_pnum,
        g.pnum as tjh_pnum,
        f.prate as ghl_rate,
        g.prate as tjl_rate
	from platform_user a
	left join t_department b on a.department_id=b.id
	left join sop_target_y c on a.id=c.user_id and c.`year` = ",v_year,"
	left join sop_project d on a.id=d.create_uid
    left join platform_user h on b.manager_id=h.id
    left join (
		select 
			b.department_id,count(a.id) as completed_all
		from sop_project a
		left join platform_user b on a.create_uid=b.id
        where 1=1 ",v_where4,"
		group by b.department_id
	) x on a.department_id=x.department_id
    
    left join (
		select count(distinct a.id) as company_completed
		from sop_project a
        where 1=1 ",v_where4,"
	) o on 1=1
    
    left join (
		#部门年目标
		select b.department_id,c.`name` as department_name,sum(a.number) as number
		from sop_target_y a  
		left join platform_user b on a.user_id=b.id
		left join t_department c on b.department_id=c.id
		where c.type=1 and a.`year`=",v_year," ",v_where2,"
		group by b.department_id
	) e on a.department_id=e.department_id
    
    left join (
		#部门-立项会－申请过会议的项目数、通过数、否决数、当前待定数\过会率
       select 
			dept_id,num,pnum,fnum,num-pnum-fnum as snum,
			case when num>0 then pnum/num else 0 end as prate
		from 
		(
			select 
				c.department_id as dept_id,
				count(distinct a.project_id) as num,
				sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
				sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
			from sop_meeting_record a
			left join sop_project b on a.project_id=b.id
			left join platform_user c on b.create_uid=c.id
			left join t_department d on c.department_id=d.id
			where d.type=1 and a.meeting_type='meetingType:3' ",v_where3,"
			group by c.department_id
		) a
	) f on a.department_id=f.dept_id
    
    left join (
		#部门-立项会－申请过会议的项目数、通过数、否决数、当前待定数\过会率
		 select 
			dept_id,num,pnum,fnum,num-pnum-fnum as snum,
			case when num>0 then pnum/num else 0 end as prate
		from 
		(
			select 
				c.department_id as dept_id,
				count(distinct a.project_id) as num,
				sum(case when a.meeting_type='meetingType:4' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
				sum(case when a.meeting_type='meetingType:4' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
			from sop_meeting_record a
			left join sop_project b on a.project_id=b.id
			left join platform_user c on b.create_uid=c.id
			left join t_department d on c.department_id=d.id
			where d.type=1 and a.meeting_type='meetingType:4' ",v_where3,"
			group by c.department_id
		) a
	) g on a.department_id=g.dept_id
    
    where b.type=1 ",v_where," 
	group by a.department_id
) a
group by a.department_id
order by 6 desc
");
set v_sql_count=concat("
select  count(*) as c
from (
	select a.id		
	from platform_user a
	left join t_department b on a.department_id=b.id
	left join sop_target_y c on a.id=c.user_id and c.`year` = ",v_year,"
	left join sop_project d on a.id=d.create_uid
    where b.type=1 ",v_where," 
	group by a.department_id
) a ");
set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
if v_datatype = -1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
insert into DEPT_BASE_DATA2 select * from DEPT_BASE_DATA;
if v_datatype <> -1 then
	select 
		hhr,
        dept_id,
        dept_name,
        dept_target,
        total_target,
        completed,
        completed_all,
        zj_completed,
        (select count(*)+1+v_pageSize*v_pageNum from DEPT_BASE_DATA2 a where a.total_rate>b.total_rate) company_rank, 
        zj_rate,
        total_rate,
        lxh_pnumber,
        tjh_pnumber,
        ghl_rate,
        tjl_rate
    from DEPT_BASE_DATA b;
end if;
drop table if exists DEPT_BASE_DATA;
drop table if exists DEPT_BASE_DATA2;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_meeting_rate`(IN  `v_sdate` varchar(20),IN  `v_edate` varchar(20),IN  `v_deptid` int,IN  `v_meetingType` varchar(32),IN  `v_projectType` varchar(32),IN  `v_model` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '过会率+投决率'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(5000) default '';
declare v_sql_count varchar(5000) default '';
declare v_metting_type varchar(32) default ''; 
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(b.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(b.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
end if;
if v_deptid<>-1 then 
	set v_where=concat(v_where," and d.id=",v_deptid," ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and b.project_type='",v_projectType,"' ");
end if;
if v_meetingType<>'-1' then 
	set v_where=concat(v_where," and a.meeting_type='",v_meetingType,"' ");
end if;
set v_sql =concat("
select  
	a.dept_id as dept_id,
    a.dept_name as dept_name,
    a.meeting_type,
    sum(a.project_num) as total,
    sum(project_num_p) as pnum,
    sum(project_num_f) as fnum,
    sum(a.project_num-project_num_p-project_num_f) as snum,
    case when sum(a.project_num)>0 then round(sum(project_num_p)/sum(project_num),4) else 0 end as prate
from 
(
	select 
		a.dept_id,a.dept_name,a.meeting_type,count(*) as project_num,0 as project_num_p,0 as project_num_f
	from (
		select 
		   d.id as dept_id,
           d.name as dept_name,
		   b.id as project_id,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
        where 1=1 ",v_where,"
		group by d.id,a.meeting_type,b.id
	) a group by a.dept_id,a.meeting_type
	union all
	select 
		a.dept_id,a.dept_name,a.meeting_type,0 as project_num,count(*) as project_num_p,0 as project_num_f
	from (
		select 
		   d.id as dept_id,
           d.name as dept_name,
		   b.id as project_id,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
		where a.meeting_result='meetingResult:1' ",v_where,"
		group by d.id,a.meeting_type,b.id
	) a group by a.dept_id,a.meeting_type
	union all
	select 
		a.dept_id,a.dept_name,a.meeting_type,0 as project_num,0 as project_num_p,count(*) as project_num_f
	from (
		select 
		   d.id as dept_id,
           d.name as dept_name,
		   b.id as project_id,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
		left join t_department d on c.department_id=d.id
		where a.meeting_result='meetingResult:3' ",v_where,"
		group by d.id,a.meeting_type,b.id
	) a group by a.dept_id,a.meeting_type
) a 
group by a.dept_id
order by 8 desc
");
set v_sql_count =concat("
select 
	count(*) as c
from (
	select 
		a.dept_id,a.dept_name,a.meeting_type,count(*) as project_num,0 as project_num_p,0 as project_num_f
	from (
		select 
		   d.id as dept_id,
           d.name as dept_name,
		   b.id as project_id,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
        where 1=1 ",v_where,"
		group by d.id,a.meeting_type,b.id
	) a group by a.dept_id,a.meeting_type
    
) a ");
if v_model=1 then 
	set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
	if v_datatype = -1 then
		set @s = v_sql_count;
	else
		set @s = v_sql;
	end if;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_meeting_rate_user`(IN  `v_sdate` varchar(20),IN  `v_edate` varchar(20),IN  `v_deptid` int,IN  `v_meetingType` varchar(32),IN  `v_projectType` varchar(32),IN  `v_model` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '过会率+投决率'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(5000) default '';
declare v_sql_count varchar(5000) default '';
declare v_metting_type varchar(32) default ''; 
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(b.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(b.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
end if;
if v_deptid<>-1 then 
	set v_where=concat(v_where," and d.id=",v_deptid," ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and b.project_type='",v_projectType,"' ");
end if;
if v_meetingType<>'-1' then 
	set v_where=concat(v_where," and a.meeting_type='",v_meetingType,"' ");
end if;
set v_sql =concat("
select  
	a.user_id,
    a.real_name,
    a.dept_name,
    a.meeting_type,
    sum(a.project_num) as total, #项目数
    sum(project_num_p) as pnum, #通过数
    sum(project_num_f) as fnum, #否决数
    sum(a.project_num-project_num_p-project_num_f) as snum, #待定数
    case when sum(a.project_num)>0 then round(sum(project_num_p)/sum(project_num),4) else 0 end as prate #通过率
from 
(
	select 
	a.user_id,a.real_name,a.dept_name,a.meeting_type,count(*) as project_num,0 as project_num_p,0 as project_num_f
	from (
		select 
		   c.id as user_id,
           c.real_name,
		   b.id as project_id,
           d.name as dept_name,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
        where 1=1 ",v_where,"
		group by c.id,a.meeting_type,b.id
	) a group by a.user_id,a.meeting_type
	union all
	select 
		a.user_id,a.real_name,a.dept_name,a.meeting_type,0 as project_num,count(*) as project_num_p,0 as project_num_f
	from (
		select 
		   c.id as user_id,
           c.real_name,
		   b.id as project_id,
           d.name as dept_name,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
		where a.meeting_result='meetingResult:1' ",v_where,"
		group by c.id,a.meeting_type,b.id
	) a group by a.user_id,a.meeting_type
	union all
	select 
		a.user_id,a.real_name,a.dept_name,a.meeting_type,0 as project_num,0 as project_num_p,count(*) as project_num_f
	from (
		select 
		   c.id as user_id,
           c.real_name,
		   b.id as project_id,
           d.name as dept_name,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
		where a.meeting_result='meetingResult:3' ",v_where,"
		group by c.id,a.meeting_type,b.id
	) a group by a.user_id,a.meeting_type
) a group by a.user_id,a.meeting_type
    
order by 9 desc
");
set v_sql_count =concat("
select 
	count(*) as c
from (
	select 
	a.user_id,a.real_name,a.meeting_type,count(*) as project_num,0 as project_num_p,0 as project_num_f
	from (
		select 
		   c.id as user_id,
           c.real_name,
		   b.id as project_id,
		   a.meeting_type
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
        left join t_department d on c.department_id=d.id
        where 1=1 ",v_where,"
		group by c.id,a.meeting_type,b.id
	) a group by a.user_id,a.meeting_type
) a ");
if v_model=1 then 
	set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
	if v_datatype = -1 then
		set @s = v_sql_count;
	else
		set @s = v_sql;
	end if;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_meeting_scheduling`(IN  `v_deptid` int)
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(5000) default '';
if v_deptid<>-1 then 
	set v_where =concat(v_where," and d.id=",v_deptid," ");
end if;
set v_sql =concat("
select  
	sum(case when date_format(a.meeting_date,'%Y-%m-%d')=date(now()) and a.meeting_type='meetingType:2' and a.status='meetingResult:2' then 1 else 0 end) as ceops_today,#今日内评会
	sum(case when date_format(a.meeting_date,'%Y-%m-%d')=date(now()) and a.meeting_type='meetingType:3' and a.status='meetingResult:2' then 1 else 0 end) as lxh_today,#今日立项会
    sum(case when date_format(a.meeting_date,'%Y-%m-%d')=date(now()) and a.meeting_type='meetingType:4' and a.status='meetingResult:2' then 1 else 0 end) as tjh_today,#今日投决会
    sum(case when a.status='meetingResult:2' and a.meeting_type='meetingType:2' then 1 else 0 end) as ceops_sche_num,#ceo评审会－会议状态为等待
    sum(case when a.status='meetingResult:2' and a.meeting_type='meetingType:3' then 1 else 0 end) as lxh_sche_num,#立项会－－会议状态为等待
    sum(case when a.status='meetingResult:2' and a.meeting_type='meetingType:4' then 1 else 0 end) as tjh_sche_num #投决会－－会议状态为等待
from sop_meeting_scheduling a
left join sop_project b on a.project_id = b.id
left join platform_user c on b.create_uid=c.id
left join t_department d on c.department_id=d.id
where 1=1 ",v_where,"
 ");
 
set @s = v_sql;
 
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_meeting_scheduling_list`(IN  `v_sdate` varchar(10),IN  `v_edate` varchar(10),IN  `v_deptid` int,IN  `v_meetingTypeStr` varchar(500),IN  `v_model` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '排期池列表'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(5000) default '';
declare v_sql_count varchar(5000) default '';
if (v_sdate<>'-1' and v_edate<>'-1' and v_sdate=v_edate) then 
	set v_where = concat(v_where," and date_format(a.meeting_date,'%Y-%m-%d')='",v_sdate,"' ");
elseif (v_sdate='-1' and v_edate<>'-1') then 
	set v_where = concat(v_where," and date_format(a.meeting_date,'%Y-%m-%d')<='",v_edate,"' ");
elseif (v_sdate<>'-1' and v_edate='-1') then 
	set v_where = concat(v_where," and date_format(a.meeting_date,'%Y-%m-%d')>='",v_sdate,"' ");
elseif (v_sdate='-1' and v_edate='-1') then 
	set v_where=v_where;
else 
	set v_where = concat(v_where," and date_format(a.meeting_date,'%Y-%m-%d')>='",v_sdate,"' and date_format(a.meeting_date,'%Y-%m-%d')<='",v_edate,"' ");
end if;
if v_deptid<>-1 then 
	set v_where =concat(v_where," and d.id=",v_deptid," ");
end if;
if ( v_meetingTypeStr<>-1 and char_length(v_meetingTypeStr)>13 and left(v_meetingTypeStr,2)="('" and right(v_meetingTypeStr,2)="')" ) then 
	set v_where =concat(v_where," and a.meeting_type in ",v_meetingTypeStr," ");
elseif v_meetingTypeStr<>-1 and char_length(v_meetingTypeStr)<=13 then 
	set v_where =concat(v_where," and a.meeting_type='",v_meetingTypeStr,"' ");
end if;
set v_sql=concat("
select 
	ifnull(b.project_name,'') as project_name,
    ifnull(a.meeting_type,'') as meeting_type,
    ifnull(e.name,'') as meeting_type_name,
    ifnull(a.status,'') as status,
    ifnull(f.name,'') as status_name,
    ifnull(a.meeting_count,0) as meeting_count,
	#ifnull(a.meeting_date,'') as meeting_date,
    case when a.meeting_date is null then '' else date_format(a.meeting_date,'%Y-%m-%d') end as meeting_date,
    #(case when a.updated_time is not null then date_format(from_unixtime(a.updated_time/1000),'%Y-%m-%d') else '' end) as pre_meeting_date,
    ifnull(d.name,'') as dept_name,
    ifnull(c.real_name,'') as real_name,
    ifnull(a.remark,'') as remark
from sop_meeting_scheduling a
left join sop_project b on a.project_id = b.id
left join platform_user c on b.create_uid=c.id
left join t_department d on c.department_id=d.id
left join dict e on a.meeting_type=e.dict_code
left join dict f on a.status=f.dict_code
where a.status='meetingResult:2' ",v_where,"
");
set v_sql_count=concat("
select 
	count(*) as c
from sop_meeting_scheduling a
left join sop_project b on a.project_id = b.id
left join platform_user c on b.create_uid=c.id
left join t_department d on c.department_id=d.id
where a.status='meetingResult:2' ",v_where,"
");
if v_model<>1 then 
	set @s = v_sql;
end if;
if (v_model=1 and v_datatype=-1) then 
	set @s = v_sql_count;
end if;
if (v_model=1 and v_datatype<>-1) then  
	set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
	set @s=v_sql;
end if;
prepare stmt from @s;
execute stmt;
 
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_progress_duration_list`(IN  `v_sdate` varchar(20),IN  `v_edate` varchar(20),IN  `v_project_type` varchar(32),IN  `v_project_progress` varchar(32),IN  `v_project_status` varchar(32),IN  `v_deptid` int,IN  `v_model` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '历时统计'
begin 
declare v_where varchar(1000) default '';
declare v_sql varchar(10000) default '';
declare v_sql_count varchar(10000) default '';
declare v_wb_code varchar(32) default 'projectType:1'; 
declare v_zj_code varchar(32) default 'projectType:2'; 
declare v_lxh_str varchar(200) default "('projectProgress:3','projectProgress:4','projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10')"; 
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(b.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(b.created_time/1000,'%Y-%m-%d')<='",v_edate,"'");
end if;
if v_project_type<>-1 then 
	set v_where=concat(v_where," and b.project_type='",v_project_type,"'");
end if; 
if v_project_progress<>-1 then 
	set v_where=concat(v_where," and b.project_progress='",v_project_progress,"'");
end if;
if v_project_status<>-1 and v_project_status<>-2 then 
	set v_where=concat(v_where," and b.project_status='",v_project_status,"'");
end if;
if v_project_status=-2 then 
	set v_where=concat(v_where," or b.project_status='projectStatus:2'");
end if;
if v_deptid<>-1 then 
	set v_where=concat(v_where," and c.department_id=",v_deptid);
end if;
set v_sql =concat("
select 
	a.dict_code as project_progress, 	
	a.name as project_progress_name,
	round(ifnull(case a.dict_code when 'projectProgress:1' then (case when jcft_num<>0 then jcft_ms/jcft_num else 0 end)  
		 when 'projectProgress:2' then (case when np_num<>0 then np_ms/np_num else 0 end)  
		 when 'projectProgress:3' then (case when ceops_num<>0 then ceops_ms/ceops_num else 0 end) 
		 when 'projectProgress:4' then (case when lxh_num<>0 then lxh_ms/lxh_num else 0 end) 
		 when 'projectProgress:5' then (case when tzyxs_num<>0 then tzyxs_ms/tzyxs_num else 0 end)  
		 when 'projectProgress:6' then (case when jzdcbg_num<>0 then jzdcbg_ms/jzdcbg_num else 0 end)  
		 when 'projectProgress:7' then (case when tjh_num<>0 then tjh_ms/tjh_num else 0 end)  
		 when 'projectProgress:8' then (case when tzxy_num<>0 then tzxy_ms/tzxy_num else 0 end)  
		 when 'projectProgress:9' then (case when gqjg_num<>0 then gqjg_ms/gqjg_num else 0 end)   
         end,0)/86400000)  as duration_day
from dict a 
left join (
	select 
		sum(ifnull(a.jcft_ms,0)) as jcft_ms,
		sum(ifnull(a.np_ms,0)) as np_ms,
		sum(ifnull(a.ceops_ms,0)) as ceops_ms,
		sum(ifnull(a.lxh_ms,0)) as lxh_ms,
		sum(ifnull(a.tzyxs_ms,0)) as tzyxs_ms,
		sum(ifnull(a.jzdcbg_ms,0)) as jzdcbg_ms,
		sum(ifnull(a.tjh_ms,0)) as tjh_ms,
		sum(ifnull(a.tzxy_ms,0)) as tzxy_ms,
		sum(ifnull(a.gqjg_ms,0)) as gqjg_ms
	from v_project_progress_duration a
    left join sop_project b on a.project_id=b.id
    left join platform_user c on b.create_uid=c.id 
    where 1=1 ",v_where,"
) b on 1=1
left join 
(
	select 
	 #a.dict_code,a.num,a.dict_sortnum,
	 sum(case when dict_sortnum>1 then num else 0 end)  as jcft_num,
	 sum(case when dict_sortnum>2 then num else 0 end)  as np_num,
	 sum(case when dict_sortnum>3 then num else 0 end)  as ceops_num,
	 sum(case when dict_sortnum>4 then num else 0 end)  as lxh_num,
	 sum(case when dict_sortnum>5 then num else 0 end)  as tzyxs_num,
	 sum(case when dict_sortnum>6 then num else 0 end)  as jzdcbg_num,
	 sum(case when dict_sortnum>7 then num else 0 end)  as tjh_num,
	 sum(case when dict_sortnum>8 then num else 0 end)  as tzxy_num,
	 sum(case when dict_sortnum>9 then num else 0 end)  as gqjg_num
	from ( 
		select 
		 dt.dict_code,
		 count(b.id) as num,
		 case when right(dt.dict_code,1)=0 then 10 else right(dt.dict_code,1)+0 end as dict_sortnum
		from dict dt  
		left join sop_project b on dt.dict_code=b.project_progress and (b.project_progress='projectProgress:10'
		or b.project_status='projectStatus:2')
		left join platform_user c on b.create_uid=c.id
		where dt.parent_code='projectProgress' ",v_where,"
		group by dt.dict_code
		order by 3 asc
	) a
) c on 1=1
where a.parent_code='projectProgress' and a.dict_code<>'projectProgress:10'
group by a.dict_code
order by dict_sort asc
");
set v_sql_count =concat("select count(*) as c from dict a where a.parent_code='projectProgress' and a.dict_code<>'projectProgress:10'");
if v_model=1 then 
	set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
	if v_datatype = -1 then
		set @s = v_sql_count;
	else
		set @s = v_sql;
	end if;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_project_line_chart`(IN  `v_sdate` date,IN  `v_edate` date,IN  `v_year` int,IN  `v_deptid` int,IN  `v_projectType` varchar(32),IN  `v_model` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '项目数目标追踪'
begin
declare v_where varchar(1000) default '';
declare v_where2 varchar(1000) default '';
declare v_sql varchar(5000) default '';
declare v_sql_count varchar(5000) default '';
declare v_order varchar(1000) default '';
if v_deptid<>-1 then 
	set v_where  = concat(v_where," and c.id=" ,v_deptid," ");
    set v_where2  = concat(v_where2," and a.id=" ,v_deptid," ");
end if;
if v_projectType='-1' then
	set v_order="order by ifnull(c.completed,0)+ifnull(d.completed,0) desc";
elseif v_projectType='projectType:1' then 
	set v_order=" order by ifnull(c.completed,0) desc ";
elseif v_projectType='projectType:2' then
	set v_order=" order by ifnull(d.completed,0) desc ";
end if;
set v_sql =concat("
select 
	a.id as department_id,
	ifnull(a.name,'') as department_name,
    ifnull(b.target,0) as target,
	ifnull(c.completed,0) as wb_completed,
	ifnull(d.completed,0) as zj_completed,
    ifnull(c.completed,0)+ifnull(d.completed,0) as completed,
    case when b.target-(ifnull(c.completed,0)+ifnull(d.completed,0))>0 then b.target-(ifnull(c.completed,0)+ifnull(d.completed,0)) else 0 end as notcompleted,
    case when b.target=0 or b.target is null then 0 else round(ifnull(c.completed,0)/b.target,4) end as wb_rate,
    case when b.target=0 or b.target is null then 0 else round(ifnull(d.completed,0)/b.target,4) end as zj_rate,
    case when b.target=0 or b.target is null then 0 else round((ifnull(c.completed,0)+ifnull(d.completed,0))/b.target,4) end as rate
from t_department a
left join (
	select 
		c.id as dept_id,c.`name` as department_name,sum(a.number) as target
	from sop_target_y a
	left join platform_user b on a.user_id=b.id
	left join t_department c on b.department_id=c.id
    where c.type=1 and a.year=",v_year," ",v_where,"
	group by c.id
) b on a.id=b.dept_id
left join 
(
	select 
		c.id as dept_id,c.`name`,a.project_type,count(a.id) as completed 
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	where c.type=1
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"'
	and a.project_type='projectType:1' #外部项目 
    ",v_where,"
	group by c.id	
) c on a.id=c.dept_id
left join 
(
	select 
		c.id as dept_id,c.`name`,a.project_type,count(a.id) as completed 
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	where c.type=1
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"'
	and a.project_type='projectType:2'#内部项目
    ",v_where,"
	group by c.id	
) d on a.id=d.dept_id
where a.type=1 ",v_where," ",v_order);
set v_sql_count =concat("select count(*) as c from t_department a where a.type=1 ",v_where," ");
if v_model=1 then 
	set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
end if;
if v_datatype = -1 and v_model=1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_project_line_hhr_chart`(IN  `v_sdate` date,IN  `v_edate` date,IN  `v_year` int,IN  `v_deptid` int,IN  `v_projectType` varchar(32),IN  `v_model` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '项目数目标追踪---合伙人专用'
begin
declare v_where varchar(1000) default '';
declare v_where2 varchar(1000) default '';
declare v_sql varchar(5000) default '';
declare v_sql_count varchar(5000) default '';
declare v_order varchar(1000) default '';
if v_deptid<>-1 then 
	set v_where  = concat(v_where," and c.id=" ,v_deptid," ");
    set v_where2  = concat(v_where2," and b.id=" ,v_deptid," ");
end if;
if v_projectType='-1' then
	set v_order="order by ifnull(d.completed,0)+ifnull(e.completed,0) desc";
elseif v_projectType='projectType:1' then 
	set v_order=" order by ifnull(d.completed,0) desc ";
elseif v_projectType='projectType:2' then
	set v_order=" order by ifnull(e.completed,0) desc ";
end if;
set v_sql =concat("
select 
	a.id as user_id,
	a.real_name,
    b.name as department_name,
    ifnull(c.target,0) as target,
	ifnull(d.completed,0) as wb_completed,
	ifnull(e.completed,0) as zj_completed,
    ifnull(d.completed,0)+ifnull(e.completed,0) as completed,
    case when c.target-(ifnull(d.completed,0)+ifnull(e.completed,0))>0 then c.target-(ifnull(d.completed,0)+ifnull(e.completed,0)) else 0 end as notcompleted,
    case when c.target=0 or c.target is null then 0 else round(ifnull(d.completed,0)/c.target,4) end as wb_rate,
    case when c.target=0 or c.target is null then 0 else round(ifnull(e.completed,0)/c.target,4) end as zj_rate,
    case when c.target=0 or c.target is null then 0 else round((ifnull(d.completed,0)+ifnull(e.completed,0))/c.target,4) end as rate
from platform_user a
left join t_department b on a.department_id=b.id
left join platform_user_role f on a.id=f.user_id
left join (
	select 
		b.id as user_id,b.real_name,sum(a.number) as target
	from sop_target_y a
	left join platform_user b on a.user_id=b.id
	left join t_department c on b.department_id=c.id
    where c.type=1 and a.year=",v_year," ",v_where," 
	group by b.id
) c on a.id=c.user_id
left join 
(
	select 
		b.id as user_id,b.real_name,count(a.id) as completed 
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	where c.type=1
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"'
	and a.project_type='projectType:1'
    ",v_where," 
	group by b.id	
) d on a.id=d.user_id
left join 
(
	select 
		b.id as user_id,b.real_name,count(a.id) as completed 
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	where c.type=1 
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"'
	and a.project_type='projectType:2' 
    ",v_where," 
	group by b.id	
) e on a.id=e.user_id
where b.type=1 and f.role_id=4 ",v_where2," ",v_order," ");
set v_sql_count =concat("
select 
	count(*) as c
from platform_user a
left join t_department b on a.department_id=b.id
left join platform_user_role f on a.id=f.user_id
where b.type=1 and f.role_id=4 ",v_where2);
if v_model=1 then 
	set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
end if;
if v_datatype = -1 and v_model=1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
PREPARE stmt from @s;
EXECUTE stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_project_list`(IN  `v_sdate` date,IN  `v_edate` date,IN  `v_project_type` varchar(32),IN  `v_project_progress` varchar(32),IN  `v_deptid` int,IN `v_userid` int,IN  `v_sec_lxh` int,IN  `v_sec_tjh` int,IN  `v_type` int,IN  `v_page` int,IN  `v_perpage` int)
    COMMENT '项目数目标追踪'
begin 
declare v_where varchar(1000) default '';
declare v_sql varchar(20000) default '';
declare v_sql_count varchar(20000) default '';
declare v_wb_code varchar(32) default 'projectType:1'; 
declare v_zj_code varchar(32) default 'projectType:2'; 
declare v_lxh_str varchar(200) default "('projectProgress:3','projectProgress:4','projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10')"; 
if v_sdate<>-1 then 
	set v_where=concat(v_where," and from_unixtime(a.created_time/1000,'%Y-%m-%d') >= '",v_sdate,"' ");
end if; 
if v_edate<>-1 then 
	set v_where=concat(v_where," and from_unixtime(a.created_time/1000,'%Y-%m-%d') <= '",v_edate,"' ");
end if; 
if v_project_type<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_project_type,"'");
end if; 
if v_project_progress<>-1 then 
	set v_where=concat(v_where," and a.project_progress='",v_project_progress,"'");
end if;
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.department_id=",v_deptid);
end if;
if v_userid<>-1 then 
	set v_where=concat(v_where," and a.create_uid=",v_userid);
end if;
if v_sec_lxh<>-1 then 
	set v_where=concat(v_where," and v_mt.lxh_meeting_count>0 ");
end if;
if v_sec_tjh<>-1 then 
	set v_where=concat(v_where," and v_mt.tjh_meeting_count>0 ");
end if;
set v_sql =concat("
select 
	a.id as project_id,#项目id
	ifnull(a.project_code,' ') as project_code,#项目编号
	ifnull(a.project_name,'-') as project_name,#项目名称
	ifnull(du.real_name,' ') as partner, #合伙人
	ifnull(b.real_name,' ') as user_name,#投资经理
	ifnull(c.`name`,' ') as project_careerline,#投资事业线
	ifnull(d.`name`,' ') as project_type,#项目类型
	ifnull(a.project_contribution,0) as project_contribution,#初始投资额
    case a.currency_unit when 0 then '人民币' when 1  then '美元' when 2 then '英镑' when 3 then '欧元' else '' end as unit,#资金单位
	ifnull(dd.dict_code,' ') as project_progress,#项目当前阶段
	ifnull(dd.`name`,' ') as project_progress_name,#项目当前阶段名
	ifnull(e.`name`,' ') as project_status,#项目阶段状态
	ifnull(from_unixtime(a.created_time/1000,'%Y-%m-%d'),'-') as created_time,#项目创建时间
	ifnull(from_unixtime(a.updated_time/1000,'%Y-%m-%d %H:%i:%s'),'-') as updated_time,#项目最后修改时间
	ifnull(unix_timestamp()*1000 - a.created_time,0) as duration_millisecond, #总历时，毫秒
	ifnull(round((unix_timestamp()*1000 - a.created_time)/1000/60/60/24),0) as duration_day, #总历时，天
    ifnull(v_mt.jcft_count,0) as ft_count,#访谈记录数
	ifnull(v_dur.jcft_t_ms,0) as ft_duration_millisecond, #访谈历时,毫秒
	ifnull(round(v_dur.jcft_t_ms/1000/60/60/24),'-') as ft_duration_day,#访谈历时，天
    ifnull(v_mt.np_meeting_count,0) as np_meeting_count,#内评过会次数
    ifnull(v_mt.np_prate,'') as np_meeting_success_rate, #内评成功率
    ifnull(v_dur.np_t_ms,0) as np_duration_millisecond, #内评历时,毫秒
	ifnull(round(v_dur.np_t_ms/1000/60/60/24),0) as np_duration_day, #内评历时，天
    ifnull(v_mt.ceops_meeting_count,0) as ceops_meeting_count,#ceo评审过会次数
    ifnull(v_mt.ceops_prate,'') as ceops_meeting_success_rate, #ceo评审成功率
    ifnull(v_dur.ceops_t_ms,0) as ceops_duration_millisecond, #ceo评审历时,毫秒
	ifnull(round(v_dur.ceops_t_ms/1000/60/60/24),0) as ceops_duration_day, #ceo评审历时，天
	ifnull(v_mt.lxh_created_time,'') as lxh_created_time,#立项会申请时间
	ifnull(v_mt.lxh_meeting_count,0) as lxh_meeting_count,#立项会过会次数
    ifnull(v_mt.lxh_meeting_result,'') as lxh_return_status,#立项会结论
    ifnull(v_mt.lxh_meeting_date,'') as lxh_meeting_date,#立项会会议时间
	ifnull(v_dur.lxh_t_ms,0) as lxh_duration_millisecond, #立项会历时,毫秒
	ifnull(round(v_dur.lxh_t_ms/1000/60/60/24),'-') as lxh_duration_day, #立项会历时，天
    ifnull(v_mt.jzdc_completed_rate,'') as jzdc_completed_rate,#尽职调查完成度
    ifnull(v_dur.jzdcbg_t_ms,0) as jzdc_duration_millisecond, #尽职调查历时，毫秒
    ifnull(round(v_dur.jzdcbg_t_ms/1000/60/60),0) as jzdc_duration_hour, #尽职调查历时，小时
    ifnull(round(v_dur.jzdcbg_t_ms/1000/60/60/24),0) as jzdc_duration_day, #尽职调查历时，天
	ifnull(v_mt.tjh_created_time,'') as tjh_created_time,#投决会申请时间
	ifnull(v_mt.tjh_meeting_count,0) as tjh_meeting_count,#投决会申会次数
    ifnull(v_mt.tjh_meeting_result,' ') as tjh_return_status,#投决会结论
	ifnull(v_mt.tjh_meeting_date,' ') as tjh_meeting_date,#投决会会议时间
	ifnull(v_dur.tjh_t_ms,0) as tjh_duration_millisecond, #投决会历时,毫秒
	ifnull(round(v_dur.tjh_t_ms/1000/60/60/24),0) as tjh_duration_day, #投决会历时，天
	ifnull(v_mt.tzyxs_status,'') as tzyxs_status,#投资意向书状态
    ifnull(v_dur.tzyxs_t_ms,0) as tzyxs_duration_millisecond, #投资意向书历时，毫秒
    ifnull(round(v_dur.tzyxs_t_ms/1000/60/60),0) as tzyxs_duration_hours, #投资意向书历时，小时
    ifnull(round(v_dur.tzyxs_t_ms/1000/60/60/24),0) as tzyxs_duration_day, #投资意向书历时，天
    ifnull(v_mt.tzxy_status,'') as tzxy_status,#投资意向书状态
    ifnull(v_dur.tzxy_t_ms,0) as tzxy_duration_millisecond, #投资协议历时，毫秒
    ifnull(round(v_dur.tzxy_t_ms/1000/60/60),0) as tzxy_duration_hour, #投资协议历时，小时
    ifnull(round(v_dur.tzxy_t_ms/1000/60/60/24),0) as tzxy_duration_day, #投资协议历时，天
    ifnull(v_mt.gqjg_status,'') as gqjg_status,#股权交割状态
    ifnull(v_dur.gqjg_ms,0) as gqjg_duration_millisecond, #股权交割历时，毫秒
    ifnull(round(v_dur.gqjg_ms/1000/60/60),0) as gqjg_duration_hour, #股权交割历时，小时
    ifnull(round(v_dur.gqjg_ms/1000/60/60/24),0) as gqjg_duration_day, #股权交割历时，天
    0 as th_bk_count, #投后运营，拨款次数　
    0 as th_bk_amount_all #投后运营，拨款总额
from sop_project a 
left join platform_user b on a.create_uid=b.id
left join t_department c on b.department_id=c.id
left join platform_user du on c.manager_id=du.id
left join dict d on a.project_type=d.dict_code
left join dict e on a.project_status=e.dict_code
left join dict dd on a.project_progress=dd.dict_code
left join v_project_progress_duration v_dur on a.id=v_dur.project_id
left join v_project_meeting_and_task v_mt on a.id=v_mt.project_id
where 1=1 ",v_where," ");
set v_sql_count =concat("
select 
	count(*) as c
from sop_project a 
left join platform_user b on a.create_uid=b.id
left join t_department c on b.department_id=c.id
left join platform_user du on c.manager_id=du.id
left join dict d on a.project_type=d.dict_code
left join dict e on a.project_status=e.dict_code
left join dict dd on a.project_progress=dd.dict_code
left join v_project_progress_duration v_dur on a.id=v_dur.project_id
left join v_project_meeting_and_task v_mt on a.id=v_mt.project_id
where 1=1" ,v_where," ");
set v_sql = concat(v_sql,' limit ',v_perpage*v_page,",",v_perpage);
if v_type = -1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_project_progress_chart`(IN  `v_sdate` varchar(20),IN  `v_edate` varchar(20),IN  `v_project_type` varchar(32),IN  `v_project_progress` varchar(32),IN  `v_dept_id` INT,IN  `v_user_id` INT)
    COMMENT '项目进度分布'
begin 
declare v_where varchar(1000) DEFAULT '';
declare v_sql varchar(5000) DEFAULT '';
declare v_sql_count varchar(5000) DEFAULT '';
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(a.created_time/1000,'%Y-%m-%d') >='",v_sdate,"' ");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(a.created_time/1000,'%Y-%m-%d') <='",v_edate,"' ");
end if;
if v_project_type<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_project_type,"' ");
end if; 
if v_project_progress<>-1 then  
	set v_where=concat(v_where," and a.project_progress='",v_project_progress,"' ");
end if;
if v_dept_id<>-1 then 
	set v_where=concat(v_where," and d.id=",v_dept_id," ");
end if;
if v_user_id<>-1 then 
	set v_where=concat(v_where," and c.id=",v_user_id," ");
end if;
set v_sql=concat("
select count(*) into @count 
from sop_project a
left join dict b on a.project_progress=b.dict_code
left join platform_user c on a.create_uid=c.id
left join t_department d on c.department_id=d.id
where 1=1 ",v_where);
set @s=v_sql;
prepare stmt from @s;
execute stmt;
set v_sql ="";
set v_sql_count="";
set v_where="";
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(a.created_time/1000,'%Y-%m-%d') >='",v_sdate,"' ");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(a.created_time/1000,'%Y-%m-%d') <='",v_edate,"' ");
end if;
if v_project_type<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_project_type,"'");
end if; 
if v_project_progress<>-1 then 
	set v_where=concat(v_where," and a.project_progress='",v_project_progress,"'");
end if;
if v_dept_id<>-1 then 
	set v_where=concat(v_where," and d.id=",v_dept_id);
end if;
if v_user_id<>-1 then 
	set v_where=concat(v_where," and c.id=",v_user_id," ");
end if;
set v_sql=concat("
select 
	a.id as dict_id,a.dict_sort,a.dict_code,a.`name`,
	ifnull(b.c,0) as c,
	#b.department_name,
	b.total,
	b.rate
from (
	select 
		a.id,a.parent_code,a.dict_code,a.`name`,a.dict_sort
	from dict a
	where a.parent_code='projectProgress'
) a 
left join 
(
	select 
		count(*) as c,
		@count as total,
		round(count(*)/@count,4) as rate,
		b.`name` as dict_name,
		#d.`name` as department_name,
		b.dict_code
	from sop_project a
	left join dict b on a.project_progress=b.dict_code
	left join platform_user c on a.create_uid=c.id
	left join t_department d on c.department_id=d.id
	where 1=1 ",v_where,"
	group by a.project_progress
) b on a.dict_code=b.dict_code
order by a.dict_sort asc
");
set @d = v_sql;
prepare stmt from @d;
execute stmt;
 
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_rate_rise_d`(IN  `v_sdate` date,IN  `v_edate` date,IN  `v_deptid` int,IN  `v_projectType` varchar(32),IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '项目完成增长率(日)'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(10000) default '';
declare v_sql_count varchar(10000) default '';
declare v_wb_code varchar(32) default 'projectType:1'; 
declare v_zj_code varchar(32) default 'projectType:2'; 
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.department_id=",v_deptid," ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_projectType,"' ");
end if; 
set v_sql =concat("
select 
	ifnull(a.biz_date,'') as biz_date,
    ifnull(a.dept_id,0) as dept_id,
    ifnull(a.dept_name,'') as dept_name,
    ifnull(a.dict_code,'') as dict_code,
    ifnull(a.project_type_name,'') as project_type_name,
    ifnull(a.user_id,0) as user_id,
    ifnull(a.real_name,'') as real_name,
    ifnull(a.completed,0) as completed,
    ifnull(b.completed,0) as preday_completed,
    (case when (b.completed=0 or b.completed is null) then 0 else round((ifnull(a.completed,0)-ifnull(b.completed,0))/ifnull(b.completed,0),4) end ) as rise_rate
from (
	select  
		from_unixtime(a.created_time/1000,'%Y-%m-%d') as biz_date,
		c.id as dept_id,
		c.name as dept_name,
        d.dict_code,
		d.name as project_type_name,
		b.id as user_id,
		b.real_name,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_type=d.dict_code
	where c.type=1  
    and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ",v_where,"
	group by from_unixtime(a.created_time/1000,'%Y-%m-%d'),c.id,a.project_type,b.id
) a left join 
(
	select  
		from_unixtime(a.created_time/1000,'%Y-%m-%d') as biz_date,
        c.id as dept_id,
		c.name as dept_name,
        d.dict_code,
		d.name as project_type_name,
		b.id as user_id,
		b.real_name,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_type=d.dict_code
	where c.type=1 
    and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ",v_where,"
	group by from_unixtime(a.created_time/1000,'%Y-%m-%d'),c.id,a.project_type,b.id
) b on a.biz_date=date_add(b.biz_date,interval +1 day) 
and a.dept_id=b.dept_id
and a.dict_code=b.dict_code 
and a.user_id=b.user_id
order by a.biz_date asc
");
set v_sql_count =concat("
select count(*) as c from 
(
	select 
		from_unixtime(a.created_time/1000,'%Y-%m-%d') as biz_date,
		c.name as dept_name,
		d.name as project_progress_name,
		b.real_name,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_progress=d.dict_code
	where c.type=1
    and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
	and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"'",v_where," 
	group by from_unixtime(a.created_time/1000,'%Y-%m-%d'),c.id,a.project_type,b.id
) a
");
set v_sql = concat(v_sql,' limit ',v_pageNum*v_pageSize,",",v_pageSize);
if v_datatype = -1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_rate_rise_d_chart`(IN  `v_sdate` date,IN  `v_edate` date,IN  `v_deptid` int,IN  `v_projectType` varchar(32))
    COMMENT '项目完成数(日)- 图表数据'
begin
declare v_where varchar(5000) default '';
declare v_sql varchar(5000) default '';
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.department_id='",v_deptid,"' ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_projectType,"' ");
end if; 
set v_sql =concat("
select 
	from_unixtime(a.created_time/1000,'%Y-%m-%d') as biz_date,
	count(*) as completed
from sop_project a
left join platform_user b on a.create_uid=b.id
where from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"'
and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"'",v_where," 
group by from_unixtime(a.created_time/1000,'%Y-%m-%d')
order by from_unixtime(a.created_time/1000,'%Y-%m-%d') asc
");
set @s = v_sql;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_rate_rise_m`(IN  `v_sym` varchar(10),IN  `v_eym` varchar(10),IN  `v_deptid` int,IN  `v_projectType` varchar(32),IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '项目完成数(月)'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(10000) default '';
declare v_sql_count varchar(10000) default '';
declare v_wb_code varchar(32) default 'projectType:1'; 
declare v_zj_code varchar(32) default 'projectType:2'; 
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.department_id=",v_deptid," ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_projectType,"' ");
end if; 
set v_sql =concat("
select 
	ifnull(a.biz_date,'') as biz_date,
    ifnull(a.dept_id,0) as dept_id,
    ifnull(a.dept_name,'') as dept_name,
    ifnull(a.dict_code,'') as dict_code,
    ifnull(a.project_type_name,'') as project_type_name,
    ifnull(a.user_id,0) as user_id,
    ifnull(a.real_name,'') as real_name,
    ifnull(a.completed,0) as completed,
    ifnull(b.completed,0) as preday_completed,
    (case when (b.completed=0 or b.completed is null) then 0 else round((ifnull(a.completed,0)-ifnull(b.completed,0))/ifnull(b.completed,0),4) end ) as rise_rate
from (
	select  
		from_unixtime(a.created_time/1000,'%Y-%m') as biz_date,
		c.id as dept_id,
		c.name as dept_name,
        d.dict_code,
		d.name as project_type_name,
		b.id as user_id,
		b.real_name,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_type=d.dict_code
	where c.type=1 
    and from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"'
	and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"' ",v_where," 
	group by from_unixtime(a.created_time/1000,'%Y-%m'),c.id,d.dict_code,b.id
) a left join 
(
	select  
		from_unixtime(a.created_time/1000,'%Y-%m') as biz_date,
        c.id as dept_id,
		c.name as dept_name,
        d.dict_code,
		d.name as project_type_name,
		b.id as user_id,
		b.real_name,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_type=d.dict_code
	where c.type=1  
    and from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"'
	and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"' ",v_where,"
	group by from_unixtime(a.created_time/1000,'%Y-%m'),c.id,d.dict_code,b.id
) b on a.biz_date=date_add(b.biz_date,INTERVAL +1 DAY) 
and a.dept_id=b.dept_id
and a.dict_code=b.dict_code 
and a.user_id=b.user_id
order by a.biz_date asc
");
set v_sql_count =concat("
select count(*) as c from 
(
	select 
		from_unixtime(a.created_time/1000,'%Y-%m') as ym,
		c.name as dept_name,
		d.name as project_progress_name,
		b.real_name,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_progress=d.dict_code
	where c.type=1 
    and from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"' 
	and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"' ",v_where," 
	group by from_unixtime(a.created_time/1000,'%Y-%m'),b.department_id,a.project_type,a.create_uid
) a 
");
set v_sql = concat(v_sql,' limit ',v_pageNum*v_pageSize,",",v_pageSize);
if v_datatype = -1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_rate_rise_m_chart`(IN  `v_sym` varchar(10),IN  `v_eym` varchar(10),IN  `v_deptid` int,IN  `v_projectType` varchar(32))
    COMMENT '项目完成数(月)-图表数据'
begin
declare v_where varchar(5000) default '';
declare v_sql varchar(5000) default '';
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.department_id=",v_deptid," ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_projectType,"' ");
end if; 
set v_sql =concat("
select 
	from_unixtime(a.created_time/1000,'%Y-%m') as biz_date,
	count(*) as completed
from sop_project a
left join platform_user b on a.create_uid=b.id
where from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"'
and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"'",v_where," 
group by from_unixtime(a.created_time/1000,'%Y-%m')
order by from_unixtime(a.created_time/1000,'%Y-%m') asc
");
set @s = v_sql;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_rate_rise_month_chart`(IN  `v_sym` varchar(10),IN  `v_eym` varchar(10),IN  `v_deptid` int,IN  `v_projectType` varchar(32),IN  `v_projectProgress` varchar(32))
    COMMENT '数据简报－项目完成数(月)'
begin
declare v_where varchar(1000) default '';
declare v_sql varchar(10000) default '';
declare v_sql_count varchar(10000) default '';
declare v_wb_code varchar(32) default 'projectType:1'; 
declare v_zj_code varchar(32) default 'projectType:2'; 
if v_deptid<>-1 then 
	set v_where=concat(v_where," and b.department_id=",v_deptid," ");
end if;
if v_projectType<>-1 then 
	set v_where=concat(v_where," and a.project_type='",v_projectType,"' ");
end if; 
if v_projectProgress<>'-1' then 
	set v_where=concat(v_where," and a.project_progress='",v_projectProgress,"' ");
end if;
set v_sql =concat("
select 
	a.biz_date,#业务月
    ifnull(a.completed,0) as completed, #完成量
    ifnull(b.completed,0) as preday_completed,#上月完成量
    ifnull(a.target,0) as target, #目标
    (case when target>0 then ifnull(a.completed,0)/target else 0 end) as rate, #月完成率
    (case when (b.completed=0 or b.completed is null) then 0 else round((ifnull(a.completed,0)-ifnull(b.completed,0))/ifnull(b.completed,0),4) end ) as rise_rate, #增量环比
    a.project_valuations, #初始估值
    a.project_contribution #初始投资额
from (
	select  
		from_unixtime(a.created_time/1000,'%Y-%m') as biz_date,
		count(*) as completed,
        (select sum(number) as target from sop_target_y where year=2016) as target,
        sum(ifnull(a.project_valuations,0)) as project_valuations,
        sum(ifnull(a.project_contribution,0)) as project_contribution
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_type=d.dict_code
	where from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"'
	and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"' ",v_where," 
	group by from_unixtime(a.created_time/1000,'%Y-%m')
) a left join 
(
	select  
		from_unixtime(a.created_time/1000,'%Y-%m') as biz_date,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_type=d.dict_code
	where from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"'
	and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"' ",v_where,"
	group by from_unixtime(a.created_time/1000,'%Y-%m')
) b on a.biz_date=date_format(date_add(concat(b.biz_date,'-01'),INTERVAL +1 MONTH),'%Y-%m')
order by a.biz_date asc
");
set v_sql_count =concat("
select count(*) as c from 
(
	select 
		from_unixtime(a.created_time/1000,'%Y-%m') as ym,
		count(*) as completed
	from sop_project a
	left join platform_user b on a.create_uid=b.id
	left join t_department c on b.department_id=c.id
	left join dict d on a.project_progress=d.dict_code
	where from_unixtime(a.created_time/1000,'%Y-%m')>='",v_sym,"' 
	and from_unixtime(a.created_time/1000,'%Y-%m')<='",v_eym,"' ",v_where," 
	group by from_unixtime(a.created_time/1000,'%Y-%m')
) a 
");
set @s = v_sql;
prepare stmt from @s;
execute stmt;
end$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_fplatform_user_kpi`(IN  `v_sdate` date,IN  `v_edate` date,IN `v_year` int,IN `v_projectType` varchar(50),IN `v_projectProgress` varchar(50),IN `v_deptid` int,IN  `v_datatype` int,IN  `v_pageNum` int,IN  `v_pageSize` int)
    COMMENT '投资经理KPI'
begin
declare v_where varchar(1000) default '';
declare v_where2 varchar(1000) default '';
declare v_where4 varchar(1000) default '';
declare v_where5 varchar(1000) default '';
declare v_where6 varchar(1000) default '';
declare v_sql varchar(10000) default '';
declare v_sql_count varchar(10000) default '';
declare v_lxh_str varchar(200) default "('projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10')";
declare v_tjh_str varchar(200) default "('projectProgress:8','projectProgress:9','projectProgress:10')";
declare v_sdate_mi bigint(20) default 0;
declare v_edate_mi bigint(20) default 0;
declare v_s bigint(20) default 0;
if v_sdate<>'-1' then 
	set v_sdate_mi=unix_timestamp(concat(v_sdate,' 00:00:00'))*1000;
end if;
if v_edate<>'-1' then 
	set v_edate_mi=unix_timestamp(concat(v_edate,' 23:59:59'))*1000;
end if;
create temporary  table 
if not exists BASE_DATA (
	real_name varchar(50),
	dept_id int(11),
	dept_name varchar(50),
	target int(11),
	completed int(11),
    completed_all int(11),
    company_rank int(5),
    dept_rank int(5),
	total_rate decimal(18,4),
	dept_rate decimal(18,4),
	lxh_pnumber int(11),
	tjh_pnumber int(11),
	ghl_rate decimal(18,4),
    tjl_rate decimal(18,4)
);
create temporary  table 
if not exists BASE_DATA2 (
	real_name varchar(50),
	dept_id int(11),
	dept_name varchar(50),
	target int(11),
	completed int(11),
    completed_all int(11),
    company_rank int(5),
    dept_rank int(5),
	total_rate decimal(18,4),
	dept_rate decimal(18,4),
	lxh_pnumber int(11),
	tjh_pnumber int(11),
	ghl_rate decimal(18,4),
    tjl_rate decimal(18,4)
);
create temporary  table 
if not exists BASE_DATA3 (
	real_name varchar(50),
	dept_id int(11),
	dept_name varchar(50),
	target int(11),
	completed int(11),
    completed_all int(11),
    company_rank int(5),
    dept_rank int(5),
	total_rate decimal(18,4),
	dept_rate decimal(18,4),
	lxh_pnumber int(11),
	tjh_pnumber int(11),
	ghl_rate decimal(18,4),
    tjl_rate decimal(18,4)
);
if v_sdate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(d.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
    set v_where2 =concat(v_where2," and b.created_time>=",v_sdate_mi," ");
    set v_where4=concat(v_where4," and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
    set v_where5=concat(v_where5," and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
end if;
if v_edate<>'-1' then 
	set v_where=concat(v_where," and from_unixtime(d.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
    set v_where2 =concat(v_where2," and b.created_time<=",v_edate_mi," ");
    set v_where4=concat(v_where4," and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
    set v_where5=concat(v_where5," and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
end if;
if v_deptid<>-1 then 
	set v_where = concat(v_where," and a.department_id=" ,v_deptid," ");
    set v_where2 = concat(v_where2," and c.department_id=" ,v_deptid," ");
    set v_where4 = concat(v_where4," and b.department_id=" ,v_deptid," ");
end if;
if v_projectType<>'-1' then 
	set v_where = concat(v_where," and d.project_type='",v_projectType,"' ");
    set v_where2 = concat(v_where2," and b.project_type='",v_projectType,"' ");
    set v_where4 = concat(v_where4," and a.project_type='",v_projectType,"' ");
    set v_where5 = concat(v_where5," and a.project_type='",v_projectType,"' ");
    set v_where6 = concat(v_where6," and a.project_type='",v_projectType,"' ");
end if;
if v_projectProgress<>'-1' then 
	set v_where = concat(v_where," and d.project_progress='",v_projectProgress,"' ");
    set v_where2 = concat(v_where2," and b.project_progress='",v_projectProgress,"' ");
	set v_where4 = concat(v_where4," and a.project_progress='",v_projectProgress,"' ");
    set v_where5 = concat(v_where5," and a.project_progress='",v_projectProgress,"' ");
    set v_where6 = concat(v_where6," and a.project_progress='",v_projectProgress,"' ");
end if;
set v_sql = concat("
insert into BASE_DATA(real_name,dept_id,dept_name,target,completed,completed_all,company_rank,dept_rank,total_rate,dept_rate,lxh_pnumber,tjh_pnumber,ghl_rate,tjl_rate)
select 
	ifnull(a.real_name,'') as real_name, #投资经理姓名
	ifnull(a.department_id,0) as dept_id,#部门编号
	ifnull(b.`name`,'') as dept_name, #部门名
	c.number as target, #投资经理目标数
	count(d.id) as completed, #项目数
    h.completed_all,#累计完成数，累计值
	0 as company_rank, #公司排名
	0 as dept_rank, #部门排名
	(case when o.company_completed=0 then 0 else round(count(d.id)/o.company_completed,4) end)as total_rate, #公司完成数占比
	(case when oo.dept_completed=0 then 0 else round(count(d.id)/oo.dept_completed,4) end) as dept_rate, #部门完成数占比
	ifnull(f.pnum,0) as lxh_number, #立项会通过数
	ifnull(g.pnum,0) as tjh_number, #投决会通过数
	ifnull(f.prate,0) as ghl_rate, #过会率
	ifnull(g.prate,0) as tjl_rate  #投决率
from platform_user a
left join t_department b on a.department_id=b.id
left join sop_target_y c on a.id=c.user_id and c.`year` = ",v_year,"
left join sop_project d on a.id=d.create_uid 
left join platform_user_role tur on a.id=tur.user_id
left join (
	select count(distinct a.id) as company_completed 
    from sop_project a
    left join platform_user b on a.create_uid=b.id
    where 1=1 ",v_where5,"  
) o on 1=1 
left join (
	select b.department_id,count(distinct a.id) as dept_completed 
    from sop_project a
    left join platform_user b on a.create_uid=b.id
    where 1=1 ",v_where4,"  
    group by b.department_id
) oo on a.department_id=oo.department_id
left join (
	select a.create_uid,count(a.id) as completed_all 
    from sop_project a
    left join platform_user b on a.create_uid=b.id
    where 1=1 ",v_where6,"  
    group by create_uid
) h on a.id=h.create_uid
/*left join (
	#部门年目标
	select b.department_id,c.`name` as department_name,sum(a.number) as number
	from sop_target_y a  
	left join platform_user b on a.user_id=b.id
	left join t_department c on b.department_id=c.id
	left join platform_user_role tur on a.user_id=tur.user_id
	where a.year=",v_year," #and tur.role_id=4 #角色为投资经理
	group by b.department_id
) e on a.department_id=e.department_id*/
left join (
	#投资经理-立项会－申请过会议的项目数、通过数、否决数、当前待定数\过会率
    select  
		user_id,num,pnum,fnum,num-pnum-fnum as snum,
		case when num>0 then round(pnum/num,4) else 0 end as prate
	from 
	(
		select 
			b.create_uid as user_id,
			count(distinct b.id) as num,
			sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
			sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
		left join t_department d on c.department_id=d.id
		where d.type=1 and a.meeting_type='meetingType:3' ",v_where2,"
		group by b.create_uid
	) a
) f on a.id=f.user_id
left join (
	#投资经理-立项会－申请过会议的项目数、通过数、否决数、当前待定数\过会率
    select  
		user_id,num,pnum,fnum,num-pnum-fnum as snum,
		case when num>0 then round(pnum/num,4) else 0 end as prate
	from 
	(
		select 
			b.create_uid as user_id,
			count(distinct b.id) as num,
			sum(case when a.meeting_type='meetingType:4' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
			sum(case when a.meeting_type='meetingType:4' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
		left join t_department d on c.department_id=d.id
		where d.type=1 and a.meeting_type='meetingType:4' ",v_where2,"
		group by b.create_uid
	) a
) g on a.id=g.user_id
where 
#tur.role_id=4 #角色为投资经理 and 
from_unixtime(d.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' 
and from_unixtime(d.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ",v_where,"
group by a.id
");
set v_sql_count=concat("
select count(*) as c
from 
(
	select 
		a.id
	from platform_user a
	left join t_department b on a.department_id=b.id
	left join sop_target_y c on a.id=c.user_id and c.`year` = ",v_year,"
	left join sop_project d on a.id=d.create_uid 
	left join platform_user_role tur on a.id=tur.user_id
	where 1=1 ",v_where,"
    #tur.role_id=4 
	group by a.id
) a ");
#set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
set v_s=v_pageSize*v_pageNum;
if v_datatype = -1 then
	set @s = v_sql_count;
else
	set @s = v_sql;
end if;
prepare stmt from @s;
execute stmt;
insert into BASE_DATA2 select * from BASE_DATA;
insert into BASE_DATA3 select * from BASE_DATA;
if v_datatype <> -1 then
	select 
		b.real_name,
        b.dept_id,
        b.dept_name,
        b.target,
        b.completed,
        b.completed_all,
        
        (select count(*)+1 from BASE_DATA2 a where a.total_rate>b.total_rate) company_rank, 
        
        (select count(*)+1 from BASE_DATA3 c where c.dept_rate>b.dept_rate) dept_rank, 
        b.total_rate,
        b.dept_rate,
        b.lxh_pnumber,
        b.tjh_pnumber,
        b.ghl_rate,
        b.tjl_rate 
    from BASE_DATA b
    order by completed desc
    limit v_s,v_pageSize;
end if;
drop table if exists BASE_DATA;
drop table if exists BASE_DATA2;
drop table if exists BASE_DATA3;
end$$
DELIMITER $$

USE `fx_db`$$

DROP PROCEDURE IF EXISTS `t_ft_user_kpi`$$

CREATE DEFINER=`fxuser`@`10.%.%.%` PROCEDURE `t_ft_user_kpi`(IN  `v_sdate` DATE,IN  `v_edate` DATE,IN `v_year` INT,IN `v_projectType` VARCHAR(50),IN `v_projectProgress` VARCHAR(50),IN `v_deptid` INT,IN  `v_datatype` INT,IN  `v_pageNum` INT,IN  `v_pageSize` INT)
    COMMENT '投资经理KPI'
BEGIN
DECLARE v_where VARCHAR(1000) DEFAULT '';
DECLARE v_where2 VARCHAR(1000) DEFAULT '';
DECLARE v_where4 VARCHAR(1000) DEFAULT '';
DECLARE v_where5 VARCHAR(1000) DEFAULT '';
DECLARE v_where6 VARCHAR(1000) DEFAULT '';
DECLARE v_sql VARCHAR(10000) DEFAULT '';
DECLARE v_sql_count VARCHAR(10000) DEFAULT '';
DECLARE v_lxh_str VARCHAR(200) DEFAULT "('projectProgress:5','projectProgress:6','projectProgress:7','projectProgress:8','projectProgress:9','projectProgress:10')";
DECLARE v_tjh_str VARCHAR(200) DEFAULT "('projectProgress:8','projectProgress:9','projectProgress:10')";
DECLARE v_sdate_mi BIGINT(20) DEFAULT 0;
DECLARE v_edate_mi BIGINT(20) DEFAULT 0;
DECLARE v_s BIGINT(20) DEFAULT 0;
IF v_sdate<>'-1' THEN 
	SET v_sdate_mi=UNIX_TIMESTAMP(CONCAT(v_sdate,' 00:00:00'))*1000;
END IF;
IF v_edate<>'-1' THEN 
	SET v_edate_mi=UNIX_TIMESTAMP(CONCAT(v_edate,' 23:59:59'))*1000;
END IF;
CREATE TEMPORARY  TABLE 
IF NOT EXISTS BASE_DATA (
	real_name VARCHAR(50),
	dept_id INT(11),
	dept_name VARCHAR(50),
	target INT(11),
	completed INT(11),
    completed_all INT(11),
    company_rank INT(5),
    dept_rank INT(5),
	total_rate DECIMAL(18,4),
	dept_rate DECIMAL(18,4),
	lxh_pnumber INT(11),
	tjh_pnumber INT(11),
	ghl_rate DECIMAL(18,4),
    tjl_rate DECIMAL(18,4)
);
CREATE TEMPORARY  TABLE 
IF NOT EXISTS BASE_DATA2 (
	real_name VARCHAR(50),
	dept_id INT(11),
	dept_name VARCHAR(50),
	target INT(11),
	completed INT(11),
    completed_all INT(11),
    company_rank INT(5),
    dept_rank INT(5),
	total_rate DECIMAL(18,4),
	dept_rate DECIMAL(18,4),
	lxh_pnumber INT(11),
	tjh_pnumber INT(11),
	ghl_rate DECIMAL(18,4),
    tjl_rate DECIMAL(18,4)
);
CREATE TEMPORARY  TABLE 
IF NOT EXISTS BASE_DATA3 (
	real_name VARCHAR(50),
	dept_id INT(11),
	dept_name VARCHAR(50),
	target INT(11),
	completed INT(11),
    completed_all INT(11),
    company_rank INT(5),
    dept_rank INT(5),
	total_rate DECIMAL(18,4),
	dept_rate DECIMAL(18,4),
	lxh_pnumber INT(11),
	tjh_pnumber INT(11),
	ghl_rate DECIMAL(18,4),
    tjl_rate DECIMAL(18,4)
);
IF v_sdate<>'-1' THEN 
	SET v_where=CONCAT(v_where," and from_unixtime(d.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
    SET v_where2 =CONCAT(v_where2," and b.created_time>=",v_sdate_mi," ");
    SET v_where4=CONCAT(v_where4," and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
    SET v_where5=CONCAT(v_where5," and from_unixtime(a.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' ");
END IF;
IF v_edate<>'-1' THEN 
	SET v_where=CONCAT(v_where," and from_unixtime(d.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
    SET v_where2 =CONCAT(v_where2," and b.created_time<=",v_edate_mi," ");
    SET v_where4=CONCAT(v_where4," and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
    SET v_where5=CONCAT(v_where5," and from_unixtime(a.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ");
END IF;
IF v_deptid<>-1 THEN 
	SET v_where = CONCAT(v_where," and a.department_id=" ,v_deptid," ");
    SET v_where2 = CONCAT(v_where2," and c.department_id=" ,v_deptid," ");
    SET v_where4 = CONCAT(v_where4," and b.department_id=" ,v_deptid," ");
END IF;
IF v_projectType<>'-1' THEN 
	SET v_where = CONCAT(v_where," and d.project_type='",v_projectType,"' ");
    SET v_where2 = CONCAT(v_where2," and b.project_type='",v_projectType,"' ");
    SET v_where4 = CONCAT(v_where4," and a.project_type='",v_projectType,"' ");
    SET v_where5 = CONCAT(v_where5," and a.project_type='",v_projectType,"' ");
    SET v_where6 = CONCAT(v_where6," and a.project_type='",v_projectType,"' ");
END IF;
IF v_projectProgress<>'-1' THEN 
	SET v_where = CONCAT(v_where," and d.project_progress='",v_projectProgress,"' ");
    SET v_where2 = CONCAT(v_where2," and b.project_progress='",v_projectProgress,"' ");
	SET v_where4 = CONCAT(v_where4," and a.project_progress='",v_projectProgress,"' ");
    SET v_where5 = CONCAT(v_where5," and a.project_progress='",v_projectProgress,"' ");
    SET v_where6 = CONCAT(v_where6," and a.project_progress='",v_projectProgress,"' ");
END IF;
SET v_sql = CONCAT("
insert into BASE_DATA(real_name,dept_id,dept_name,target,completed,completed_all,company_rank,dept_rank,total_rate,dept_rate,lxh_pnumber,tjh_pnumber,ghl_rate,tjl_rate)
select 
	ifnull(a.real_name,'') as real_name, #投资经理姓名
	ifnull(a.department_id,0) as dept_id,#部门编号
	ifnull(b.`name`,'') as dept_name, #部门名
	c.number as target, #投资经理目标数
	count(d.id) as completed, #项目数
    h.completed_all,#累计完成数，累计值
	0 as company_rank, #公司排名
	0 as dept_rank, #部门排名
	(case when o.company_completed=0 then 0 else round(count(d.id)/o.company_completed,4) end)as total_rate, #公司完成数占比
	(case when oo.dept_completed=0 then 0 else round(count(d.id)/oo.dept_completed,4) end) as dept_rate, #部门完成数占比
	ifnull(f.pnum,0) as lxh_number, #立项会通过数
	ifnull(g.pnum,0) as tjh_number, #投决会通过数
	ifnull(f.prate,0) as ghl_rate, #过会率
	ifnull(g.prate,0) as tjl_rate  #投决率
from platform_user a
left join t_department b on a.department_id=b.id
left join sop_target_y c on a.id=c.user_id and c.`year` = ",v_year,"
left join sop_project d on a.id=d.create_uid 
left join platform_user_role tur on a.id=tur.user_id
left join (
	select count(distinct a.id) as company_completed 
    from sop_project a
    left join platform_user b on a.create_uid=b.id
    where 1=1 ",v_where5,"  
) o on 1=1 
left join (
	select b.department_id,count(distinct a.id) as dept_completed 
    from sop_project a
    left join platform_user b on a.create_uid=b.id
    where 1=1 ",v_where4,"  
    group by b.department_id
) oo on a.department_id=oo.department_id
left join (
	select a.create_uid,count(a.id) as completed_all 
    from sop_project a
    left join platform_user b on a.create_uid=b.id
    where 1=1 ",v_where6,"  
    group by create_uid
) h on a.id=h.create_uid
/*left join (
	#部门年目标
	select b.department_id,c.`name` as department_name,sum(a.number) as number
	from sop_target_y a  
	left join platform_user b on a.user_id=b.id
	left join t_department c on b.department_id=c.id
	left join platform_user_role tur on a.user_id=tur.user_id
	where a.year=",v_year," #and tur.role_id=4 #角色为投资经理
	group by b.department_id
) e on a.department_id=e.department_id*/
left join (
	#投资经理-立项会－申请过会议的项目数、通过数、否决数、当前待定数\过会率
    select  
		user_id,num,pnum,fnum,num-pnum-fnum as snum,
		case when num>0 then round(pnum/num,4) else 0 end as prate
	from 
	(
		select 
			b.create_uid as user_id,
			count(distinct b.id) as num,
			sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
			sum(case when a.meeting_type='meetingType:3' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
		left join t_department d on c.department_id=d.id
		where d.type=1 and a.meeting_type='meetingType:3' ",v_where2,"
		group by b.create_uid
	) a
) f on a.id=f.user_id
left join (
	#投资经理-立项会－申请过会议的项目数、通过数、否决数、当前待定数\过会率
    select  
		user_id,num,pnum,fnum,num-pnum-fnum as snum,
		case when num>0 then round(pnum/num,4) else 0 end as prate
	from 
	(
		select 
			b.create_uid as user_id,
			count(distinct b.id) as num,
			sum(case when a.meeting_type='meetingType:4' and a.meeting_result='meetingResult:1' then 1 else 0 end ) as pnum,
			sum(case when a.meeting_type='meetingType:4' and a.meeting_result='meetingResult:3' then 1 else 0 end ) as fnum
		from sop_meeting_record a
		left join sop_project b on a.project_id=b.id
		left join platform_user c on b.create_uid=c.id
		left join t_department d on c.department_id=d.id
		where d.type=1 and a.meeting_type='meetingType:4' ",v_where2,"
		group by b.create_uid
	) a
) g on a.id=g.user_id
where 
#tur.role_id=4 #角色为投资经理 and 
from_unixtime(d.created_time/1000,'%Y-%m-%d')>='",v_sdate,"' 
and from_unixtime(d.created_time/1000,'%Y-%m-%d')<='",v_edate,"' ",v_where,"
group by a.id
");
SET v_sql_count=CONCAT("
select count(*) as c
from 
(
	select 
		a.id
	from platform_user a
	left join t_department b on a.department_id=b.id
	left join sop_target_y c on a.id=c.user_id and c.`year` = ",v_year,"
	left join sop_project d on a.id=d.create_uid 
	left join platform_user_role tur on a.id=tur.user_id
	where 1=1 ",v_where,"
    #tur.role_id=4 
	group by a.id
) a ");
#set v_sql = concat(v_sql,' limit ',v_pageSize*v_pageNum,",",v_pageSize);
SET v_s=v_pageSize*v_pageNum;
IF v_datatype = -1 THEN
	SET @s = v_sql_count;
ELSE
	SET @s = v_sql;
END IF;
PREPARE stmt FROM @s;
EXECUTE stmt;
INSERT INTO BASE_DATA2 SELECT * FROM BASE_DATA;
INSERT INTO BASE_DATA3 SELECT * FROM BASE_DATA;
IF v_datatype <> -1 THEN
	SELECT 
		b.real_name,
        b.dept_id,
        b.dept_name,
        b.target,
        b.completed,
        b.completed_all,
        
        (SELECT COUNT(*)+1 FROM BASE_DATA2 a WHERE a.total_rate>b.total_rate) company_rank, 
        
        (SELECT COUNT(*)+1 FROM BASE_DATA3 c WHERE c.dept_rate>b.dept_rate) dept_rank, 
        b.total_rate,
        b.dept_rate,
        b.lxh_pnumber,
        b.tjh_pnumber,
        b.ghl_rate,
        b.tjl_rate 
    FROM BASE_DATA b
    ORDER BY completed DESC
    LIMIT v_s,v_pageSize;
END IF;
DROP TABLE IF EXISTS BASE_DATA;
DROP TABLE IF EXISTS BASE_DATA2;
DROP TABLE IF EXISTS BASE_DATA3;
END$$

DELIMITER ;