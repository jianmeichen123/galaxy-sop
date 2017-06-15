package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
/**
 * 请立项会排期：前置条件判定，必须一条“通过”的会议记录。然后进入“立项会“阶段，同时触发一条会议排期申请。
 * @author wangsong
 *
 */
@Component
public class LXHHandler implements ProgressChangeHandler
{
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectService projectService;
	
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.CEO评审.getCode().equals(event.getProject().getProjectProgress())
				&& projectProgress.立项会.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		try
		{
			//验证会议状态
			MeetingRecordBo query = new MeetingRecordBo();
			query.setProjectId(project.getId());
			query.setMeetingResult(meetingResult.通过.getCode());
			query.setMeetingType(meetingType.CEO评审.getCode());
			
			Long count = meetingService.queryCount(query);
			if(count.intValue() == 0)
			{
				throw new BusinessException("没有通过的会议记录");
			}
			Project po = new Project();
			po.setId(project.getId());
			projectService.toEstablishStage(po);
		} catch (Exception e)
		{
			throw new BusinessException(e);
		}
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), StageChangeHandler._6_4_, UrlNumber.three);
	}

}
