package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum.projectStatus;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
/**
 * 启动内部评审：前置条件判定，需要有一条“通过”的访谈记录，然后进入”内部评审“阶段
 * @author wangsong
 *
 */
@Component
public class NBPSHandler implements ProgressChangeHandler
{
	@Autowired
	private InterviewRecordService interViewService;
	@Autowired
	private ProjectService projectService;
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.接触访谈.getCode().equals(event.getProject().getProjectProgress()) 
				&& projectProgress.内部评审.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		if(!hasPassedInterview(event.getProject()))
		{
			throw new BusinessException("没有通过的访谈记录");
		}
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.内部评审.getCode());
		po.setProjectStatus(projectStatus.GJZ.getCode());
		projectService.updateById(po);
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(),StageChangeHandler._6_2_, UrlNumber.one);

	}
	
	private boolean hasPassedInterview(Project project)
	{
		InterviewRecordBo query = new InterviewRecordBo();
		query.setProjectId(project.getId());
		query.setInterviewResult(meetingResult.通过.getCode());
		return interViewService.selectCount(query) > 0l;
	}

}
