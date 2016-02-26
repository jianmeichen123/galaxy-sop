package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.service.MeetingRecordService;


@Service("com.galaxyinternet.service.meetingRecordService")
public class MeetingRecordServiceImpl extends BaseServiceImpl<MeetingRecord> implements MeetingRecordService {

	@Autowired
	private MeetingRecordDao meetingRecordDao;
	
	@Override
	protected BaseDao<MeetingRecord, Long> getBaseDao() {
		return this.meetingRecordDao;
	}


}