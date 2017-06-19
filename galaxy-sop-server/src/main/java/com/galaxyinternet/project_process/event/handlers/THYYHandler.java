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
 * 股权交割 -> 进入投后运营：前置条件判定，需要上传完成工商转让凭证，然后进入“投后运营”阶段
 * @author wangsong
 *
 */
@Component
public class THYYHandler implements ProgressChangeHandler
{
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopFileService fileService;
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.股权交割.getCode().equals(event.getProject().getProjectProgress())
				&& projectProgress.投后运营.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		SopFile query = new SopFile();
		query.setProjectId(project.getId());
		query.setFileStatus(fileStatus.已上传.getCode());
		query.setFileWorktype(fileWorktype.工商转让凭证.getCode());
		Long count = fileService.queryCount(query);
		if(count == null || count == 0)
		{
			throw new BusinessException(fileWorktype.工商转让凭证.getName()+"未上传");
		}
		
		Project po = new Project();
		po.setId(po.getId());
		po.setProjectProgress(projectProgress.投后运营.getCode());
		projectService.updateById(po);
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), UrlNumber.thirteen);

	}

}
