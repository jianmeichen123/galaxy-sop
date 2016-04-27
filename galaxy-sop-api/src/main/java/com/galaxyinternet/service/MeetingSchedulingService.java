package com.galaxyinternet.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingScheduling;

public interface MeetingSchedulingService extends BaseService<MeetingScheduling> {
	
	public int updateCountBySelective(MeetingScheduling entity);
	/**
	 * 排期top5列表
	 * @author zhaoying
	 * @return
	 */
	List<MeetingSchedulingBo>selectTop5ProjectMeetingByType(String type);
	
	/**
	 * 排期 all
	 * @author zhaoying
	 * @return
	 */
	List<MeetingSchedulingBo>selectProjectMeetingByType(String type);
	/**
	 * 分页查询 供app调用
	 * @author zhaoying
	 * @param query
	 * @param pageable
	 * @return
	 */
	Page<MeetingScheduling> queryMeetingPageList(MeetingScheduling query, Pageable pageable);
	
	/**
	 * 秘书排期池
	 * @param ms
	 * @return
	 */
	public Page<MeetingScheduling> queryMeetPageList(MeetingSchedulingBo query,Pageable pageable);
	
	
	int updateBySelective(MeetingScheduling ms);
	
}