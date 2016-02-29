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

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.InterviewRecordService;

@Controller
@RequestMapping("/galaxy/interviewRecord")
public class InterviewController extends BaseControllerImpl<InterviewRecord, InterviewRecordBo> {
	
	final Logger logger = LoggerFactory.getLogger(InterviewController.class);
	
	@Autowired
	private InterviewRecordService interviewRecordService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<InterviewRecord> getBaseService() {
		return this.interviewRecordService;
	}
	
	
	
	/**
	 * 访谈添加
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addInterview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(HttpServletRequest request,@RequestBody InterviewRecord interviewRecord ) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		if(interviewRecord.getProjectId() == null 
				|| interviewRecord.getViewDate() == null 
				|| interviewRecord.getViewTarget() == null
				|| interviewRecord.getViewNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR, "interviewRecord info not complete"));
			return responseBody;
		}
		
		try {
			Long id = interviewRecordService.insert(interviewRecord);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "insert interviewRecord faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("insert interviewRecord faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	/**
	 * 访谈查询,查询个人项目下的访谈记录   to query interviewRecord
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInterview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecordBo> queryInterview(HttpServletRequest request,@RequestBody InterviewRecordBo query ,PageRequest pageable ) {
		
		ResponseData<InterviewRecordBo> responseBody = new ResponseData<InterviewRecordBo>();
		
		//String sessionId = request.getHeader("sessionID");
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		try {
			query.setUid(user.getId());
			
			Page<InterviewRecordBo> pageList = interviewRecordService.queryInterviewPageList(query, pageable);
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "queryInterviewPageList faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
}
