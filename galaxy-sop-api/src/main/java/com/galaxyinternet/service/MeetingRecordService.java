package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;



public interface MeetingRecordService extends BaseService<MeetingRecord> {

	//public Long insertMeet(MeetingRecord meetingRecord,Project project,Long userid,Long departid);
	public Long insertMeet(MeetingRecord meetingRecord, Project project, MultipartFile file, String path, Long id,Long departmentId,boolean equalNowPrograss);
	
	public Page<MeetingRecordBo> queryMeetPageList(MeetingRecordBo query, Pageable pageable);
	
	public void projectSchedule(Project project);

	public void upTermSheetSign(Project project,Long userid,Long departid);
	
	public void decisionSchedule(Project project);
	
	public void upInvestmentSign(Project project);



}