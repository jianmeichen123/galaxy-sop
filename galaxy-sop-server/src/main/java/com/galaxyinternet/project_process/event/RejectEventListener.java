package com.galaxyinternet.project_process.event;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.Assert;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.NBPSResult;
import com.galaxyinternet.common.dictEnum.DictEnum.SWTPResult;
import com.galaxyinternet.common.dictEnum.DictEnum.TJHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.fileWorktype;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
@Component
public class RejectEventListener implements ApplicationListener<RejectEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(RejectEventListener.class);
	@Autowired
	private InterviewRecordService interViewService;
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopFileService fileService;
	@Override
	public void onApplicationEvent(RejectEvent event)
	{
		Assert.notNull(event.getProject(), "Project can not be null");
		if(logger.isDebugEnabled())
		{
			logger.debug(String.format("RejectEvent received, project=", event.getProject().toString()));
		}
		reject(event.getProject());
		
	}
	private void reject(Project project)
	{
		projectProgress progress = projectProgress.valueOf(project.getProgress());
		switch(progress)
		{
			case 接触访谈 :
				if(!hasRejectedInterview(project))
				{
					throw new BusinessException("没有否决的访谈记录");
				}
				break;
			case 内部评审:
			case CEO评审:
			case 立项会:
			case 投资决策会:
			case 会后商务谈判:
				if(!hasRejectedMeeting(project))
				{
					throw new BusinessException("没有否决的会议记录");
				}
				break;
			case 尽职调查:
				/*if(!hasAllFiles(project))
				{
					throw new BusinessException("文档不齐全");
				}*/
				break;
			
			default:
				throw new BusinessException("参数错误");
		}
		//删除待排期记录
		MeetingScheduling query = new MeetingScheduling();
		query.setProjectId(project.getId());
		query.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		meetingSchedulingDao.delete(query);
		//更新项目状态、任务状态
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectStatus(DictEnum.projectStatus.YFJ.getCode());
		
		projectService.closeProject(po);
		
	}
	private boolean hasRejectedInterview(Project project)
	{
		InterviewRecordBo query = new InterviewRecordBo();
		query.setProjectId(project.getId());
		query.setInterviewResult(meetingResult.否决.getCode());
		return interViewService.selectCount(query) > 0l;
	}
	
	private boolean hasRejectedMeeting(Project project)
	{
		String type = null;
		String result = meetingResult.否决.getCode();
		if(projectProgress.内部评审.getName().equals(project.getProgress()))
		{
			type = meetingType.内评会.getCode();
			result=NBPSResult.FJ.getCode();
		}
		else if(projectProgress.CEO评审.getName().equals(project.getProgress()))
		{
			type = meetingType.CEO评审.getCode();
		}
		else if(projectProgress.立项会.getName().equals(project.getProgress()))
		{
			type = meetingType.立项会.getCode();
			result = LXHResult.FJ.getCode();
		}
		else if(projectProgress.会后商务谈判.getName().equals(project.getProgress()))
		{
			type = meetingType.会后商务谈判.getCode();
			result = SWTPResult.FJ.getCode();
		}
		else if(projectProgress.投资决策会.getName().equals(project.getProgress()))
		{
			type = meetingType.投决会.getCode();
			result = TJHResult.FJ.getCode();
		}
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(project.getId());
		query.setMeetingResult(result);
		query.setMeetingType(type);
		return meetingService.selectCount(query) > 0l;
	}
	
	private boolean hasAllFiles(Project project)
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
			return false;
		}
		return true;
	}

}
