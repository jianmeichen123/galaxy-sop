package com.galaxyinternet.project_process.event.handlers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.dictEnum.DictEnum.titleIdResult;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationResultService;
/**
 * 进入会后商务谈判：前置条件判定，必须一条“闪投”或“投资”或“转向”结果的会议记录。然后进入会后商务谈判阶段。
 * @author wangsong
 *
 */
@Component
public class SWTPHandler implements ProgressChangeHandler
{
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	@Autowired
	private InformationResultService informationResultService;
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		// TODO Auto-generated method stub
		return projectProgress.立项会.getCode().equals(event.getProject().getProjectProgress()) 
				&& projectProgress.会后商务谈判.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		Project project = event.getProject();
		MeetingRecord hasPassedMeeting = hasPassedMeeting(project.getId());
		if(null==hasPassedMeeting)
		{
			throw new BusinessException("没有闪投\\投资\\转向”结果的会议记录");
		}else{
			InformationResult selectById=new InformationResult();
			selectById.setProjectId(project.getId().toString());
			selectById.setTitleId(titleIdResult.LX.getCode());
			String meetingResult = informationResultService.meetingResult(project.getId(), "LX",DictEnum.meetingType.立项会.getCode());
			if(!"".equals(meetingResult)){
				hasPassedMeeting.setMeetingResult(meetingResult);
			}
			String contentChoose="";
				if(hasPassedMeeting.getMeetingResult().equals(LXHResult.ST.getCode())){
					contentChoose=LXHResult.ST.getConnect();
				}else if(hasPassedMeeting.getMeetingResult().equals(LXHResult.TZ.getCode())){
					contentChoose=LXHResult.TZ.getConnect();
				}else if(hasPassedMeeting.getMeetingResult().equals(LXHResult.ZX.getCode())){
					contentChoose=LXHResult.ZX.getConnect();
				}
				informationResultService.updateOrInsert(selectById, contentChoose);
		}
		
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.会后商务谈判.getCode());
		po.setProgressHistory(project.getProgressHistory()+","+po.getProjectProgress());
		projectService.updateById(po);
		
		//修改立项会评审排期记录为完成
		MeetingScheduling m = new MeetingScheduling();
		m.setProjectId(project.getId());
		m.setMeetingType(DictEnum.meetingType.立项会.getCode());
		m.setStatus(DictEnum.meetingResult.通过.getCode());
		m.setUpdatedTime((new Date()).getTime());
		m.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
		meetingSchedulingDao.updateBySelective(m);
				
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), UrlNumber.four);

	}
	/**
	 * “闪投”或“投资”或“转向”结果的会议记录
	 * @param projectId
	 * @return
	 */
	private MeetingRecord hasPassedMeeting(Long projectId)
	{
		MeetingRecord result=new MeetingRecord();
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(projectId);
		query.setMeetingType(meetingType.立项会.getCode());
			query.setPassLXFlag("yes");
			query.setProjectType("meeting_date");
			query.setDirection("desc");
		List<MeetingRecord> queryList = meetingService.queryList(query);
		if(null!=queryList&&!queryList.isEmpty())
		{
			result=queryList.get(0);
			
		}
		
		return result;
	}

}
