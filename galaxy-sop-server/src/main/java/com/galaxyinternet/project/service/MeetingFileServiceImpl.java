package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.MeetingFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.MeetingFile;
import com.galaxyinternet.service.MeetingFileService;

@Service("com.galaxyinternet.service.meetingFileService")
public class MeetingFileServiceImpl extends BaseServiceImpl<MeetingFile> implements MeetingFileService {

	@Autowired
	private MeetingFileDao meetingFileDao;
	
	@Override
	protected BaseDao<MeetingFile, Long> getBaseDao() {
		return this.meetingFileDao;
	}


}