package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;

public interface InterviewRecordService extends BaseService<InterviewRecord> {
	
	
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable);
}