package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.InterviewFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.InterviewFile;
import com.galaxyinternet.service.InterviewFileService;

@Service("com.galaxyinternet.service.interviewFileService")
public class InterviewFileServiceImpl extends BaseServiceImpl<InterviewFile> implements InterviewFileService {

	@Autowired
	private InterviewFileDao interviewFileDao;
	
	@Override
	protected BaseDao<InterviewFile, Long> getBaseDao() {
		return this.interviewFileDao;
	}


}