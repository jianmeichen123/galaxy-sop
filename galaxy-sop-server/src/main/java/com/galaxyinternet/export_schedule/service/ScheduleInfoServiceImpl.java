package com.galaxyinternet.export_schedule.service;

import org.springframework.stereotype.Service;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;

@Service("scheduleInfoService")
public class ScheduleInfoServiceImpl extends BaseServiceImpl<ScheduleInfo> implements ScheduleInfoService{

	@Override
	protected BaseDao<ScheduleInfo, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
