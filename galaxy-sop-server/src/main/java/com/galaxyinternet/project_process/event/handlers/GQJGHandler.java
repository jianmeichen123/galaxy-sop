package com.galaxyinternet.project_process.event.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.dictEnum.DictEnum.fileWorktype;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
/**
 * 1 投资协议 -> 股权交割 前置条件判定，需要上传完成投资协议，同时判定该项目在“会后商务谈判”阶段的结论是“投资”，然后进入“股权交割”阶段
 * 2 投决会 -> 股权交割 前置条件判定，需要一条“通过”的会议记录，同时判定该项目在“会后商务谈判”阶段的结论是“闪投”，然后进入“股权交割”阶段。
 * @author wangsong
 *
 */
@Component
public class GQJGHandler implements ProgressChangeHandler
{
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private SopFileService fileService;

	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return (
				projectProgress.投资决策会.getCode().equals(event.getProject().getProjectProgress())
				|| projectProgress.投资协议.getCode().equals(event.getProject().getProjectProgress())
				)
				&& projectProgress.股权交割.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		String currProgress = project.getProjectProgress();
		UrlNumber num = UrlNumber.eleven;
		if(projectProgress.投资协议.getCode().equals(currProgress))
		{
			SopFile query = new SopFile();
			query.setProjectId(project.getId());
			query.setFileStatus(fileStatus.已上传.getCode());
			query.setFileWorktype(fileWorktype.投资协议.getCode());
			
			Long count = fileService.queryCount(query);
			if(count == null || count.intValue()<1)
			{
				throw new BusinessException(fileWorktype.投资协议.getName()+"未上传");
			}
		}
		else
		{
			num = UrlNumber.twelve;
			MeetingRecordBo query = new MeetingRecordBo();
			query.setProjectId(project.getId());
			query.setMeetingType(meetingType.投决会.getCode());
			query.setMeetingResult(meetingResult.通过.getCode());
			
			Long count = meetingService.queryCount(query);
			if(count.intValue()==0)
			{
				throw new BusinessException("没有通过的投决会会议记录");
			}
		}
		
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.股权交割.getCode());
		projectService.updateById(po);
		
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), num);
	}

}
