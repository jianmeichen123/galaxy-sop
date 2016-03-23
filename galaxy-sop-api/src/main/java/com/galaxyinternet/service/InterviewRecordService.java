package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;

public interface InterviewRecordService extends BaseService<InterviewRecord> {
	
	public Long insertInterview(InterviewRecord interviewRecord,SopFile sopFile);
	
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable);

	public Long insertFileForView(SopFile sopFile, InterviewRecord view);

}