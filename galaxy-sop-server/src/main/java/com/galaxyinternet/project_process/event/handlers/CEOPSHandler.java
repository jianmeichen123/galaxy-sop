package com.galaxyinternet.project_process.event.handlers;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
/**
 * 申请CEO评审排期：前置条件判定，必须一条“通过”的会议记录。然后进入“CEO评审”阶段，同时触发一条会议排期申请
 * @author wangsong
 *
 */
@Component
public class CEOPSHandler implements ProgressChangeHandler
{
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private MeetingSchedulingService schedService;
	@Autowired
	private ProjectService projectService;
	
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.内部评审.getCode().equals(event.getProject().getProjectProgress()) 
				&& projectProgress.CEO评审.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		//验证会议状态
		Project project = event.getProject();
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(project.getId());
		query.setMeetingResult(meetingResult.通过.getCode());
		query.setMeetingType(meetingType.内评会.getCode());
		
		Long count = meetingService.queryCount(query);
		if(count.intValue() == 0)
		{
			throw new BusinessException("没有通过的会议记录");
		}
		
		//添加排期
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.立项会.getCode());
		ms.setMeetingCount(0);
		ms.setStatus(DictEnum.meetingResult.待定.getCode());
		ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		ms.setApplyTime(new Timestamp(new Date().getTime()));
		schedService.insert(ms);
		
		//更新状态
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.CEO评审.getCode());
		projectService.updateById(po);
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), "10.1", UrlNumber.two);

	}

}
