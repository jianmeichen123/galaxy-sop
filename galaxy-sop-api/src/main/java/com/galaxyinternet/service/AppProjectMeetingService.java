package com.galaxyinternet.service;

import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;

public interface AppProjectMeetingService {
	
	public void addToAudioFileByMeeting(Project p1, Long careerLine ,Long fileUid ,AppSopFile sopFile)throws Exception;

}
