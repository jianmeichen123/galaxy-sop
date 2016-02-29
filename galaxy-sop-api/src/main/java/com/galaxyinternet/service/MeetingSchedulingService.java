package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingScheduling;

public interface MeetingSchedulingService extends BaseService<MeetingScheduling> {
	
	public int updateCountBySelective(MeetingScheduling entity);

}