package com.galaxyinternet.service.hologram;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.project.MeetingRecord;

public interface InformationResultService extends BaseService<InformationResult>{
	
	public int upResultByMeeting(MeetingRecord mr);
	
	public void updateOrInsert(InformationResult selectById,String contentChoose);

}
