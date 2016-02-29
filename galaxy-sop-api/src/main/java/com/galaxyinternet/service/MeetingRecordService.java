package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingRecord;

public interface MeetingRecordService extends BaseService<MeetingRecord> {

	public Long insertMeet(MeetingRecord meetingRecord,Long userId);
	
	public Page<MeetingRecordBo> queryMeetPageList(MeetingRecordBo query, Pageable pageable);

}