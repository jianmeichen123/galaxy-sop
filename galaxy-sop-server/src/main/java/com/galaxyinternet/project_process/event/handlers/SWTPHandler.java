package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
/**
 * 进入会后商务谈判：前置条件判定，必须一条“闪投”或“投资”或“转向”结果的会议记录。然后进入会后商务谈判阶段。
 * @author wangsong
 *
 */
@Component
public class SWTPHandler implements ProgressChangeHandler
{
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectService projectService;
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		// TODO Auto-generated method stub
		return projectProgress.立项会.getCode().equals(event.getProject().getProjectProgress()) 
				&& projectProgress.会后商务谈判.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		if(!hasPassedMeeting(project.getId()))
		{
			throw new BusinessException("没有闪投\\投资\\转向”结果的会议记录");
		}
		
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.会后商务谈判.getCode());
		po.setProgressHistory(project.getProgressHistory()+","+po.getProjectProgress());
		projectService.updateById(po);
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), UrlNumber.four);

	}
	/**
	 * “闪投”或“投资”或“转向”结果的会议记录
	 * @param projectId
	 * @return
	 */
	private boolean hasPassedMeeting(Long projectId)
	{
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(projectId);
		query.setMeetingType(meetingType.立项会.getCode());
		query.setMeetingResult(LXHResult.ST.getCode());
		
		Long count = meetingService.queryCount(query);
		if(count.intValue()>0)
		{
			return true;
		}
		
		query.setMeetingResult(LXHResult.TZ.getCode());
		count = meetingService.queryCount(query);
		if(count.intValue()>0)
		{
			return true;
		}
		
		query.setMeetingResult(LXHResult.ZX.getCode());
		count = meetingService.queryCount(query);
		if(count.intValue()>0)
		{
			return true;
		}
		
		return false;
	}

}
