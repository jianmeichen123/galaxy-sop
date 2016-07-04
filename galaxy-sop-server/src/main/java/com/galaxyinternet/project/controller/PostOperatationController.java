package com.galaxyinternet.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;

@Controller
@RequestMapping(value="/galaxy/project/postOperation")
public class PostOperatationController extends BaseControllerImpl<Project, ProjectBo> {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingRecordService meetingService;
	
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

}
