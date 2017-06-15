package com.galaxyinternet.project_process.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;


/**
 * 访谈、
 * 内部评审、 
 * CEO评审 、
 * 立项会、
 * 商务谈判、
 * 投资意向书、
 * 尽职调查、
 * 投决会 、
 * 投资协议 、
 * 股权交割、
 * 投后运营
 */
@Controller
@RequestMapping("/galaxy/progress")
public class ProjectFlowController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(ProjectFlowController.class);
	
	private String tempfilePath;
	public String getTempfilePath() { return tempfilePath; }
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) { this.tempfilePath = tempfilePath; }
	
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	
	@Autowired
	private InterviewRecordService interviewRecordService;
	
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	
	@Autowired
	private  SopFileService sopFileService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	


	// TODO : 访谈
	/**
	 * 访谈页面
	 */
	@RequestMapping(value = "/p1", method = RequestMethod.GET)
	public String p1() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p1/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p1_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	

	
	// TODO : 内部评审
	/**
	 * 内部评审页面
	 */
	@RequestMapping(value = "/p2", method = RequestMethod.GET)
	public String p2() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p2/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p2_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	
	

	
	
	// TODO : CEO评审 
	/**
	 * CEO评审 
	 */
	@RequestMapping(value = "/p3", method = RequestMethod.GET)
	public String p3() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p3/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p3_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	

	
	// TODO : 立项会
	/**
	 * 立项会
	 */
	@RequestMapping(value = "/p4", method = RequestMethod.GET)
	public String p4() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p4/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p4_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	

	// TODO : 商务谈判
	/**
	 * 商务谈判
	 */
	@RequestMapping(value = "/p5", method = RequestMethod.GET)
	public String p5() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p5/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p5_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	
	

	
	// TODO : 投资意向书
	/**
	 * 投资意向书
	 */
	@RequestMapping(value = "/p6", method = RequestMethod.GET)
	public String p6() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p6/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p6_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	
	

	
	// TODO : 尽职调查
	/**
	 * 尽职调查
	 */
	@RequestMapping(value = "/p7", method = RequestMethod.GET)
	public String p7() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p7/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p7_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	
	
	

	// TODO : 投决会
	/**
	 * 投决会
	 */
	@RequestMapping(value = "/p8", method = RequestMethod.GET)
	public String p8() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p8/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p8_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	

	
	// TODO : 投资协议
	/**
	 * 投资协议
	 */
	@RequestMapping(value = "/p9", method = RequestMethod.GET)
	public String p9() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p9/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p9_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	

	
	// TODO : 股权交割
	/**
	 * 股权交割
	 */
	@RequestMapping(value = "/p10", method = RequestMethod.GET)
	public String p10() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p10/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p10_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	
	
	
	
	
	// TODO : 投后运营
	/**
	 * 投后运营
	 */
	@RequestMapping(value = "/p11", method = RequestMethod.GET)
	public String p11() {
		return "view";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/p11/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> p11_add(HttpServletRequest request,HttpServletResponse response ) 
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		
		
		return responseBody;
	}
	
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	@ResponseBody
	@RequestMapping(value = "/reject", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> reject(@RequestBody OperationLogs param, HttpServletRequest request)
	{
		ResponseData<Project> data = new ResponseData<Project>();
		try
		{
			Project project = projectService.queryById(param.getProjectId());
			projectService.reject(param.getProjectId());
			ControllerUtils.setRequestParamsForMessageTip(request,project.getProjectName(), project.getId(),null, false, null, param.getReason(), null);
		} catch (Exception e)
		{
			data.setResult(new Result(Status.ERROR, null,"否决项目失败"));
			if (logger.isErrorEnabled()) 
			{
				logger.error("否决项目失败 ", e);
			}
		}

		return data;
	}
	/**
	 * 项目推进统一接口
	 * @param p
	 * @param request
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/stageChange", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> stageChange(ProjectQuery p,HttpServletRequest request) 
	{
		ResponseData<Project> data = new ResponseData<Project>();
		try
		{
			projectService.updateProgress(p.getId(), p.getStage());
			
		} catch (Exception e)
		{
			data.setResult(new Result(Status.ERROR, null,"项目推进失败"));
			if (logger.isErrorEnabled()) 
			{
				logger.error("项目推进失败 ", e);
			}
		}

		return data;
	}
	
	
}
