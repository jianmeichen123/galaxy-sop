package com.galaxyinternet.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.MeetingRecordService;

@Controller
@RequestMapping("/galaxy/meeetingRecord")
public class MeetingController extends BaseControllerImpl<MeetingRecord, MeetingRecordBo> {
	
	final Logger logger = LoggerFactory.getLogger(MeetingController.class);
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<MeetingRecord> getBaseService() {
		return this.meetingRecordService;
	}
	
	
	
	/**
	 * 添加会议记录   判断会议结论，更新项目进度，启动关联任务
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(HttpServletRequest request,@RequestBody MeetingRecord meetingRecord ) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		//String sessionId = request.getHeader("sessionID");
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		if(meetingRecord.getProjectId() == null 
				|| meetingRecord.getMeetingDate() == null 
				|| meetingRecord.getMeetingType() == null 
				|| meetingRecord.getMeetingResult() == null 
				|| meetingRecord.getMeetingNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR, "meetingRecord info not complete"));
			return responseBody;
		}
		
		try {
			Long id = meetingRecordService.insertMeet(meetingRecord, user.getId());
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "add meetingRecord faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("add meetingRecord faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	/**
	 * 查询个人项目下的会议记录
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMeet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecordBo> queryMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ,PageRequest pageable ) {
		
		ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
		
		//String sessionId = request.getHeader("sessionID");
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		try {
			query.setUid(user.getId());
			
			Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPageList(query, pageable);
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "queryMeetPageList faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
}
