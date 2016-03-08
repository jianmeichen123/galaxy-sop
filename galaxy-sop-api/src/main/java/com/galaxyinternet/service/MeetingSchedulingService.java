package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingScheduling;

public interface MeetingSchedulingService extends BaseService<MeetingScheduling> {
	
	public int updateCountBySelective(MeetingScheduling entity);
	/**
	 * 立项会排期top5列表
	 * @author zhaoying
	 * @return
	 */
	List<MeetingSchedulingBo>selectTop5ProjectMeeting();
	
	/**
	 * 立项会排期 all
	 * @author zhaoying
	 * @return
	 */
	List<MeetingSchedulingBo>selectMoreProjectMeeting();
}