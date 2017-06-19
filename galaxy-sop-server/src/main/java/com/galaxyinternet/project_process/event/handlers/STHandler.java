package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
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
 * 会后商务谈判->签订投资协议书（闪投）：前置条件判定，必须一条“闪投”结果的会议记录。然后进入“投资协议”阶段
 * @author wangsong
 *
 */
public class STHandler implements ProgressChangeHandler
{

	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectService projectService;
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.会后商务谈判.getCode().equals(event.getProject().getProjectProgress())
				&& projectProgress.投资协议.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = new Project();
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(project.getId());
		query.setMeetingResult(LXHResult.ST.getCode());
		
		Long count = meetingService.queryCount(query);
		if(count.intValue()==0)
		{
			throw new BusinessException("没有闪投的会议记录");
		}
		
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.投资协议.getCode());
		projectService.updateById(po);
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), UrlNumber.six);

	}

}
