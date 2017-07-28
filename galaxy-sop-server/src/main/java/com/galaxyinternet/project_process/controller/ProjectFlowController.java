package com.galaxyinternet.project_process.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
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

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum.LXHResult;
import com.galaxyinternet.common.dictEnum.DictEnum.SWTPResult;
import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingResult;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.fileWorktype;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project.service.HandlerManager;
import com.galaxyinternet.project_process.util.ProFlowUtilImpl;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
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
	
	@Autowired
	private HandlerManager handlerManager;
	
	@Autowired
	private SopFileDao sopFileDao;
	
	@Autowired
	private SopTaskDao sopTaskDao;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	
	// TODO : 流程总页面
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		String projectId = request.getParameter("projectId");
		if(StringUtils.isNotBlank(projectId)){
			Project pro = projectService.queryById(Long.valueOf(projectId));
			String progress = pro.getProjectProgress();
			request.setAttribute("progress", progress);
		}
		return "project/sop/sop_progress/list";
	}


	// TODO : 访谈
	/**
	 * 访谈页面
	 */
	@RequestMapping(value = "/p1", method = RequestMethod.GET)
	public String p1() {
		return "project/sop/sop_progress/edit";
	}
	
	/**
	 * 访谈查询,
	 * 			投资经理： 查询个人项目下		的访谈记录  
	 * 			合伙人：	    查询个人事业线下	的访谈记录  
	 * 			高管： 	    查询所有			的访谈记录  
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/p1/queryInterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecordBo> queryInterview(HttpServletRequest request,@RequestBody InterviewRecordBo query ) {
		ResponseData<InterviewRecordBo> responseBody = new ResponseData<InterviewRecordBo>();
		
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.DMS) || roleIdList.contains(UserConstant.CEOMS)){  //无限制，根据传参查询
				//query.setUid(null);
			}else if(roleIdList.contains(UserConstant.HHR)){   //固定为其部门
				query.setDepartId(user.getDepartmentId());
			}else if(roleIdList.contains(UserConstant.TZJL)){  //固定为其创建
				query.setUid(user.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
				return responseBody;
			}

			if(query.getProjectId()!=null){
				List<Project> proList = null;
				boolean checked = false;
				if(roleIdList.contains(UserConstant.HHR)){   //部门下项目校验
					Project  proQ = new Project();
					proQ.setId(query.getProjectId());
					proQ.setProjectDepartid(user.getDepartmentId());
					proList = projectService.queryList(proQ);
					checked = true;
				}else if(roleIdList.contains(UserConstant.TZJL)){  //个人下项目校验
					Project  proQ = new Project();
					proQ.setCreateUid(user.getId());
					proQ.setId(query.getProjectId());
					proList = projectService.queryList(proQ);
					checked = true;
				}
				if(checked){
					if(proList==null || proList.isEmpty()){
						responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
						return responseBody;
					}
				}
			}
			query.setDirection("desc");
			Page<InterviewRecordBo> pageList = interviewRecordService.queryInterviewPage(query,  new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()) );
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterview 查询失败",e);
			}
		}
		
		return responseBody;
	}
	
	/**
	 * 接触访谈阶段: 附件添加 -访谈添加
	 * @param   interviewRecord 
	 * 			produces="application/text;charset=utf-8"
	 * @RequestBody InterviewRecord interviewRecord ,
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG })
	@ResponseBody
	@RequestMapping(value = "/p1/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectQuery> p1_add(ProjectQuery p,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<ProjectQuery> responseBody = new ResponseData<ProjectQuery>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (p.getPid() == null) {
			String json = JSONUtils.getBodyString(request);
			p = GSONUtil.fromJson(json, ProjectQuery.class);
		}
		// 所有都必须附带pid和stage
		if (p.getPid() == null
				|| p.getStage() == null
				|| !SopConstant._progress_pattern_.matcher(p.getStage())
						.matches() || p.getParseDate() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		Project project = projectService.queryById(p.getPid());
		if (project == null) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "未找到相应的项目信息!"));
			return responseBody;
		}
		// 项目创建者用户ID与当前登录人ID是否一样
		if (user.getId().longValue() != project.getCreateUid().longValue()) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "没有权限修改该项目!"));
			return responseBody;
		}
		p.setCreatedUid(user.getId());
		p.setDepartmentId(user.getDepartmentId());
		/**
		 * 2.文件上传 这里都是上传，无更新，所以每次都生成一个新的fileKey
		 */
		String fileKey = String
				.valueOf(IdGenerator.generateId(OSSHelper.class));
		UploadFileResult result = uploadFileToOSS(request, fileKey,
				tempfilePath);
		/**
		 * 3.处理业务
		 */
		try {
			SopResult r = interviewRecordService.operateInterview(project, p, result, request);
			// 记录操作日志
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), null, r.getNumber());
			
		} catch (Exception e) {
			logger.error("操作失败", e);
			responseBody.getResult().addError("操作失败!");
		}
		
		return responseBody;
	}
	
	/**
	 * 访谈页面查看
	 */
	@RequestMapping(value = "/p1/view/{type}", method = RequestMethod.GET)
	public String p1_view(@PathVariable String type) {
		if(StringUtils.isNotBlank(type)){
			if("e".equals(type)){
				return "project/sop/sop_progress/edit";
			}
		}
		return "project/sop/sop_progress/view";
	}
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段 : 添加会议记录   
	 * 			判断会议结论，更新项目进度，启动关联任务
	 * @param   interviewRecord 
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG })
	@ResponseBody
	@RequestMapping(value = "/p2/add",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> p2_add(MeetingRecordBo meetingRecord,HttpServletRequest request,HttpServletResponse response  ) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(meetingRecord.getProjectId() == null){
			String json = JSONUtils.getBodyString(request);
			meetingRecord = GSONUtil.fromJson(json, MeetingRecordBo.class);
		}
		if(meetingRecord.getProjectId() == null 
				|| meetingRecord.getMeetingDate() == null 
				|| meetingRecord.getMeetingType() == null 
				|| meetingRecord.getMeetingResult() == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善会议信息"));
			return responseBody;
		}
		try {
			if(meetingRecord.getRecordId() != null){
				meetingRecord.setId(meetingRecord.getRecordId());
			}
			int prograss = 0;
			if(meetingRecord.getId() != null){
				prograss = 1;
			}
			UrlNumber uNum = null;
			/**
			 * 操作日志分区判断
			 */
			switch(meetingRecord.getMeetingType()){
			       case "meetingType:1":
			    	    if(prograss == 0)
			    	    	uNum = UrlNumber.one;
			    	    else 
			    	    	uNum = UrlNumber.two;
			    	    break;
			       case "meetingType:2":
			    	    if(prograss == 0)
			    	    	uNum = UrlNumber.three;
			    	    else 
			    	    	uNum = UrlNumber.four;
			    	    break;
			       case "meetingType:3":
			    	    if(prograss == 0)
			    	    	uNum = UrlNumber.five;
			    	    else 
			    	    	uNum = UrlNumber.six;
			    	    break;
			       case "meetingType:4":
			    	    if(prograss == 0)
			    	    	uNum = UrlNumber.nine;
			            else 
			    	    	uNum = UrlNumber.ten;
			    	    break;
			       case "meetingType:5":
			    	    if(prograss == 0)
			    	    	uNum = UrlNumber.seven;
			            else 
			    	    	uNum = UrlNumber.eight;
			    	    break;
			    	default :
			    		 break;
			}
			
			//project id 验证
			Project project = new Project();
			project = projectService.queryById(meetingRecord.getProjectId());
			
			if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode()) &&
					meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
				project.setFinalValuations(meetingRecord.getFinalValuations());
				project.setFinalContribution(meetingRecord.getFinalContribution());
				project.setFinalShareRatio(meetingRecord.getFinalShareRatio());
				project.setServiceCharge(meetingRecord.getServiceCharge());
			}
			//没有上传文件的
			if(!ServletFileUpload.isMultipartContent(request)){
				meetingRecordService.operateFlowMeeting(null,meetingRecord);
			}else{
				/**
				 * 2.文件上传 这里都是上传，无更新，所以每次都生成一个新的fileKey
				 */
				String fileKey = String
						.valueOf(IdGenerator.generateId(OSSHelper.class));
				UploadFileResult result = uploadFileToOSS(request, fileKey,
						tempfilePath);
				if (result != null
						&& result.getResult().getStatus().equals(Result.Status.OK)) {
					SopFile sopFile = new SopFile();
					sopFile.setBucketName(result.getBucketName());
					sopFile.setFileKey(fileKey);
					sopFile.setFileLength(result.getContentLength());
					sopFile.setFileName(result.getFileName());
					sopFile.setFileSuffix(result.getFileSuffix());
					sopFile.setProjectId(project.getId());
					sopFile.setProjectProgress(project.getProjectProgress());
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
					sopFile.setCreatedTime(new Date().getTime());
					meetingRecordService.operateFlowMeeting(sopFile,meetingRecord);
				}
			}
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), null, uNum);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "会议添加失败"));
			if(logger.isErrorEnabled()){
				logger.error("addfilemeet 会议添加失败 ",e);
			}
		}
		return responseBody;
	}
	
	
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段
	 * 			查询个人项目下的会议记录
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/p/queryMeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecordBo> queryMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ) {
		
		ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
		
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			//角色校验
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.DMS) || roleIdList.contains(UserConstant.CEOMS)){  //无限制，根据传参查询
				//query.setUid(null);
			}else if(roleIdList.contains(UserConstant.HHR)){   //固定为其部门
				query.setDepartId(user.getDepartmentId());
			}else if(roleIdList.contains(UserConstant.TZJL)){  //固定为其创建
				query.setUid(user.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
				return responseBody;
			}
			
			if(query.getProjectId()!=null){
				List<Project> proList = null;
				boolean checked = false;
				if(roleIdList.contains(UserConstant.HHR)){   //部门下项目校验
					Project  proQ = new Project();
					proQ.setId(query.getProjectId());
					proQ.setProjectDepartid(user.getDepartmentId());
					proList = projectService.queryList(proQ);
					checked = true;
				}else if(roleIdList.contains(UserConstant.TZJL)){  //个人下项目校验
					Project  proQ = new Project();
					proQ.setCreateUid(user.getId());
					proQ.setId(query.getProjectId());
					proList = projectService.queryList(proQ);
					checked = true;
				}
				if(checked){
					if(proList==null || proList.isEmpty()){
						responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
						return responseBody;
					}
				}
			}
			
			Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPage(query, new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
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
			data.setResult(new Result(Status.ERROR, null,e.getMessage()));
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG })
	@ResponseBody
	@RequestMapping(value = "/stageChange", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> stageChange(@RequestBody ProjectQuery p,HttpServletRequest request) 
	{
		ResponseData<Project> data = new ResponseData<Project>();
		try
		{
			projectService.updateProgress(p.getId(), p.getStage());
			Project entity = projectService.queryById(p.getId());
			data.setEntity(entity);
			
		} catch (Exception e)
		{
			data.setResult(new Result(Status.ERROR, null,e.getMessage()));
			if (logger.isErrorEnabled()) 
			{
				logger.error("项目推进失败 ", e);
			}
		}
		return data;
	}
	
	
	public String errMessage(Project project,User user,String prograss){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())||project.getProjectStatus().equals(DictEnum.projectStatus.YFJ.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已经关闭";
		}else if(project.getProjectStatus().equals(DictEnum.projectStatus.YTC.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已退出";
		}
		
		if(user != null){
			if(project.getCreateUid()==null || user.getId().longValue()!=project.getCreateUid().longValue()){ 
				return "不允许操作他人项目";
			}
		}
		if(prograss != null){
			if(project.getProjectProgress()!=null){
				try {
					int operationPro = Integer.parseInt(prograss.substring(prograss.length()-1)) ;
					int projectPro = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
					if(projectPro < operationPro){
						return "项目当前阶段不允许进行该操作";
					}
				} catch (Exception e) {
					return "项目阶段不和规范";
				}
			}else{
				return "项目阶段出错";
			}
		}
		
		return null;
	}
	@RequestMapping(value = "/searchMeeting", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<MeetingRecord> searchMeeting(MeetingRecord query)
	{
		ResponseData<MeetingRecord> data = new ResponseData<MeetingRecord>();
		List<MeetingRecord> list = meetingRecordService.queryList(query);
		data.setEntityList(list);
		return data;
	}
	@RequestMapping(value = "/buttonToggle/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<Project> buttonToggle(@PathVariable Long id)
	{
		boolean rejectValid = false;
		boolean next1Valid = false;
		boolean next2Valid = false;
		ResponseData<Project> data = new ResponseData<>();
		Project project = projectService.queryById(id);
		String currProgress = project.getProjectProgress();
		if(projectProgress.接触访谈.getCode().equals(currProgress))
		{
			InterviewRecordBo query = new InterviewRecordBo();
			query.setProjectId(project.getId());
			query.setInterviewResult(meetingResult.通过.getCode());
			Long count = interviewRecordService.selectCount(query);
			next1Valid = count>0L;
			
			query.setInterviewResult(meetingResult.否决.getCode());
			count = interviewRecordService.selectCount(query);
			rejectValid = count>0L;
		}
		else if(projectProgress.内部评审.getCode().equals(currProgress) ||
				projectProgress.CEO评审.getCode().equals(currProgress)
				)
		{
			String type = meetingType.内评会.getCode();
			if(projectProgress.CEO评审.getCode().equals(currProgress))
			{
				type = meetingType.CEO评审.getCode();
			}
			MeetingRecordBo query = new MeetingRecordBo();
			query.setProjectId(project.getId());
			query.setMeetingType(type);
			query.setMeetingResult(meetingResult.通过.getCode());
			
			Long count = meetingRecordService.queryCount(query);
			next1Valid = count>0L;
			
			query.setMeetingResult(meetingResult.否决.getCode());
			count = meetingRecordService.queryCount(query);
			rejectValid = count>0L;
		}
		else if(projectProgress.立项会.getCode().equals(currProgress))
		{
			MeetingRecordBo query = new MeetingRecordBo();
			query.setProjectId(project.getId());
			query.setMeetingType(meetingType.立项会.getCode());
			query.setMeetingResult(LXHResult.ST.getCode());
			
			Long count = meetingRecordService.queryCount(query);
			if(count.intValue()==0)
			{
				query.setMeetingResult(LXHResult.TZ.getCode());
				count = meetingRecordService.queryCount(query);
			}
			
			if(count.intValue()==0)
			{
				query.setMeetingResult(LXHResult.ZX.getCode());
				count = meetingRecordService.queryCount(query);
			}
			
			next1Valid = count>0L;
			
			query.setMeetingResult(LXHResult.FJ.getCode());
			count = meetingRecordService.queryCount(query);
			rejectValid = count>0L;
		}
		else if(projectProgress.会后商务谈判.getCode().equals(currProgress))
		{
			MeetingRecordBo query = new MeetingRecordBo();
			query.setProjectId(project.getId());
			query.setMeetingType(meetingType.会后商务谈判.getCode());
			query.setMeetingResult(SWTPResult.ST.getCode());
			
			Long count = meetingRecordService.queryCount(query);
			next1Valid = count>0L; //签订投资协议书（闪投）
			
			query.setMeetingResult(SWTPResult.TZ.getCode());
			count = meetingRecordService.queryCount(query);
			next2Valid = count>0L; //签订投资意向书（投资）
			
			query.setMeetingResult(SWTPResult.FJ.getCode());
			count = meetingRecordService.queryCount(query);
			rejectValid = count>0L;
		}
		else if(projectProgress.投资意向书.getCode().equals(currProgress))
		{
			SopFile query = new SopFile();
			query.setProjectId(project.getId());
			query.setFileWorktype(fileWorktype.投资意向书.getCode());
			query.setFileStatus(fileStatus.已上传.getCode());
			query.setFileValid(1);//查询有效文件
			Long count = sopFileService.queryCount(query);
			next1Valid = count>0L;
		}
		else if(projectProgress.尽职调查.getCode().equals(currProgress))
		{
			String[] typeList = {
					fileWorktype.业务尽职调查报告.getCode(), 
					fileWorktype.人力资源尽职调查报告.getCode(), 
					fileWorktype.法务尽职调查报告.getCode(),
					fileWorktype.财务尽职调查报告.getCode(),
					fileWorktype.尽职调查启动会报告.getCode(),
					fileWorktype.尽职调查总结会报告.getCode()
					
			};
			String[] fileStatusList = {fileStatus.已上传.getCode(),fileStatus.已放弃.getCode()};
			SopFile query = new SopFile();
			query.setProjectId(project.getId());
			query.setFileworktypeList(Arrays.asList(typeList));
			query.setFileStatusList(Arrays.asList(fileStatusList));;
			query.setFileValid(1);//查询有效文件
			Long count = sopFileService.queryCount(query);
			
			next1Valid = count == 6L;
			rejectValid = true;
		}
		else if(projectProgress.投资决策会.getCode().equals(currProgress))
		{
			MeetingRecordBo query = new MeetingRecordBo();
			query.setProjectId(project.getId());
			query.setMeetingType(meetingType.投决会.getCode());
			query.setMeetingResult(meetingResult.通过.getCode());
			Long count = meetingRecordService.queryCount(query);
			if(count.intValue() > 0)
			{
				if(SopConstant.BUSINESS_TYPE_TZ.equals(project.getBusinessTypeCode()))
				{
					next1Valid = true; //签订投资协议
				}
				else if(SopConstant.BUSINESS_TYPE_ST.equals(project.getBusinessTypeCode()))
				{
					next2Valid = true; //进入股权交割
				}
			}
			
			query.setMeetingResult(meetingResult.否决.getCode());
			count = meetingRecordService.queryCount(query);
			rejectValid = count>0L;
		}
		else if(projectProgress.投资协议.getCode().equals(currProgress))
		{
			SopFile query = new SopFile();
			query.setProjectId(project.getId());
			query.setFileWorktype(fileWorktype.投资协议.getCode());
			query.setFileStatus(fileStatus.已上传.getCode());
			query.setFileValid(1);//查询有效文件
			Long count = sopFileService.queryCount(query);
			if(count > 0L)
			{
				if(SopConstant.BUSINESS_TYPE_ST.equals(project.getBusinessTypeCode()))
				{
					next1Valid = true; //进入尽职调查
				}
				else if(SopConstant.BUSINESS_TYPE_TZ.equals(project.getBusinessTypeCode()))
				{
					next2Valid = true; //进入股权交割
				}
			}
		}
		else if(projectProgress.股权交割.getCode().equals(currProgress))
		{
			String[] fileTypeList = {
					fileWorktype.工商转让凭证.getCode(),
					fileWorktype.资金拨付凭证.getCode()
			};
			SopFile query = new SopFile();
			query.setProjectId(project.getId());
			query.setFileStatus(fileStatus.已上传.getCode());
			query.setFileworktypeList(Arrays.asList(fileTypeList));
			query.setFileValid(1);//查询有效文件
			Long count = sopFileService.queryCount(query);
			next1Valid = count == 2L;
		}
		data.getUserData().put("next1Valid", next1Valid);
		data.getUserData().put("next2Valid", next2Valid);
		data.getUserData().put("rejectValid", rejectValid);
		return data;
	}
	
}
