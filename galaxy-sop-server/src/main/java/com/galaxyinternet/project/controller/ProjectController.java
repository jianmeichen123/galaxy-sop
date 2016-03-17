package com.galaxyinternet.project.controller;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.exception.PlatformException;
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
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project.service.HandlerManager;
import com.galaxyinternet.project.service.handler.Handler;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.ProjectPersonService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/project")
public class ProjectController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PersonPoolService personPoolService;
	@Autowired
	private ProjectPersonService projectPersonService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private MeetingRecordService meetingRecordService;
	@Autowired
	private InterviewRecordService interviewRecordService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private HandlerManager handlerManager;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	/**
	 * 新建项目接口
	 * @author yangshuhua
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ap", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> addProject(@RequestBody Project project, HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if(project == null || project.getProjectName() == null || "".equals(project.getProjectName().trim())
				|| project.getProjectType() == null || "".equals(project.getProjectType().trim())){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		Object code = request.getSession().getAttribute(Constants.SESSION_PROJECT_CODE);
		if(code == null){
			responseBody.setResult(new Result(Status.ERROR, "项目编码丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		//判断当前用户是否为投资经理
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		if(!roleIdList.contains(UserConstant.HHR) && !roleIdList.contains(UserConstant.TZJL)){
			responseBody.setResult(new Result(Status.ERROR, "没有权限添加项目!"));
			return responseBody;
		}
		project.setProjectCode(String.valueOf(code));
		if(project.getProjectShareRatio() != null && project.getProjectShareRatio() > 0 
				&& project.getProjectContribution() != null && project.getProjectContribution() > 0){
			project.setProjectValuations(project.getProjectContribution() * 100 / project.getProjectShareRatio());
		}
		project.setStockTransfer(0);
		project.setCreateUid(user.getId());
		project.setCreateUname(user.getNickName());
		project.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		//获取当前登录人的部门信息
		Long did = user.getDepartmentId();
		project.setProjectDepartid(did);
		project.setCreatedTime((new Date()).getTime());
		try {
			long id = projectService.newProject(project);
			if(id > 0){
				responseBody.setResult(new Result(Status.OK,"项目添加成功!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
	
	/**
	 * 修改项目信息接口
	 * @author yangshuhua
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> resetProject(@RequestBody Project project, HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if(project == null || project.getId() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		
		Project p = projectService.queryById(project.getId());
		if(p == null){
			responseBody.setResult(new Result(Status.ERROR, "未找到相应的项目信息!"));
			return responseBody;
		}
		//项目创建者用户ID与当前登录人ID是否一样
		if(user.getId().longValue() != p.getCreateUid().longValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限修改该项目!"));
			return responseBody;
		}
		project.setUpdatedTime(System.currentTimeMillis());
		int num = projectService.updateById(project);
		if(num > 0){
			responseBody.setResult(new Result(Status.OK,"项目修改成功!"));
		}
		return responseBody;
	}
	
	
	/**
	 * 查询指定的项目信息接口
	 * @author yangshuhua
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/sp/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> selectProject(@PathVariable("pid") String pid, HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		Project project = projectService.queryById(Long.parseLong(pid));
		if(project == null){
			responseBody.setResult(new Result(Status.ERROR, "未查找到指定项目信息!"));
			return responseBody;
		}
		responseBody.setEntity(project);
		ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		return responseBody;
	}
	
	
	/**
	 * 获取项目列表(高管)
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> queryAllProjects(HttpServletRequest request, @RequestBody Project project) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		try {
			if(roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.CEO)){
				/*Page<Project> pageProject = projectService.queryPageList(project,new PageRequest(project.getPageNum(), project.getPageSize()));
				responseBody.setPageList(pageProject);
				responseBody.setResult(new Result(Status.OK, ""));*/
			}
			Page<Project> pageProject = projectService.queryPageList(project,new PageRequest(project.getPageNum(), project.getPageSize()));
			responseBody.setPageList(pageProject);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}

	
	/**
	 * 获取项目列表(投资经理)
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/spl", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProjectList(HttpServletRequest request, @RequestBody Project project) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		project.setCreateUid(user.getId());
		try {
			Page<Project> pageProject = projectService.queryPageList(project,new PageRequest(project.getPageNum(), project.getPageSize()));
			responseBody.setPageList(pageProject);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}
	
	/**
	 * 添加团队成员
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> addProjectPerson(@RequestBody PersonPoolBo pool, HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if(pool.getProjectId() == null || pool.getProjectId() <= 0
				|| pool.getPersonName() == null || pool.getPersonSex() == null
				|| pool.getPersonAge() == null || pool.getPersonDuties() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(pool.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId().doubleValue() != p.getCreateUid().doubleValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限为该项目添加团队成员!"));
			return responseBody;
		}
		try {
			pool.setCreatedTime(System.currentTimeMillis());
			Long id = personPoolService.savePersonToProject(pool);
			if(id > 0){
				responseBody.setResult(new Result(Status.OK,"团队成员添加成功!"));
				responseBody.setEntity(pool);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
	/**
	 * 修改团队成员
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/upp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> resetProjectPerson(@RequestBody PersonPoolBo pool, HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if(pool == null || pool.getId() == null || pool.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(pool.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId().doubleValue() != p.getCreateUid().doubleValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限修改该项目的团队成员信息!"));
			return responseBody;
		}
		
		int num = personPoolService.updateById(pool);
		if(num > 0){
			responseBody.setResult(new Result(Status.OK,"团队成员信息修改成功!"));
		}
		return responseBody;
	}
	
	/**
	 * 删除团队成员
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/dpp/{id}/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> deleteProjectPerson(@PathVariable("id") Long id,@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if(projectId == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(projectId);
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId().doubleValue() != p.getCreateUid().doubleValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限删除该项目的团队成员!"));
			return responseBody;
		}
		ProjectPerson pp=new ProjectPerson();
		pp.setPersonId(id);
		pp.setProjectId(projectId);
		int num = projectPersonService.delete(pp);
		
		int mump = personPoolService.deleteById(id);
		
		if(num > 0 && mump > 0){
			responseBody.setResult(new Result(Status.OK,"团队成员删除成功!"));
		}
		return responseBody;
	}
	
	
	/**
	 * 查询团队成员列表
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProjectPerson",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> queryProjectPerson(HttpServletRequest request,@RequestBody PersonPoolBo personPoolBo) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		try {
			Page<PersonPool> pageList = personPoolService.queryPageListByPid(personPoolBo, new PageRequest(personPoolBo.getPageNum(), personPoolBo.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
		
		
	}
	/**
	 * 创建项目编码
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/cpc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Config> createProjectCode(HttpServletRequest request){
		ResponseData<Config> responseBody = new ResponseData<Config>();
		
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		if(!roleIdList.contains(UserConstant.HHR) && !roleIdList.contains(UserConstant.TZJL)){
			responseBody.setResult(new Result(Status.ERROR, "没有权限!"));
			return responseBody;
		}
		
		try {
			//long did = user.getDepartmentId();
			Config config = configService.createCode();
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMaximumIntegerDigits(6);
			nf.setMinimumIntegerDigits(6);
			String code = "10" + nf.format(Integer.parseInt(config.getValue()));
			request.getSession().setAttribute(Constants.SESSION_PROJECT_CODE, code);
			config.setPcode(code);
			responseBody.setEntity(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
	/***
	 * 获取项目信息
	 * @param pid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProjectInfo/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectBo> projectInfo(@PathVariable("pid") String pid, HttpServletRequest request) {
	
		
		ProjectBo projectBo=new ProjectBo();
		//查询项目信息
		ResponseData<ProjectBo> responseBody = new ResponseData<ProjectBo>();
		Project project = projectService.queryById(Long.parseLong(pid));
		if(project == null){
			responseBody.setResult(new Result(Status.ERROR, "未查找到指定项目信息!"));
			return responseBody;
		}
		//项目合伙人
		/**
		ProjectPerson person=new ProjectPerson();
		person.setProjectId(project.getId());
		ProjectPerson pp=projectPersonService.queryOne(person);
		
		if(pp == null ){
			responseBody.setResult(new Result(Status.ERROR, "未查找到指定项目关联合伙人信息!"));
			return responseBody;
		}
		//人资信息
		PersonPool pool=personPoolService.queryById(pp.getPersonId());
		if(pool == null){
			responseBody.setResult(new Result(Status.ERROR, "未查找到指定项目合伙人信息!"));
			return responseBody;
		}***/
		
		projectBo.setProjectName(project.getProjectName());
		projectBo.setProjectCode(project.getProjectCode());
		projectBo.setProjectDescribe(project.getProjectDescribe());
		projectBo.setProjectType(project.getProjectType());
		//projectBo.setPartnerName(pool.getPersonName());
		projectBo.setCreateUname(project.getCreateUname());
		projectBo.setProjectCareerline(project.getProjectCareerline());
		responseBody.setEntity(projectBo);
		
		
		return responseBody;
	}
	
	
	/**
	 * 跳转到修改项目页面
	 * @return
	 */
	@RequestMapping(value = "/updatePro/{id}", method = RequestMethod.GET)
	public String updateProject(@PathVariable("id") Long id,HttpServletRequest request) {
		
		PersonPool person = personPoolService.queryById(id);
		if(person == null ){
			return "未查找到指定信息!";
		}
		request.setAttribute("person", person);
		return "project/updatePerson";
	}
	
	
	/**
	 * 项目阶段中的文档上传
	 * 协同部门的人员操作
	 * @author yangshuhua
	 */
	
	/**
	 * 项目阶段中的文档上传
	 * 该项目对应的创建人操作
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(writeOperationScope=LogType.LOG)
	@ResponseBody
	@RequestMapping(value = "/stageChange", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectQuery> stageChange(ProjectQuery p, HttpServletRequest request) {
		ResponseData<ProjectQuery> responseBody = new ResponseData<ProjectQuery>();
		/**
		 * 1.参数校验
		 */
		if(p.getPid() == null || p.getStage() == null 
				|| !SopConstant._progress_pattern_.matcher(p.getStage()).matches()
				|| p.getParseDate() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		if(p.getStage().equals(DictEnum.projectProgress.内部评审.getCode()) || p.getStage().equals(DictEnum.projectProgress.CEO评审.getCode())){
			if(p.getMeetingType() == null || !SopConstant._meeting_type_pattern_.matcher(p.getMeetingType()).matches()
					|| p.getResult() == null || !SopConstant._meeting_result_pattern_.matcher(p.getResult()).matches()){
				responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
				return responseBody;
			}
		}
		Project project = projectService.queryById(p.getPid());
		if(project == null){
			responseBody.setResult(new Result(Status.ERROR, "未找到相应的项目信息!"));
			return responseBody;
		}
		//投资意向书、尽职调查及投资协议的文档上传、更新操作只能在当前阶段才能进行
		if(p.getStage().equals(DictEnum.projectProgress.投资意向书.getCode()) || p.getStage().equals(DictEnum.projectProgress.尽职调查.getCode())
				|| p.getStage().equals(DictEnum.projectProgress.投资协议.getCode())){
			if(p.getType() == null || p.getFileType() == null || !SopConstant._file_type_pattern_.matcher(p.getFileType()).matches()
					|| p.getFileWorktype() == null || !SopConstant._file_worktype_pattern_.matcher(p.getFileWorktype()).matches()){
				responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
				return responseBody;
			}
			int in = Integer.parseInt(p.getStage().substring(p.getStage().length()-1));
			int pin = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
			if(in < pin){
				responseBody.setResult(new Result(Status.ERROR, "该操作已过期!"));
				return responseBody;
			}
		}
		User user = (User) getUserFromSession(request);
		//项目创建者用户ID与当前登录人ID是否一样
		if(user.getId().longValue() != project.getCreateUid().longValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限修改该项目!"));
			return responseBody;
		}
		p.setCreatedUid(user.getId());
		p.setDepartmentId(user.getDepartmentId());
		/**
		 * 2.文件上传
		 */
		UploadFileResult result = null;
		String fileKey = null;
		MultipartFile file = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			file = multipartRequest.getFile("file");
			fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
			result = OSSHelper.simpleUploadByOSS(file.getInputStream(),fileKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 3.处理业务
		 */
		try {
			if(result != null && result.getResult().getStatus().equals(Result.Status.OK)){
				String fileOriginalName = file.getOriginalFilename();
				p.setFileName(fileOriginalName.substring(0, fileOriginalName.lastIndexOf(".")));
				p.setSuffix(fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1));
				//int dotPos = fileName.lastIndexOf(".");String ext = fileName.substring(dotPos);
				p.setBucketName(result.getBucketName());
				p.setFileKey(fileKey);
				p.setFileSize(file.getSize());
				if(handlerManager.getStageHandlers().containsKey(p.getStage())){
					Handler handler = handlerManager.getStageHandlers().get(p.getStage());
					SopResult r = handler.handler(p, project);
					if(r != null && r.getStatus().equals(Result.Status.OK)){
						responseBody.setResult(r);
						//记录操作日志
						ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), r.getNumber());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
	
	/**
	 * 接触访谈阶段: 启动内部评审
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(writeOperationScope=LogType.LOG)
	@ResponseBody
	@RequestMapping(value="/startReview/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> startReview(HttpServletRequest request,@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.接触访谈.getCode(), project, user);
		if(!result.getStatus().equals(Status.OK)){
			responseBody.setResult(result);
			return responseBody;
		}
		InterviewRecord ir = new InterviewRecord();
		ir.setProjectId(pid);
		Long count = interviewRecordService.queryCount(ir);
		if(count != null && count.doubleValue() > 0){
			try {
				project.setProjectProgress(DictEnum.projectProgress.内部评审.getCode());   //字典  项目进度  内部评审
				project.setProjectStatus(DictEnum.meetingResult.待定.getCode());     //字典 项目状态 = 会议结论   待定
				projectService.updateById(project);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "异常，启动内部评审失败!"));
				if(logger.isErrorEnabled()){
					logger.error("update project faild ",e);
				}
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "不存在访谈记录，不允许启动内部评审!"));
		}
		return responseBody;
	}
	
	/**
	 * 申请立项会排期
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(writeOperationScope=LogType.LOG)
	@ResponseBody
	@RequestMapping(value="/ges/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> ges(HttpServletRequest request,@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.CEO评审.getCode(), project, user);
		if(!result.getStatus().equals(Status.OK)){
			responseBody.setResult(result);
			return responseBody;
		}
		//必须又一次会议记录为通过
		MeetingRecord mr = new MeetingRecord();
		mr.setProjectId(pid);
		mr.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
		mr.setMeetingResult(DictEnum.meetingResult.通过.getCode());
		Long count = meetingRecordService.queryCount(mr);
		if(count != null && count.doubleValue() > 0){
			try {
				projectService.toEstablishStage(project);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "异常，申请立项会失败!"));
				if(logger.isErrorEnabled()){
					logger.error("update project faild ",e);
				}
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "不存在通过的会议记录，不能申请立项会!"));
		}
		return responseBody;
	}
	
	/**
	 * 申请投决会排期
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(writeOperationScope=LogType.LOG)
	@ResponseBody
	@RequestMapping(value="/smp/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> sureMeetingPool(HttpServletRequest request,@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.尽职调查.getCode(), project, user);
		if(!result.getStatus().equals(Status.OK)){
			responseBody.setResult(result);
			return responseBody;
		}
		//验证文档是否齐全
		SopFile file = new SopFile();
		file.setProjectId(pid);
		file.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		List<SopFile> files = sopFileService.queryList(file);
		for(SopFile f : files){
			if(f.getFileKey() == null || "".equals(f.getFileKey().trim())){
				responseBody.setResult(new Result(Status.ERROR,null, "文档不齐全，不能申请投决会!"));
				break;
			}
		}
		try {
			projectService.toSureMeetingStage(project);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "异常，申请投决会失败!"));
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		return responseBody;
	}

	
	
	/**
	 * 判断项目的操作是否合法
	 * @author yangshuhua
	 */
	public Result validate(String progress, Project project, User user){
		if(project == null){
			return new Result(Status.ERROR, "未找到相应的项目信息!");
		}
		if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())){
			return new Result(Status.ERROR, "项目已关闭!");
		}
		
		if(user.getId().longValue() != project.getCreateUid().longValue()){
			return new Result(Status.ERROR, "没有权限修改该项目!");
		}
		int in = Integer.parseInt(progress.substring(progress.length()-1)) ;
		int pin = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
		if(in < pin){
			return new Result(Status.ERROR, "501", "该操作已过期!");
		}
		return new Result(Status.OK, "200", null);
	}
	
	
	
	
	/**
	 * 关闭项目
	 * @param   pid 项目id 
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(writeOperationScope=LogType.ALL)
	@ResponseBody
	@RequestMapping(value = "/breakpro/{pid}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> breakproject(@PathVariable Long pid,HttpServletRequest request ) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			//project id 验证
			Project project = new Project();
			project = projectService.queryById(pid);
			
			if(project == null || project.getCreateUid()==null){
				responseBody.setResult(new Result(Status.ERROR,null, "项目检索不到"));
				return responseBody;
			}else{
				if(!project.getCreateUid().equals(user.getId())){
					responseBody.setResult(new Result(Status.ERROR,null, "无操作权限"));
					return responseBody;
				}
			}
			
			project.setProjectStatus(DictEnum.meetingResult.否决.getCode());
			int id = projectService.updateById(project);
			if(id!=1){
				responseBody.setResult(new Result(Status.ERROR,null, "更新失败"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "add meetingRecord faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("add meetingRecord faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
}
