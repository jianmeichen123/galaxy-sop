package com.galaxyinternet.idea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.common.utils.StrUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.RoleUtils;
@Controller
@RequestMapping("/galaxy/idea")
public class IdeaController extends BaseControllerImpl<Idea, Idea> {
	private static final Logger logger = Logger.getLogger(IdeaController.class);
	@Autowired
	private IdeaService ideaService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserService userService;
	@Autowired
	private  SopFileService sopFileService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ConfigService configService;
	@Override
	protected BaseService<Idea> getBaseService() {
		return this.ideaService;
	}

	private String tempfilePath;
	public String getTempfilePath() {
		return tempfilePath;
	}
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	
	
	/**
	 * 验证提取，
	 */
	public Result errMessage(Idea idea,User user,String ideaProgress){
		if(idea == null){
			return new Result(Status.ERROR, null, "创意检索为空!");
		}

		if(user != null){  //认领人Id 和 登陆用户id
			if(idea.getClaimantUid()==null || user.getId().longValue()!=idea.getClaimantUid().longValue()){ 
				return new Result(Status.ERROR, null, "没有权限!");
			}
		}
		
		if(ideaProgress != null){
			if(idea.getIdeaProgress()!=null){
				try {
					int operationPro = Integer.parseInt(ideaProgress.substring(ideaProgress.length()-1)) ;
					int ideaPro = Integer.parseInt(idea.getIdeaProgress().substring(idea.getIdeaProgress().length()-1)) ;
					if(ideaPro < operationPro){
						return new Result(Status.ERROR, "501", "操作违规!");
					}
				} catch (Exception e) {
					return new Result(Status.ERROR, null, "信息不和规范");
				}
			}else{
				return new Result(Status.ERROR, null, "创意阶段出错");
			}
		}
		return null;
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String list()
	{
		return "idea/idea_list";
	}
	@ResponseBody
	@RequestMapping("/search")
	public ResponseData<Idea> search(@RequestBody Idea query, HttpServletRequest request)
	{
		ResponseData<Idea> resp = new ResponseData<Idea>();
		
		try {
			PageRequest pageable = new PageRequest();
			Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
			Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
			pageable.setPage(pageNum);
			pageable.setSize(pageSize);
			//提出人
			if(StringUtils.isNotEmpty(query.getCreatedUname()))
			{
				User userQuery = new User();
				userQuery.setRealName(query.getCreatedUname());
				List<User> users = userService.queryList(userQuery);
				if(users == null || users.size() == 0)
				{
					return resp;
				}
				
				List<Long> userIds = new ArrayList<Long>();
				for(User user : users)
				{
					userIds.add(user.getId());
				}
				query.setCreatedUids(userIds);
			}
			//提出时间
			if(query.getCreatedDateFrom() != null)
			{
				Date date = DateUtil.convertStringToDate(query.getCreatedDateFrom());
				query.setCreatedTimeFrom(DateUtil.getSearchFromDate(date).getTime());
			}
			if(query.getCreatedDateThrough() != null)
			{
				Date date = DateUtil.convertStringToDate(query.getCreatedDateThrough());
				query.setCreatedTimeThrough(DateUtil.getSearchToDate(date).getTime());
			}
			//角色 - 投资经理、合伙人查看本事业线创意
			User user = (User)getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList != null && roleIdList.size()>0 && (roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HHR)))
			{
				query.setDepartmentId(user.getDepartmentId());
			}
			
			//立项会、投决会阶段查询
			List<String> projectProgressList = null;
			if(SopConstant.IDEA_PROGRESS_LXH.equals(query.getIdeaProgress()))
			{
				projectProgressList = Arrays.asList(
													DictEnum.projectProgress.立项会.getCode(),
													DictEnum.projectProgress.投资意向书.getCode(),
													DictEnum.projectProgress.尽职调查.getCode()
													);
			}
			else if(SopConstant.IDEA_PROGRESS_TJH.equals(query.getIdeaProgress()))
			{
				projectProgressList = Arrays.asList(
													DictEnum.projectProgress.投资决策会.getCode(),
													DictEnum.projectProgress.投资协议.getCode(),
													DictEnum.projectProgress.股权交割.getCode(),
													DictEnum.projectProgress.投后运营.getCode()
													);
}
			if(projectProgressList != null)
			{
				query.setIdeaProgress(SopConstant.IDEA_PROGRESS_CJXM);
				ProjectBo projQuery = new ProjectBo();
				projQuery.setFromIdea(true);
				projQuery.setProjectProgressList(projectProgressList);
				if(query.getDepartmentId() != null)
				{
					projQuery.setProjectDepartid(query.getDepartmentId());
				}
				List<Project> projects = projectService.queryList(projQuery);
				if(projects == null || projects.size() == 0)
				{
					return resp;
				}
				
				List<Long> ideaIds = new ArrayList<Long>();
				for(Project project : projects)
				{
					if(project.getIdeaId() != null && !ideaIds.contains(project.getIdeaId()))
					{
						ideaIds.add(project.getIdeaId());
					}
				}
				if(ideaIds == null || ideaIds.size() == 0)
				{
					return resp;
				}
				query.setIds(ideaIds);
			}
			Page<Idea> page = ideaService.queryPageList(query, pageable);
			resp.setPageList(page);
		} catch (Exception e) {
			resp.getResult().addError("查询创意出错");
			logger.error("查询创意出错", e);
		}
		return resp;
	}
	
	@RequestMapping("/ideaProjectList")
	public ModelAndView ideaProjectList(String ideaProgress)
	{
		ModelAndView mv = new ModelAndView("/idea/idea_project_list");
		mv.addObject("ideaProgress", ideaProgress);
		return mv;
	}
	@ResponseBody
	@RequestMapping("/getDepartment")
	public ResponseData<Department> getDepartment(HttpServletRequest request)
	{
		ResponseData<Department> resp = new ResponseData<Department>();
		try {
			User user = (User)getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList != null && roleIdList.size()>0)
			{
				List<Department> departments = new ArrayList<Department>();
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HHR))
				{
					Department department = departmentService.queryById(user.getDepartmentId());
					departments.add(department);
				}
				else if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ))
				{
					Department depQuery = new Department();
					depQuery.setType(1);
					departments =  departmentService.queryList(depQuery);
				}
				resp.setEntityList(departments);
			}
		} catch (Exception e) {
			resp.getResult().addError("查询事业线失败");
			logger.error("查询事业线失败",e);
		}
		return resp;
	}
	/**
	 * 弹出页面
	 */
	@com.galaxyinternet.common.annotation.Logger
	@RequestMapping(value = "/goIdeaStagePage",method = RequestMethod.GET)
	public String goClaimtcPage(HttpServletRequest request) {
       String id = request.getParameter("id");
	   request.setAttribute("id", id);
		return "idea/stage/stage";
	}
	/**
	 * 弹出页面
	 */
	@com.galaxyinternet.common.annotation.Logger
	@RequestMapping(value = "/goIdeaMeeting",method = RequestMethod.GET)
	public String goIdeaMeeting(HttpServletRequest request) {
       String id = request.getParameter("id");
	   request.setAttribute("id", id);
		return "idea/stage/meeting";
	}
	/**
	 * 跳转修改页面
	 */
	@com.galaxyinternet.common.annotation.Logger
	@RequestMapping(value = "/goIdeaEdit",method = RequestMethod.GET)
	public String goIdeaEdit(HttpServletRequest request) {
       String id = request.getParameter("id");
	   request.setAttribute("id",id);
		return "idea/stage/edit";
	}
	/**
	 * 根据创意id获取创意相关信息
	 * @author jianmeichen
	 * @serialData 2016-05-04
	 * @param idea
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getIdea/{id}")
	public ResponseData<Idea> getIdea( @PathVariable Long id,HttpServletRequest request)
	{
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		try {
			Idea queryById = ideaService.queryById(id);
			responseBody.setEntity(queryById);
			responseBody.setResult(new Result(Status.OK,null,"查询数据成功"));
		} catch (Exception e) {
			responseBody.getResult().addError("查询创意信息失败");
			logger.error("查询创意信息失败",e);
		}
		return responseBody;
	}
	/**
	 * 根据创意id获取创意相关信息
	 * @param idea
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateIdea")
	public ResponseData<Idea> updateIdea(@RequestBody Idea idea,HttpServletRequest request)
	{
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		try {
			int queryById = ideaService.updateById(idea);
			if(queryById<=0){
				responseBody.setResult(new Result(Status.ERROR, null, "编辑创意信息失败!"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK,null,"更新创意成功！"));
		} catch (Exception e) {
			responseBody.getResult().addError("编辑创意信息失败");
			logger.error("编辑创意信息失败",e);
		}
		return responseBody;
	}
	

	
	
	
	
	//=======================   上传可行性报告      ===============================//
		//=======================    更新可行性报告     ===============================//
		/**
		 * 上传可行性报告
		 * @param   ideafile recordType:1 创意    <br/>
		 * 					 projectId   创意Id  <br/>
		 * 					 projectProgress  <br/>
		 * 								CYCJ("创意已创建/待认领","ideaProgress:1"),  <br/>
										CYDY("调研","ideaProgress:2"),   <br/>
										CYLXH("创建立项会","ideaProgress:3"),  <br/>
										CYXM("创建项目","ideaProgress:4");  <br/>
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ideaUpReport", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<SopFile> ideaUpReport(SopFile ideafile,HttpServletRequest request,HttpServletResponse response ) {
			
			ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			SopFile editFile = null;
			if(ideafile.getProjectId() == null){
				String json = JSONUtils.getBodyString(request);
				ideafile = GSONUtil.fromJson(json, SopFile.class);
			}
			
			//前端必传项  CYid CYprogress fileType
			if(ideafile == null || ideafile.getProjectId() == null  || ideafile.getProjectProgress() == null || ideafile.getFileType()==null){
				responseBody.setResult(new Result(Status.ERROR,null, "传入信息不全"));
				return responseBody;
			}
			
			try {
				//Idea  验证
				Idea idea = new Idea();
				idea = ideaService.queryById(ideafile.getProjectId());
				Result err = errMessage(idea,user,ideafile.getProjectProgress());   //字典  项目进度  接触访谈  DictEnum.projectProgress.接触访谈.getCode()
				if(err!=null && err.getStatus()!=null){
					responseBody.setResult(err);
					return responseBody;
				}
				if(ideafile.getIsEdit()!=null && ideafile.getIsEdit().equals("edit")){  //更新验证
					if(ideafile.getId()!=null){
						editFile = sopFileService.queryById(ideafile.getId());
						if(editFile == null || editFile.getId()==null){
							responseBody.setResult(new Result(Status.ERROR,null, "传入信息错误"));
							return responseBody;
						}
					}else{
						responseBody.setResult(new Result(Status.ERROR,null, "传入信息不全"));
						return responseBody;
					}
				}
				
				//保存
				Long id = null;
				Map<String,String> nameMap = null;
				if(ideafile.getFileKey()!=null){  //前端已完成上传
					if(ideafile.getFileLength()==null||ideafile.getFileName()==null||ideafile.getFileName().trim().length()==0){ //FileName.txt
						responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
						return responseBody;
					}
					if(ideafile.getBucketName()==null){
						ideafile.setBucketName(OSSFactory.getDefaultBucketName());
					}
					
					nameMap = StrUtils.transFileNames(ideafile.getFileName().trim());
					
				}else if(ServletFileUpload.isMultipartContent(request)){   // 后台上传
					String fileKey = null;
					if(editFile != null && editFile.getFileKey()!=null){
						fileKey = editFile.getFileKey();
					}else{
						fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
					}
					UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
					
					//上传成功后
					if(result!=null && result.getResult().getStatus()!=null && result.getResult().getStatus().equals(Status.OK)){
						nameMap = new HashMap<String,String>();
						if(ideafile.getFileName()!=null && ideafile.getFileName().trim().length()>0){
							nameMap = StrUtils.transFileNames(ideafile.getFileName().trim());
						}else{
							 nameMap.put("fileName",result.getFileName());
							 nameMap.put("fileSuffix", result.getFileSuffix());
						}
						
						ideafile.setBucketName(result.getBucketName()==null?OSSFactory.getDefaultBucketName():result.getBucketName()); 
						ideafile.setFileKey(fileKey);  
						ideafile.setFileLength(result.getContentLength());  
					}else{
						responseBody.setResult(new Result(Status.ERROR,null, "文件上传失败"));
						return responseBody;
					}
				}else{
					responseBody.setResult(new Result(Status.ERROR,null, "文件缺失"));
					return responseBody;
				}
				
				ideafile.setRecordType(RecordType.IDEAS.getType());
				ideafile.setFileName(nameMap.get("fileName"));
				ideafile.setFileSuffix(nameMap.get("fileSuffix"));
				ideafile.setFileUid(user.getId());	
				ideafile.setCareerLine(user.getDepartmentId());
				ideafile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
				//sopFile.setFileWorktype(fileWorkType);    //业务分类
				ideafile.setFileStatus(DictEnum.fileStatus.已上传.getCode()); 
				
				if(ideafile.getIsEdit()!=null && ideafile.getIsEdit().equals("edit")){
					int ud =  sopFileService.updateById(ideafile);
					if(ud == 0){
						responseBody.setResult(new Result(Status.ERROR,null, "更新失败"));
						return responseBody;
					}
					id = ideafile.getId();
				}else{
					id = sopFileService.insert(ideafile);
				}
				
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(id);
				
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "文件上传失败"));
				logger.error("ideaUpReport 文件上传失败",e);
			}
			return responseBody;
		}
		
		
		//=======================     创意调研阶段文档列表    ===============================//
		/**
		 * 创意调研阶段文档列表   单个创意下
		 * @param   ideafile <br/>
		 * 				projectId : 创意id 
		 * 				
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/queryIdeaDyList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<SopFile> queryIdeaDyList(HttpServletRequest request,@RequestBody SopFile ideafile ) {
			
			ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
			
			try {
				User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				
				if(ideafile==null || ideafile.getProjectId()==null ){
					responseBody.setResult(new Result(Status.ERROR,null, "传入信息不全"));
					return responseBody;
				}
				if(ideafile.getProjectProgress()==null){
					ideafile.setProjectProgress(DictEnum.IdeaProgress.CYDY.getCode());
				}
				
				List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
				if(!roleIdList.contains(UserConstant.TZJL) && !roleIdList.contains(UserConstant.HHR)
						&& !roleIdList.contains(UserConstant.CEO) && !roleIdList.contains(UserConstant.DSZ)){
					responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
					return responseBody;
				}
				if(roleIdList.contains(UserConstant.TZJL)&&!roleIdList.contains(UserConstant.HHR)){
					ideafile.setFileUid(user.getId());
					ideafile.setFileUName(user.getRealName());
					ideafile.setCareerLine(user.getDepartmentId());
					ideafile.setCareerLineName(user.getDepartmentName());
				}
				
				ideafile.setRecordType(RecordType.IDEAS.getType());
				
				Page<SopFile> pageList = sopFileService.queryFileList(ideafile, 
						new PageRequest(ideafile.getPageNum()==null?0:ideafile.getPageNum(), ideafile.getPageSize()==null?10:ideafile.getPageSize(),Direction.DESC,"updated_time"));
				
				responseBody.setPageList(pageList);
				responseBody.setResult(new Result(Status.OK, ""));
				
				return responseBody;
				
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
				
				logger.error("queryIdeaDyList 查询失败",e);
			}
			
			return responseBody;
		}
		
		
		
		
		//=======================     启动创建立项会    ===============================//
		/**
		 * 更新创意阶段
		 * @param  ideaid 创意id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ideaStartMeet/{ideaid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<Idea> ideaStartMeet(HttpServletRequest request,@PathVariable("ideaid") Long ideaid) {
			
			ResponseData<Idea> responseBody = new ResponseData<Idea>();
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			try {
				Idea idea = ideaService.queryById(ideaid);
				
				Result err = errMessage(idea,user,null);   //仅创建人可启动
				if(err!=null && err.getStatus()!=null){
					responseBody.setResult(err);
					return responseBody;
				}
				
				if(idea.getIdeaProgress()==null||!idea.getIdeaProgress().equals(DictEnum.IdeaProgress.CYDY.getCode())){
					responseBody.setResult(new Result(Status.ERROR, null, "操作不允许"));
					return responseBody;
				}else{
					//调研阶段  SopFile中是否有可行性报告
					SopFile file = new SopFile();
					file.setProjectId(ideaid);
					file.setProjectProgress(idea.getIdeaProgress());
					file.setRecordType(RecordType.IDEAS.getType());
					List<SopFile> ideaFileList = sopFileService.queryList(file);
					if(ideaFileList==null || ideaFileList.isEmpty()){
						responseBody.setResult(new Result(Status.ERROR, null, "请完善可行性报告"));
						return responseBody;
					}else{
						idea.setIdeaProgress(DictEnum.IdeaProgress.CYLXH.getCode());
						ideaService.updateById(idea);
					}
				}
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setEntity(idea);
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "启动创建立项会失败"));
				logger.error("ideaStartMeet 启动创建立项会失败",e);
			}
			
			return responseBody;
		}
	
	
	@ResponseBody
	@RequestMapping(value="/getAddIdeaInfo",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Idea> getAddIdeaInfo(HttpServletRequest request,HttpServletResponse response){
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		try {
			//获取session
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			if (user == null) {
				responseBody.setResult(new Result(Status.ERROR, "未登录!"));
				return responseBody;
			}
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
					.getId());
			Idea idea = new Idea();
			idea.setCreatedUid(user.getId());
			idea.setCreatedUname(user.getRealName());
			idea.setCreatedTime(System.currentTimeMillis());
			Config config = configService.getByKey(SopConstant.CONFIG_KEY_IDEA_CODE, true);
			idea.setIdeaCode(config.getValue());
			
			if(!RoleUtils.isCEO(roleIdList) && !RoleUtils.isDSZ(roleIdList) && !RoleUtils.isHHR(roleIdList)){
				idea.setDepartmentId(user.getDepartmentId());
			}
			
			responseBody.setEntity(idea);
			responseBody.setResult(new Result(Status.OK,""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseBody.setResult(new Result(Status.ERROR,"添加创意失败"));
		}
		return responseBody;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/addIdea",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Idea> addIdea(@RequestBody @Valid Idea idea,BindingResult result){
		ResponseData<Idea> responseBody = getErrorResponse(idea);
		if(responseBody !=null){
			return responseBody;
		}
		responseBody = new ResponseData<Idea>();
		try{
			idea.setCreatedTime(System.currentTimeMillis());
			ideaService.insert(idea);
		}catch(DaoException e){
			responseBody.setResult(new Result(Status.ERROR,"创意添加出错,请联系管理员!"));
			return responseBody;
		}
		responseBody.setResult(new Result(Status.OK,"添加成功"));
		return responseBody;
	}
	@RequestMapping("/showHistory")
	public ModelAndView showHistory(Long id)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/idea/stage/history");
		mv.addObject("id", id);
		return mv;
	}
	
	
	
	
	
	
	
	
}
