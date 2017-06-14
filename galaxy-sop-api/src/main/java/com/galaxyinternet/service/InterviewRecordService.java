package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.sopfile.SopFile;

public interface InterviewRecordService extends BaseService<InterviewRecord> {
	
	public Long insertInterview(InterviewRecord interviewRecord,SopFile sopFile);
	
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable);

	public Long updateViewForFile(SopFile sopFile, InterviewRecord view);

	public Page<InterviewRecordBo> queryInterviewPage(InterviewRecordBo query, Pageable pageable);
	
	/**
	 * 项目移交修改create_uid
	 */
	public int updateCreateUid(InterviewRecord ir);
	
	Long selectCount(InterviewRecordBo query);

}