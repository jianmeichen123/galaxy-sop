package com.galaxyinternet.project_process.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.Assert;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
@Component
public class RejectEventListener implements ApplicationListener<RejectEvent>
{
	@Autowired
	private InterviewRecordService interViewService;
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectService projectService;
	@Override
	public void onApplicationEvent(RejectEvent event)
	{
		Assert.notNull(event.getProject(), "Project can not be null");
		reject(event.getProject());
		
	}
	public void reject(Project project)
	{
		projectProgress progress = projectProgress.valueOf(project.getProgress());
		switch(progress)
		{
			case 接触访谈 :
				if(!hasRejectedInterview(project))
				{
					throw new BusinessException("没有否决的访谈记录");
				}
				break;
			case 内部评审:
			case CEO评审:
			case 立项会:
			case 投资决策会:
				if(!hasRejectedMeeting(project))
				{
					throw new BusinessException("没有否决的会议记录");
				}
				break;
			case 尽职调查:
				break;
			//case 会后商务谈判:
			
			default:
				break;
		}
		
		
	}
	private boolean hasRejectedInterview(Project project)
	{
		InterviewRecordBo query = new InterviewRecordBo();
		query.setProjectId(project.getId());
		return interViewService.selectCount(query) > 0l;
	}
	
	private boolean hasRejectedMeeting(Project project)
	{
		String type = null;
		if(projectProgress.内部评审.getClass().equals(project.getProgress()))
		{
			type = meetingType.内评会.getCode();
		}
		else if(projectProgress.CEO评审.getClass().equals(project.getProgress()))
		{
			type = meetingType.CEO评审.getCode();
		}
		else if(projectProgress.立项会.getClass().equals(project.getProgress()))
		{
			type = meetingType.立项会.getCode();
		}
		else if(projectProgress.投资决策会.getClass().equals(project.getProgress()))
		{
			type = meetingType.投决会.getCode();
		}
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(project.getId());
		query.setMeetingResult(meetingResult.否决.getCode());
		query.setMeetingType(type);
		return meetingService.selectCount(query) > 0l;
	}

}
