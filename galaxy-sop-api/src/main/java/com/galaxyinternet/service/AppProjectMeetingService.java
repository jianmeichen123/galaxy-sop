package com.galaxyinternet.service;

import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;

public interface AppProjectMeetingService {
	
	public void addToAudioFileByMeeting(Project p1, Long careerLine ,Long fileUid ,AppSopFile sopFile)throws Exception;
	
	/**
	 * 添加会议（新）功能方法
	 * @param meetingRecord
	 * @param project
	 * @param sopFile
	 * @return
	 * @throws Exception
	 */
	public Long addingMeeting(MeetingRecord meetingRecord,Project project,SopFile sopFile) throws Exception;
	
	
	//创意流程里的添加会议
	public void addIdeaFileByMeeting(MeetingRecord meetingRecord , Idea idea, Long careerLine ,Long fileUid ,AppSopFile sopFile)throws Exception;

}
