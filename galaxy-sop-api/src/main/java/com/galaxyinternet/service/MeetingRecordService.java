package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;



public interface MeetingRecordService extends BaseService<MeetingRecord> {

	Long insertMeet(MeetingRecordBo meetingRecord, Project project, SopFile sopFile, boolean equalNowPrograss);
	
	public Page<MeetingRecordBo> queryMeetPageList(MeetingRecordBo query, Pageable pageable);

	public void upTermSheetSign(Project project, Long id, Long departmentId);

	public Page<MeetingRecordBo> queryMeetPage(MeetingRecordBo query, Pageable pageable);

	Long addCyMeetRecord(MeetingRecord meetingRecord, SopFile sopFile);
	
	/**
	 * @param query
	 * @return
	 * 根据项目及类型查询会议名称的序号
	 */
	public Long queryMeetNumberByType(MeetingRecord query);
	/**
	 * 删除投后运营会议
	 * @param id
	 * @return
	 */
	public int deletePostMeetingById(Long id);
	
	public Long insertMeeting(MeetingRecord query);
	
	public boolean saveMeeting(MeetingRecord query,Long userId);


}