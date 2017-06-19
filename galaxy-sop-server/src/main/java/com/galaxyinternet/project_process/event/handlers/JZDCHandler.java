package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.dictEnum.DictEnum.fileWorktype;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
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
		SopFile file = fileService.queryOne(query);
		
		if(file == null || fileStatus.缺失.getCode().equals(file.getFileStatus()))
		{
			throw new BusinessException(fileWorktype.getNameByCode(type)+"缺失");
		}
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.尽职调查.getCode());
		projectService.updateById(po);
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), num);

	}

}
