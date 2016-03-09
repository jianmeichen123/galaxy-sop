package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;

public interface InterviewRecordService extends BaseService<InterviewRecord> {
	
	
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable);

	public Long insertInterview(InterviewRecord interviewRecord, Project project, MultipartFile file, String path,Long uid);
}