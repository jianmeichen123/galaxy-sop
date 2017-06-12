package com.galaxyinternet.export_schedule.service;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.export_schedule.model.BaiFanTj;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.service.BaseService;

public interface BaiFanTjService extends BaseService<BaiFanTj>{

	
	/**
	 * 统计                           总计划拜访数量、已完成拜访数量
	 * @param info   封装的查询条件
	 * @return 
	 */
	List<BaiFanTj> exportBaiFanSum(ScheduleInfo info);
	
	
	/**
	 * schedule_info 表所有用户的拜访统计数据
	 * @param info   封装的查询条件
	 * @return 
	 */
	Map<Long,Integer> queryAllUserSchedule(ScheduleInfo info, Map<Long, String> id_nameMap);
	
	
	/**
	 * schedule_info、sop_interview_record 表 :  用户 已完成的拜访统计数据
	 * @param info   封装的查询条件
	 * @return 
	 */
	Map<Long,Integer> queryCompleteUserSchedule(ScheduleInfo info, Map<Long, String> id_nameMap);
	
	
	
	
	
}
