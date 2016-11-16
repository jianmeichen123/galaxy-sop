package com.galaxyinternet.project.controller;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.web.servlet.ModelAndView;

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
import com.galaxyinternet.common.taglib.FXFunctionTags;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.UtilsService;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.config.PlaceholderConfigurer;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.FormatterUtils;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.framework.core.utils.UUIDUtils;
import com.galaxyinternet.framework.core.utils.mail.MailTemplateUtils;
import com.galaxyinternet.framework.core.utils.mail.SimpleMailSender;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.FinanceHistory;
import com.galaxyinternet.model.project.FormatData;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.operationMessage.handler.SopFileMessageHandler;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.project.service.HandlerManager;
import com.galaxyinternet.project.service.handler.Handler;
import com.galaxyinternet.project.service.handler.YwjzdcHandler;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.PassRateService;
import com.galaxyinternet.service.PersonLearnService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.PersonWorkService;
import com.galaxyinternet.service.ProjectPersonService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.CollectionUtils;
import com.galaxyinternet.utils.ListSortUtil;
import com.galaxyinternet.utils.SopConstatnts;

@Controller
@RequestMapping("/galaxy/project")
public class ProjectController extends BaseControllerImpl<Project, ProjectBo> {

	private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PersonPoolService personPoolService;
	@Autowired
	private PersonLearnService personLearnService;
	@Autowired
	private PersonWorkService personWorkService;
	
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
	private YwjzdcHandler ywjzdcHandler;
	
	
	@Autowired
	private SopTaskService sopTaskService;
	
	@Autowired
	private com.galaxyinternet.mongodb.service.ProjectService mongoProjectService;
	
	@Resource(name ="utilsService")
	private UtilsService utilsService;
	
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
	//##########################版本V2.3.111开始##########################
	
	/**
	 * 跳转到添加项目页面
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public String addProject(HttpServletRequest request) {
		return "project/v_addProject";
	}
	
	/**
	 * 添加团队成员弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addProjectPerson", method = RequestMethod.GET)
	public String addProjectPerson(HttpServletRequest request) {
		request.setAttribute("uuid", UUIDUtils.create().toString());
		return "project/v_add_project_person";
	}
	
	/**
	 * 添加团队成员-学习经历弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addPersonLearning", method = RequestMethod.GET)
	public String addPersonLearning(HttpServletRequest request) {
		return "project/v_person_learning";
	}
	
	/**
	 * 编辑学习经历弹出层
	 * @return
	 */
	@RequestMapping(value = "/toEditLearn/{puuid}/{luuid}", method = RequestMethod.GET)
	public String toEditLearn(@PathVariable("puuid") String puuid,
			@PathVariable("luuid") String luuid,
			HttpServletRequest request) {
		request.setAttribute("puuid", puuid);
		request.setAttribute("luuid", luuid);
		return "project/v_update_person_learn";
	}
	
	/**
	 * 添加团队成员-工作经历弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addPersonWork", method = RequestMethod.GET)
	public String addPersonWork(HttpServletRequest request) {
		return "project/v_person_work";
	}
	
	/**
	 * 添加股权结构弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addProjectShares", method = RequestMethod.GET)
	public String addProjectShares(HttpServletRequest request) {
		return "project/v_project_shares";
	}
	/**
	 * 添加融资历史
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addFinanceHistory", method = RequestMethod.GET)
	public String addFinanceHistory(HttpServletRequest request) {
		return "project/v_addFinanceHistory";
	}
	
	/**
	 * 修改融资历史信息
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/upateFinanceHistory", method = RequestMethod.GET)
	public String upateFinanceHistory(HttpServletRequest request) {
		return "project/v_upateFinanceHistory";
	}
	
	/**
	 * 编辑团队成员
	 * @return
	 */
	@RequestMapping(value = "/editPerson/{puuid}", method = RequestMethod.GET)
	public String editPerson(@PathVariable("puuid") String puuid, HttpServletRequest request) {
		request.setAttribute("uuid", puuid);
		return "project/v_update_project_person";
	}
	
	/**
	 * 查看团队信息弹出层
	 * @return
	 */
	@RequestMapping(value = "/personDetail/{puuid}", method = RequestMethod.GET)
	public String personDetail(@PathVariable("puuid") String puuid, HttpServletRequest request) {
		request.setAttribute("uuid", puuid);
		return "project/v_look_project_person";
	}
	
	/**
	 * 添加商业计划书弹出层
	 * @return
	 */
	@RequestMapping(value = "/toAddBuessDoc", method = RequestMethod.GET)
	public String toAddBuessDoc(HttpServletRequest request) {
		return "project/v_add_bussiness_doc";
	}
	
	/**
	 * 添加股权结构弹出层
	 * @param id 项目ID
	 * @return
	 */
	@RequestMapping(value = "/toAddShares", method = RequestMethod.GET)
	public String toAddShares(HttpServletRequest request) {
		return "project/v_add_project_shares";
	}
	/**
	 * 编辑股权结构弹出层
	 * @return
	 */
	@RequestMapping(value = "/toEditShares/{uuid}", method = RequestMethod.GET)
	public String toEditShares(@PathVariable("uuid") String uuid, HttpServletRequest request) {
		request.setAttribute("uuid", uuid);
		return "project/v_update_project_shares";
	}
	
