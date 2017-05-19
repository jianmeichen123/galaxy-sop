package com.galaxyinternet.export_schedule.service;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.service.BaseService;

public interface ScheduleInfoService extends BaseService<ScheduleInfo>{

	public Map<String,Object> getVisitStatistics(ScheduleInfo info);
	public List<ScheduleInfo> getVisitFanceStatus(ScheduleInfo info);
	public Map<String,Object> getProjectVisit(ScheduleInfo info);
	public Map<String,Object> getRecordVisit(ScheduleInfo info);
}
