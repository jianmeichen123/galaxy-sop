package com.galaxyinternet.scheduling;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;

@Component("pullMessageTask")
public class PullMessageTask extends BaseGalaxyTask{
	private final static Logger logger = LoggerFactory.getLogger(PullMessageTask.class);
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	Cache cache;
	
	@Override
	protected void executeInteral() throws BusinessException {
		// TODO Auto-generated method stub
		MeetingScheduling meeting = new MeetingScheduling();
		meeting.setScheduleStatus(1);
		try{
			logger.info("------------------定时拉取排期信息缓存开始start------------------------");
			cache.removeRedisKeyOBJ(Constants.PUSH_MESSAGE_LIST);
			List<MeetingScheduling> MeetingSchedulingList = meetingSchedulingService.queryList(meeting);
			if(StringUtils.isEmpty(MeetingSchedulingList)){
				return;
			}
			MeetingScheduling pullMessage;
		    for(MeetingScheduling meet : MeetingSchedulingList){
		    	Project pro = projectService.queryById(meet.getProjectId());
		    	if(!StringUtils.isEmpty(pro)){
		    		pullMessage = new MeetingScheduling();
			    	pullMessage.setId(meet.getId());
			    	pullMessage.setProjectId(meet.getProjectId());
			    	pullMessage.setMeetingType(meet.getMeetingType());
			    	pullMessage.setProjectName(pro.getProjectName());
			    	pullMessage.setCreateId(pro.getCreateUid().toString());
			    	pullMessage.setReserveTimeStart(meet.getReserveTimeStart());
			    	cache.setRedisSetOBJ(Constants.PUSH_MESSAGE_LIST,pullMessage);
		    	}
		    	
		    }
		    logger.info("------------------定时拉取排期信息缓存结束end------------------------");
		}catch(Exception e){
			logger.error("定时拉取排期时间信息错误!");
		}
	
	}

}
