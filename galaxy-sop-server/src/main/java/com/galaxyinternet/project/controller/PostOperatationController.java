package com.galaxyinternet.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.touhou.ProjectHealthBo;
import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectHealthService;
import com.galaxyinternet.service.ProjectService;

@Controller
@RequestMapping(value="/galaxy/project/postOperation")
public class PostOperatationController extends BaseControllerImpl<Project, ProjectBo> {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectHealthService projectHealthService;
	@Autowired
	private DeliveryService deliveryService;
	
	@Override
	protected BaseService<Project> getBaseService() {
		// TODO Auto-generated method stub
		return projectService;
	}
	
	
	/**
	 * 分页查询透后运营会议记录
	 * @param meetingRecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryMeeting", method=RequestMethod.POST ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> queryMeeting(MeetingRecord meetingRecord){
		return null;
		
	}
	
	/**
	 * 新建编辑投后会议记录
	 * @param request
	 * @param meetingRecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveMeeting", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> saveMeeting(HttpServletRequest request, MeetingRecord meetingRecord){
		return null;
	}
	/**
	 * 投后运营信息
	 * @param request
	 * @param meetingRecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getThyyInfo")
	public ResponseData<MeetingRecord> getThyyInfo(Long projectId)
	{
		
		ResponseData<MeetingRecord> data = new ResponseData<MeetingRecord>();
		
		//运营状态-默认为正常(2)
		Byte healthState = (byte)2;
		ProjectHealthBo healthQuery = new ProjectHealthBo();
		healthQuery.setProjectId(projectId);
		PageRequest healthPageable = new PageRequest(0,1, new Sort(Direction.DESC,"created_time"));
		List<ProjectHealth> healthList = projectHealthService.queryList(healthQuery, healthPageable);
		if(healthList != null && healthList.size() >0)
		{
			ProjectHealth health = healthList.iterator().next();
			if(health != null && health.getHealthState() != null)
			{
				healthState = health.getHealthState();
			}
		}
		data.putAttachmentItem("healthState", healthState);
		
		//交割前事项
		Delivery deliveryQuery = new Delivery();
		deliveryQuery.setProjectId(projectId);
		Long total = deliveryService.queryCount(deliveryQuery);
		deliveryQuery.setStatus((byte) 1);
		Long complete = deliveryService.queryCount(deliveryQuery);
		
		data.putAttachmentItem("total",total);
		data.putAttachmentItem("complete",complete);
		
		//运营会议
		PageRequest pageable = new PageRequest();
		pageable = new PageRequest(0, 3);
		MeetingRecord meetQuery = new MeetingRecord();
		meetQuery.setProjectId(projectId);
		meetQuery.setRecordType(RecordType.OPERATION_MEETING.getType());
		List<MeetingRecord> list = meetingService.queryList(meetQuery,pageable);
		data.setEntityList(list);
		
		return data;
	}

}
