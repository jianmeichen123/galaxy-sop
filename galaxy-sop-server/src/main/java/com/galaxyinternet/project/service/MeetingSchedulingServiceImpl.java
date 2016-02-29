package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.service.MeetingSchedulingService;


@Service("com.galaxyinternet.service.MeetingSchedulingService")
public class MeetingSchedulingServiceImpl extends BaseServiceImpl<MeetingScheduling> implements MeetingSchedulingService {

	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	
	@Override
	protected BaseDao<MeetingScheduling, Long> getBaseDao() {
		return this.meetingSchedulingDao;
	}

	@Override
	public int updateCountBySelective(MeetingScheduling entity) {
		return meetingSchedulingDao.updateCountBySelective(entity);
	}

}