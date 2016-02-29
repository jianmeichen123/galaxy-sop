package com.galaxyinternet.dao.project;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.project.InterviewRecord;

public interface InterviewRecordDao extends BaseDao<InterviewRecord, Long> {
	
	public Page<InterviewRecordBo> selectInterviewPageList(InterviewRecordBo query, Pageable pageable);
	
}