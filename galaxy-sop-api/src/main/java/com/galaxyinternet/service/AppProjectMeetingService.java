package com.galaxyinternet.service;

import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;

public interface AppProjectMeetingService {
	
	public void addToAudioFileByMeeting(Project p1, Long careerLine ,Long fileUid ,AppSopFile sopFile)throws Exception;
	
	
	//创意流程里的添加会议
	public void addIdeaFileByMeeting(Idea idea, Long careerLine ,Long fileUid ,AppSopFile sopFile)throws Exception;

}
