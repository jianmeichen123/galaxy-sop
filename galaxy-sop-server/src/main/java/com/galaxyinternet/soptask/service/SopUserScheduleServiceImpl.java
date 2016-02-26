package com.galaxyinternet.soptask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.service.SopUserScheduleService;

@Service("com.galaxyinternet.service.SopUserScheduleService")
public class SopUserScheduleServiceImpl extends BaseServiceImpl<SopUserSchedule>implements SopUserScheduleService {

	@Autowired
	private SopUserScheduleDao sopUserScheduleDao;
	
	@Override
	protected BaseDao<SopUserSchedule, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleDao;
	}

}
