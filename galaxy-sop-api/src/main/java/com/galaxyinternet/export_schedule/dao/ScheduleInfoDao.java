package com.galaxyinternet.export_schedule.dao;

import java.util.List;

import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.dao.BaseDao;

public interface ScheduleInfoDao extends BaseDao<ScheduleInfo, Long>{

	public Long getVisitCount(ScheduleInfo info);
	
	public Long getCompletedVisit(ScheduleInfo info);
	
	public List<ScheduleInfo> getVisitFanceStatus(ScheduleInfo info);
	
	public Long getAllRecordVisitCount(ScheduleInfo info);

	public Long getRecordVisitCount(ScheduleInfo info);
}