	/**
	 * 获取指定的商业计划书信息
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/lookBuessDoc/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> lookBuessDoc(@PathVariable("id") String id, 
			HttpServletRequest request) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		if(id == null || "".equals(id.trim())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project p = mongoProjectService.findById(id);
			if(p != null && p.getSopFile() != null){
				responseBody.setEntity(p.getSopFile());
				responseBody.setResult(new Result(Status.OK, "ok" , "操作成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR, "error" , "未找到指定的记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to find bussiness doc get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}	
	/**
	 * 创建项目时上传/更新商业计划书
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/inBuessDoc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> inBuessDoc(SopFile file,
			HttpServletRequest request) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		if(file.getTempPid() == null || "".equals(file.getTempPid().trim())
				||file.getFileSource() == null || "".equals(file.getFileSource().trim())
				||file.getFileType() == null || "".equals(file.getFileType().trim())
				||file.getFileWorktype() == null || "".equals(file.getFileWorktype().trim())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project p = mongoProjectService.findById(file.getTempPid());
			if(p != null){
				String fileKey = null;
				if(file.getFileKey() != null){
					fileKey = file.getFileKey();
				}else{
					fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				}
				UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
				if (result != null && result.getResult().getStatus().equals(Result.Status.OK)) {
					file.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
					file.setCareerLine(user.getDepartmentId());
					file.setFileStatus(DictEnum.fileStatus.已上传.getCode());
					file.setFileUid(user.getId());
					file.setCreatedTime(System.currentTimeMillis());
					file.setFileLength(result.getContentLength());
					file.setFileKey(fileKey);
					file.setBucketName(result.getBucketName());
					file.setFileName(result.getFileName());
					file.setFileSuffix(result.getFileSuffix());
					file.setRecordType((byte)0);
					p.setSopFile(file);
					mongoProjectService.updateById(file.getTempPid(), p);
					if(logger.isInfoEnabled()){
						logger.info(user.getId() + ":" + user.getRealName() + " to upload bussiness doc successful > " + file.getTempPid());
					}
					responseBody.setEntity(file);
					responseBody.setResult(new Result(Status.OK, "ok" , "操作成功!"));
				}
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to upload bussiness doc get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	
	/**
	 * "下一步"3
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/save3/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<com.galaxyinternet.mongodb.model.Project> save3(@PathVariable("id") String id, 
			@RequestBody com.galaxyinternet.mongodb.model.Project project,
			HttpServletRequest request) {
		ResponseData<com.galaxyinternet.mongodb.model.Project> responseBody = new ResponseData<com.galaxyinternet.mongodb.model.Project>();
		if(id == null || "".equals(id.trim()) || project == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project p = mongoProjectService.findById(id);
			if(p != null){
				if(project.getProjectCompany() != null && !"".equals(project.getProjectCompany().trim())
						&& project.getProjectCompanyCode() != null && !"".equals(project.getProjectCompanyCode().trim())
						&& project.getCompanyLegal() != null && !"".equals(project.getCompanyLegal().trim())
						&& project.getFormationDate() != null && !"".equals(project.getFormationDate().trim())){
					p.setProjectCompany(project.getProjectCompany().trim());
					p.setProjectCompanyCode(project.getProjectCompanyCode().trim());
					p.setCompanyLegal(project.getCompanyLegal());
					p.setFormationDate(project.getFormationDate());
					mongoProjectService.updateById(id, p);
				}
				if(logger.isInfoEnabled()){
					logger.info(user.getId() + ":" + user.getRealName() + " to update company messages successful > " + project.getId());
				}
				responseBody.setResult(new Result(Status.OK, "ok" , "操作成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR, "error" , "未找到匹配记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to update company messages get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	/**
	 * 添加股权结构
	 * @param uuid 新增团队成员的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/saveShares/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> saveShares(@PathVariable("id") String id, 
			@RequestBody ProjectShares projectShares,
			HttpServletRequest request) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(id == null || "".equals(id.trim()) || projectShares == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			projectShares.setUuid(UUIDUtils.create().toString());
			projectShares.setCreatedTime(System.currentTimeMillis());
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			ListSortUtil<ProjectShares> sortList = new ListSortUtil<ProjectShares>();
			List<ProjectShares> sharesList = null;
			if(project.getPsc() == null){
				sharesList = new ArrayList<ProjectShares>();
			}else{
				sharesList = project.getPsc();
			}
			sharesList.add(projectShares);
			project.setPsc(sharesList);
			mongoProjectService.updateById(id, project);
			if(logger.isInfoEnabled()){
				logger.info(user.getId() + ":" + user.getRealName() + " to save shares successful > " + project.getId());
			}
			sortList.Sort(sharesList, "sharesRatio", "desc");
			responseBody.setEntityList(sharesList);
			responseBody.setResult(new Result(Status.OK, "ok" , "添加股权结构成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to save shares get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 获取指定的股权结构信息
	 * @param uuid 股权结构的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/lookProjectShares/{uuid}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> lookProjectShares(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id, 
			HttpServletRequest request) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project != null && project.getPsc() != null){
				List<ProjectShares> list = project.getPsc();
				for(ProjectShares s : list){
					if(s.getUuid().equals(uuid)){
						responseBody.setEntity(s);
						break;
					}
				}
			}
			responseBody.setResult(new Result(Status.OK, "ok" , "查询股权结构信息成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search shares data get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 编辑股权结构记录
	 * @param uuid 股权结构的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/updateShares/{uuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> updateShares(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id, 
			@RequestBody ProjectShares personShares,
			HttpServletRequest request) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(id == null || "".equals(id.trim()) || personShares == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			personShares.setUuid(uuid);
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			List<ProjectShares> sharesList = null;
			ListSortUtil<ProjectShares> sortList = new ListSortUtil<ProjectShares>();
			if(project.getPsc() != null && project.getPsc().contains(personShares)){
				sharesList = project.getPsc();
				for(ProjectShares s : sharesList){
					if(s.getUuid().equals(uuid)){
						sharesList.remove(personShares);
						sharesList.add(personShares);
						project.setPsc(sharesList);
						mongoProjectService.updateById(id, project);
						if(logger.isInfoEnabled()){
							logger.info(user.getId() + ":" + user.getRealName() + " to update shares successful > " + project.getId());
						}
						sortList.Sort(sharesList, "sharesRatio", "desc");
						responseBody.setEntityList(sharesList);
						responseBody.setResult(new Result(Status.OK, "ok" , "修改股权结构成功!"));
						break;
					}
				}
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to update shares get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	/**
	 * 删除股权结构
	 * @param uuid 股权结构的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProjectShares/{uuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> deleteProjectShares(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id,
			HttpServletRequest request) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(uuid == null || "".equals(uuid.trim()) 
				|| uuid == null || "".equals(uuid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			ListSortUtil<ProjectShares> sortList = new ListSortUtil<ProjectShares>();
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			ProjectShares w = new ProjectShares();
			w.setUuid(uuid);
			if(project != null && project.getPsc() != null && project.getPsc().contains(w)){
				project.getPsc().remove(w);
				mongoProjectService.updateById(id, project);
				if(logger.isInfoEnabled()){
					logger.info(FormatterUtils.formatStr(
							"{0}:{1} to delete shares successfully > pid : {3}, uuid : {4}", 
							user.getId(), user.getRealName(), id, uuid));
				}
				sortList.Sort(project.getPsc(), "sharesRatio", "desc");
				responseBody.setEntityList(project.getPsc());
				responseBody.setResult(new Result(Status.OK,"ok" , "删除股权结构成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找该股权结构记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete shares get an exception > pid : {3}, uuid : {4}", 
						user.getId(), user.getRealName(), id, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 查询股权结构列表
	 */
	@ResponseBody
	@RequestMapping(value = "/searchProjectShares/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> searchProjectShares(@PathVariable("id") String id, HttpServletRequest request) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		ListSortUtil<ProjectShares> sortList = new ListSortUtil<ProjectShares>();
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project != null&& null!=project.getPsc()&& project.getPsc().size()>0){
				sortList.Sort(project.getPsc(), "sharesRatio", "desc");
				responseBody.setEntityList(project.getPsc());
			}else{
				responseBody.setEntityList(null);
			}
			responseBody.setResult(new Result(Status.OK, "ok" , "查询股权结构信息成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search shares data get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	
	
	
	/**
	 * 查看团队成员信息
	 * @param id 项目ID
	 * @param uuid 团队成员的uuid
	 */
	@ResponseBody
	@RequestMapping(value = "/lookPerson/{uuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> lookPerson(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id,
			HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(id == null || "".equals(id.trim())
			|| uuid == null || "".equals(uuid.trim())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		PersonPool p = new PersonPool();
		p.setUuid(uuid);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project != null && project.getPc() != null){
				List<PersonPool> pools = project.getPc();
				for(PersonPool pool : pools){
					if(pool.getUuid().equals(uuid.trim())){
						responseBody.setEntity(pool);
						responseBody.setResult(new Result(Status.OK, "ok" , "查看团队成员信息成功!"));
						break;
					}
				}
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to save person get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		
		return responseBody;
	}
	
	/**
	 * 添加团队成员记录
	 * @param uuid 新增团队成员的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/savePerson/{uuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> savePerson(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id, 
			@RequestBody PersonPool personPool,
			HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(id == null || "".equals(id.trim()) || personPool == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			personPool.setUuid(uuid);
			personPool.setCreatedTime(System.currentTimeMillis());
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			List<PersonPool> personList = null;
			if(project.getPc() == null){
				personList = new ArrayList<PersonPool>();
				personList.add(personPool);
			}else{
				personList = project.getPc();
				if(personList.contains(personPool)){
					for(PersonPool p : personList){
						if(p.getUuid().equals(uuid)){
							personList.remove(personPool);
							personPool.setPwc(p.getPwc());
							personPool.setPlc(p.getPlc());
							personList.add(personPool);
							break;
						}
					}
				}else{
					personList.add(personPool);
				}
			}
			project.setPc(personList);
			mongoProjectService.updateById(id, project);
			if(logger.isInfoEnabled()){
				logger.info(user.getId() + ":" + user.getRealName() + " to save person successful > " + project.getId());
			}
			responseBody.setEntityList(personList);
			responseBody.setResult(new Result(Status.OK, "ok" , "添加团队成员成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to save person get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	/**
	 * 编辑团队成员记录
	 * @param uuid 团队成员的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePerson/{uuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> updatePerson(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id, 
			@RequestBody PersonPool personPool,
			HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(id == null || "".equals(id.trim()) || personPool == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			personPool.setUuid(uuid);
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			List<PersonPool> personList = null;
			if(project.getPc() != null && project.getPc().contains(personPool)){
				personList = project.getPc();
				for(PersonPool p : personList){
					if(p.getUuid().equals(uuid)){
						personList.remove(personPool);
						personPool.setPwc(p.getPwc());
						personPool.setPlc(p.getPlc());
						personList.add(personPool);
						project.setPc(personList);
						mongoProjectService.updateById(id, project);
						if(logger.isInfoEnabled()){
							logger.info(user.getId() + ":" + user.getRealName() + " to update person successful > " + project.getId());
						}
						responseBody.setEntityList(personList);
						responseBody.setResult(new Result(Status.OK, "ok" , "修改团队成员成功!"));
						break;
					}
				}
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to update person get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	/**
	 * 删除团队成员
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProjectPerson/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> deleteProjectPerson(@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,
			HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			PersonPool w = new PersonPool();
			w.setUuid(uuid);
			if(project != null && project.getPc() != null && project.getPc().contains(w)){
				project.getPc().remove(w);
				mongoProjectService.updateById(pid, project);
				if(logger.isInfoEnabled()){
					logger.info(FormatterUtils.formatStr(
							"{0}:{1} to delete person successfully > pid : {3}, uuid : {4}", 
							user.getId(), user.getRealName(), pid, uuid));
				}
				responseBody.setEntityList(project.getPc());
				responseBody.setResult(new Result(Status.OK,"ok" , "删除团队成员成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找该团队成员记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete person get an exception > pid : {3}, uuid : {4}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 查询团队成员列表
	 */
	@ResponseBody
	@RequestMapping(value = "/searchProjectPerson/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> searchProjectPerson(@PathVariable("id") String id, HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			responseBody.setEntityList(project != null ? project.getPc() : null);
			responseBody.setResult(new Result(Status.OK, "ok" , "查询团队成员信息成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search person data get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	
	/**
	 * 添加工作经历记录
	 * @param puuid 团队成员记录的uuid
	 * @param id 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/savePersonWork/{puuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonWork> savePersonWork(@PathVariable("puuid") String puuid,
			@PathVariable("id") String id, 
			@RequestBody PersonWork personWork,
			HttpServletRequest request) {
		ResponseData<PersonWork> responseBody = new ResponseData<PersonWork>();
		if(id == null || "".equals(id.trim())
				|| puuid == null || "".equals(puuid.trim())
				|| personWork == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			personWork.setUuid(UUIDUtils.create().toString());
			personWork.setCreatedTime(System.currentTimeMillis());
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			PersonPool w = new PersonPool();
			w.setUuid(puuid);
			List<PersonWork> workList = null;
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				if(list.contains(w)){
					for(PersonPool p : list){
						if(p.getUuid().equals(puuid.trim())){
							if(p.getPwc() == null){
								workList = new ArrayList<PersonWork>();
							}else{
								workList = p.getPwc();
							}
							workList.add(personWork);
							p.setPwc(workList);
							list.remove(p);
							list.add(p);
							project.setPc(list);
							mongoProjectService.updateById(id, project);
							break;
						}
					}
				}else{
					workList = new ArrayList<PersonWork>();
					workList.add(personWork);
					w.setPwc(workList);
					list.add(w);
					project.setPc(list);
					mongoProjectService.updateById(id, project);
				}
			}else{
				workList = new ArrayList<PersonWork>();
				workList.add(personWork);
				w.setPwc(workList);
				List<PersonPool> pList = new ArrayList<PersonPool>();
				pList.add(w);
				project.setPc(pList);
				mongoProjectService.updateById(id, project);
			}
			if(logger.isInfoEnabled()){
				logger.info(user.getId() + ":" + user.getRealName() + " to save person work successful > " + project.getId());
			}
			responseBody.setEntityList(workList);
			responseBody.setResult(new Result(Status.OK, "ok" , "添加工作经历成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to save person work get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 删除工作经历记录
	 * @param puuid 团队成员记录的uuid
	 * @param uuid 工作经历记录的uuid
	 * @param pid 所属团队信息的ID
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProjectWork/{puuid}/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonWork> deleteProjectWork(@PathVariable("puuid") String puuid,
			@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,
			HttpServletRequest request) {
		ResponseData<PersonWork> responseBody = new ResponseData<PersonWork>();
		if(puuid == null || "".equals(puuid.trim()) 
				||uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			PersonWork w = new PersonWork();
			w.setUuid(uuid);
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				PersonPool pp = new PersonPool();
				pp.setUuid(puuid);
				if(list.contains(pp)){
					for(PersonPool p : list){
						if(p.getUuid().equals(puuid.trim())){
							List<PersonWork> works = p.getPwc();
							works.remove(w);
							list.remove(p);
							list.add(p);
							project.setPc(list);
							mongoProjectService.updateById(pid, project);
							if(logger.isInfoEnabled()){
								logger.info(FormatterUtils.formatStr(
										"{0}:{1} to delete work successfully > pid : {3}, uuid : {4}", 
										user.getId(), user.getRealName(), pid, uuid));
							}
							responseBody.setEntityList(works);
							responseBody.setResult(new Result(Status.OK,"ok" , "删除工作经历成功!"));
							break;
						}
					}
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找该工作经历记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete work get an exception > pid : {3}, uuid : {4}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 工作经历列表查询
	 * @param puuid 团队成员的uuid
	 * @param pid 项目ID
	 */
	@ResponseBody
	@RequestMapping(value = "/searchProjectWork/{puuid}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonWork> searchProjectWork(@PathVariable("puuid") String puuid,
			@PathVariable("id") String id, 
			HttpServletRequest request) {
		ResponseData<PersonWork> responseBody = new ResponseData<PersonWork>();
		if(id == null || "".equals(id.trim())
				|| puuid == null || "".equals(puuid.trim())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			PersonPool pool = new PersonPool();
			pool.setUuid(puuid);
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				for(PersonPool p : list){
					if(p.getUuid().equals(puuid)){
						responseBody.setEntityList(p.getPwc());
						break;
					}
				}
			}
			responseBody.setResult(new Result(Status.OK, "ok" , "查询工作经历数据成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search person work get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	
	
	
	/**
	 * 添加学习经历记录
	 * @param id 草稿项目ID
	 * @param puuid 团队成员记录的uuid
	 */
	@ResponseBody
	@RequestMapping(value = "/savePersonLearning/{uuid}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> savePersonLearning(@PathVariable("uuid") String uuid,
			@PathVariable("id") String id, 
			@RequestBody PersonLearn personLearn,
			HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		if(id == null || "".equals(id.trim())
				|| uuid == null || "".equals(uuid.trim())
				|| personLearn == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			personLearn.setUuid(UUIDUtils.create().toString());
			personLearn.setCreatedTime(System.currentTimeMillis());
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			PersonPool w = new PersonPool();
			w.setUuid(uuid);
			List<PersonLearn> learnList = null;
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				if(list.contains(w)){
					for(PersonPool p : list){
						if(p.getUuid().equals(uuid.trim())){
							if(p.getPlc() == null){
								learnList = new ArrayList<PersonLearn>();
							}else{
								learnList = p.getPlc();
							}
							learnList.add(personLearn);
							p.setPlc(learnList);
							list.remove(p);
							list.add(p);
							project.setPc(list);
							mongoProjectService.updateById(id, project);
							break;
						}
					}
				}else{
					learnList = new ArrayList<PersonLearn>();
					learnList.add(personLearn);
					w.setPlc(learnList);
					list.add(w);
					project.setPc(list);
					mongoProjectService.updateById(id, project);
				}
			}else{
				learnList = new ArrayList<PersonLearn>();
				learnList.add(personLearn);
				w.setPlc(learnList);
				List<PersonPool> pList = new ArrayList<PersonPool>();
				pList.add(w);
				project.setPc(pList);
				mongoProjectService.updateById(id, project);
			}
			if(logger.isInfoEnabled()){
				logger.info(user.getId() + ":" + user.getRealName() + " to save person learning successful > " + project.getId());
			}
			responseBody.setEntityList(learnList);
			responseBody.setResult(new Result(Status.OK, "ok" , "添加学习经历成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to save person learning get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 编辑学习经历记录
	 * @param pid 项目ID
	 * @param puuid 团队成员记录的uuid
	 * @param uuid 学习经历记录的uuid
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePersonLearning/{puuid}/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> updatePersonLearning(@PathVariable("puuid") String puuid,
			@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,
			@RequestBody PersonLearn learn,
			HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		if(puuid == null || "".equals(puuid.trim()) 
				||uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim())
				|| learn == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			PersonLearn w = new PersonLearn();
			w.setUuid(uuid);
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				PersonPool pp = new PersonPool();
				pp.setUuid(puuid);
				if(list.contains(pp)){
					for(PersonPool p : list){
						if(p.getUuid().equals(puuid.trim())){
							List<PersonLearn> learns = p.getPlc();
							learns.remove(w);
							learns.add(learn);
							list.remove(p);
							list.add(p);
							project.setPc(list);
							mongoProjectService.updateById(pid, project);
							if(logger.isInfoEnabled()){
								logger.info(FormatterUtils.formatStr(
										"{0}:{1} to update learning successfully > pid : {3}, uuid : {4}", 
										user.getId(), user.getRealName(), pid, uuid));
							}
							responseBody.setEntityList(learns);
							responseBody.setResult(new Result(Status.OK,"ok" , "更新学习经历成功!"));
							break;
						}
					}
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找该学习经历记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to update learning get an exception > pid : {3}, uuid : {4}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 删除学习经历记录
	 * @param pid 项目ID
	 * @param puuid 团队成员记录的uuid
	 * @param uuid 学习经历记录的uuid
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProjectLearning/{puuid}/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> deleteProjectLearning(@PathVariable("puuid") String puuid,
			@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,
			HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		if(puuid == null || "".equals(puuid.trim()) 
				||uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			PersonLearn w = new PersonLearn();
			w.setUuid(uuid);
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				PersonPool pp = new PersonPool();
				pp.setUuid(puuid);
				if(list.contains(pp)){
					for(PersonPool p : list){
						if(p.getUuid().equals(puuid.trim())){
							List<PersonLearn> learns = p.getPlc();
							learns.remove(w);
							list.remove(p);
							list.add(p);
							project.setPc(list);
							mongoProjectService.updateById(pid, project);
							if(logger.isInfoEnabled()){
								logger.info(FormatterUtils.formatStr(
										"{0}:{1} to delete learning successfully > pid : {3}, uuid : {4}", 
										user.getId(), user.getRealName(), pid, uuid));
							}
							responseBody.setEntityList(learns);
							responseBody.setResult(new Result(Status.OK,"ok" , "删除学习经历成功!"));
							break;
						}
					}
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找该学习经历记录!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete learning get an exception > pid : {3}, uuid : {4}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 学习经历列表查询
	 * @param pid 草稿项目ID
	 * @param puuid 团队成员的uuid
	 */
	@ResponseBody
	@RequestMapping(value = "/searchProjectLearning/{puuid}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> searchProjectLearning(@PathVariable("puuid") String puuid,
			@PathVariable("id") String id, 
			HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		if(id == null || "".equals(id.trim())
				|| puuid == null || "".equals(puuid.trim())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			PersonPool pool = new PersonPool();
			pool.setUuid(puuid);
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				for(PersonPool p : list){
					if(p.getUuid().equals(puuid)){
						responseBody.setEntityList(p.getPlc());
						break;
					}
				}
			}
			responseBody.setResult(new Result(Status.OK, "ok" , "查询学习经历数据成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search person learning get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 学习经历信息查询
	 * @param pid 项目ID
	 * @param puuid 团队成员的uuid
	 * @param uuid 学习经历的uuid
	 */
	@ResponseBody
	@RequestMapping(value = "/lookProjectLearning/{uuid}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> lookProjectLearning(@PathVariable("id") String id, 
			@PathVariable("uuid") String uuid, 
			HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		if(id == null || "".equals(id.trim())
				|| uuid == null || "".equals(uuid.trim())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project != null && project.getPc() != null){
				List<PersonPool> list = project.getPc();
				for(PersonPool p : list){
					if(p.getPlc() != null){
						List<PersonLearn> learns = p.getPlc();
						for(PersonLearn l : learns){
							if(l.getUuid().equals(uuid.trim())){
								responseBody.setEntity(l);
								break;
							}
						}
					}
				}
			}
			responseBody.setResult(new Result(Status.OK, "ok" , "查询学习经历数据成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search person learning get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	
	
	/**
	 * 新建项目接口
	 * @version 2016-11-03
	 * @author chenjianmei
	 */
	@ResponseBody
	@RequestMapping(value = "/apDB", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<com.galaxyinternet.mongodb.model.Project> apDB(@RequestBody com.galaxyinternet.mongodb.model.Project project,
			HttpServletRequest request) {
		ResponseData<com.galaxyinternet.mongodb.model.Project> responseBody = new ResponseData<com.galaxyinternet.mongodb.model.Project>();
		//校验
		if (project == null || project.getProjectName() == null
				|| "".equals(project.getProjectName().trim())
				|| project.getProjectType() == null
				|| "".equals(project.getProjectType().trim())
				|| project.getCreateDate() == null
				|| "".equals(project.getCreateDate().trim())
				|| project.getIndustryOwn() == null) {
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			String uuid=UUIDUtils.create().toString();
			project.setUuid(uuid);
			project.setUid(user.getId());
			if(null==project.getFlagId()||"".equals(project.getFlagId())){
			   mongoProjectService.save(project);
			}else{
				com.galaxyinternet.mongodb.model.Project fh=new com.galaxyinternet.mongodb.model.Project();
				fh=mongoProjectService.findById(project.getFlagId());
				project.setFh(fh.getFh());
				mongoProjectService.updateById(project.getFlagId(), project);
			}
			com.galaxyinternet.mongodb.model.Project param=new com.galaxyinternet.mongodb.model.Project();
			param.setUuid(uuid);
			param = mongoProjectService.findOne(param);
			responseBody.setEntity(param);
			logger.info(user.getId() + ":" + user.getRealName() + " to save project successful");
			responseBody.setResult(new Result(Status.OK, "ok" , "保存基本信息成功!"));
		} catch (Exception e) {
			logger.error("异常信息:",e.getMessage());
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	/**
	 * 添加股权结构弹出层
	 * @version v
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveFinanceHistory/{flagId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<com.galaxyinternet.mongodb.model.Project> saveFinanceHistory(@PathVariable("flagId") String flagId, 
			@RequestBody FinanceHistory financeHistory,
			HttpServletRequest request) {
		ResponseData<com.galaxyinternet.mongodb.model.Project> responseBody = new ResponseData<com.galaxyinternet.mongodb.model.Project>();
		if(financeHistory == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			financeHistory.setCreatedTime(System.currentTimeMillis());
			com.galaxyinternet.mongodb.model.Project project =new com.galaxyinternet.mongodb.model.Project();
			com.galaxyinternet.mongodb.model.Project entity =new com.galaxyinternet.mongodb.model.Project();
			ListSortUtil<FinanceHistory> sortList = new ListSortUtil<FinanceHistory>();
			String uuidHistory=UUIDUtils.create().toString();
			financeHistory.setUuid(uuidHistory);
			financeHistory.setFinanceDate(null);
			financeHistory.setFinanceStatus(financeHistory.getFinanceStatus());
			List<FinanceHistory> fh=null;
			if(null==flagId||"null".equals(flagId)){
				String uuid=UUIDUtils.create().toString();
				project.setUuid(uuid);
				project.setUid(user.getId());
				if(null==project.getFh()){
					fh= new ArrayList<FinanceHistory>();
					project.setFh(fh);
				}
				project.getFh().add(financeHistory);
				mongoProjectService.save(project);
				com.galaxyinternet.mongodb.model.Project param =new com.galaxyinternet.mongodb.model.Project();
				param.setUuid(uuid);
				param.setUid(user.getId());
				entity=mongoProjectService.findOne(param);
			}else{
				project=mongoProjectService.findById(flagId);
				project.getFh().add(financeHistory);
				mongoProjectService.updateById(flagId, project);
				entity=project;
			}
			
			sortList.Sort(entity.getFh(),"financeDateStr",
					"desc");
			logger.info(user.getId() + ":" + user.getRealName() + " to save FinanceHistory successful > " + project.getId());
			responseBody.setResult(new Result(Status.OK, "ok" , "添加融资历史成功!"));
			responseBody.setEntity(entity);
		} catch (MongoDBException e) {
			logger.error(user.getId() + ":" + user.getRealName() + " to save FinanceHistory get an exception", e);
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 查询项目的历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/searchFinanceHistory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> searchFinanceHistory(@PathVariable("id") String id, HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		if(id.equals("null")){
			responseBody.setEntityList(null);
			return responseBody;
		}
		ListSortUtil<FinanceHistory> sortList = new ListSortUtil<FinanceHistory>();
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(null!=project.getFh()){
				project.setFh(new ArrayList<FinanceHistory>());
			}
			sortList.Sort(project.getFh(),"financeDateStr",
					"desc");
			responseBody.setEntityList(project != null ? project.getFh() : null);
			responseBody.setResult(new Result(Status.OK, "ok" , "查询融资历史成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search FinanceHistory get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	
	/**
	 * 删除历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFinanceHistory/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<com.galaxyinternet.mongodb.model.Project> deleteFinanceHistory(@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,
			HttpServletRequest request) {
		ResponseData<com.galaxyinternet.mongodb.model.Project> responseBody = new ResponseData<com.galaxyinternet.mongodb.model.Project>();
		if(uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			FinanceHistory l = new FinanceHistory();
			l.setUuid(uuid);
			if(project != null && project.getFh() != null && project.getFh().contains(l)){
				project.getFh().remove(l);
				mongoProjectService.updateById(pid, project);
				logger.info(user.getId() + ":" + user.getRealName() + " to delete FinanceHistory successful > " + project.getId());
				ListSortUtil<FinanceHistory> sortList = new ListSortUtil<FinanceHistory>();
				sortList.Sort(project.getFh(),"financeDateStr",
						"desc");
				responseBody.setEntity(project);
				responseBody.setResult(new Result(Status.OK,"ok" , "删除融资历史成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找到该融资信息!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete financeHistory get an exception {pid : {3}, uuid : {4}}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 查询历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getFinanceHistory/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> getFinanceHistory(@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,
			HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if(uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			FinanceHistory l = new FinanceHistory();
			l.setUuid(uuid);
			if(project != null && project.getFh() != null && project.getFh().contains(l)){
		        for(int i=0;i<project.getFh().size();i++){
		        	if(project.getFh().get(i).getUuid().equals(uuid)){
		        		l=project.getFh().get(i);
		        	}
		        }
				responseBody.setEntity(l);
				responseBody.setResult(new Result(Status.OK,"ok" , "查询融资历史成功!"));
				logger.info(user.getId() + ":" + user.getRealName() + " to search FinanceHistory by uuid successful > " + project.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR,"error" , "未找到该融资信息!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete learning get an exception {pid : {3}, uuid : {4}}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 修改历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSave/{uuid}/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<com.galaxyinternet.mongodb.model.Project> updateSave(@PathVariable("uuid") String uuid,
			@PathVariable("pid") String pid,@RequestBody FinanceHistory financeHistory,
			HttpServletRequest request) {
		ResponseData<com.galaxyinternet.mongodb.model.Project> responseBody = new ResponseData<com.galaxyinternet.mongodb.model.Project>();
		if(uuid == null || "".equals(uuid.trim()) 
				|| pid == null || "".equals(pid.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(pid);
			FinanceHistory l = new FinanceHistory();
			l.setUuid(uuid);
			financeHistory.setFinanceStatus(financeHistory.getFinanceStatus());
			financeHistory.setFinanceDate(null);
			if(project != null && project.getFh() != null && project.getFh().contains(l)){
				project.getFh().remove(l);
				financeHistory.setUuid(uuid);
				project.getFh().add(financeHistory);
				mongoProjectService.updateById(pid, project);
				logger.info(user.getId() + ":" + user.getRealName() + " to updateSave financeHistory successful > " + project.getId());
				ListSortUtil<FinanceHistory> sortList = new ListSortUtil<FinanceHistory>();
				sortList.Sort(project.getFh(),"financeDateStr",
						"desc");
				responseBody.setEntity(project);
				responseBody.setResult(new Result(Status.OK,"ok" , "更新融资历史成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR,"no" , "未找到该融资信息!"));
			}
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to updateSave financeHistory get an exception {pid : {3}, uuid : {4}}", 
						user.getId(), user.getRealName(), pid, uuid), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 创建项目
	 * @param id 项目ID
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.MESSAGE)
	@ResponseBody
	@RequestMapping(value = "/createProject/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> createProject(@PathVariable("id") String id,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			// 判断当前用户是否为投资经理
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if (!roleIdList.contains(UserConstant.TZJL)) {
				responseBody.setResult(new Result(Status.ERROR, "myqx", "没有权限添加项目!"));
				return responseBody;
			}
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project != null){
				//验证项目名是否重复
				Project obj = new Project();
				obj.setProjectName(project.getProjectName());
				List<Project> projectList = projectService.queryList(obj);
				if (null != projectList && projectList.size() > 0) {
					responseBody.setResult(new Result(Status.ERROR, "mccf", "项目名重复!"));
					return responseBody;
				}
				//创建项目编码
				Config config = configService.createCode();
				NumberFormat nf = NumberFormat.getInstance();
				nf.setGroupingUsed(false);
				nf.setMaximumIntegerDigits(6);
				nf.setMinimumIntegerDigits(6);
				Long did = user.getDepartmentId();
				if (did != null) {
					int code = EnumUtil.getCodeByCareerline(did.longValue());
					String projectCode = String.valueOf(code) + nf.format(Integer.parseInt(config.getValue()));
					obj.setProjectCode(String.valueOf(projectCode));
					//从MongoDB的Project中转移基础值到Mysql的Project
					obj.setProjectType(project.getProjectType());
					//默认不涉及股权转让
					obj.setStockTransfer(0);
					obj.setProjectDepartid(did);
					obj.setIndustryOwn(project.getIndustryOwn());
					obj.setProjectValuations(Double.parseDouble(project.getFormatValuations()));
					obj.setFinanceStatus(project.getFinanceStatus());
					obj.setProjectContribution(Double.parseDouble(project.getFormatContribution()));
					obj.setCurrencyUnit(0);
					obj.setProjectShareRatio(Double.parseDouble(project.getFormatShareRatio()));
					obj.setProjectCompany(project.getProjectCompany());
					if(project.getFormationDate() != null && !"".equals(project.getFormationDate().trim())){
						obj.setFormationDate(DateUtil.convertStringToDate(project.getFormationDate().trim(), "yyyy-MM-dd").getTime());
					}
					obj.setCompanyLegal(project.getCompanyLegal());
					obj.setProjectCompanyCode(project.getProjectCompanyCode());
					obj.setCreateUid(user.getId());
					obj.setCreateUname(user.getRealName());
					obj.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
					obj.setProjectStatus(DictEnum.projectStatus.GJZ.getCode());
					obj.setProjectDescribe(project.getProjectDescribe());
					obj.setProjectDescribeFinancing(project.getProjectDescribeFinancing());
					obj.setProjectBusinessModel(project.getProjectBusinessModel());
					obj.setCompanyLocation(project.getCompanyLocation());
					obj.setUserPortrait(project.getUserPortrait());
					obj.setProspectAnalysis(project.getProspectAnalysis());
					obj.setNextFinancingSource(project.getNextFinancingSource());
					obj.setIndustryAnalysis(project.getIndustryAnalysis());
					obj.setOperationalData(project.getOperationalData());
					obj.setUpdatedTime(new Date().getTime());
					obj.setCreatedTime(DateUtil.convertStringToDate(project.getCreateDate().trim(), "yyyy-MM-dd").getTime());
					obj.setFaFlag(project.getFaFlag());
					obj.setFaName(project.getFaName());
					projectService.createProject(project, obj);
				}
			}
			responseBody.setResult(new Result(Status.OK, "ok" , "查询股权结构信息成功!"));
		} catch (MongoDBException e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search shares data get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search shares data get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	//##########################版本V2.3.111结束##########################
	/**
	 * 项目列表查询
	 * @version 2016-06-21
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProject(HttpServletRequest request, @RequestBody ProjectBo project) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) getUserFromSession(request);
		
		//有搜索条件则不启动默认筛选
		if(project.getCreateUid() == null && project.getProjectDepartid() == null){
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.TZJL)){
				project.setCreateUid(user.getId());
			}else if (roleIdList.contains(UserConstant.HHR)){
				project.setProjectDepartid(user.getDepartmentId());
			}else{
				
			}
		}
		//搜索全部时,传过来参数值为0,此时要转换为查询全部
		project.setCreateUid((project.getCreateUid() != null && project.getCreateUid().longValue() == 0L) ? null : project.getCreateUid());
		project.setProjectDepartid((project.getProjectDepartid() != null && project.getProjectDepartid().longValue() == 0L) ? null : project.getProjectDepartid());
		if(null!=project.getProperty()&&project.getProperty().equals("project_progress")){
			project.setProperty(" CAST(REPLACE(project_progress,'projectProgress---','')  AS SIGNED) ");
			
		}
		if(project.getProjectPerson()!=null) project.setProjectPerson(project.getProjectPerson().toUpperCase());
		List<Department> departmentList = departmentService.queryAll();
		Page<Project> pageProject = projectService.queryPageList(project,
						new PageRequest(project.getPageNum(), 
								project.getPageSize(), 
								Direction.fromString(project.getDirection()), 
								project.getProperty()));
		//封装事业线数据
		List<Project> projectList = new ArrayList<Project>();
		for(Project p : pageProject.getContent()){
			projectList.add(p);
			for(Department d : departmentList){
				if(p.getProjectDepartid().longValue() == d.getId().longValue()){
					p.setProjectCareerline(d.getName());
					break;
				}
			}
		}
		pageProject.setContent(projectList);
		responseBody.setPageList(pageProject);
		responseBody.putAttachmentItem("user",user);
		responseBody.setResult(new Result(Status.OK, ""));
		return responseBody;
	}

	/**
	 * 修改项目信息接口
	 * 
	 * @author yangshuhua
	 * @return
	 * @throws ParseException
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.MESSAGE)
	@ResponseBody
	@RequestMapping(value = "/editProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> resetProject(@RequestBody Project project,
			HttpServletRequest request) throws ParseException {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if (project == null || project.getId() == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}

		//验证项目名是否重复
		if(project.getProjectName() != null){
			ProjectBo obj = new ProjectBo();
			obj.setProjectName(project.getProjectName());
			obj.setIdFilter(project.getId());
			List<Project> projectList = projectService.queryList(obj);
			if (null != projectList && projectList.size() > 0) {
				responseBody.setResult(new Result(Status.ERROR, "mccf", "项目名重复!"));
				return responseBody;
			}
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
		if(null!=project.getIndustryOwn()&&project.getIndustryOwn().longValue()==0){
			project.setIndustryOwn(null);
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

		String projectName = p.getProjectName();
		if(!StringUtils.isBlank(project.getProjectName())){
			projectName = project.getProjectName();
		}
		
		int num = projectService.updateById(project);
		if (num > 0) {
			responseBody.setResult(new Result(Status.OK, null, "项目修改成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request,
					projectName, project.getId(),"2");
		}
		return responseBody;
	}
//TODO
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
			List<Department> departments = departmentService.queryAll();
			Department queryOne = CollectionUtils.getItem(departments, "id", project.getProjectDepartid());
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
			
			if(project.getIndustryOwn()!=null){
				Department queryTwo = CollectionUtils.getItem(departments, "id", project.getIndustryOwn());
				if (queryTwo != null) {
					project.setIndustryOwnDs(queryTwo.getName());				
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
			project.setProjectStatus(DictEnum.projectStatus.YFJ.getCode());
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
				project.setProjectStatus(DictEnum.projectStatus.YFJ.getCode());
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
		try {
			
		User user = (User) getUserFromSession(request);
		Project p = projectService.queryById(pool.getProjectId());
		// 项目创建者用户ID与当前登录人ID是否一样
		if (p != null
				&& user.getId().doubleValue() != p.getCreateUid().doubleValue()) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"没有权限修改该项目的团队成员信息!"));
			return responseBody;
		}
		
		if(pool.getPersonBirthdayStr() != null){
			try {
				Date date = DateUtil.convertStringToDate(pool.getPersonBirthdayStr());
				pool.setPersonBirthday(date);
			} catch (ParseException e) {
				throw new Exception(pool.getPersonBirthdayStr() +" 转  Date 失败" + e);
			}
		}
		
		int num = personPoolService.updateById(pool);
		if (num > 0) {
			responseBody.setResult(new Result(Status.OK, null, "团队成员信息修改成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request,
					p.getProjectName(), p.getId());
		}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"resetProjectPerson faild"));
			if (logger.isErrorEnabled()) {
				logger.error("resetProjectPerson ", e);
			}
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

			int in = Integer.parseInt(p.getStage().substring(p.getStage().length() - 1));
			int pin = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length() - 1));
			if (in < pin) {
				//if(!utilsService.checkProIsGreenChannel(SopConstatnts.Redis._GREEN_CHANNEL_6_, p.getPid())){
				if(!utilsService.checkProIsGreenChannel(p.getPid())){
					responseBody.setResult(new Result(Status.ERROR, null, "该操作已过期!"));
					return responseBody;				
				}
			}
			
			
			// 如果是创建/未勾选"涉及股权转让"没有股权转让文档
			if (p.getFileWorktype().equals(
					DictEnum.fileWorktype.股权转让协议.getCode())) {
				if (project != null
						&& project.getProjectType() != null
						&& project.getProjectType().equals(
								DictEnum.projectType.创建.getCode())) {
					responseBody.setResult(new Result(Status.ERROR, null,
							"创建项目不需要股权转让协议!"));
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
							r.getMessageType(), r.getNumber(),r.getAttachment());
				}
			}
		} catch (Exception e) {
			logger.error("操作失败", e);
			responseBody.getResult().addError("操作失败!");
		}

		return responseBody;
	}
	
	/**
	 * 上传业务尽调报告
	 */
	/**
	 * 项目阶段中的文档上传 该项目对应的创建人操作
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/businessAdjustment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectQuery> businessAdjustment(ProjectQuery p,
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
		
		Project project = projectService.queryById(p.getPid());
		if (project == null) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "未找到相应的项目信息!"));
			return responseBody;
		}
		//上传只能在当前阶段才能进行
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
		
		int in = Integer.parseInt(p.getStage().substring(p.getStage().length() - 1));
		int pin = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length() - 1));
		if (in < pin) {
			if(!utilsService.checkProIsGreenChannel(p.getPid())){
				responseBody.setResult(new Result(Status.ERROR, null, "该操作已过期!"));
				return responseBody;				
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
		if (result == null
				|| !result.getResult().getStatus().equals(Result.Status.OK)) {
			responseBody
					.setResult(new Result(Status.ERROR, null, "缺失相应文档!"));
			return responseBody;
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
				SopResult r = ywjzdcHandler.handler(p, project);
				if (r != null && r.getStatus().equals(Result.Status.OK)) {
					responseBody.setResult(r);
					// 记录操作日志
					ControllerUtils.setRequestParamsForMessageTip(request,
							project.getProjectName(), project.getId(),
							r.getMessageType(), r.getNumber(),r.getAttachment());
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
				project.setProjectStatus(DictEnum.projectStatus.GJZ.getCode()); // 字典
																				// 项目状态
																				// =
																				// 会议结论
																				// 待定
				projectService.updateById(project);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(),StageChangeHandler._6_2_);
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
	@com.galaxyinternet.common.annotation.Logger
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
				tm.setReserveTimeStartStr(null);
				tm.setReserveTimeEndStr(null);
				tm.setReserveTimeEnd(null);
				tm.setReserveTimeStart(null);
				tm.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				tm.setUpdatedTime((new Date()).getTime());
				tm.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingService.updateById(tm);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				
				ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), "10.1", UrlNumber.one);
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
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), StageChangeHandler._6_4_);
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
	@com.galaxyinternet.common.annotation.Logger
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
				tm.setReserveTimeStartStr(null);
				tm.setReserveTimeEndStr(null);
				tm.setReserveTimeEnd(null);
				tm.setReserveTimeStart(null);
				tm.setUpdatedTime((new Date()).getTime());
				tm.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingService.updateById(tm);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), "10.2", UrlNumber.one);
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
		/*// 验证文档是否齐全//尽职调查去掉文件验证
		SopFile file = new SopFile();
		file.setProjectId(pid);
		file.setFileValid(1);
		file.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		List<SopFile> files = sopFileService.queryList(file);
		if (files == null
				|| (project.getProjectType().equals(
						DictEnum.projectType.投资.getCode()) && files.size() < 4)
				|| (project.getProjectType().equals(
						DictEnum.projectType.创建.getCode()) && files.size() < 2)) {
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
		}*/
		try {
			//绿色通道标识 入库
			boolean toGreen = false;
			SopFile file = new SopFile();
			file.setProjectId(pid);
			file.setFileValid(1);
			file.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			List<SopFile> files = sopFileService.queryList(file);
			if (files == null
					|| (project.getProjectType().equals(DictEnum.projectType.投资.getCode()) && files.size() < 4)
					|| (project.getProjectType().equals(DictEnum.projectType.创建.getCode()) && files.size() < 2)) {
				toGreen = true;
			}
			//校验，文件记录存在，并且 aliyun中对应的key也存在
			if(!toGreen){
				for (SopFile f : files) {
					if (f.getFileKey() == null || "".equals(f.getFileKey().trim())) {
						toGreen = true;
					}
				}
			}
			if(toGreen){
				if(project.getGreanChannel() == null || StringUtils.isBlank(project.getGreanChannel())){
					project.setGreanChannel(SopConstatnts.GreanMark.JZDC);
				}else if(project.getGreanChannel().indexOf(SopConstatnts.GreanMark.JZDC) == -1){
					project.setGreanChannel(project.getGreanChannel() + "," +SopConstatnts.GreanMark.JZDC);
				}
				
			//如果不是绿色通道,则处理任务	
			}else{
				SopTask task = new SopTask();
				//条件
				task.setProjectId(pid);
				task.setTaskType(DictEnum.taskType.协同办公.getCode());
				task.setTaskFlag(SopConstant.TASK_FLAG_YWJD);
				//修改
				Date time = new Date();
				task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
				task.setUpdatedTime(time.getTime());
				task.setTaskDeadline(time);
				sopTaskService.updateTask(task);
			}
			
			projectService.toSureMeetingStage(project);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), StageChangeHandler._6_7_);
			
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
				tm.setReserveTimeStartStr(null);
				tm.setReserveTimeEndStr(null);
				tm.setReserveTimeEnd(null);
				tm.setReserveTimeStart(null);
				tm.setUpdatedTime((new Date()).getTime());
				tm.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingService.updateById(tm);
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(project.getId());
				ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), "10.3", UrlNumber.one);
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
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())||project.getProjectStatus().equals(DictEnum.projectStatus.YFJ.getCode())){ //字典 项目状态 = 会议结论 关闭
			return new Result(Status.ERROR, null, "项目已关闭!");
		}else if(project.getProjectStatus().equals(DictEnum.projectStatus.YTC.getCode())){ //字典 项目状态 = 会议结论 关闭
			return new Result(Status.ERROR, null, "项目已退出!");
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
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	@ResponseBody
	@RequestMapping(value = "/breakpro",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> breakproject(@RequestBody OperationLogs param,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();

		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_KEY);
		try {
			// project id 验证
			Project project = new Project();
			project = projectService.queryById(param.getProjectId());
			// 项目关闭将会议记录修改为否决项目
			MeetingScheduling me = new MeetingScheduling();
			me.setProjectId(param.getProjectId());
			List<MeetingScheduling> meetingList = meetingSchedulingService
					.queryList(me);
			if (!meetingList.isEmpty()) {
				for (MeetingScheduling meet : meetingList) {
					
					/**当否决项目时,将(当前)排期未排期的会议删掉 .注:已经通过排期的会议状态不进行更改**/
					//1.否决CEO评审
					if(DictEnum.projectProgress.CEO评审.getCode().equals(project.getProjectProgress())
							&& DictEnum.meetingType.CEO评审.getCode().equals(meet.getMeetingType())){
						if(DictEnum.meetingSheduleResult.待排期.getCode() == meet.getScheduleStatus()){
							meetingSchedulingService.deleteById(meet.getId());
						}
					}
					//2.否决内部评审
					if(DictEnum.projectProgress.内部评审.getCode().equals(project.getProjectProgress())
							&& DictEnum.meetingType.内评会.getCode().equals(meet.getMeetingType())){
						if(DictEnum.meetingSheduleResult.待排期.getCode() == meet.getScheduleStatus()){
							meetingSchedulingService.deleteById(meet.getId());
						}
					}
					//3.否决立项会
					if(DictEnum.projectProgress.立项会.getCode().equals(project.getProjectProgress())
							&& DictEnum.meetingType.立项会.getCode().equals(meet.getMeetingType())){
						if(DictEnum.meetingSheduleResult.待排期.getCode() == meet.getScheduleStatus()){
							meetingSchedulingService.deleteById(meet.getId());
						}
					}
					//4.否决投决会
					if(DictEnum.projectProgress.投资决策会.getCode().equals(project.getProjectProgress())
							&& DictEnum.meetingType.投决会.getCode().equals(meet.getMeetingType())){
						if(DictEnum.meetingSheduleResult.待排期.getCode() == meet.getScheduleStatus()){
							meetingSchedulingService.deleteById(meet.getId());
						}
					}
					
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

			project.setProjectStatus(DictEnum.projectStatus.YFJ.getCode());
			int id = projectService.closeProject(project);
			if (id != 1) {
				responseBody.setResult(new Result(Status.ERROR, null, "更新失败"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			ControllerUtils.setRequestParamsForMessageTip(request,project.getProjectName(), project.getId(),null, false, null, param.getReason(), null);
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
			} /*else if (index == 6) { // 尽调阶段，文档齐全后， 申请投决会排期按钮 可用
			//尽职调查去掉文件验证
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
						&& ((projectType.equals(DictEnum.projectType.投资
								.getCode()) && files.size() == 4) || (projectType
								.equals(DictEnum.projectType.创建.getCode()) && files
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

			} */else if (index == 7) { // 投决会阶段，在排期池中时， 申请投决会排期按钮 不可用
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.MESSAGE })
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
			if(sopFile != null){
				if (sopFile.getFileKey() == null) {
					fileKey = String.valueOf(IdGenerator
							.generateId(OSSHelper.class));
				} else {
					fileKey = sopFile.getFileKey();
				}
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
//			if(DictsopFile.getFileWorktype())
			responseBody.setResult(new Result(Status.OK, null, "更新文件成功!"));
			Project project = projectService.queryById(sopFile.getProjectId());
			String messageType = null;
			if(p.getFileWorktype().equals(DictEnum.fileWorktype.投资意向书.getCode())){
				messageType = SopFileMessageHandler._5_2_;
			}else if(p.getFileWorktype().equals(DictEnum.fileWorktype.业务尽职调查报告.getCode())){
				messageType = SopFileMessageHandler._5_4_;
			}else if(p.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
				messageType = SopFileMessageHandler._5_8_;
			}else if(p.getFileWorktype().equals(DictEnum.fileWorktype.股权转让协议.getCode())){
				messageType = SopFileMessageHandler._5_12_;
			}
			ControllerUtils.setRequestParamsForMessageTip(request,
					project.getProjectName(),project.getId(),
					messageType,null,sopFile);
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
	//TODO
	/**
	 * 获取枚举里的融资状态列表
	 * @param id
	 * @param request
	 * @return 2016/6/8
	 */
	@ResponseBody
	@RequestMapping(value = "/getFinanceStatusByParent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Dict> getFinanceStatusByParent(@PathVariable String id,
			HttpServletRequest request) {
		ResponseData<Dict> responseBody = new ResponseData<Dict>();
		List<Dict> dicts = new ArrayList<Dict>();
		Dict dict = null;
		Result result = new Result();
		try {
			for (DictEnum.financeStatus financeStatus : DictEnum.financeStatus.values()) {
				dict = new Dict();
				dict.setCode(financeStatus.getCode());
				dict.setName(financeStatus.getName());
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
				byte Edit = 1;//可编辑
				byte isTransfor = 1;//未移交
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
				//正在移交中的项目
				if(FXFunctionTags.isTransfering(ms.getProjectId())){
					Edit = 0;//不可编辑
					isTransfor = 0;//移交中
				}
				ms.setIsEdit(Edit);
				ms.setIsTransfor(isTransfor);
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.BATCHMESSAGE,LogType.IOSPUSHMESS})
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
		List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();
		try {
			for (MeetingScheduling ms : query) {
				MeetingScheduling  redisPush = new MeetingScheduling();
				String mestr = "";
				String messageType = "";
				MeetingScheduling oldMs = msmap.get(ms.getId());
				
				redisPush.setId(oldMs.getId());
				redisPush.setProjectId(oldMs.getProjectId());
				redisPush.setMeetingType(oldMs.getMeetingType());
				Project pj = mapProject.get(oldMs.getProjectId());
				
				redisPush.setProjectName(pj.getProjectName());
				redisPush.setCreateId(pj.getCreateUid().toString());
				//验证已经已通过|已否决的会议不能进行排期
				if(2 == oldMs.getScheduleStatus() || 3 == oldMs.getScheduleStatus()){
					proNameList.append(pj.getProjectName());
					proNameList.append(" ");
					continue;
				}
				if (DictEnum.meetingType.投决会.getCode().equals(
						ms.getMeetingType())) {
					mestr = DictEnum.meetingType.投决会.getName();
					messageType = "11.3";
				}
				if (DictEnum.meetingType.立项会.getCode().equals(
						ms.getMeetingType())) {
					mestr = DictEnum.meetingType.立项会.getName();
					messageType = "11.2";
				}
				if (DictEnum.meetingType.CEO评审.getCode().equals(
						ms.getMeetingType())) {
					mestr = DictEnum.meetingType.CEO评审.getName();
					messageType = "11.1";
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
				User belongUser = userService.queryById(pj.getCreateUid());
				// 如果是更新或取消排期时间
				if (oldMs.getReserveTimeStart() != null
						&& oldMs.getReserveTimeEnd() != null) {
					// 取消排期时间
					if (ms.getReserveTimeStart() == null
							&& ms.getReserveTimeEnd() == null) {
						ms.setScheduleStatus(0);
						meetingSchedulingService.updateByIdSelective(ms);
						sendTaskProjectEmail(request,pj,messageInfo,userlist,null,null,0,UrlNumber.three);
						belongUser.setKeyword("cancle:"+DateUtil.convertDateToStringForChina(oldMs.getReserveTimeStart()));	
					
						redisPush.setReserveTimeStart(oldMs.getReserveTimeStart());
						cache.removeRedisSetOBJ(Constants.PUSH_MESSAGE_LIST, redisPush);
						
					} else {
						// 更新会议时间
						if (oldMs.getReserveTimeStart().getTime() != ms
								.getReserveTimeStart().getTime()
								|| oldMs.getReserveTimeEnd().getTime() != ms
										.getReserveTimeEnd().getTime()) {
							meetingSchedulingService.updateByIdSelective(ms);
							sendTaskProjectEmail(request,pj,messageInfo,userlist,ms.getReserveTimeStart(),ms.getReserveTimeEnd(),1,UrlNumber.two);
							belongUser.setKeyword("update:"+DateUtil.convertDateToStringForChina(oldMs.getReserveTimeStart())+","+DateUtil.convertDateToStringForChina(ms.getReserveTimeStart()));	
							
							redisPush.setReserveTimeStart(oldMs.getReserveTimeStart());
							cache.removeRedisSetOBJ(Constants.PUSH_MESSAGE_LIST, redisPush);
							
							redisPush.setReserveTimeStart(ms.getReserveTimeStart());
							cache.setRedisSetOBJ(Constants.PUSH_MESSAGE_LIST,redisPush);
							
						}else{
							belongUser.setKeyword("operate");	
						}
					}
				} else {
					// 新安排会议时间
					if (ms.getReserveTimeStart() != null
							&& ms.getReserveTimeEnd() != null) {
						meetingSchedulingService.updateByIdSelective(ms);
						sendTaskProjectEmail(request,pj,messageInfo,userlist,ms.getReserveTimeStart(),ms.getReserveTimeEnd(),1,UrlNumber.one);
						belongUser.setKeyword("insert:"+DateUtil.convertDateToStringForChina(ms.getReserveTimeStart()));	
						
						
						redisPush.setReserveTimeStart(ms.getReserveTimeStart());
						cache.setRedisSetOBJ(Constants.PUSH_MESSAGE_LIST,redisPush);
						
					}else{
						belongUser.setKeyword("operate");	
					}

				}
				//消息数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, pj.getProjectName());
				params.put(PlatformConst.REQUEST_SCOPE_USER, belongUser);
				params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, pj.getId());
				params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
				params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, UrlNumber.one);
				if(!"operate".equals(belongUser.getKeyword())){
					messageList.add(params);
				}
			}
			ControllerUtils.setRequestBatchParamsForMessageTip(request,messageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "更新失败!"));
			logger.error("更新排期失败 ",e.getMessage());
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
			String address = "";
			for(User user: userlist){
				/*ControllerUtils.setRequestParamsForMessageTip(request,
						user, pj.getProjectName(), pj.getId(),
						number);*/
				
				if(StringUtils.isBlank(address)){
					address+=user.getEmail()+"@galaxyinternet.com";
				}else{
					address+=";"+user.getEmail()+"@galaxyinternet.com";
				}
				
			}
			sendMailToTZJL(request, type, address,
					"",
					pj.getProjectCode(),pj.getProjectName(),
					messageInfo, reserveTimeStart,
					reserveTimeEnd);
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
			logger.error("更新排期失败 ", e.getMessage());
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
		//toAddress =toAddress+"@galaxyinternet.com";
		String content = MailTemplateUtils
				.getContentByTemplate(Constants.MAIL_PQC_CONTENT);
		/*String[] to = toAddress.split(";");
		if (to != null && to.length == 1) {
			int atIndex = toAddress.lastIndexOf("@");
			tzjlName = toAddress.substring(0, atIndex) + ":<br>您好!";
		}*/
		tzjlName = "您好!";
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
			logger.error("发送邮件失败.");
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
	/**
	 * 项目信息页面
	 * @param id 项目ID
	 * @param index 选项卡序号：0-6
	 * @return
	 */
	@RequestMapping("/showProject/{id}/{index}")
	public ModelAndView projectDetail(@PathVariable("id") Long id, @PathVariable("index") Integer index)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/project/sopinfo/main");
		mv.addObject("projectId", id);
		mv.addObject("pid", id);
		mv.addObject("index", index);
		return mv;
	}
	/**
	 * 项目详情页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView detail(@PathVariable("id") Long id)
	{
		ModelAndView mv = new ModelAndView();
//		mv.setViewName("/project/sopinfo/projectinfo");
		mv.setViewName("/project/sopinfo/project_detail");
		mv.addObject("projectId", id);
		mv.addObject("pid", id);
		return mv;
	}
	/**
	 * 项目详情页-股权结构选项卡
	 * @param id
	 * @return
	 */
	@RequestMapping("/tabShares")
	public ModelAndView tabShares(Long id)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/project/sopinfo/tab_shares");
		mv.addObject("projectId", id);
		return mv;
	}
	/**
	 * 保存公司法人信息
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/saveCompanyInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData<Project> saveCompanyInfo(@RequestBody Project project)
	{
		ResponseData<Project> data = new ResponseData<Project>();
		try
		{
			if(project == null || project.getId() == null)
			{
				data.getResult().addError("数据错误.");
				return data;
			}
			Project po = projectService.queryById(project.getId());
			if(po == null)
			{
				data.getResult().addError("数据错误.");
				return data;
			}
			projectService.updateById(project);
			
			po.setProjectCompany(project.getProjectCompany());
			po.setProjectCompanyCode(project.getProjectCompanyCode());
			po.setCompanyLegal(project.getCompanyLegal());
			po.setFormationDate(project.getFormationDate());
			data.setEntity(po);
		}
		catch (Exception e)
		{
			if(logger.isErrorEnabled())
			{
				logger.error("保存法人信息错误:"+project,e);
			}
			data.getResult().addError(e.getMessage());
		}
		return data;
	}
	
	
	
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toprolog/{pid}", method = RequestMethod.GET)
	public String toprolog(@PathVariable("pid") Long pid, HttpServletRequest request) {
		Project proinfo = new Project();
		proinfo = projectService.queryById(pid);
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", proinfo.getProjectProgress());
		request.setAttribute("pname", proinfo.getProjectName());
		request.setAttribute("projectId", pid);
		return "project/sopinfo/tab_operLog";
	}
	/**
	 * sop tab页面  访谈 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/proview/{pid}", method = RequestMethod.GET)
	public String proView(@PathVariable("pid") Long pid, HttpServletRequest request) {
		Project proinfo = new Project();
		proinfo = projectService.queryById(pid);
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", proinfo.getProjectProgress());
		request.setAttribute("pname", proinfo.getProjectName());
		request.setAttribute("projectId", pid);
		return "project/sopinfo/tab_interview";
	}
	/**
	 * sop tab页面  会议 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/promeet/{pid}", method = RequestMethod.GET)
	public String proMeet(@PathVariable("pid") Long pid, HttpServletRequest request) {
		Project proinfo = new Project();
		proinfo = projectService.queryById(pid);
		request.setAttribute("proinfo", GSONUtil.toJson(proinfo));
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", proinfo.getProjectProgress());
		request.setAttribute("pname", proinfo.getProjectName());
		request.setAttribute("projectId", pid);
		return "project/sopinfo/tab_meeting";
	}
	/**
	 * sop tab页面  近期访谈、会议记录查询    /galaxy/project/getnearnotes/
	 */
	@ResponseBody
	@RequestMapping(value = "/getnearnotes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<BaseEntity> getNearNotes(@PathVariable Long id, HttpServletRequest request) {
		ResponseData<BaseEntity> responseBody = new ResponseData<BaseEntity>();
		try {
			Project proinfo = new Project();
			proinfo = projectService.queryById(id);
			if(proinfo ==null || proinfo.getId()==null){
				responseBody.setResult(new Result(Status.ERROR, null, "项目信息错误!"));
				return responseBody;
			}
			
			List<InterviewRecord> viewList = new ArrayList<InterviewRecord>();
			List<MeetingRecord> meetList = new ArrayList<MeetingRecord>();
			
			PageRequest pageable = new PageRequest();
			pageable = new PageRequest(0, 3);
			
			InterviewRecord viewQuery = new InterviewRecord();
			viewQuery.setProjectId(id);;
			
			MeetingRecord meetQuery = new MeetingRecord();
			meetQuery.setProjectId(id);
			
			viewList = interviewRecordService.queryList(viewQuery, pageable);
			meetList = meetingRecordService.queryList(meetQuery, pageable);
			
			Map<String,Object> listresult = new HashMap<String,Object>();
			if(viewList!=null && !viewList.isEmpty()){
				listresult.put("viewList", viewList);
			}
			if(meetList!=null && !meetList.isEmpty()){
				listresult.put("meetList", meetList);
			}
			responseBody.setUserData(listresult);
			responseBody.setResult(new Result(Status.OK, null));
		} catch (Exception e) {
			logger.error("getnearnotes 近期访谈、会议记录查询失败", e);
			responseBody.setResult(new Result(Status.ERROR, null, "近期访谈、会议记录查询失败"));
			return responseBody;
		}
		
		return responseBody;
	}
	
	/**
	 * 初始化 项目会议tab的按钮， 明确会议添加、排期
	 * @return map.add = y/n/k 
	 * 				y:可添加会议
	 * 				n:不可以添加，需要排期
	 * 				k:不允许添加
	 * 				v:不可以添加，需要秘书排期操作
	 * 		  map.meettype = meetingType:2
	 * 		  map.butname = '添加立项会会议记录' / '申请立项会排期'
	 */
	@ResponseBody
	@RequestMapping(value = "/initpromeetbut/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<BaseEntity> initProMeetBut(@PathVariable Long id, HttpServletRequest request) {
		ResponseData<BaseEntity> responseBody = new ResponseData<BaseEntity>();
		User user = (User) getUserFromSession(request);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Project proinfo = projectService.queryById(id);
			if(proinfo ==null || proinfo.getId()==null ||proinfo.getProjectProgress()==null){
				responseBody.setResult(new Result(Status.ERROR, null, "项目信息错误!"));
				return responseBody;
			}
			
			//非项目创建者，or  项目已否决    不允许添加会议
			/*GJZ("跟进中"	 ,   "projectStatus:0"),
			THYY("投后运营" ,  "projectStatus:1"),
			YFJ("已否决"		,"projectStatus:2"),
			YTC("已退出"		,"projectStatus:3");*/
			if(user.getId().intValue()!=proinfo.getCreateUid().intValue() || proinfo.getProjectStatus().equals("meetingResult:3") ||  
					proinfo.getProjectStatus().equals(DictEnum.projectStatus.YFJ.getCode()) || proinfo.getProjectStatus().equals(DictEnum.projectStatus.YTC.getCode())){
				resultMap.put("add", "k");
				responseBody.setUserData(resultMap);
				responseBody.setResult(new Result(Status.OK, null));
				return responseBody;
			}
			
			String add = null;
			String meettype = null;
			String butname = null;
			
			int indez = Integer.parseInt(proinfo.getProjectProgress().substring(proinfo.getProjectProgress().length()-1)) ;
			
			if(indez == 2){   // 内部评审("内部评审","projectProgress:2"),
				meettype = "meetingType:1";
				butname = "内评会";
			}else if(indez == 3){   // CEO评审("CEO评审","projectProgress:3"),
				meettype = "meetingType:2";
				butname = "CEO评审";
			}else if(indez == 4){   //立项会("立项会","projectProgress:4"),
				meettype = "meetingType:3";
				butname = "立项会";
			}else if(indez == 7){   // 投资决策会("投资决策会","projectProgress:7"),
				meettype = "meetingType:4";
				butname = "投决会";
			}else{ 
				add = "k";
			}
			/*
					    会议通过                               待定 
			内评会：     直接到下一个阶段          一直循环添加会议记录
			ceo评审：  申请立项会排期  		申请ceo排期						
			立项会：     直接到下一个阶段  	申请立项会排期	
			投决会：     直接到下一个阶段  	申请投决会排期
			*/
			if(add == null && meettype !=null){
				MeetingRecord mrQuery = new MeetingRecord();
				mrQuery.setProjectId(id);
				mrQuery.setMeetingType(meettype);
				mrQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
				Long mrCount = meetingRecordService.queryCount(mrQuery);
				
				if(mrCount != null && mrCount.longValue() > 0L){  //有通过的会议，不能再添加
					if(meettype.equals("meetingType:2")){
						add = "n";
						butname = "申请"+"立项会"+"排期";
						meettype = "p_"+meettype;
					}else{
						add = "k";
					}
				}else{    //待定，会议排期池校验
					if(meettype.equals("meetingType:1")){
						add = "y";
						butname = "添加会议记录";
					}else{
						MeetingScheduling scheduling = new MeetingScheduling();
						scheduling.setMeetingType(meettype);
						scheduling.setProjectId(id);
						scheduling = meetingSchedulingService.queryOne(scheduling);
						if(scheduling == null || scheduling.getId()==null){
							responseBody.setResult(new Result(Status.ERROR, null, "项目排期池信息错误!"));
							return responseBody;
						}
						/*
						`sop_meeting_scheduling`    0       2	
												ms -1      	排期but-->0  ms-->1   
						`sop_meeting_record`        待定   	待定
						*/
						if(scheduling.getScheduleStatus() == 0){ //0 等秘书排期操作
							add = "v";
							butname = "待排期";
						}else if(scheduling.getScheduleStatus() == 1){ //1表示已排期,可以添加会议记录
							add = "y";
							//butname = "添加"+butname+"会议记录";
							butname = "添加会议记录";
						}else if(scheduling.getScheduleStatus() == 2){ //1表示已排期,可以添加会议记录
							add = "n";
							butname = "申请"+butname+"排期";
						}
					}
				}
			}
			
			resultMap.put("add", add);
			resultMap.put("meettype", meettype);
			resultMap.put("butname", butname);

			responseBody.setUserData(resultMap);
			responseBody.setResult(new Result(Status.OK, null));
		} catch (Exception e) {
			logger.error("initProMeetBut 会议功能按钮初始化失败", e);
			responseBody.setResult(new Result(Status.ERROR, null, "会议功能按钮初始化失败"));
			return responseBody;
		}

		return responseBody;
	}

	/**
	 * sop tab页面  会议 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toFileList/{pid}", method = RequestMethod.GET)
	public String toFileList(@PathVariable("pid") Long pid, HttpServletRequest request) {
		Project project = new Project();
		project = projectService.queryById(pid);
		request.setAttribute("proinfo", GSONUtil.toJson(project));
		request.setAttribute("projectId", pid);
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", project.getProjectProgress());
		request.setAttribute("projectName", project.getProjectName());
		return "project/sopinfo/tab_filelist";
	}
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toAppropriation/{searchPartMoney}/{pid}", method = RequestMethod.GET)
	public String toAppropriation(@PathVariable("pid") Long pid, @PathVariable("searchPartMoney") String searchPartMoney, HttpServletRequest request) {
		Project proinfo = new Project();
		proinfo = projectService.queryById(pid);
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", proinfo.getProjectProgress());
		request.setAttribute("pname", proinfo.getProjectName());
		request.setAttribute("projectId", pid);
		request.setAttribute("searchPartMoney", searchPartMoney);
		
		String numOfShow = request.getParameter("numOfShow");
		if(numOfShow == null)
		{
			numOfShow = "2";
		}
		request.setAttribute("numOfShow", numOfShow);
		return "project/sopinfo/tab_appropriation";
	}
	
	/**
	 * 否决项目弹框
	 * @return
	 */
	@RequestMapping(value = "/toRefuseProject", method = RequestMethod.GET)
	public String toRefuseProject(){
		return "project/dialog/refuseProjectDialog";
	}
	
	
	
	@RequestMapping(value="/detail/toTabProjectInfo/{projectId}", method=RequestMethod.GET)
	public String toTabProjectInfo(HttpServletRequest request,@PathVariable Long projectId){
		if(projectId!=null && projectId.intValue() != 0){
			Project project = projectService.queryById(projectId);
			if (project != null) {
				List<Department> departments = departmentService.queryAll();
				Department queryOne = CollectionUtils.getItem(departments, "id", project.getProjectDepartid());
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
				
				if(project.getIndustryOwn()!=null){
					Department queryTwo = CollectionUtils.getItem(departments, "id", project.getIndustryOwn());
					if (queryTwo != null) {
						project.setIndustryOwnDs(queryTwo.getName());				
					}
				}						
				
			} 
			request.setAttribute("proinfo", GSONUtil.toJson(project));
			request.setAttribute("projectId", projectId);
		}
			
		return "project/sopinfo/tab_info";
	}
	
	/**
	 * 跳转tabFile页面
	 */
	@RequestMapping(value = "/detail/toTabFile/{projectId}", method = RequestMethod.GET)
	public String toTabFile(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		Project project = new Project();
		project = projectService.queryById(projectId);
		request.setAttribute("proinfo", GSONUtil.toJson(project));
		request.setAttribute("projectId", projectId);
		request.setAttribute("prograss", project.getProjectProgress());
		request.setAttribute("projectName", project.getProjectName());
		return "project/sopinfo/tab_file";
	}
	
	
	/**
	 * 跳转Right页面
	 */
	@RequestMapping(value = "/detail/toRight/{projectId}", method = RequestMethod.GET)
	public String toRight(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		Project project = new Project();
		project = projectService.queryById(projectId);
		request.setAttribute("proinfo", GSONUtil.toJson(project));
		request.setAttribute("projectId", projectId);
		request.setAttribute("pid", projectId);
		request.setAttribute("prograss", project.getProjectProgress());
		request.setAttribute("projectName", project.getProjectName());
		return "project/sopinfo/includeRight";
	}
	
	/**
	 * 新建项目step2
	 */
	@ResponseBody
	@RequestMapping(value = "/addProjectStep2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<com.galaxyinternet.mongodb.model.Project> addProjectStep2(@RequestBody com.galaxyinternet.mongodb.model.Project project,
			HttpServletRequest request) throws ParseException {
		ResponseData<com.galaxyinternet.mongodb.model.Project> responseBody = new ResponseData<com.galaxyinternet.mongodb.model.Project>();
		
		if(project.getId() == null || "".equals(project.getId())){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!")); 
		}
		    responseBody.setResult(new Result(Status.OK, "操作成功!")); 
		try {
			if(!StringUtils.isEmpty(project.getSopfiletype())){
				//验证商业计划书是否上传成功
				SopFile file = (SopFile) request.getSession().getAttribute("businessPlan");
				if(file != null && file.getFileLength().longValue() <= 0){
					responseBody.setResult(new Result(Status.ERROR, "file error", "商业计划书上传失败!"));
					return responseBody;
				}
				project.setSopFile(file);
			}
			mongoProjectService.updateById(project.getId(), project);
			responseBody.setEntity(project);
		} catch (MongoDBException e) {
			// TODO Auto-generated catch block
			responseBody.setResult(new Result(Status.ERROR, "操作失败!"));
			logger.error("add project step2 error", e);
		}
		return responseBody;
	}
	
	
	/**
	 * 跳转Right页面
	 */
	@RequestMapping(value = "/addProjectHistory/{id}", method = RequestMethod.GET)
	public String toRight(@PathVariable("id") String id, HttpServletRequest request) {
		try {
			com.galaxyinternet.mongodb.model.Project pro = mongoProjectService.findById(id);
			request.setAttribute("project", pro);
		} catch (MongoDBException e) {
			// TODO Auto-generated catch block
			logger.error("add project step2 error", e);
		}
		
		return "project/v_addProject";
	}
	
	
	
	
	
	
	
	
	//TODO STEP 4 
	
	/**
	 * 查询项目 创建前 访谈记录
	 * 
	 * @param uuid:mongoDB中 项目标识 <br/>
	 * 	
	 * @return responseBody
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPreProView", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> queryPreProView(HttpServletRequest request, @RequestBody InterviewRecord view) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		Long total = 0l;
		List<InterviewRecord> resultViewList = new ArrayList<InterviewRecord>();
		Page<InterviewRecord> resultViewPage = new Page<InterviewRecord>(resultViewList, total);
		
		try {
			String id = view.getPid();
			if(id!=null){
				com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
				if(project!=null){
					resultViewList = project.getView();
					if(resultViewList!=null && !resultViewList.isEmpty()){
						resultViewPage.setContent(resultViewList);
						resultViewPage.setTotal((long) resultViewList.size());
					}
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR,null, "项目信息缺失"));
				return responseBody;
			}
			
			responseBody.setPageList(resultViewPage);
			responseBody.setResult(new Result(Status.OK, ""));
			
			return responseBody;
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			logger.error("add project step4 error", e);
		}
		
		return responseBody;
	}
	
	
	/**
	 * 保存项目 创建前 访谈记录
	 * 
	 * @param id:mongoDB中 项目标识 <br/>
	 * 	
	 * @return responseBody
	 */
	@ResponseBody
	@RequestMapping(value = "/savePreProViewAndFile", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> savePreProViewAndFile(InterviewRecord interviewRecord, HttpServletRequest request,HttpServletResponse response ) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		try {
			if(interviewRecord.getPid() == null || interviewRecord.getViewTarget() == null){
				String json = JSONUtils.getBodyString(request);
				interviewRecord = GSONUtil.fromJson(json, InterviewRecord.class);
			}
			
			if(interviewRecord == null || interviewRecord.getPid() == null || interviewRecord.getViewDate() == null || interviewRecord.getViewTarget() == null ){
				responseBody.setResult(new Result(Status.ERROR,null, "请完善访谈信息"));
				return responseBody;
			}
			
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(interviewRecord.getPid());
			if(project == null){
				responseBody.setResult(new Result(Status.ERROR,null, "项目信息缺失"));
				return responseBody;
			}
			
			List<InterviewRecord> resultViewList = project.getView();
			if(resultViewList==null || resultViewList.isEmpty()){
				resultViewList = new ArrayList<InterviewRecord>();
			}
			
			interviewRecord.setCreatedId(user.getId());
			String uuid=UUIDUtils.create().toString();
			interviewRecord.setUuid(uuid);
			
			
			//保存
			if(ServletFileUpload.isMultipartContent(request)){
				String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
				//上传成功后
				if(result!=null){
					SopFile sopFile = new SopFile();
					//sopFile.setProjectId(project.getId());
					sopFile.setProjectProgress("projectProgress:1");
					sopFile.setBucketName(OSSFactory.getDefaultBucketName()); 
					sopFile.setFileKey(fileKey); 
					sopFile.setFileLength(result.getContentLength()); 
					sopFile.setFileName(result.getFileName());
					sopFile.setFileSuffix(result.getFileSuffix());
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
					
					interviewRecord.setSopFile(sopFile);
				}else{
					responseBody.setResult(new Result(Status.ERROR,null, "访谈添加文件上传失败"));
					return responseBody;
				}
			}
			
			resultViewList.add(interviewRecord);
			project.setView(resultViewList);
			mongoProjectService.updateById(interviewRecord.getPid(), project);
			
			responseBody.setResult(new Result(Status.OK, ""));

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "访谈添加失败"));
			logger.error("add project step4 error", e);
		}
		return responseBody;
	}
	
	
	
	/**
	 * 保存项目 创建前 访谈记录(编辑后保存)
	 * 
	 * @param id:mongoDB中 项目标识 <br/>
	 * 	
	 * @return responseBody
	 */
	@ResponseBody
	@RequestMapping(value = "/editPreProViewAndFile/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> editPreProViewAndFile(HttpServletRequest request,@PathVariable("id") String id, @RequestBody InterviewRecord view ) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		try {
			
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project == null){
				responseBody.setResult(new Result(Status.ERROR,null, "项目信息缺失"));
				return responseBody;
			}
			
			List<InterviewRecord> resultViewList = project.getView();
			if(resultViewList==null || resultViewList.isEmpty()){
				responseBody.setResult(new Result(Status.ERROR,null, "访谈信息缺失"));
				return responseBody;
			}
			
			for(InterviewRecord inView : resultViewList){
				if(inView.getUuid().equals(view.getUuid())){
					inView.setViewNotes(view.getViewNotes());
					break;
				}
			}
			
			project.setView(resultViewList);
			mongoProjectService.updateById(id, project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			
			return responseBody;
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"操作失败"));
			logger.error("add project step4 error", e);
		}
		
		return responseBody;
	}
		
	




	/**
	 * 项目 创建前
	 */
	@ResponseBody
	@RequestMapping(value = "/createProByPreInfo/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> createProByPreInfo(HttpServletRequest request,@PathVariable("id") String id) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			com.galaxyinternet.mongodb.model.Project project = mongoProjectService.findById(id);
			if(project == null){
				responseBody.setResult(new Result(Status.ERROR,null, "项目前置信息缺失"));
				return responseBody;
			}
			
			projectService.newProByPreInfo(user.getId(),user.getDepartmentId(),user.getRealName(),project);

			
			responseBody.setResult(new Result(Status.OK, ""));
			
			return responseBody;
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"操作失败"));
			logger.error("add project step4 error", e);
		}
		
		return responseBody;
	}

	





	/**
	 * 添加/编辑  团队成员弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addProPerson", method = RequestMethod.GET)
	public String addProPerson(HttpServletRequest request) {
		return "project/tanchuan/v_project_person";
	}
	
	/**
	 * 查看团队信息弹出层
	 * @return
	 */
	@RequestMapping(value = "/toProPerView", method = RequestMethod.GET)
	public String toProPerView(HttpServletRequest request) {
		return "project/tanchuan/v_look_project_person";
	}
	
	/**
	 * 添加团队成员
	 * 1、personPool
	 * 2、personLearn
	 * 3、personWork
	 * 4、projectPerson
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> addProPersonAndPerInfo(@RequestBody PersonPool pool, HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		
		try {
			//PersonPool pool = personResumetc.getPersonPool();
			if (pool.getProjectId() == null || pool.getProjectId() <= 0 || pool.getPersonName() == null || pool.getPersonTelephone() == null) {
				responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
				return responseBody;
			}
			User user = (User) getUserFromSession(request);
			Project p = projectService.queryById(pool.getProjectId());
			// 项目创建者用户ID与当前登录人ID是否一样
			if (p != null && user.getId().longValue() != p.getCreateUid().longValue()) {
				responseBody.setResult(new Result(Status.ERROR, null, "没有权限为该项目添加团队成员!"));
				return responseBody;
			}
			
			Long id = projectService.addProPersonAndPerInfo(pool);
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, null, "团队成员添加成功!"));
			//responseBody.setEntity(pool);
			ControllerUtils.setRequestParamsForMessageTip(request,p.getProjectName(), p.getId());
		} catch (Exception e) {
			logger.error("添加团队成员异常 ",e.getMessage());
		}
		return responseBody;
	}
	
	
	/**
	 * 团队成员   数据查询
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProPerInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> queryProPerInfo(HttpServletRequest request, @RequestBody PersonPool query) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		
		Long total = 0l;
		List<PersonPool> resultViewList = new ArrayList<PersonPool>();
		Page<PersonPool> resultViewPage = new Page<PersonPool>(resultViewList, total);
		
		try {
			Long personId = query.getId();
			if(personId!=null){
				resultViewList = personPoolService.queryList(query);
				if(resultViewList!=null && !resultViewList.isEmpty()){
					resultViewPage.setContent(resultViewList);
					resultViewPage.setTotal((long) resultViewList.size());
				}
			}
			responseBody.setPageList(resultViewPage);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			logger.error("queryProPerInfo error", e);
		}
		
		return responseBody;
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 添加团队成员-学习经历弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addProPerLearning", method = RequestMethod.GET)
	public String addProPerLearning(HttpServletRequest request) {
		return "project/tanchuan/v_person_learning";
	}
	
	/**
	 * 团队成员的学习经历 数据查询
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProPerLearn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> queryProPerLearn(HttpServletRequest request, @RequestBody PersonLearn query) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		
		Long total = 0l;
		List<PersonLearn> resultViewList = new ArrayList<PersonLearn>();
		Page<PersonLearn> resultViewPage = new Page<PersonLearn>(resultViewList, total);
		
		try {
			Long personId = query.getPersonId();
			if(personId!=null){
				resultViewList = personLearnService.queryList(query);
				if(resultViewList!=null && !resultViewList.isEmpty()){
					resultViewPage.setContent(resultViewList);
					resultViewPage.setTotal((long) resultViewList.size());
				}
			}
			responseBody.setPageList(resultViewPage);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"学习经历查询失败"));
			logger.error("queryProPerLearn error", e);
		}
		
		return responseBody;
		
	}
	
	/**
	 * 新增    或   编辑     团队成员的学习经历     
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrEditProPerLearn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> editProPerLearn(HttpServletRequest request, @RequestBody PersonLearn query) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		try {
			Long personId = query.getPersonId();
			if(personId ==null){
				responseBody.setResult(new Result(Status.ERROR, null, "人员id缺失，更新失败"));
				return responseBody;
			}
			if(query.getBeginDateStr()!= null){
				try {
					Date date = DateUtil.convertStringToDate(query.getBeginDateStr());
					query.setBeginDate(date);
				} catch (ParseException e) {
					throw new Exception(query.getOverDateStr() +" 转  Date 失败" + e);
				}
			}
			if(query.getOverDateStr()!= null){
				try {
					Date date = DateUtil.convertStringToDate(query.getOverDateStr());
					query.setOverDate(date);
				} catch (ParseException e) {
					throw new Exception(query.getOverDateStr() +" 转  Date 失败" + e);
				}
			}
			if(query.getId()==null){
				Long id = personLearnService.insert(query);
				query.setId(id);
			}else{
				personLearnService.updateById(query);
			}
			
			responseBody.setId(query.getId());
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"学习经历更新失败"));
			logger.error("queryProPerLearn error", e);
		}
		
		return responseBody;
	}
	
	/**
	 * 删除  团队信息的学习经历
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProPerLearning/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> deleteProPerLearning(@PathVariable("id") Long id, HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		try {
			if(id == null){
				responseBody.setResult(new Result(Status.ERROR, null, "id缺失"));
				return responseBody;
			}
			personLearnService.deleteById(id);
			
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"学习经历更新失败"));
			logger.error("queryProPerLearn error", e);
		}
		return responseBody;
	}
	
	
	
	
	
	
	
	/**
	 * 添加团队成员-工作经历弹出层
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/addProPerWork", method = RequestMethod.GET)
	public String addProPerWork(HttpServletRequest request) {
		return "project/tanchuan/v_person_work";
	}
	
	/**
	 * 团队成员的工作经历 数据查询
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProPerWork", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonWork> queryProPerLearn(HttpServletRequest request, @RequestBody PersonWork query) {
		ResponseData<PersonWork> responseBody = new ResponseData<PersonWork>();
		
		Long total = 0l;
		List<PersonWork> resultViewList = new ArrayList<PersonWork>();
		Page<PersonWork> resultViewPage = new Page<PersonWork>(resultViewList, total);
		
		try {
			Long personId = query.getPersonId();
			if(personId!=null){
				resultViewList = personWorkService.queryList(query);
				if(resultViewList!=null && !resultViewList.isEmpty()){
					resultViewPage.setContent(resultViewList);
					resultViewPage.setTotal((long) resultViewList.size());
				}
			}
			responseBody.setPageList(resultViewPage);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"学习经历查询失败"));
			logger.error("queryProPerLearn error", e);
		}
		
		return responseBody;
		
	}
	
	/**
	 * 添加  或  编辑     团队成员的工作经历     
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrEditProPerWork", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonWork> editProPerWork(HttpServletRequest request, @RequestBody PersonWork query) {
		ResponseData<PersonWork> responseBody = new ResponseData<PersonWork>();
		try {
			Long personId = query.getPersonId();
			if(personId ==null){
				responseBody.setResult(new Result(Status.ERROR, null, "人员id缺失，更新失败"));
				return responseBody;
			}
			
			if(query.getBeginWorkStr() != null){
				try {
					Date date = DateUtil.convertStringToDate(query.getBeginWorkStr());
					query.setBeginWork(date);
				} catch (ParseException e) {
					throw new Exception(query.getBeginWorkStr() +" 转  Date 失败" + e);
				}
			}
			if(query.getOverWorkStr() != null){
				try {
					Date date = DateUtil.convertStringToDate(query.getOverWorkStr());
					query.setOverWork(date);
				} catch (ParseException e) {
					throw new Exception(query.getOverWorkStr() +" 转  Date 失败" + e);
				}
			}
			
			if(query.getId()==null){
				Long id = personWorkService.insert(query);
				query.setId(id);
			}else{
				personWorkService.updateById(query);
			}
			
			responseBody.setId(query.getId());
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"学习经历更新失败"));
			logger.error("queryProPerLearn error", e);
		}
		
		return responseBody;
	}
	
	/**
	 * 删除  团队信息的工作经历
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProPerWork/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> deleteProPerWork(@PathVariable("id") Long id, HttpServletRequest request) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		try {
			if(id == null){
				responseBody.setResult(new Result(Status.ERROR, null, "id缺失"));
				return responseBody;
			}
			personWorkService.deleteById(id);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"学习经历更新失败"));
			logger.error("queryProPerLearn error", e);
		}
		return responseBody;
	}
	
	
	
	
}
