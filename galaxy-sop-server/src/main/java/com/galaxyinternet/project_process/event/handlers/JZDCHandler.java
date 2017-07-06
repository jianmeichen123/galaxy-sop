package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.dictEnum.DictEnum.fileWorktype;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
/**
 * 1.投资意向书->进入尽职调查：前置条件判定，需要上传完成投资意向书，然后进入“尽职调查”阶段
 * 2.投资协议-> 进入尽职调查：前置条件判定，需要上传完成投资协议，同时判定该项目在“会后商务谈判”阶段的结论是“闪投”，然后进入“尽职调查”阶段
 * @author wangsong
 *
 */
@Component
public class JZDCHandler implements ProgressChangeHandler
{
	@Autowired
	private SopFileService fileService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopTaskService taskService;
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return (
				projectProgress.投资意向书.getCode().equals(event.getProject().getProjectProgress())
				|| projectProgress.投资协议.getCode().equals(event.getProject().getProjectProgress())
				)
				&& projectProgress.尽职调查.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		String type = fileWorktype.投资意向书.getCode();
		UrlNumber num = UrlNumber.seven;
		if(projectProgress.投资协议.getCode().equals(project.getProjectProgress()))
		{
			type = fileWorktype.投资协议.getCode();
			num = UrlNumber.eight;
		}
		SopFile query = new SopFile();
		query.setProjectId(project.getId());
		query.setFileWorktype(type);
		query.setFileStatus(fileStatus.已上传.getCode());
		query.setFileValid(1);//查询有效文件
		Long count = fileService.queryCount(query);
		
		if(count == 0l)
		{
			throw new BusinessException(fileWorktype.getNameByCode(type)+"缺失");
		}
		long now = System.currentTimeMillis();
		
		//待办任务 - 上传业务尽职调查报告
		SopTask task = new SopTask();
		task.setProjectId(project.getId());
		task.setTaskName(SopConstant.TASK_NAME_YWJD);
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_YWJD);
		task.setAssignUid(project.getCreateUid());
		task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
		task.setDepartmentId(project.getProjectDepartid());
		task.setCreatedTime(now);
		taskService.insert(task);
		
		//待办任务 - 上传人事尽职调查报告
		task = new SopTask();
		task.setProjectId(project.getId());
		task.setAssignUid(null);
		task.setTaskName(SopConstant.TASK_NAME_RSJD);
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_RSJD);
		task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
		task.setDepartmentId(SopConstant.DEPARTMENT_RS_ID);
		task.setCreatedTime(now);
		taskService.insert(task);
		
		//待办任务 - 上传法务尽职调查报告
		task = new SopTask();
		task.setProjectId(project.getId());
		task.setAssignUid(null);
		task.setTaskName(SopConstant.TASK_NAME_FWJD);
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_FWJD);
		task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
		task.setDepartmentId(SopConstant.DEPARTMENT_FW_ID);
		task.setCreatedTime(now);
		taskService.insert(task);
		
		//待办任务 - 上传财务尽职调查报告
		task = new SopTask();
		task.setProjectId(project.getId());
		task.setAssignUid(null);
		task.setTaskName(SopConstant.TASK_NAME_CWJD);
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_CWJD);
		task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
		task.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);
		task.setCreatedTime(now);
		taskService.insert(task);
		
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.尽职调查.getCode());
		po.setProgressHistory(project.getProgressHistory()+","+po.getProjectProgress());
		projectService.updateById(po);
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), num);

	}

}
