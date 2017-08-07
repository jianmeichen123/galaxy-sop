package com.galaxyinternet.project_process.event.handlers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopTaskService;
/**
 * 投决会->投资协议，前置条件判定，需要一条“通过”的会议记录，同时判定该项目在“会后商务谈判”阶段的结论是“投资”，然后进入“投资协议”阶段
 * @author wangsong
 *
 */
@Component
public class TZXYHandler implements ProgressChangeHandler
{
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private SopTaskService taskService;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.投资决策会.getCode().equals(event.getProject().getProjectProgress())
				&& projectProgress.投资协议.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		//验证会议状态
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(project.getId());
		query.setMeetingResult(meetingResult.通过.getCode());
		query.setMeetingType(meetingType.投决会.getCode());
		
		Long count = meetingService.queryCount(query);
		if(count.intValue() == 0)
		{
			throw new BusinessException("没有通过的会议记录");
		}
		//判定该项目在“会后商务谈判”阶段的结论是“投资”
		if(!SopConstant.BUSINESS_TYPE_TZ.equals(project.getBusinessTypeCode()))
		{
			throw new BusinessException("会后商务谈判”阶段的结论不是“投资”");
		}
		
		//待办任务 - 上传投资协议、股权转让协议
		SopTask task = new SopTask();
		task.setProjectId(project.getId());
		task.setTaskName(SopConstant.TASK_NAME_TZXY);
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_TZXY);
		task.setTaskOrder(SopConstant.NORMAL_STATUS);
		task.setDepartmentId(project.getProjectDepartid());
		task.setAssignUid(project.getCreateUid());
		task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
		task.setCreatedTime(System.currentTimeMillis());
		taskService.insert(task);
		
		
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.投资协议.getCode());
		po.setProgressHistory(project.getProgressHistory()+","+po.getProjectProgress());
		projectService.updateById(po);
		
		//修改投决会评审排期记录为完成
		MeetingScheduling m = new MeetingScheduling();
		m.setProjectId(project.getId());
		m.setMeetingType(DictEnum.meetingType.投决会.getCode());
		m.setStatus(DictEnum.meetingResult.通过.getCode());
		m.setUpdatedTime((new Date()).getTime());
		m.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
		meetingSchedulingDao.updateBySelective(m);
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), UrlNumber.ten);

	}

}
