package com.galaxyinternet.project.controller;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.PassRateBo;
import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.EnumUtil;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.config.PlaceholderConfigurer;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.form.Token;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.framework.core.utils.mail.MailTemplateUtils;
import com.galaxyinternet.framework.core.utils.mail.SimpleMailSender;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.FormatData;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.project.service.HandlerManager;
import com.galaxyinternet.project.service.handler.Handler;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.PassRateService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.ProjectPersonService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;

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
	private UserService userService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private MeetingRecordService meetingRecordService;
	@Autowired
	private InterviewRecordService interviewRecordService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private SopVoucherFileService sopVoucherFileService;
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	@Autowired
	private HandlerManager handlerManager;

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PassRateService passRateService;
	
	@Autowired
	private SopTaskService sopTaskService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	private String tempfilePath;

	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}

	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}

	/**
	 * 新建项目接口
	 * 
	 * @author yangshuhua
	 * @return
	 */
	@Token
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.MESSAGE)
	@ResponseBody
	@RequestMapping(value = "/ap", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> addProject(@RequestBody Project project,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if (project == null || project.getProjectName() == null
				|| "".equals(project.getProjectName().trim())
				|| project.getProjectType() == null
				|| "".equals(project.getProjectType().trim())
				|| project.getCreateDate() == null
				|| "".equals(project.getCreateDate().trim())) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		Object code = request.getSession().getAttribute(
				Constants.SESSION_PROJECT_CODE);
		if (code == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "项目编码丢失!"));
			return responseBody;
		}
		Project obj = new Project();
		obj.setProjectName(project.getProjectName());
		List<Project> projectList = projectService.queryList(obj);
		/*
		 * Integer count = 0 ; for (Project p: projectList) { count ++; }
		 * if(count>0){
		 */
		if (null != projectList && projectList.size() > 0) {
			responseBody.setResult(new Result(Status.ERROR, null, "用户名重复!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		// 判断当前用户是否为投资经理
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		if (!roleIdList.contains(UserConstant.HHR)
				&& !roleIdList.contains(UserConstant.TZJL)) {
			responseBody.setResult(new Result(Status.ERROR, null, "没有权限添加项目!"));
			return responseBody;
		}

		project.setProjectCode(String.valueOf(code));
		if (project.getProjectValuations() == null) {
			if (project.getProjectShareRatio() != null
					&& project.getProjectShareRatio() > 0
					&& project.getProjectContribution() != null
					&& project.getProjectContribution() > 0) {
				project.setProjectValuations(project.getProjectContribution()
						* 100 / project.getProjectShareRatio());
			}
		}

		project.setStockTransfer(0);
		project.setCreateUid(user.getId());
		project.setCreateUname(user.getRealName());
		project.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		// 获取当前登录人的部门信息
		Long did = user.getDepartmentId();
		project.setProjectDepartid(did);
		project.setUpdatedTime(new Date().getTime());
		try {
			project.setCreatedTime(DateUtil.convertStringToDate(
					project.getCreateDate().trim(), "yyyy-MM-dd").getTime());
			long id = projectService.newProject(project);
			if (id > 0) {
				responseBody.setResult(new Result(Status.OK, null, "项目添加成功!"));
				responseBody.setId(id);
				ControllerUtils.setRequestParamsForMessageTip(request,
						project.getProjectName(), project.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	/**
	 * 修改项目信息接口
	 * 
	 * @author yangshuhua
	 * @return
	 * @throws ParseException
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> resetProject(@RequestBody Project project,
			HttpServletRequest request) throws ParseException {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if (project == null || project.getId() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}

		// 执行转换
		project.getProjectContribution();
		project.getProjectValuations();
		project.getCurrencyUnit();
		project.getProjectShareRatio();

		User user = (User) getUserFromSession(request);
		if (project.getProjectValuations() == null) {
			if (project.getProjectShareRatio() != null
					&& project.getProjectShareRatio() > 0
					&& project.getProjectContribution() != null
					&& project.getProjectContribution() > 0) {
				project.setProjectValuations(project.getProjectContribution()
						* 100 / project.getProjectShareRatio());
			}
		}

		Project p = projectService.queryById(project.getId());
		if (p == null) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "未找到相应的项目信息!"));
			return responseBody;
		}
		// 项目创建者用户ID与当前登录人ID是否一样
		if (user.getId().longValue() != p.getCreateUid().longValue()) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "没有权限修改该项目!"));
			return responseBody;
		}
		project.setUpdatedTime(System.currentTimeMillis());
		project.setCreatedTime(DateUtil.convertStringToDate(
				p.getCreateDate().trim(), "yyyy-MM-dd").getTime());

		int num = projectService.updateById(project);
		if (num > 0) {
			responseBody.setResult(new Result(Status.OK, null, "项目修改成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request,
					project.getProjectName(), project.getId());
		}
		return responseBody;
	}

	/**
	 * 查询指定的项目信息接口
	 * 
	 * @author yangshuhua
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/sp/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> selectProject(@PathVariable("pid") String pid,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		Project project = projectService.queryById(Long.parseLong(pid));
		if (project != null) {
			Department Department = new Department();//
			Department.setId(project.getProjectDepartid());
			Department queryOne = departmentService.queryOne(Department);
			Long deptId = null;
			if (queryOne != null) {
				project.setProjectCareerline(queryOne.getName());
				deptId = queryOne.getManagerId();
				if (null != deptId && deptId.longValue() > 0L) {
					User queryById = userService.queryById(queryOne
							.getManagerId());
					if (queryById != null) {
						project.setHhrName(queryById.getRealName());
					}
				}
			}
		} else {
			responseBody
					.setResult(new Result(Status.ERROR, null, "未查找到指定项目信息!"));
			return responseBody;
		}
		responseBody.setEntity(project);
		ControllerUtils.setRequestParamsForMessageTip(request,
				project.getProjectName(), project.getId());
		return responseBody;
	}

	/**
	 * 获取所有事业线 判断选中登录人事业线
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCheckLine", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Department> queryCheckLine(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_KEY);
		ResponseData<Department> responseBody = new ResponseData<Department>();
		try {
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
					.getId());
			List<Department> syxList = null;
			if (roleIdList.contains(UserConstant.DSZ)
					|| roleIdList.contains(UserConstant.CEO)) {
				Department syxType = new Department();
				syxType.setType(1);
				syxList = departmentService.queryList(syxType);// 获取所有事业线

				responseBody.setResult(new Result(Status.OK, null, ""));
				responseBody.setEntityList(syxList);
			} else {
				responseBody.setResult(new Result(Status.OK, null, "notg"));
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "查询事业线失败"));
			if (logger.isErrorEnabled()) {
				logger.error("queryCheckLine 查询事业线失败 ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 获取项目列表(高管)
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllProjects", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> queryAllProjects(HttpServletRequest request,
			@RequestBody ProjectBo project) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		
		Direction direction = Direction.DESC;
		String property = "updated_time";
		if(!StringUtils.isEmpty(project.getProperty())){
			if("desc".equals(project.getDirection())){
				direction = Direction.DESC;
			}else{
				direction = Direction.ASC;
			}
			property = "created_time";
		}
		
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		if (project.getProjectProgress() != null
				&& project.getProjectProgress().equals("guanbi")) {
			project.setProjectStatus("meetingResult:3");
			project.setProjectProgress(null);
		}
		try {
			if (roleIdList.contains(UserConstant.DSZ)
					|| roleIdList.contains(UserConstant.CEO)) {
				/*
				 * Page<Project> pageProject =
				 * projectService.queryPageList(project,new
				 * PageRequest(project.getPageNum(), project.getPageSize()));
				 * responseBody.setPageList(pageProject);
				 * responseBody.setResult(new Result(Status.OK, ""));
				 */
			}
			if (roleIdList.contains(UserConstant.HHR)) {
				project.setProjectDepartid(user.getDepartmentId());
			}

			Page<Project> pageProject = projectService
					.queryPageList(
							project,
							new PageRequest(project.getPageNum(), project
									.getPageSize(),direction,property));
			FormatData format = new FormatData();
			if (!pageProject.getContent().isEmpty()) {
				format = setFormatData(pageProject.getContent());
				if (null != format.getIds() && format.getIds().size() > 0) {
					List<Department> queryListdepById = departmentService
							.queryListById(format.getIds());
					List<String> ids = new ArrayList<String>();
					Map<String, Object> depmap = new HashMap<String, Object>();
					if (!queryListdepById.isEmpty()) {
						depmap = setFormatdepeentDate(queryListdepById)
								.getMap();
						Long deptId = null;
						for (Department depentment : queryListdepById) {
							deptId = depentment.getManagerId();
							if (null != deptId && deptId.longValue() > 0L) {
								ids.add(String.valueOf(deptId));
							}
						}
					}
					FormatData usermapForat = new FormatData();
					Map<String, Object> usermap = new HashMap<String, Object>();
					if (!ids.isEmpty()) {
						List<User> queryListByDepId = userService
								.queryListById(ids);
						if (!queryListByDepId.isEmpty()) {
							usermapForat = setFormatUserDate(queryListByDepId);
							usermap = usermapForat.getMap();
						}
					}
					if (usermap != null || depmap != null) {
						for (Project proje : pageProject.getContent()) {
							String depid = proje.getProjectDepartid()
									.toString();
							if (usermap != null) {
								User u = (User) usermap.get(depid);
								proje.setHhrName(u == null ? "" : u
										.getRealName());
							}
							if (depmap != null) {
								Department dep = (Department) depmap.get(depid);
								proje.setProjectCareerline(dep == null ? ""
										: dep.getName());
							}
						}
					}
					if (null != usermap) {
						usermap.clear();
						usermap = null;
					}
					if (null != depmap) {
						depmap.clear();
						depmap = null;
					}
				}
			}
			responseBody.setPageList(pageProject);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/spl", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseData<Project>
	 * searchProjectList(HttpServletRequest request, @RequestBody ProjectBo
	 * project) { ResponseData<Project> responseBody = new
	 * ResponseData<Project>(); User user = (User) getUserFromSession(request);
	 * project.setCreateUid(user.getId()); project.setrComplany("11");
	 * project.setbComplany(1000d); project.setaComplany(100d);
	 * project.setCascOrDes("created_time"); project.setAscOrDes("asc"); try {
	 * Page<Project> pageProject=null;
	 * if(project.getAscOrDes()!=null&&project.getCascOrDes()!=null){
	 * if(project.getAscOrDes().equals("desc")){ Sort sort = new
	 * Sort(Direction.DESC,project.getCascOrDes()); pageProject =
	 * projectService.queryPageList(project,new
	 * PageRequest(project.getPageNum(), project.getPageSize(),sort));
	 * 
	 * }else if(project.getAscOrDes().equals("asc")){ Sort sort = new
	 * Sort(Direction.ASC,project.getCascOrDes()); pageProject=
	 * projectService.queryPageList(project,new
	 * PageRequest(project.getPageNum(), project.getPageSize(),sort)); } }else{
	 * pageProject= projectService.queryPageList(project,new
	 * PageRequest(project.getPageNum(), project.getPageSize()));
	 * if(project.getProjectProgress
	 * ()!=null&&project.getProjectProgress().equals("guanbi")){
	 * project.setProjectStatus("meetingResult:3");
	 * project.setProjectProgress(null); } }
	 * responseBody.setPageList(pageProject); responseBody.setResult(new
	 * Result(Status.OK, "")); return responseBody; } catch (PlatformException
	 * e) { responseBody.setResult(new Result(Status.ERROR,
	 * "queryUserList faild")); if (logger.isErrorEnabled()) {
	 * logger.error("queryUserList ", e); } } return responseBody; }
	 */
	/**
	 * 获取项目列表(投资经理)
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/spl", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProjectList(HttpServletRequest request,
			@RequestBody ProjectBo project) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Direction direction = Direction.DESC;
		String property = "updated_time";
		if(!StringUtils.isEmpty(project.getProperty())){
			if("desc".equals(project.getDirection())){
				direction = Direction.DESC;
			}else{
				direction = Direction.ASC;
			}
			property = "created_time";
		}
		
		try {
			if (project.getProjectProgress() != null
					&& project.getProjectProgress().equals("guanbi")) {
				project.setProjectStatus("meetingResult:3");
				project.setProjectProgress(null);
			}
			if (project.getProType() != null
					&& "2".equals(project.getProType())) {
				project.setProjectDepartid(user.getDepartmentId());
			} else {
				project.setCreateUid(user.getId());
			}

			Page<Project> pageProject = projectService
					.queryPageList(
							project,
							new PageRequest(project.getPageNum(), project
									.getPageSize(),direction,
									property));

			responseBody.setPageList(pageProject);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 添加团队成员
	 * 
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> addProjectPerson(
			@RequestBody PersonPoolBo pool, HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if (pool.getProjectId() == null || pool.getProjectId() <= 0
				|| pool.getPersonName() == null
				|| pool.getPersonTelephone() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(pool.getProjectId());
		// 项目创建者用户ID与当前登录人ID是否一样
		if (p != null
				&& user.getId().doubleValue() != p.getCreateUid().doubleValue()) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"没有权限为该项目添加团队成员!"));
			return responseBody;
		}
		try {
			pool.setCreatedTime(System.currentTimeMillis());
			Long id = personPoolService.addProjectPerson(pool);
			if (id > 0) {
				responseBody
						.setResult(new Result(Status.OK, null, "团队成员添加成功!"));
				responseBody.setEntity(pool);
				ControllerUtils.setRequestParamsForMessageTip(request,
						p.getProjectName(), p.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	/**
	 * 修改团队成员
	 * 
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/upp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> resetProjectPerson(
			@RequestBody PersonPoolBo pool, HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if (pool == null || pool.getId() == null || pool.getProjectId() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(pool.getProjectId());
		// 项目创建者用户ID与当前登录人ID是否一样
		if (p != null
				&& user.getId().doubleValue() != p.getCreateUid().doubleValue()) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"没有权限修改该项目的团队成员信息!"));
			return responseBody;
		}

		int num = personPoolService.updateById(pool);
		if (num > 0) {
			responseBody.setResult(new Result(Status.OK, null, "团队成员信息修改成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request,
					p.getProjectName(), p.getId());
		}
		return responseBody;
	}

	/**
	 * 删除团队成员
	 * 
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/dpp/{id}/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> deleteProjectPerson(
			@PathVariable("id") Long id,
			@PathVariable("projectId") Long projectId,
			HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if (projectId == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(projectId);
		// 项目创建者用户ID与当前登录人ID是否一样
		if (p != null
				&& user.getId().doubleValue() != p.getCreateUid().doubleValue()) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"没有权限删除该项目的团队成员!"));
			return responseBody;
		}
		ProjectPerson pp = new ProjectPerson();
		pp.setPersonId(id);
		pp.setProjectId(projectId);
		int num = projectPersonService.delete(pp);

		int mump = personPoolService.deleteById(id);

		if (num > 0 && mump > 0) {
			responseBody.setResult(new Result(Status.OK, null, "团队成员删除成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request,
					p.getProjectName(), p.getId());
		}
		return responseBody;
	}

	/**
	 * 查询团队成员列表
	 * 
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProjectPerson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> queryProjectPerson(
			HttpServletRequest request, @RequestBody PersonPoolBo personPoolBo) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		try {
			Page<PersonPool> pageList = personPoolService.queryPageListByPid(
					personPoolBo, new PageRequest(personPoolBo.getPageNum(),
							personPoolBo.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 查询完善简历任务所属的人员列表
	 * 
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPersonListToTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> queryPersonListToTask(
			HttpServletRequest request, @RequestBody PersonPoolBo personPoolBo) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if (personPoolBo.getTid() == null
				|| personPoolBo.getProjectId() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pid", personPoolBo.getProjectId());
			params.put("tid", personPoolBo.getTid());
			List<PersonPool> list = personPoolService.selectNoToTask(params);
			if (list != null && !list.isEmpty()) {
				responseBody.setEntityList(list);
				responseBody.setResult(new Result(Status.OK, null, "查询成功!"));
			}
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null, "异常，请重试!"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 创建项目编码
	 * 
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/cpc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Config> createProjectCode(HttpServletRequest request) {
		ResponseData<Config> responseBody = new ResponseData<Config>();

		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		if (!roleIdList.contains(UserConstant.HHR)
				&& !roleIdList.contains(UserConstant.TZJL)) {
			responseBody.setResult(new Result(Status.ERROR, null, "没有权限!"));
			return responseBody;
		}

		try {
			Config config = configService.createCode();
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMaximumIntegerDigits(6);
			nf.setMinimumIntegerDigits(6);
			Long did = user.getDepartmentId();
			if (did != null) {
				int code = EnumUtil.getCodeByCareerline(did.longValue());
				String projectCode = String.valueOf(code)
						+ nf.format(Integer.parseInt(config.getValue()));
				request.getSession().setAttribute(
						Constants.SESSION_PROJECT_CODE, projectCode);
				config.setPcode(projectCode);
				responseBody.setEntity(config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}

	/***
	 * 获取项目信息
	 * 
	 * @param pid
	 * @param request
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/getProjectInfo/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectBo> projectInfo(@PathVariable("pid") String pid,
			HttpServletRequest request) {

		ProjectBo projectBo = new ProjectBo();
		// 查询项目信息
		ResponseData<ProjectBo> responseBody = new ResponseData<ProjectBo>();
		Project project = projectService.queryById(Long.parseLong(pid));
		if (project == null) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "未查找到指定项目信息!"));
			return responseBody;
		}
		// 项目合伙人
		/**
		 * ProjectPerson person=new ProjectPerson();
		 * person.setProjectId(project.getId()); ProjectPerson
		 * pp=projectPersonService.queryOne(person);
		 * 
		 * if(pp == null ){ responseBody.setResult(new Result(Status.ERROR,
		 * "未查找到指定项目关联合伙人信息!")); return responseBody; } //人资信息 PersonPool
		 * pool=personPoolService.queryById(pp.getPersonId()); if(pool == null){
		 * responseBody.setResult(new Result(Status.ERROR, "未查找到指定项目合伙人信息!"));
		 * return responseBody; }
		 ***/

		projectBo.setProjectName(project.getProjectName());
		projectBo.setProjectCode(project.getProjectCode());
		projectBo.setProjectDescribe(project.getProjectDescribe());
		projectBo.setProjectType(project.getProjectType());
		// projectBo.setPartnerName(pool.getPersonName());
		projectBo.setCreateUname(project.getCreateUname());
		projectBo.setProjectCareerline(project.getProjectCareerline());
		responseBody.setEntity(projectBo);
		ControllerUtils.setRequestParamsForMessageTip(request,
				project.getProjectName(), project.getId());

		return responseBody;
	}

	/**
	 * 跳转到修改项目页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePro/{id}", method = RequestMethod.GET)
	public String updateProject(@PathVariable("id") Long id,
			HttpServletRequest request) {

		PersonPool person = personPoolService.queryById(id);
		if (person == null) {
			return "未查找到指定信息!";
		}
		request.setAttribute("person", person);
		return "project/updatePerson";
	}

	/**
	 * 项目阶段中的文档上传 该项目对应的创建人操作
	 * 
	 * @author yangshuhua voucherType
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/stageChange", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectQuery> stageChange(ProjectQuery p,
			HttpServletRequest request) {
		ResponseData<ProjectQuery> responseBody = new ResponseData<ProjectQuery>();
		// 解析文件上传的非file表单值
		if (p.getPid() == null) {
			String json = JSONUtils.getBodyString(request);
			p = GSONUtil.fromJson(json, ProjectQuery.class);
		}
		/**
		 * 1.参数校验
		 */
		// 所有都必须附带pid和stage
		if (p.getPid() == null
				|| p.getStage() == null
				|| !SopConstant._progress_pattern_.matcher(p.getStage())
						.matches() || p.getParseDate() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		// 如果是内评会、CEO评审会、立项会、投决会,则会议类型和会议结论不能缺少
		if (p.getStage().equals(DictEnum.projectProgress.内部评审.getCode())
				|| p.getStage()
						.equals(DictEnum.projectProgress.CEO评审.getCode())
				|| p.getStage().equals(DictEnum.projectProgress.立项会.getCode())
				|| p.getStage()
						.equals(DictEnum.projectProgress.投资决策会.getCode())) {
			if (p.getMeetingType() == null
					|| !SopConstant._meeting_type_pattern_.matcher(
							p.getMeetingType()).matches()
					|| p.getResult() == null
					|| !SopConstant._meeting_result_pattern_.matcher(
							p.getResult()).matches()) {
				responseBody.setResult(new Result(Status.ERROR, null,
						"必要的参数丢失!"));
				return responseBody;
			}
			// 已有通过的会议，不能再添加会议纪要
			MeetingRecord mrQuery = new MeetingRecord();
			mrQuery.setProjectId(p.getPid());
			mrQuery.setMeetingType(p.getMeetingType());
			mrQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
			Long mrCount = meetingRecordService.queryCount(mrQuery);
			if (mrCount != null && mrCount.longValue() > 0L) {
				responseBody.setResult(new Result(Status.ERROR, null,
						"已有通过的会议，不能再添加会议纪要!"));
				return responseBody;
			}

			// 排期池校验
			if (p.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())
					|| p.getMeetingType().equals(
							DictEnum.meetingType.投决会.getCode())) {
				MeetingScheduling ms = new MeetingScheduling();
				ms.setProjectId(p.getPid());
				ms.setMeetingType(p.getMeetingType());
				ms.setStatus(DictEnum.meetingResult.待定.getCode());
				List<MeetingScheduling> mslist = meetingSchedulingService
						.queryList(ms);
				if (mslist == null || mslist.isEmpty()) {
					responseBody.setResult(new Result(Status.ERROR, "",
							"未在排期池中，不能添加会议记录!"));
					return responseBody;
				}
			}

		}
		Project project = projectService.queryById(p.getPid());
		if (project == null) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "未找到相应的项目信息!"));
			return responseBody;
		}

		// 投资意向书、尽职调查及投资协议的文档上传只能在当前阶段才能进行
		if (p.getStage().equals(DictEnum.projectProgress.投资意向书.getCode())
				|| p.getStage().equals(DictEnum.projectProgress.尽职调查.getCode())
				|| p.getStage().equals(DictEnum.projectProgress.投资协议.getCode())) {
			if (p.getType() == null
					|| p.getFileType() == null
					|| !SopConstant._file_type_pattern_
							.matcher(p.getFileType()).matches()
					|| p.getFileWorktype() == null
					|| !SopConstant._file_worktype_pattern_.matcher(
							p.getFileWorktype()).matches()) {
				responseBody.setResult(new Result(Status.ERROR, null,
						"必要的参数丢失!"));
				return responseBody;
			}
			int in = Integer.parseInt(p.getStage().substring(
					p.getStage().length() - 1));
			int pin = Integer.parseInt(project.getProjectProgress().substring(
					project.getProjectProgress().length() - 1));
			if (in < pin) {
				responseBody
						.setResult(new Result(Status.ERROR, null, "该操作已过期!"));
				return responseBody;
			}
			// 如果是内部创建/未勾选"涉及股权转让"没有股权转让文档
			if (p.getFileWorktype().equals(
					DictEnum.fileWorktype.股权转让协议.getCode())) {
				if (project != null
						&& project.getProjectType() != null
						&& project.getProjectType().equals(
								DictEnum.projectType.内部创建.getCode())) {
					responseBody.setResult(new Result(Status.ERROR, null,
							"内部创建项目不需要股权转让协议!"));
					return responseBody;
				} else if (project.getStockTransfer() == null
						|| project.getStockTransfer() == 0) {
					responseBody.setResult(new Result(Status.ERROR, null,
							"项目未选择涉及股权转让!"));
					return responseBody;
				}
			}
			/**
			 * 上传签署凭证时要对相对应的文档是否已上传进行校验
			 */
			if (p.getVoucherType() != null
					&& p.getVoucherType().intValue() == 1) {
				SopFile fileQuery = null;
				if (p.getFileWorktype().equals(
						DictEnum.fileWorktype.投资意向书.getCode())) {
					// file表
					fileQuery = new SopFile();
					fileQuery.setProjectId(p.getPid());
					fileQuery.setFileWorktype(DictEnum.fileWorktype.投资意向书
							.getCode());
					fileQuery = sopFileService.queryOne(fileQuery);
					if (fileQuery.getFileKey() == null
							|| fileQuery.getBucketName() == null) {
						responseBody.setResult(new Result(Status.ERROR, null,
								"前置文件缺失!"));
						return responseBody;
					}
				} else if (p.getFileWorktype().equals(
						DictEnum.fileWorktype.投资协议.getCode())) {
					fileQuery = new SopFile();
					fileQuery.setProjectId(p.getPid());
					fileQuery.setFileWorktype(DictEnum.fileWorktype.投资协议
							.getCode());
					fileQuery = sopFileService.queryOne(fileQuery);
					if (fileQuery.getFileKey() == null
							|| fileQuery.getBucketName() == null) {
						responseBody.setResult(new Result(Status.ERROR, null,
								"前置文件缺失!"));
						return responseBody;
					}
				} else if (p.getFileWorktype().equals(
						DictEnum.fileWorktype.股权转让协议.getCode())) {
					fileQuery = new SopFile();
					fileQuery.setProjectId(p.getPid());
					fileQuery.setFileWorktype(DictEnum.fileWorktype.股权转让协议
							.getCode());
					fileQuery = sopFileService.queryOne(fileQuery);
					if (fileQuery.getFileKey() == null
							|| fileQuery.getBucketName() == null) {
						responseBody.setResult(new Result(Status.ERROR, null,
								"前置文件缺失!"));
						return responseBody;
					}

					// 验证投资协议签署证明是否已上传
					SopFile fq = new SopFile();
					fq.setProjectId(p.getPid());
					fq.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
					fq = sopFileService.queryOne(fq);
					Long voucherId = fq.getVoucherId();
					if (voucherId == null) {
						responseBody.setResult(new Result(Status.ERROR, null,
								"数据异常!"));
						return responseBody;
					}
					SopVoucherFile f = sopVoucherFileService
							.queryById(voucherId);
					if (f.getFileKey() == null || f.getBucketName() == null) {
						responseBody.setResult(new Result(Status.ERROR, null,
								"缺失投资协议签署证明!"));
						return responseBody;
					}
				}
			}
		}

		User user = (User) getUserFromSession(request);
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

		// 验证是否文件是必须的
		if (!p.getStage().equals(DictEnum.projectProgress.接触访谈.getCode())
				&& !p.getStage()
						.equals(DictEnum.projectProgress.内部评审.getCode())
				&& !p.getStage().equals(
						DictEnum.projectProgress.CEO评审.getCode())
				&& !p.getStage().equals(DictEnum.projectProgress.立项会.getCode())
				&& !p.getStage().equals(
						DictEnum.projectProgress.投资决策会.getCode())) {
			if (result == null
					|| !result.getResult().getStatus().equals(Result.Status.OK)) {
				responseBody
						.setResult(new Result(Status.ERROR, null, "缺失相应文档!"));
				return responseBody;
			}
		}

		/**
		 * 3.处理业务
		 */
		try {
			if (result != null
					&& result.getResult().getStatus().equals(Result.Status.OK)) {
				p.setFileName(result.getFileName());
				p.setSuffix(result.getFileSuffix());
				p.setBucketName(result.getBucketName());
				p.setFileKey(fileKey);
				p.setFileSize(result.getContentLength());
			}
			if (handlerManager.getStageHandlers().containsKey(p.getStage())) {
				Handler handler = handlerManager.getStageHandlers().get(
						p.getStage());
				SopResult r = handler.handler(p, project);
				if (r != null && r.getStatus().equals(Result.Status.OK)) {
					responseBody.setResult(r);
					// 记录操作日志
					ControllerUtils.setRequestParamsForMessageTip(request,
							project.getProjectName(), project.getId(),
							r.getNumber());
				}
			}
		} catch (Exception e) {
			logger.error("操作失败", e);
			responseBody.getResult().addError("操作失败!");
		}

		return responseBody;
	}

	/**
	 * 是否涉及"股权转让"点击事件
	 */
	@ResponseBody
	@RequestMapping(value = "/store/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> store(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if (pid == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		Project project = projectService.queryById(pid);
		if (project == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "未找到指定的项目!"));
			return responseBody;
		}

		int r = (project.getStockTransfer() == null || project
				.getStockTransfer().intValue() == 0) ? 1 : 0;
		if (logger.isInfoEnabled()) {
			logger.info("old stockTransfer:" + project.getStockTransfer()
					+ ", new stockTransfer:" + r);
		}
		project.setStockTransfer(r);
		projectService.updateById(project);
		responseBody.setEntity(project);
		responseBody.setResult(new Result(Status.OK, null, ""));
		return responseBody;
	}

	/**
	 * 接触访谈阶段: 启动内部评审
	 * 
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/startReview/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> startReview(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.接触访谈.getCode(),
				project, user);
		if (!result.getStatus().equals(Status.OK)) {
			responseBody.setResult(result);
			return responseBody;
		}
		InterviewRecord ir = new InterviewRecord();
		ir.setProjectId(pid);
		Long count = interviewRecordService.queryCount(ir);
		if (count != null && count.doubleValue() > 0) {
			try {
				project.setProjectProgress(DictEnum.projectProgress.内部评审
						.getCode()); // 字典 项目进度 内部评审
				project.setProjectStatus(DictEnum.meetingResult.待定.getCode()); // 字典
																				// 项目状态
																				// =
																				// 会议结论
																				// 待定
				projectService.updateById(project);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request,
						project.getProjectName(), project.getId());
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR, null,
						"异常，启动内部评审失败!"));
				if (logger.isErrorEnabled()) {
					logger.error("update project faild ", e);
				}
			}
		} else {
			responseBody.setResult(new Result(Status.ERROR, null,
					"不存在访谈记录，不允许启动内部评审!"));
		}
		return responseBody;
	}

	/**
	 * CEO评审阶段申请CEO评审排期
	 * 
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/incm/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> inCeoMeetingPool(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.CEO评审.getCode(),
				project, user);
		if (!result.getStatus().equals(Status.OK)) {
			responseBody.setResult(result);
			return responseBody;
		}
		try {
			MeetingScheduling m = new MeetingScheduling();
			m.setProjectId(project.getId());
			m.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
			MeetingScheduling tm = meetingSchedulingService.queryOne(m);
			if (!tm.getStatus().equals(DictEnum.meetingResult.待定.getCode())) {
				tm.setStatus(DictEnum.meetingResult.待定.getCode());
				tm.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				tm.setUpdatedTime((new Date()).getTime());
				tm.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingService.updateById(tm);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
			} else {
				responseBody.setResult(new Result(Status.ERROR, null,
						"项目不能重复申请CEO评审排期!"));
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"异常，申请CEO评审排期失败!"));
			if (logger.isErrorEnabled()) {
				logger.error("update project faild ", e);
			}
		}
		return responseBody;
	}

	/**
	 * CEO评审阶段申请立项会排期
	 * 
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/ges/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> ges(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.CEO评审.getCode(),
				project, user);
		if (!result.getStatus().equals(Status.OK)) {
			responseBody.setResult(result);
			return responseBody;
		}
		// 必须又一次会议记录为通过
		MeetingRecord mr = new MeetingRecord();
		mr.setProjectId(pid);
		mr.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
		mr.setMeetingResult(DictEnum.meetingResult.通过.getCode());
		Long count = meetingRecordService.queryCount(mr);
		if (count != null && count.doubleValue() > 0) {
			try {
				projectService.toEstablishStage(project);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request,
						project.getProjectName(), project.getId());
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR, null,
						"异常，申请立项会失败!"));
				if (logger.isErrorEnabled()) {
					logger.error("update project faild ", e);
				}
			}
		} else {
			responseBody.setResult(new Result(Status.ERROR, null,
					"不存在通过的会议记录，不能申请立项会!"));
		}
		return responseBody;
	}

	/**
	 * 立项会阶段申请立项会排期
	 * 
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/inlx/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> inLxmeetingPool(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.立项会.getCode(),
				project, user);
		if (!result.getStatus().equals(Status.OK)) {
			responseBody.setResult(result);
			return responseBody;
		}
		try {
			MeetingScheduling m = new MeetingScheduling();
			m.setProjectId(project.getId());
			m.setMeetingType(DictEnum.meetingType.立项会.getCode());
			MeetingScheduling tm = meetingSchedulingService.queryOne(m);
			if (!tm.getStatus().equals(DictEnum.meetingResult.待定.getCode())) {
				tm.setStatus(DictEnum.meetingResult.待定.getCode());
				tm.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				tm.setUpdatedTime((new Date()).getTime());
				tm.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingService.updateById(tm);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
			} else {
				responseBody.setResult(new Result(Status.ERROR, null,
						"项目不能重复申请立项会排期!"));
			}
		} catch (Exception e) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "异常，申请立项会失败!"));
			if (logger.isErrorEnabled()) {
				logger.error("update project faild ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 尽职调查阶段--申请投决会排期
	 * 
	 * @author yangshuhua
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/smp/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> sureMeetingPool(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.尽职调查.getCode(),
				project, user);
		if (!result.getStatus().equals(Status.OK)) {
			responseBody.setResult(result);
			return responseBody;
		}
		// 验证文档是否齐全
		SopFile file = new SopFile();
		file.setProjectId(pid);
		file.setFileValid(1);
		file.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		List<SopFile> files = sopFileService.queryList(file);
		if (files == null
				|| (project.getProjectType().equals(
						DictEnum.projectType.外部投资.getCode()) && files.size() < 4)
				|| (project.getProjectType().equals(
						DictEnum.projectType.内部创建.getCode()) && files.size() < 2)) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"文档不齐全，不能申请投决会!"));
			return responseBody;
		}
		for (SopFile f : files) {
			if (f.getFileKey() == null || "".equals(f.getFileKey().trim())) {
				responseBody.setResult(new Result(Status.ERROR, null,
						"文档不齐全，不能申请投决会!"));
				return responseBody;
			}
		}
		try {
			projectService.toSureMeetingStage(project);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
			ControllerUtils.setRequestParamsForMessageTip(request,
					project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "异常，申请投决会失败!"));
			if (logger.isErrorEnabled()) {
				logger.error("update project faild ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 投决会阶段--申请投决会排期
	 * 
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/intj/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> inSureMeetingPool(HttpServletRequest request,
			@PathVariable("pid") Long pid) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		Project project = projectService.queryById(pid);
		Result result = validate(DictEnum.projectProgress.投资决策会.getCode(),
				project, user);
		if (!result.getStatus().equals(Status.OK)) {
			responseBody.setResult(result);
			return responseBody;
		}
		try {
			MeetingScheduling m = new MeetingScheduling();
			m.setProjectId(project.getId());
			m.setMeetingType(DictEnum.meetingType.投决会.getCode());
			MeetingScheduling tm = meetingSchedulingService.queryOne(m);
			if (!tm.getStatus().equals(DictEnum.meetingResult.待定.getCode())) {
				tm.setStatus(DictEnum.meetingResult.待定.getCode());
				tm.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				tm.setUpdatedTime((new Date()).getTime());
				tm.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingService.updateById(tm);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
			} else {
				responseBody.setResult(new Result(Status.ERROR, null,
						"项目不能重复申请立项会排期!"));
			}
		} catch (Exception e) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "异常，申请投决会失败!"));
			if (logger.isErrorEnabled()) {
				logger.error("update project faild ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 判断项目的操作是否合法
	 * 
	 * @author yangshuhua
	 */
	public Result validate(String progress, Project project, User user) {
		if (project == null) {
			return new Result(Status.ERROR, null, "未找到相应的项目信息!");
		}
		if (project.getProjectStatus().equals(
				DictEnum.meetingResult.否决.getCode())) {
			return new Result(Status.ERROR, null, "项目已关闭!");
		}

		if (user.getId().longValue() != project.getCreateUid().longValue()) {
			return new Result(Status.ERROR, null, "没有权限修改该项目!");
		}
		int in = Integer.parseInt(progress.substring(progress.length() - 1));
		int pin = Integer.parseInt(project.getProjectProgress().substring(
				project.getProjectProgress().length() - 1));
		if (in < pin) {
			return new Result(Status.ERROR, "501", "该操作已过期!");
		}
		return new Result(Status.OK, "200", null);
	}

	/**
	 * 关闭项目
	 * 
	 * @param pid
	 *            项目id
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/breakpro/{pid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> breakproject(@PathVariable Long pid,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();

		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_KEY);
		try {
			// project id 验证
			Project project = new Project();
			project = projectService.queryById(pid);
			// 项目关闭将会议记录修改为否决项目
			MeetingScheduling me = new MeetingScheduling();
			me.setProjectId(pid);
			List<MeetingScheduling> meetingList = meetingSchedulingService
					.queryList(me);
			if (!meetingList.isEmpty()) {
				for (MeetingScheduling meet : meetingList) {
					meet.setStatus(DictEnum.meetingResult.否决.getCode());
					meet.setScheduleStatus(DictEnum.meetingSheduleResult.已否决.getCode());
				}
			}
			meetingSchedulingService.updateBatch(meetingList);
			if (project == null || project.getCreateUid() == null) {
				responseBody
						.setResult(new Result(Status.ERROR, null, "项目检索不到"));
				return responseBody;
			} else {
				if (!project.getCreateUid().equals(user.getId())) {
					responseBody.setResult(new Result(Status.ERROR, null,
							"无操作权限"));
					return responseBody;
				}
			}

			project.setProjectStatus(DictEnum.meetingResult.否决.getCode());
			int id = projectService.closeProject(project);
			if (id != 1) {
				responseBody.setResult(new Result(Status.ERROR, null, "更新失败"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			ControllerUtils.setRequestParamsForMessageTip(request,
					project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"add meetingRecord faild"));

			if (logger.isErrorEnabled()) {
				logger.error("add meetingRecord faild ", e);
			}
		}

		return responseBody;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/getSummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData getSummary(HttpServletRequest request) {
		ResponseData resp = new ResponseData();
		try {
			String userId = getUserId(request);
			Map<String, Object> summary = null;
			if (StringUtils.isNotEmpty(userId)) {
				summary = projectService.getSummary(Long.valueOf(userId));
			}
			resp.setUserData(summary);
		} catch (Exception e) {
			logger.error("获取数据快览失败", e);
			resp.getResult().addError("获取数据快览失败");
		}

		return resp;
	}

	public String getHHRNname(Project p) {
		String hhrname = "";
		UserRole userrole = new UserRole();
		userrole.setRoleId(UserConstant.HHR);
		List<UserRole> queryList = userRoleService.queryList(userrole);
		if (queryList != null && queryList.size() > 0) {
			for (UserRole ur : queryList) {
				Long userid = ur.getUserId();
				User queryById = userService.queryById(userid);
				if (queryById != null) {

					if (null == queryById.getDepartmentId()) {
						return "";
					}
					if (queryById.getDepartmentId().equals(
							p.getProjectDepartid())) {
						hhrname = queryById.getRealName();
					}
				}
			}
		}
		return hhrname;
	}

	/**
	 * Ajax判断项目名称，组织机构代码是否重复
	 */
	@RequestMapping(value = "checkProject")
	@ResponseBody
	public Map<String, Integer> checkProject(@RequestBody Project query) {
		// String projectCompanyCode = "";
		if (query != null && query.getProjectCompanyCode() != null) {
			// projectCompanyCode = query.getProjectCompanyCode();
			query.setProjectCompanyCode(null);
		}
		List<Project> projectList = projectService.queryList(query);
		// Integer count = 0 ;
		// if (!StringUtils.equals(projectCompanyCode,"")) {
		// for (Project project: projectList) {

		// if (project.getProjectCompanyCode()!= null &&
		// StringUtils.equals(projectCompanyCode,
		// project.getProjectCompanyCode())) {
		// count ++;
		// }
		// }
		// }
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (null == projectList || projectList.size() < 1) {
			// 不存在重复
			map.put("count", 0);
			// else if (count > 0) {
			// 重复且相同组织机构数为count
			// map.put("companyCode", count);
			// map.put("count", projectList.size());
		} else {
			map.put("count", projectList.size());
		}
		return map;
	}

	/**
	 * 验证sop流程中按钮是否可用
	 */
	@RequestMapping(value = "/checkCanUse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData<MeetingRecord> checkCanUse(HttpServletRequest request,
			Integer index, Long projectId, String projectType) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		try {
			if (index == null || projectId == null) {
				responseBody.setResult(new Result(Status.ERROR, null, "入参失败"));
				return responseBody;
			}
			if (index == 1) { // 访谈纪要判断
				InterviewRecord view = new InterviewRecord();
				view.setProjectId(projectId);
				List<InterviewRecord> viewList = interviewRecordService
						.queryList(view);
				if (viewList == null || viewList.isEmpty()) {
					responseBody.setResult(new Result(Status.OK, null, false)); // 无记录
																				// false：启动内部评审按钮不可用
				} else {
					responseBody.setResult(new Result(Status.OK, null, true));
				}

			} else if (index == 3) { // CEO评审，会议通过，可以 启用 申请立项会排期按钮
				Result result = new Result(Status.OK, null, null);
				MeetingScheduling meetSchedu = new MeetingScheduling();
				meetSchedu.setProjectId(projectId);
				meetSchedu.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
				meetSchedu.setStatus(DictEnum.meetingResult.待定.getCode());
				List<MeetingScheduling> meetScheduList = meetingSchedulingService
						.queryList(meetSchedu);
				if (meetScheduList == null || meetScheduList.isEmpty()) {
					result.setErrorCode("100");
				} else {
					result.setErrorCode("101");
				}

				MeetingRecord meet = new MeetingRecord();
				meet.setProjectId(projectId);
				meet.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
				meet.setMeetingResult(DictEnum.meetingResult.通过.getCode());
				List<MeetingRecord> meetList = meetingRecordService
						.queryList(meet);
				if (meetList == null || meetList.isEmpty()) {
					result.setMessage(false);
				} else {
					result.setMessage(true);
				}
				responseBody.setResult(result);
				return responseBody;
			} else if (index == 4) { // 立项会阶段，在排期池中时， 申请立项会排期按钮 不可用
				MeetingScheduling meetSchedu = new MeetingScheduling();
				meetSchedu.setProjectId(projectId);
				meetSchedu.setMeetingType(DictEnum.meetingType.立项会.getCode());
				meetSchedu.setStatus(DictEnum.meetingResult.待定.getCode());
				List<MeetingScheduling> meetScheduList = meetingSchedulingService
						.queryList(meetSchedu);
				if (meetScheduList == null || meetScheduList.isEmpty()) {
					responseBody.setResult(new Result(Status.OK, null, true)); // 池中没有记录
				} else {
					responseBody.setResult(new Result(Status.OK, null, false));
				}
			} else if (index == 6) { // 尽调阶段，文档齐全后， 申请投决会排期按钮 可用
				if (projectType == null) {
					responseBody.setResult(new Result(Status.ERROR, null,
							"入参失败"));
					return responseBody;
				}
				// 验证文档是否齐全
				SopFile file = new SopFile();
				file.setProjectId(projectId);
				file.setFileValid(1);
				file.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
				List<SopFile> files = sopFileService.queryList(file);

				boolean allHas = true;
				if (files != null
						&& ((projectType.equals(DictEnum.projectType.外部投资
								.getCode()) && files.size() == 4) || (projectType
								.equals(DictEnum.projectType.内部创建.getCode()) && files
								.size() == 2))) {
					for (SopFile f : files) {
						if (f.getFileKey() == null
								|| "".equals(f.getFileKey().trim())) {
							allHas = false;
							break;
						}
					}
				} else {
					allHas = false;
				}
				responseBody.setResult(new Result(Status.OK, null, allHas));

			} else if (index == 7) { // 投决会阶段，在排期池中时， 申请投决会排期按钮 不可用
				MeetingScheduling meetSchedu = new MeetingScheduling();
				meetSchedu.setProjectId(projectId);
				meetSchedu.setMeetingType(DictEnum.meetingType.投决会.getCode());
				meetSchedu.setStatus(DictEnum.meetingResult.待定.getCode());
				List<MeetingScheduling> meetScheduList = meetingSchedulingService
						.queryList(meetSchedu);
				if (meetScheduList == null || meetScheduList.isEmpty()) {
					responseBody.setResult(new Result(Status.OK, null, true)); // 池中没有记录
				} else {
					responseBody.setResult(new Result(Status.OK, null, false));
				}
			}

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "验证失败"));
			if (logger.isErrorEnabled()) {
				logger.error("checkCanUse 验证失败", e);
			}
		}

		return responseBody;
	}

	/**
	 * 排期池中是否存在 状态为待定
	 * 
	 * CEO评审("CEO评审","meetingType:2"), 立项会("立项会","meetingType:3"),
	 * 投决会("投决会","meetingType:4");
	 */
	@RequestMapping(value = "checkHasPool")
	@ResponseBody
	public ResponseData<MeetingScheduling> checkHasPool(
			@RequestBody MeetingScheduling query) {
		ResponseData<MeetingScheduling> responseBody = new ResponseData<MeetingScheduling>();
		List<MeetingScheduling> list = new ArrayList<MeetingScheduling>();
		try {
			if (query.getProjectId() == null || query.getMeetingType() == null) {
				responseBody.setResult(new Result(Status.ERROR, null, "参数缺失"));
				return responseBody;
			}
			query.setStatus(DictEnum.meetingResult.待定.getCode());

			list = meetingSchedulingService.queryList(query);
			if (list != null && !list.isEmpty()) {
				if (list.size() == 1) {
					responseBody.setResult(new Result(Status.OK, ""));
					responseBody.setId(list.get(0).getId());
				} else {
					responseBody.setResult(new Result(Status.ERROR, null,
							"数据返回错误"));
					logger.error("checkHasPool 数据返回错误,应返回一条数据  "
							+ GSONUtil.toJson(list));
				}
			} else {
				responseBody.setResult(new Result(Status.OK, ""));
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "查询失败"));
			if (logger.isErrorEnabled()) {
				logger.error("checkHasPool 查询失败", e);
			}
		}

		return responseBody;
	}

	/**
	 * 排期池中是否存在 状态为待定
	 * 
	 * CEO评审("CEO评审","meetingType:2"), 立项会("立项会","meetingType:3"),
	 * 投决会("投决会","meetingType:4");
	 */
	@RequestMapping(value = "checkPassMeet")
	@ResponseBody
	public ResponseData<MeetingRecord> checkPassMeet(
			@RequestBody MeetingRecord query) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		List<MeetingRecord> list = new ArrayList<MeetingRecord>();
		try {
			if (query.getProjectId() == null || query.getMeetingType() == null) {
				responseBody.setResult(new Result(Status.ERROR, null, "参数缺失"));
				return responseBody;
			}
			query.setMeetingResult(DictEnum.meetingResult.通过.getCode());

			list = meetingRecordService.queryList(query);

			if (list != null && !list.isEmpty()) {
				if (list.size() == 1) {
					responseBody.setResult(new Result(Status.OK, ""));
					responseBody.setId(list.get(0).getId());
				} else {
					responseBody.setResult(new Result(Status.ERROR, null,
							"数据返回错误"));
					logger.error("checkPassMeet 数据返回错误,应返回一条数据  "
							+ GSONUtil.toJson(list));
				}
			} else {
				responseBody.setResult(new Result(Status.OK, ""));
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "查询失败"));
			if (logger.isErrorEnabled()) {
				logger.error("checkPassMeet 查询失败", e);
			}
		}

		return responseBody;
	}

	/*
	 * 将项目的list封装成一个FormatData对象
	 * 
	 * @param plist
	 * 
	 * @return
	 */
	public FormatData setFormatData(List<Project> plist) {
		FormatData formatData = new FormatData();
		Map<String, Object> projectmap = new HashMap<String, Object>();
		List<String> ids = new ArrayList<String>();

		for (Project p : plist) {
			projectmap.put(p.getProjectDepartid().toString(), p);
			ids.add(p.getProjectDepartid().toString());
		}
		formatData.setIds(ids);
		formatData.setMap(projectmap);
		return formatData;
	}

	/**
	 * 将项目的list封装成一个FormatData对象
	 * 
	 * @param plist
	 * @return
	 */
	public FormatData setFormatUserDate(List<User> userList) {
		FormatData formatData = new FormatData();
		Map<String, Object> usermap = new HashMap<String, Object>();
		for (User user : userList) {
			usermap.put(user.getDepartmentId().toString(), user);
		}
		formatData.setMap(usermap);
		return formatData;
	}

	/**
	 * 将项目的list封装成一个FormatData对象
	 * 
	 * @param plist
	 * @return
	 */
	public FormatData setFormatdepeentDate(List<Department> depList) {
		FormatData formatData = new FormatData();
		List<String> ids = new ArrayList<String>();
		Map<String, Object> usermap = new HashMap<String, Object>();
		Long deptId = null;
		for (Department dep : depList) {
			usermap.put(String.valueOf(dep.getId()), dep);
			deptId = dep.getManagerId();
			if (null != deptId && deptId.longValue() > 0L) {
				ids.add(String.valueOf(deptId));
			}
		}
		formatData.setIds(ids);
		formatData.setMap(usermap);
		return formatData;
	}

	/***
	 * 更新文件：1.投资意向书;2.更新尽职调查;3.更新投资协议|股权转让...
	 * 
	 * @param p
	 * @param request
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/updateCommonFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectQuery> updateCommonFile(ProjectQuery p,
			HttpServletRequest request) {
		ResponseData<ProjectQuery> responseBody = new ResponseData<ProjectQuery>();
		// 参数校验
		if (p.getPid() == null
				|| p.getStage() == null
				|| !SopConstant._progress_pattern_.matcher(p.getStage())
						.matches() || p.getParseDate() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		SopFile sopFile = null;
		String fileKey = null;
		try {
			if (p.getId() != null) {
				sopFile = sopFileService.queryById(p.getId());
			} else {
				responseBody.setResult(new Result(Status.ERROR, null,
						"所存在的更新文件丢失!"));
			}
			if (sopFile.getFileKey() == null) {
				fileKey = String.valueOf(IdGenerator
						.generateId(OSSHelper.class));
			} else {
				fileKey = sopFile.getFileKey();
			}
			// 更新文件服务器信息
			UploadFileResult result = uploadFileToOSS(request, fileKey,
					tempfilePath);
			if (result == null
					|| !result.getResult().getStatus().equals(Result.Status.OK)) {
				responseBody
						.setResult(new Result(Status.ERROR, null, "缺失相应文档!"));
				return responseBody;
			}
			// 更新文件数据表信息
			sopFile.setFileSource(String.valueOf(p.getType()));
			sopFile.setFileType(p.getFileType());
			sopFile.setFileWorktype(p.getFileWorktype());
			sopFile.setFileName(result.getFileName());
			sopFile.setFileSuffix(result.getFileSuffix());
			sopFile.setBucketName(result.getBucketName());
			sopFile.setFileKey(fileKey);
			sopFile.setFileLength(result.getContentLength());
			sopFileService.updateById(sopFile);
			responseBody.setResult(new Result(Status.OK, null, "更新文件成功!"));
		} catch (Exception e) {
			responseBody.getResult().addError("更新失败");
			logger.error("更新失败", e);
		}

		return responseBody;
	}

	@ResponseBody
	@RequestMapping(value = "/getDegreeByParent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Dict> getDictByParent(@PathVariable String id,
			HttpServletRequest request) {
		ResponseData<Dict> responseBody = new ResponseData<Dict>();
		List<Dict> dicts = new ArrayList<Dict>();
		Dict dict = null;
		Result result = new Result();
		try {
			for (DictEnum.degree degree : DictEnum.degree.values()) {
				dict = new Dict();
				dict.setCode(degree.getCode());
				dict.setName(degree.getName());
				dicts.add(dict);
			}
		} catch (PlatformException e) {
			result.setErrorCode(e.getCode() + "");
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setMessage("系统错误");
			result.addError("系统错误");
			logger.error("根据parentId查找数据字典错误", e);
		}
		if (!("null").equals(id)) {
			result.setMessage(id);
		}
		result.setStatus(Status.OK);
		responseBody.setEntityList(dicts);
		responseBody.setResult(result);
		return responseBody;
	}

	/**
	 * 排期池列表查询
	 * 
	 * @param type
	 *            -- 1表示立项会、2表示投决会、3表示CEO内评会
	 */
	@ResponseBody
	@RequestMapping(value = "/queryScheduling/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingScheduling> searchSchedulingList(
			HttpServletRequest request, @PathVariable("type") Integer type,
			@RequestBody MeetingSchedulingBo query) {

		ResponseData<MeetingScheduling> responseBody = new ResponseData<MeetingScheduling>();
		Direction direction = Direction.ASC;
		String property = "apply_time";
		
		if(!StringUtils.isEmpty(query.getProperty())){
			if("desc".equals(query.getDirection())){
				direction = Direction.DESC;
			}else{
				direction = Direction.ASC;
			}
			if("meetingDate".equals(query.getProperty())){
				property = "meeting_date";
			}else if("reserveTimeStart".equals(query.getProperty())){
				property = "reserve_time_start";
			}
		}
		PageRequest pageable = new PageRequest(0, 10, direction,
				property);
		Page<MeetingScheduling> pageEntity = new Page<MeetingScheduling>(null,
				pageable, null);
		List<MeetingScheduling> sl = new ArrayList<MeetingScheduling>();
		pageEntity.setTotal(new Long(0));
		pageEntity.setContent(sl);
		responseBody.setPageList(pageEntity);
		responseBody.setResult(new Result(Status.OK, ""));
		try {
			if (type.intValue() == 0) {
				query.setMeetingType(DictEnum.meetingType.立项会.getCode());
			} else if (type.intValue() == 1) {
				query.setMeetingType(DictEnum.meetingType.投决会.getCode());
			} else if (type.intValue() == 2) {
				query.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
			}
			byte isEdit = 0;
			User user = (User) getUserFromSession(request);

			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
					.getId());

			if (roleIdList.contains(UserConstant.DMS)) {

				if (type.intValue() == 0 || type.intValue() == 1) {
					isEdit = 1;
				} else {
					isEdit = 2;
				}
			} else if (roleIdList.contains(UserConstant.CEOMS)) {
				if (type.intValue() == 2) {
					isEdit = 1;
				} else {
					isEdit = 2;
				}
			} else {
				responseBody.setResult(new Result(Status.ERROR, null, "不可见!"));
				return responseBody;
			}
			/**
			 * 默认查询待排期的，可通过条件查询搜索其他
			 */
			if (query.getScheduleStatus() == null) {
				query.setScheduleStatus(0);
			}
			/**
			 * 查询出所有的事业线
			 */
			List<Long> depids = new ArrayList<Long>();
			Map<Long, Department> careerlineMap = new HashMap<Long, Department>();
			Department d = new Department();
			if (query.getCareline() != null) {
				Department de = departmentService.queryById(new Long(query
						.getCareline()));
				careerlineMap.put(de.getId(), de);
				depids.add(de.getId());
			} else {
				List<Department> careerlineList = departmentService
						.queryList(d);
				for (Department department : careerlineList) {
					careerlineMap.put(department.getId(), department);
					depids.add(department.getId());
				}
				d.setType(1);
			}
			/**
			 * 查询出相关的所有项目
			 */
			List<Project> projectCommonList = new ArrayList<Project>();
			List<MeetingScheduling> schedulingList = new ArrayList<MeetingScheduling>();
			Page<MeetingScheduling> pageList = null;
			ProjectBo mpb = new ProjectBo();
			if (query.getKeyword() != null) {
				mpb.setKeyword(query.getKeyword());
			}
			mpb.setDeptIdList(depids);
			projectCommonList = projectService.queryList(mpb);
			/**
			 * 根据相关项目查找排期池数据
			 */
			List<Long> pids = new ArrayList<Long>();
			if (projectCommonList != null && projectCommonList.size() > 0) {
				for (Project pr : projectCommonList) {
					pids.add(pr.getId());
				}
				query.setProjectIdList(pids);
				pageList = meetingSchedulingService
						.getMeetingList(
								query,
								new PageRequest(query.getPageNum(), query.getPageSize(),direction,
										property));
				schedulingList = pageList.getContent();
			} else {
				return responseBody;
			}
			/***
			 * 若无数据则返回
			 */
			if (schedulingList.size() == 0) {
				return responseBody;
			}

			List<String> ids = new ArrayList<String>();
			for (MeetingScheduling ms : schedulingList) {
				byte Edit = 1;
				Integer sheduleStatus = ms.getScheduleStatus();
				if (sheduleStatus == 2 || sheduleStatus == 3) {
					Edit = 0;
				}
				if (ms.getReserveTimeStart() != null) {
					long time = System.currentTimeMillis();
					long startTime = ms.getReserveTimeStart().getTime();
					if ((time > startTime) && sheduleStatus == 1) {
						Edit = 0;
					}
				}
				ms.setIsEdit(Edit);
				ids.add(String.valueOf(ms.getProjectId()));
			}

			/**
			 * 查询出相关的所有项目
			 */
			ProjectBo pb = new ProjectBo();
			pb.setIds(ids);
			List<Project> projectList = projectService.queryList(pb);

			// 组装项目的投资经理uid
			List<String> uids = new ArrayList<String>();
			for (Project pr : projectList) {
				uids.add(String.valueOf(pr.getCreateUid()));
			}
			// 获取投资经理的过会率
			PassRateBo borate = new PassRateBo();
			borate.setUids(uids);
			borate.setRateType(type.intValue());
			List<PassRate> prateList = passRateService.queryListById(borate);
			Map<Long, PassRate> passRateMap = new HashMap<Long, PassRate>();
			
			if (prateList.size() > 0) {
				for (PassRate pr : prateList) {
					passRateMap.put(pr.getUid(), pr);
				}
			}
			// 组装数据
			for (MeetingScheduling ms : schedulingList) {
				for (Project p : projectList) {
					if (ms.getProjectId().longValue() == p.getId().longValue()) {
						if (passRateMap.get(p.getCreateUid()) != null) {
							ms.setMeetingRate(passRateMap.get(p.getCreateUid())
									.getRate());
						} else {
							ms.setMeetingRate(new Double(0));
						}
						ms.setProjectCode(p.getProjectCode());
						ms.setProjectName(p.getProjectName());
						ms.setProjectCareerline(careerlineMap.get(
								p.getProjectDepartid()).getName());
						ms.setCreateUname(p.getCreateUname());
					}

				}
			}
			 
			pageEntity.setTotal(pageList.getTotal());
			pageEntity.setContent(pageList.getContent());
			responseBody.setPageList(pageEntity);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}


	/**
	 * 更新排期池时间/updateReserveTime
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.MESSAGE)
	@ResponseBody
	@RequestMapping(value = "/updateReserveTime", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingScheduling> updateReserveTime(
			HttpServletRequest request,
			@RequestBody List<MeetingScheduling> query) {
		
		ResponseData<MeetingScheduling> responseBody = new ResponseData<MeetingScheduling>();
		if (query == null || query.size() == 0) {
			responseBody.setResult(new Result(Status.ERROR, null, "无操作数据!"));
			return responseBody;
		}
		//对要排期的会议进行重组
		List<Long> ids=new ArrayList<Long>();
		List<Long> meetingids = new ArrayList<Long>();
    	for(MeetingScheduling ms:query){
    		meetingids.add(ms.getId());
    	}
    	//判断是否为投决会
    	boolean flag = false;
    	Map<Long,List<String>> proMap= new HashMap<Long,List<String>>();
    	//要操作的排期
    	MeetingScheduling msche = new MeetingScheduling();
    	msche.setIds(meetingids);
    	List<MeetingScheduling> mslist = meetingSchedulingService.getMeetingListByIds(msche);
    	Map<Long,MeetingScheduling> msmap = new HashMap<Long,MeetingScheduling>();
    	if(mslist.isEmpty()){
    		responseBody.setResult(new Result(Status.ERROR, null, "无要操作的排期!"));
    	}else{
    		for(MeetingScheduling meeting:mslist){
    			ids.add(meeting.getProjectId());
    			msmap.put(meeting.getId(), meeting);
    			//如果是投决会阶段-则对人|财|法的进行发送邮件，即查找认领人的id
    			if (DictEnum.meetingType.投决会.getCode().equals(
    					meeting.getMeetingType())) {
    				flag = true;
    				SopTaskBo soptask = new SopTaskBo();
    				soptask.setProjectId(meeting.getProjectId());
    		    	List<SopTask> taskList = sopTaskService.selectForTaskOverList(soptask);
    		    	if(!taskList.isEmpty()){
    		    		List<String> users = new ArrayList<String>();
    		    		for(SopTask task:taskList){
    		    			if(!users.contains(String.valueOf(task.getAssignUid()))){
    		    				users.add(String.valueOf(task.getAssignUid()));
    		    			}
    		    		}
    		    		proMap.put(meeting.getProjectId(), users);
    		    	}
    				
    			}
    		
    		}
    	}
    	//查找项目
    	List<Project> projectList = projectService.queryListById(ids);
    	Map<Long,Project> mapProject = new HashMap<Long,Project>();
		if(projectList.isEmpty()){
			responseBody.setResult(new Result(Status.ERROR, null, "无相关操作项目!"));
		}else{
			//对批量排期的会议项目进行组装
			for(Project pr:projectList){
				mapProject.put(pr.getId(), pr);
				if(!flag){
					List<String> userlists = new ArrayList<String>();
					if(!userlists.contains(String.valueOf(pr.getCreateUid()))){
						userlists.add(String.valueOf(pr.getCreateUid()));
	    			}
					proMap.put(pr.getId(), userlists);
				}
			}
		}
		StringBuffer proNameList=new StringBuffer();
		try {
			for (MeetingScheduling ms : query) {
				String mestr = "";
				MeetingScheduling oldMs = msmap.get(ms.getId());
				Project pj = mapProject.get(oldMs.getProjectId());
				//验证已经已通过|已否决的会议不能进行排期
				if(2 == oldMs.getScheduleStatus() || 3 == oldMs.getScheduleStatus()){
					proNameList.append(pj.getProjectName());
					proNameList.append(" ");
					continue;
				}
				if (DictEnum.meetingType.投决会.getCode().equals(
						ms.getMeetingType())) {
					mestr = DictEnum.meetingType.投决会.getName();
				}
				if (DictEnum.meetingType.立项会.getCode().equals(
						ms.getMeetingType())) {
					mestr = DictEnum.meetingType.立项会.getName();
				}
				if (DictEnum.meetingType.CEO评审.getCode().equals(
						ms.getMeetingType())) {
					mestr = DictEnum.meetingType.CEO评审.getName();
				}
				String messageInfo = mestr + "排期时间为";
				if (oldMs.getReserveTimeStart() != null
						&& ms.getReserveTimeStart() != null) {
					messageInfo = mestr + "排期时间变更为";
				}
				if (oldMs.getReserveTimeStart() != null
						&& ms.getReserveTimeStart() == null) {
					messageInfo = mestr + "排期时间已取消";
				}
				List<String> userLs = proMap.get(pj.getId());
				//获取项目中的user
				List<User> userlist = userService.queryListById(userLs);
				// 如果是更新或取消排期时间
				if (oldMs.getReserveTimeStart() != null
						&& oldMs.getReserveTimeEnd() != null) {
					// 取消排期时间
					if (ms.getReserveTimeStart() == null
							&& ms.getReserveTimeEnd() == null) {
						ms.setScheduleStatus(0);
						meetingSchedulingService.updateByIdSelective(ms);
						sendTaskProjectEmail(request,pj,messageInfo,userlist,null,null,0,UrlNumber.three);
						
					} else {
						// 更新会议时间
						if (oldMs.getReserveTimeStart().getTime() != ms
								.getReserveTimeStart().getTime()
								|| oldMs.getReserveTimeEnd().getTime() != ms
										.getReserveTimeEnd().getTime()) {
							meetingSchedulingService.updateByIdSelective(ms);
							sendTaskProjectEmail(request,pj,messageInfo,userlist,ms.getReserveTimeStart(),ms.getReserveTimeEnd(),1,UrlNumber.two);
						}
					}
				} else {
					// 新安排会议时间
					if (ms.getReserveTimeStart() != null
							&& ms.getReserveTimeEnd() != null) {
						meetingSchedulingService.updateByIdSelective(ms);
						sendTaskProjectEmail(request,pj,messageInfo,userlist,ms.getReserveTimeStart(),ms.getReserveTimeEnd(),1,UrlNumber.one);
					}

				}

			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "更新失败!"));
			e.printStackTrace();
			return responseBody;
		}
		responseBody.setResult(new Result(Status.OK, null, "更新成功!"));
		if(proNameList.length() > 0){
			responseBody.setResult(new Result(Status.OK, null, proNameList.toString()+" 项目已经通过,不能进行排期!"));
		}
		return responseBody;
	}
	
	/**
	 * 根据业务发送邮件和消息：人、财、法
	 * @param request
	 * @param pj 项目
	 * @param messageInfo 邮件内容
	 * @param userlist 发送的用户
	 * @param 
	 * @param type 0：取消发送邮件 1:更新或新增邮件
	 */
	public void sendTaskProjectEmail(HttpServletRequest request,Project pj,String messageInfo,List<User> userlist,Timestamp reserveTimeStart,Timestamp reserveTimeEnd,Integer type,UrlNumber number){
		if(!userlist.isEmpty()){
			for(User user: userlist){
				sendMailToTZJL(request, type, user.getEmail(),
						user.getRealName(),
						pj.getProjectCode(),pj.getProjectName(),
						messageInfo, reserveTimeStart,
						reserveTimeEnd);
				ControllerUtils.setRequestParamsForMessageTip(request,
						user, pj.getProjectName(), pj.getId(),
						number);
			}
		}
	}
	

	/**
	 * 更新排期池时间/updateReserveTime-客户端用
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.MESSAGE)
	@ResponseBody
	@RequestMapping(value = "/updateReserveTimeByApp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingScheduling> updateReserveTimeByApp(
			HttpServletRequest request, @RequestBody MeetingScheduling query) {
		ResponseData<MeetingScheduling> responseBody = new ResponseData<MeetingScheduling>();
		if (query == null || query.getMeetingType() == null 
				|| "".equals(query.getMeetingType().trim()) || query.getId() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "无操作数据!"));
			return responseBody;
		}
		try {
			MeetingScheduling oldMs = meetingSchedulingService.queryById(query
					.getId());
			String mestr = "";
			if (DictEnum.meetingType.投决会.getCode().equals(
					query.getMeetingType())) {
				mestr = DictEnum.meetingType.投决会.getName();
			}
			if (DictEnum.meetingType.立项会.getCode().equals(
					query.getMeetingType())) {
				mestr = DictEnum.meetingType.立项会.getName();
			}
			if (DictEnum.meetingType.CEO评审.getCode().equals(
					query.getMeetingType())) {
				mestr = DictEnum.meetingType.CEO评审.getName();
			}
			String messageInfo = mestr + "排期时间为";
			if (oldMs.getReserveTimeStart() != null
					&& query.getReserveTimeStart() != null) {
				messageInfo = mestr + "排期时间变更为";
			}
			if (oldMs.getReserveTimeStart() != null
					&& query.getReserveTimeStart() == null) {
				messageInfo = mestr + "排期时间已取消";
			}
			Project pj = projectService.queryById(oldMs.getProjectId());
			List<String> users = new ArrayList<String>();
			if (DictEnum.meetingType.投决会.getCode().equals(
					query.getMeetingType())) {
				SopTaskBo soptask = new SopTaskBo();
				soptask.setProjectId(oldMs.getProjectId());
		    	List<SopTask> taskList = sopTaskService.selectForTaskOverList(soptask);
		    	if(!taskList.isEmpty()){
		    		for(SopTask task:taskList){
		    			if(!users.contains(String.valueOf(task.getAssignUid()))){
		    				users.add(String.valueOf(task.getAssignUid()));
		    			}
		    		}
		    	
		    	}
				
			}else{
				users.add(String.valueOf(pj.getCreateUid()));
			}
			//获取项目中的user
			List<User> userlist = userService.queryListById(users);
			// 如果是更新或取消排期时间
			if (oldMs.getReserveTimeStart() != null
					&& oldMs.getReserveTimeEnd() != null) {
				// 取消排期时间
				if (query.getReserveTimeStart() == null
						&& query.getReserveTimeEnd() == null) {
					query.setScheduleStatus(0);
					meetingSchedulingService.updateByIdSelective(query);
					sendTaskProjectEmail(request,pj,messageInfo,userlist,null,null,0,UrlNumber.three);
				} else {
					// 更新会议时间
					if (oldMs.getReserveTimeStart().getTime() != query
							.getReserveTimeStart().getTime()
							|| oldMs.getReserveTimeEnd().getTime() != query
									.getReserveTimeEnd().getTime()) {
						meetingSchedulingService.updateByIdSelective(query);
						sendTaskProjectEmail(request,pj,messageInfo,userlist,query.getReserveTimeStart(),query.getReserveTimeEnd(),1,UrlNumber.two);
					}
				}
			} else {
				// 新安排会议时间
				if (query.getReserveTimeStart() != null
						&& query.getReserveTimeEnd() != null) {
					meetingSchedulingService.updateByIdSelective(query);
					sendTaskProjectEmail(request,pj,messageInfo,userlist,query.getReserveTimeStart(),query.getReserveTimeEnd(),1,UrlNumber.one);
				}

			}

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "更新失败!"));
			e.printStackTrace();
			return responseBody;
		}
		responseBody.setResult(new Result(Status.OK, null, "更新成功!"));
		return responseBody;
	}

	/***
	 * 发送邮件
	 */
	public String sendMailToTZJL(HttpServletRequest request, Integer type,
			String toAddress, String tzjlName, String projectinfoCode,String projectinfoName,
			String messageInfo, Date meetingTimestart, Date meetingTimeend) {
		String starttime = null;
		String endtime = null;
		if(meetingTimestart != null){
			starttime = DateUtil.convertDateToStringForChina(meetingTimestart);
		}
		if(meetingTimeend != null){
			endtime = DateUtil.convertDateToStringForChina(meetingTimeend);
		}
		toAddress =toAddress+"@galaxyinternet.com";
		String content = MailTemplateUtils
				.getContentByTemplate(Constants.MAIL_PQC_CONTENT);
		String[] to = toAddress.split(";");
		if (to != null && to.length == 1) {
			int atIndex = toAddress.lastIndexOf("@");
			tzjlName = toAddress.substring(0, atIndex) + ":<br>您好!";
		}
		if (type == 0) {
			content = MailTemplateUtils
					.getContentByTemplate(Constants.MAIL_PQC_CONTENT_CANCLE);
			content = PlaceholderConfigurer.formatText(content, tzjlName,
					projectinfoCode,projectinfoName, messageInfo);
		} else {
			content = PlaceholderConfigurer.formatText(content, tzjlName,
					projectinfoCode,projectinfoName, messageInfo, starttime, endtime);
		}
		boolean success = SimpleMailSender.sendMultiMail(toAddress, "会议排期通知",
				content.toString());

		if (success) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	/***
	 * 返回角色列表
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoleList")
	public List<Long> getRoleList(HttpServletRequest request){
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = null;
		if(user != null){
			try{
				roleIdList = userRoleService.selectRoleIdByUserId(user
						.getId());
			}catch(Exception e){
				logger.error("获取角色列表失败!", e);
			}
		}
		return roleIdList;
	}
	
}
