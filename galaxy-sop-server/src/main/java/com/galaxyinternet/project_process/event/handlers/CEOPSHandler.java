package com.galaxyinternet.project_process.event.handlers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.dictEnum.DictEnum.NBPSResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.dictEnum.DictEnum.titleIdResult;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationResultService;
/**
 * 申请CEO评审排期：前置条件判定，必须一条“通过”的会议记录。然后进入“CEO评审”阶段，同时触发一条会议排期申请
 * @author wangsong
 *
 */
@Component
public class CEOPSHandler implements ProgressChangeHandler
{
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private MeetingSchedulingService schedService;
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private InformationResultService informationResultService;
	
	
	@Override
	public boolean support(ProgressChangeEvent event)
	{
		return projectProgress.内部评审.getCode().equals(event.getProject().getProjectProgress()) 
				&& projectProgress.CEO评审.equals(event.getNext());
	}

	@Override
	public void handler(ProgressChangeEvent event)
	{
		//验证会议状态
		Project project = event.getProject();
		MeetingRecordBo query = new MeetingRecordBo();
		query.setProjectId(project.getId());
		//query.setMeetingResult(NBPSResult.ST.getCode());
		query.setMeetingType(meetingType.内评会.getCode());
		//查询闪投跟投资的会议
		query.setPassNPFlag("yes");
		query.setProperty("meeting_date");
		query.setDirection("desc");
	    //Long count = meetingService.queryCount(query);
		//将原来的查询个数修改为查询list
		List<MeetingRecord> queryList = meetingService.queryList(query);
		MeetingRecord mr=new MeetingRecord();
		if(null==queryList||queryList.isEmpty())
		{
			throw new BusinessException("没有通过的会议记录");
		}else{
		  //更新状态报告里面的状态
			InformationResult selectById=new InformationResult();
			selectById.setProjectId(project.getId().toString());
			String meetingResult = informationResultService.meetingResult(project.getId(), "NP",meetingType.内评会.getCode());
			if(!"".equals(meetingResult)){
				mr.setMeetingResult(meetingResult);
			}
			selectById.setTitleId(titleIdResult.NP.getCode());
			String contentChoose="";
				mr=queryList.get(0);
				if(null!=mr){
					if(mr.getMeetingResult().equals(NBPSResult.ST.getCode())){
						contentChoose=NBPSResult.ST.getConnect();
					}else if(mr.getMeetingResult().equals(NBPSResult.TZ.getCode())){
						contentChoose=NBPSResult.TZ.getConnect();
					}
					informationResultService.updateOrInsert(selectById, contentChoose);
				}
		}
		
		//添加排期
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
		ms.setMeetingCount(0);
		ms.setStatus(DictEnum.meetingResult.待定.getCode());
		ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		ms.setApplyTime(new Timestamp(new Date().getTime()));
		schedService.insert(ms);
		
		//更新状态
		Project po = new Project();
		po.setId(project.getId());
		po.setProjectProgress(projectProgress.CEO评审.getCode());
		po.setProgressHistory(project.getProgressHistory()+","+po.getProjectProgress());
		projectService.updateById(po);
		HttpServletRequest request = WebUtils.getRequest();
		ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), UrlNumber.two);

	}

}
