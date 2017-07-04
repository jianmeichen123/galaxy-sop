package com.galaxyinternet.project_process.event.handlers;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum.fileWorktype;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
/**
 * 申请投决会排期：前置条件判定，需要上传完成业务尽调报告、人事尽调报告、法务尽调报告、财务尽调报告、尽调启动报告、尽调总结会报告，然后进入“投决会”阶段，同时触发一条会议排期申请。
 * @author wangsong
 *
 */
@Component
public class TJHHandler implements ProgressChangeHandler
{

	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopFileService fileService;
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.尽职调查.getCode().equals(event.getProject().getProjectProgress())
				&& projectProgress.投资决策会.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		try
		{
			String[] typeList = {
					fileWorktype.业务尽职调查报告.getCode(), 
					fileWorktype.人力资源尽职调查报告.getCode(), 
					fileWorktype.法务尽职调查报告.getCode(),
					fileWorktype.财务尽职调查报告.getCode(),
					fileWorktype.尽职调查启动会报告.getCode(),
					fileWorktype.尽职调查总结会报告.getCode()
					
			};
			SopFile query = new SopFile();
			query.setProjectId(project.getId());
			query.setFileworktypeList(Arrays.asList(typeList));
			query.setFileStatus(fileStatus.已上传.getCode());
			
			Long count = fileService.queryCount(query);
			if(count == null || count.intValue()<6)
			{
				throw new BusinessException("文档不齐全，不能申请投决会!");
			}
			Project po = new Project();
			po.setId(project.getId());
			po.setProgressHistory(project.getProgressHistory()+","+projectProgress.投资决策会.getCode());
			projectService.toSureMeetingStage(po);
			
			
		} 
		catch (Exception e)
		{
			throw new BusinessException(e.getMessage(),e);
		}
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), StageChangeHandler._6_7_,UrlNumber.nine);
	}
}
