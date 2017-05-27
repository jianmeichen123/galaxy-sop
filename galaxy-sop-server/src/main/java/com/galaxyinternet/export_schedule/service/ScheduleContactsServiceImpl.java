package com.galaxyinternet.export_schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.export_schedule.dao.ScheduleContactsDao;
import com.galaxyinternet.export_schedule.model.ScheduleContacts;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;

@Service("ScheduleContactsService")
public class ScheduleContactsServiceImpl  extends BaseServiceImpl<ScheduleContacts> implements ScheduleContactsService{

	@Autowired
	private ScheduleContactsDao scheduleContactsDao;

	@Override
	protected BaseDao<ScheduleContacts, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.scheduleContactsDao;
	}


	

	
}
