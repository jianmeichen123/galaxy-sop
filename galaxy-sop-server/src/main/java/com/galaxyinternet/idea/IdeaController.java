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
import org.springframework.data.domain.Sort;
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

import com.galaxyinternet.bo.IdeaBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.common.utils.StrUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.BusinessException;
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
import com.galaxyinternet.model.idea.Abandoned;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.AbandonedService;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.MeetingRecordService;
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
	private MeetingRecordService meetingRecordService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private AbandonedService abandonedService;
	
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
	
	private static final String IDEA_CODE_PREFIX = "CY";
	
	/**
	 * 验证提取，
	 * @param  idea           数据库数据
	 * @param  ClaimantUser   认领人验证
	 * @param  CreateUser     创建人验证
	 * @param  ideaProgressEquals     当前阶段验证
	 * @param  ideaProgress           后面阶段 可以操作 之前阶段
	 */
	public Result errMessage(Idea idea,User ClaimantUser,User CreateUser,String ideaProgressEquals,String ideaProgress){
		if(idea == null){
			return new Result(Status.ERROR, null, "创意检索为空!");
		}
		if(ClaimantUser != null){  // 登陆用户id 和  认领人Id 
			if(idea.getClaimantUid()==null || ClaimantUser.getId().longValue()!=idea.getClaimantUid().longValue()){ 
				return new Result(Status.ERROR, null, "没有权限!");
			}
		}
		if(CreateUser != null){    // 登陆用户id 和   创建人Id 
			if(idea.getClaimantUid()==null || CreateUser.getId().longValue()!=idea.getCreatedUid().longValue()){ 
				return new Result(Status.ERROR, null, "没有权限!");
			}
		}
		if(ideaProgressEquals != null){  //仅当前阶段能用
			if(idea.getIdeaProgress()!=null){
				if(!ideaProgressEquals.equals(idea.getIdeaProgress())){
					return new Result(Status.ERROR, "501", "操作违规!");
				}
			}else{
				return new Result(Status.ERROR, null, "创意阶段出错");
			}
		}
		if(ideaProgress != null){   //之后阶段 能操作 之前阶段
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
	
	/**
	 * 创意
	*/
	@RequestMapping(method = RequestMethod.GET)
	public String showIdea(HttpServletRequest request)
	{
		String id = request.getParameter("indextoid");
		if(StringUtils.isNotBlank(id)){
			Idea idea = ideaService.queryById(Long.parseLong(id));
			request.setAttribute("indextoid", id);
			request.setAttribute("name", idea.getIdeaName());
		}

		String zixunid = request.getParameter("zixunid");
		if(StringUtils.isNotBlank(zixunid)){
			request.setAttribute("zixunid", zixunid);
		}
		
		return "idea/idea_tag";
	}
	
	/**
	 * 项目创意列表  tab 页
	*/
	@RequestMapping(value="/idealist", method = RequestMethod.GET)
	public String idealist(HttpServletRequest request)
	{
		String id = request.getParameter("indextoid");
		if(StringUtils.isNotBlank(id)){
			Idea idea = ideaService.queryById(Long.parseLong(id));
			request.setAttribute("indextoid", id);
			request.setAttribute("name", idea.getIdeaName());
		}
		
		return "idea/idea_list2";
	}
	
	
	@ResponseBody
	@RequestMapping("/search")
	public ResponseData<Idea> search(@RequestBody Idea query, HttpServletRequest request)
	{
		ResponseData<Idea> resp = new ResponseData<Idea>();
		try {
			PageRequest pageable = new PageRequest();
			if(StringUtils.isNotBlank(query.getIsforindex())&&query.getIsforindex().equals("isfor")){
				Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
				Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 3;
				pageable = new PageRequest(pageNum, pageSize,Direction.DESC,"created_time");
				
				//query.setIdeaProgress(DictEnum.IdeaProgress.CYCJ.getCode());
			}else{
				Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
				Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
				if(StringUtils.isNotEmpty(query.getProperty()))
				{
					Direction direction = Direction.ASC;
					if(StringUtils.isNotEmpty(query.getDirection()))
					{
						direction = Direction.fromString(query.getDirection());
					}
					pageable = new PageRequest(pageNum,pageSize, new Sort(direction,query.getProperty()));
				}
				else
				{
					pageable = new PageRequest(pageNum,pageSize);
				}
			}
			resp.setPageList(new Page<Idea>(new ArrayList<Idea>() , pageable, 0l));
			
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
			
			
			User user = (User)getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			//无权限查看，返回null
			if(roleIdList == null || (!roleIdList.contains(UserConstant.TZJL) && !roleIdList.contains(UserConstant.HHR)
					&& !roleIdList.contains(UserConstant.CEO) && !roleIdList.contains(UserConstant.DSZ)&& !roleIdList.contains(UserConstant.YJY))){
				resp.setPageList(new Page<Idea>(new ArrayList<Idea>() , pageable, 0l));
				return resp;
			}
			
			//角色 - 投资经理、合伙人查看本事业线创意
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
				else if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.YJY))
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
	
	@RequestMapping(value = "/editnotes",method = RequestMethod.GET)
	public String editnotes(HttpServletRequest request)
	{
		return "idea/stage/notesEdit";
	}
	/**
	 * 弹出页面
	 */
	@RequestMapping(value = "/goIdeaStagePage",method = RequestMethod.GET)
	public String goIdeaStagePage(HttpServletRequest request) {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Idea idea = ideaService.queryById(Long.parseLong(id));
			request.setAttribute("projectId", idea.getProjectId());
			request.setAttribute("id", id);
		}
	   
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
	 * 弹出页面---放弃创意
	 */
	@RequestMapping(value = "/goGiveUpPage",method = RequestMethod.GET)
	public String goGiveUpPage(HttpServletRequest request) {
    String id = request.getParameter("id");
	   request.setAttribute("id", id);
		return "idea/stage/abandont";
	} 
	/**
	 * 弹出页面---
	 */
	@RequestMapping(value = "/goClaimtcPage",method = RequestMethod.GET)
	public String goClaimtcPage(HttpServletRequest request) {
       String id = request.getParameter("id");
	   request.setAttribute("id", id);
		return "idea/stage/claimtc";
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
		User user = (User)getUserFromSession(request);
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		if(id == null){
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		Map<String,Object> map=new HashMap<String,Object>();
		Abandoned abandoned=new Abandoned();
		abandoned.setIdeaId(id);
		List<Abandoned> queryList=new ArrayList<Abandoned>();
		queryList= abandonedService.queryList(abandoned);
		if(!queryList.isEmpty()&&queryList.size()>0){
			for(Abandoned ab:queryList){
				String convertDateToString = DateUtil.convertDateToStringForChina(ab.getAbDatetime());
				ab.setAbDatetimeToString(convertDateToString);
			}
			map.put("giveup", queryList);
			responseBody.setUserData(map);
		}
		try {
			Idea queryById = ideaService.queryById(id);
			if(queryById.getProjectId()!=null){
				Project project = projectService.queryById(queryById.getProjectId().longValue());
				queryById.setProjectName(project.getProjectName());
			}
			if(queryById.getClaimantUid()!=null){
				User us = userService.queryById(queryById.getClaimantUid());
				queryById.setClaimantUname(us.getRealName());
			}
			if(queryById.getCreatedUid().equals(user.getId())){
				queryById.setCreateBySelf("self");
			}else{
				queryById.setCreateBySelf("other");
			}
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(!RoleUtils.isCEO(roleIdList) && !RoleUtils.isDSZ(roleIdList)){
				queryById.setDepartmentEditable("false");
			}else{
				//为高管时所属事业线可编辑
				queryById.setDepartmentEditable("true");;
			}
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
	@RequestMapping("/giveUp")
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.IDEANEWS},recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
	public ResponseData<Idea> giveUp(@RequestBody IdeaBo idea,HttpServletRequest request)
	{
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		if(idea.getId()==null){
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		
		
		UrlNumber urlNum=null;
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			Idea queryIdea = ideaService.queryById(idea.getId());
			//如果创意已经放弃则返回
			if(DictEnum.IdeaProgress.CYLGZ.getCode().equals(queryIdea.getIdeaProgress())){
				responseBody.setResult(new Result(Status.ERROR, "already", "创意已经放弃!"));
				return responseBody;

			}
			if(queryIdea !=null && queryIdea.getIdeaProgress()!=null){
				if(queryIdea.getIdeaProgress().equals(SopConstant.IDEA_PROGRESS_GZ)){
					responseBody.setResult(new Result(Status.ERROR, null, "操作过期"));
					return responseBody;
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR, null, "创意信息错误"));
				return responseBody;
			}
			idea.setGiveUpId(user.getId());
			int queryById = ideaService.updateById(idea);
		    urlNum=UrlNumber.one;
			if(queryById<=0){
				responseBody.setResult(new Result(Status.ERROR, null, "编辑创意状态失败!"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK,null,"更新创意成功！"));
			ControllerUtils.setRequestIdeaParamsForMessageTip(request, user,idea.getIdeaName(), idea.getId(),"放弃创意",urlNum);
			
		} catch (Exception e) {
			responseBody.getResult().addError("编辑创意信息失败");
			logger.error("编辑创意信息失败",e);
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.IDEANEWS},recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
	public ResponseData<Idea> updateIdea(@RequestBody IdeaBo idea,HttpServletRequest request)
	{
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		if(idea.getId()==null){
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		UrlNumber urlNum=null;
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			Idea queryIdea = ideaService.queryById(idea.getId());
			if(queryIdea !=null && queryIdea.getIdeaProgress()!=null){
				if(!(queryIdea.getIdeaProgress().equals(SopConstant.IDEA_PROGRESS_DRL) || queryIdea.getIdeaProgress().equals(SopConstant.IDEA_PROGRESS_GZ))){
					responseBody.setResult(new Result(Status.ERROR, null, "操作过期"));
					return responseBody;
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR, null, "创意信息错误"));
				return responseBody;
			}
			
			idea.setClaimantUid(user.getId());
			idea.setClaimantUname(user.getRealName());
			int queryById = ideaService.updateById(idea);
		    urlNum=UrlNumber.one;
			if(queryById<=0){
				responseBody.setResult(new Result(Status.ERROR, null, "编辑创意状态失败!"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK,null,"更新创意成功！"));
			ControllerUtils.setRequestIdeaParamsForMessageTip(request, user,idea.getIdeaName(), idea.getId(),"认领创意成功",urlNum);
			
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
			@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.IDEANEWS,recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
			@ResponseBody
			@RequestMapping(value = "/ideaUpReport", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseData<SopFile> ideaUpReport(SopFile ideafile,HttpServletRequest request,HttpServletResponse response ) {
				
				ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
				User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				SopFile editFile = null;
				if(!ServletFileUpload.isMultipartContent(request) && ideafile.getProjectId() == null){
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
					//Result err = errMessage(idea,user,null,ideafile.getProjectProgress(),null);   
					Result err = errMessage(idea,user,null,DictEnum.IdeaProgress.CYDY.getCode(),null);   //仅创建人、当前阶段
					if(err!=null && err.getStatus()!=null){
						responseBody.setResult(err);
						return responseBody;
					}
					
					UrlNumber uNum = null;
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
					}else{
						//新增    只能有1条记录    SopFile中是否有可行性报告
						SopFile file = new SopFile();
						file.setProjectId(ideafile.getProjectId());
						file.setRecordType(RecordType.IDEAS.getType());
						file.setFileValid(1);
						List<SopFile> ideaFileList = sopFileService.queryList(file);
						if(ideaFileList!=null && !ideaFileList.isEmpty()){
							responseBody.setResult(new Result(Status.ERROR,null, "可行性报告已存在"));
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
					
					ideafile.setFileValid(1);
					
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
						uNum = UrlNumber.one;
						if(ud == 0){
							responseBody.setResult(new Result(Status.ERROR,null, "更新失败"));
							return responseBody;
						}
						id = ideafile.getId();
						ControllerUtils.setRequestIdeaParamsForMessageTip(request, user,idea.getIdeaName(), idea.getId(),"更新可行性报告",uNum);
					}else{
						uNum = UrlNumber.two;
						id = sopFileService.insert(ideafile);
						ControllerUtils.setRequestIdeaParamsForMessageTip(request, user,idea.getIdeaName(), idea.getId(),"上传可行性报告",uNum);
					}
					
					responseBody.setResult(new Result(Status.OK, ""));
					responseBody.setId(id);
					//ControllerUtils.setRequestParamsForMessageTip(request, idea.getIdeaName(), idea.getId(),uNum);
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
							&& !roleIdList.contains(UserConstant.CEO) && !roleIdList.contains(UserConstant.DSZ)&& !roleIdList.contains(UserConstant.YJY)){
						responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
						return responseBody;
					}
					/*if(roleIdList.contains(UserConstant.TZJL)&&!roleIdList.contains(UserConstant.HHR)){
						ideafile.setFileUid(user.getId());
						ideafile.setFileUName(user.getRealName());
						ideafile.setCareerLine(user.getDepartmentId());
						ideafile.setCareerLineName(user.getDepartmentName());
					}*/
					
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
			@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.IDEANEWS,recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
			@ResponseBody
			@RequestMapping(value = "/ideaStartMeet/{ideaid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseData<Idea> ideaStartMeet(HttpServletRequest request,@PathVariable("ideaid") Long ideaid) {
				
				ResponseData<Idea> responseBody = new ResponseData<Idea>();
				User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				
				try {
					Idea idea = ideaService.queryById(ideaid);
					
					Result err = errMessage(idea,user,null,DictEnum.IdeaProgress.CYDY.getCode(),null);   //仅创建人、当前阶段
					if(err!=null && err.getStatus()!=null){
						responseBody.setResult(err);
						return responseBody;
					}
					
					//调研阶段  SopFile中是否有可行性报告
					SopFile file = new SopFile();
					file.setProjectId(ideaid);
					file.setProjectProgress(idea.getIdeaProgress());
					file.setRecordType(RecordType.IDEAS.getType());
					file.setFileValid(1);
					List<SopFile> ideaFileList = sopFileService.queryList(file);
					if(ideaFileList==null || ideaFileList.isEmpty()){
						responseBody.setResult(new Result(Status.ERROR, null, "请完善可行性报告"));
						return responseBody;
					}else{
						//idea.setIdeaProgress(DictEnum.IdeaProgress.CYLXH.getCode());
						idea.setIdeaProgress(SopConstant.IDEA_PROGRESS_CJLXH);
						
						ideaService.updateById(idea);
					}
					responseBody.setResult(new Result(Status.OK, ""));
					responseBody.setEntity(idea);
					//ControllerUtils.setRequestParamsForMessageTip(request, idea.getIdeaName(), idea.getId());
					ControllerUtils.setRequestIdeaParamsForMessageTip(request, user,idea.getIdeaName(), idea.getId(),"启动创建立项会",UrlNumber.one);
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
			int codeLength = config.getValue().length();
			String newCode = "";
			for(int i = 0;i<6-codeLength;i++){
				newCode += "0";
			}
			newCode += config.getValue();
			idea.setIdeaCode(IDEA_CODE_PREFIX + newCode);
			
			if(!RoleUtils.isCEO(roleIdList) && !RoleUtils.isDSZ(roleIdList)){
				idea.setDepartmentId(user.getDepartmentId());
				idea.setDepartmentEditable("false");
			}else{
				//为高管时所属事业线可编辑
				idea.setDepartmentEditable("true");;
			}
			
			
			responseBody.setEntity(idea);
			responseBody.setResult(new Result(Status.OK,""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseBody.setResult(new Result(Status.ERROR,"添加创意失败"));
		}
		return responseBody;
	}

	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.IDEANEWS,recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
	@ResponseBody
	@RequestMapping(value="/addIdea",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Idea> addIdea(HttpServletRequest request,@RequestBody @Valid Idea idea,BindingResult result){
		ResponseData<Idea> responseBody = getErrorResponse(idea);
		if(responseBody !=null){
			return responseBody;
		}
		responseBody = new ResponseData<Idea>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		
		if(idea.getDepartmentId() == null || idea.getDepartmentId().toString().equals("")){
			if(RoleUtils.isGaoGuan(roleIdList)){
				responseBody.setResult(new Result(Status.ERROR,"请选择所属事业线"));
				return responseBody;
			}else{
				idea.setDepartmentId(user.getDepartmentId());
			}
		}
		String operatorStr = "";
		UrlNumber uNum = null;
		String content = "";
		try{
			if(idea.getId()!=0){
				Idea tempIdea = ideaService.queryById(idea.getId());
				ideaService.updateById(idea);
				if(DictEnum.IdeaProgress.CYCJ.getCode().equals(tempIdea.getIdeaProgress())){
					uNum = UrlNumber.two;
				}else if(DictEnum.IdeaProgress.CYDY.getCode().equals(tempIdea.getIdeaProgress())){
					uNum = UrlNumber.three;
				}else if(DictEnum.IdeaProgress.CYLXH.getCode().equals(tempIdea.getIdeaProgress())){
					uNum = UrlNumber.four;
				}else{
					uNum = UrlNumber.five;
				}
				operatorStr = "修改";
				content = "修改创意";
				
			}else{
				idea.setId(null);
				idea.setCreatedTime(System.currentTimeMillis());
				idea.setIdeaProgress(SopConstant.IDEA_PROGRESS_DRL);
				ideaService.insert(idea);
				operatorStr = "添加";
				uNum = UrlNumber.one;
				content = "添加创意";
			}
			
			ControllerUtils.setRequestIdeaParamsForMessageTip(request, user, idea.getIdeaName(), idea.getId(), content + idea.getIdeaName(), uNum);
		}catch(DaoException e){
			responseBody.setResult(new Result(Status.ERROR,"创意添加出错,请联系管理员!"));
			return responseBody;
		}
		responseBody.setId(idea.getId());
		responseBody.setResult(new Result(Status.OK,operatorStr + "成功"));
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
	@RequestMapping("/showCreateProjectDialog")
	public ModelAndView showCreateProjectDialog(Long ideaId)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/idea/stage/create_project");
		mv.addObject("ideaId", ideaId);
		
		MeetingRecord meetQuery = new MeetingRecord();
		meetQuery.setProjectId(ideaId);
		meetQuery.setRecordType(DictEnum.RecordType.IDEAS.getType());
		meetQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
		Long count = meetingRecordService.queryCount(meetQuery);
		if(count == null || count.intValue() == 0)
		{
			mv.addObject("errorMsg", "立项会未通过，不能创建项目.");
		}
		
		return mv;
	}
	@ResponseBody
	@RequestMapping("/createProject")
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.IDEANEWS,recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
	public ResponseData<Idea> createProject(@RequestBody Idea ideaBo, HttpServletRequest request)
	{
		Long ideaId = ideaBo.getId();
		String projectName = ideaBo.getProjectName();
		ResponseData<Idea> resp = new ResponseData<Idea>();
		try {
			MeetingRecord meetQuery = new MeetingRecord();
			meetQuery.setProjectId(ideaId);
			meetQuery.setRecordType(DictEnum.RecordType.IDEAS.getType());
			meetQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
			Long count = meetingRecordService.queryCount(meetQuery);
			if(count == null || count.intValue() == 0)
			{
				throw new BusinessException("立项会未通过，不能创建项目。");
			}
			
			Project obj = new Project();
			obj.setProjectName(projectName);
			List<Project> projectList = projectService.queryList(obj);
			/*
			 * Integer count = 0 ; for (Project p: projectList) { count ++; }
			 * if(count>0){
			 */
			if (null != projectList && projectList.size() > 0) {
				resp.setResult(new Result(Status.ERROR, null, "和已有项目名称重复!"));
				return resp;
			}
			
			ideaService.createProject(ideaId, projectName);
			Idea idea = ideaService.queryById(ideaId);
			resp.setEntity(idea);
			final User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			ControllerUtils.setRequestIdeaParamsForMessageTip(request, user, idea.getIdeaName(), ideaId, "创建项目"+projectName, UrlNumber.one);
		} catch (BusinessException e) 
		{
			resp.getResult().addError(e.getMessage());
			logger.error("创建项目失败 ID:"+ideaId+" , Project Name:"+projectName, e);
		}
		return resp;
	}
	@RequestMapping("/showProject")
	public ModelAndView showProject(Long ideaId)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/idea/stage/project_list");
		mv.addObject("ideaId", ideaId);
		return mv;
	}
	@RequestMapping("/showEditProjectDialog")
	public ModelAndView showEditProjectDialog(Long ideaId)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/idea/stage/edit_project");
		mv.addObject("ideaId", ideaId);
		return mv;
	}
	@ResponseBody
	@RequestMapping("/editProjectName")
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.IDEANEWS,recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
	public ResponseData<Idea> editProjectName(@RequestBody Idea ideaBo, HttpServletRequest request)
	{
		Long ideaId = ideaBo.getId();
		String projectName = ideaBo.getProjectName();
		ResponseData<Idea> resp = new ResponseData<Idea>();
		try {
			if(StringUtils.isBlank(projectName))
			{
				throw new Exception("请填写新项目名称.");
			}
			Idea idea = ideaService.queryById(ideaId);
			if(idea == null || idea.getProjectId() == null)
			{
				throw new Exception("数据错误.");
			}
			Project project = projectService.queryById(idea.getProjectId());
			if(project == null )
			{
				throw new Exception("数据错误.");
			}
			if(projectName != null && projectName.equals(project.getProjectName()))
			{
				throw new Exception("新项目名称与旧项目名称相同.");
			}
			
			Project obj = new Project();
			obj.setProjectName(projectName);
			List<Project> projectList = projectService.queryList(obj);
			/*
			 * Integer count = 0 ; for (Project p: projectList) { count ++; }
			 * if(count>0){
			 */
			if (null != projectList && projectList.size() > 0) {
				resp.setResult(new Result(Status.ERROR, null, "和已有项目名称重复!"));
				return resp;
			}
			
			String originProjectName = project.getProjectName();
			project.setProjectName(projectName);
			projectService.updateById(project);
			idea = ideaService.queryById(ideaId);
			resp.setEntity(idea);
			final User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			ControllerUtils.setRequestIdeaParamsForMessageTip(request, user, idea.getIdeaName(), ideaId, "项目名称由"+originProjectName +"变更为"+projectName, UrlNumber.one);
		} catch (Exception e) {
			resp.getResult().addError(e.getMessage());
			logger.error("编辑项目名称失败。 Idea Id: "+ideaId+", Project Name: "+projectName, e);
		}
		
		return resp;
	}
	
	
	
	
	
	
	//=======================   添加 会议记录        ===============================//
	
		//会议记录弹窗
		@RequestMapping(value = "/addCyMeetRecordTc", method = RequestMethod.GET)
		public String addCyMeetRecordTc()
		{
			return "idea/stage/meeting";
		}
		
		
		//添加 会议记录   meettype：meetingType:3 立项会  创意recordType 
		@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.IDEANEWS,recordType=com.galaxyinternet.common.annotation.RecordType.IDEAS)
		@ResponseBody
		@RequestMapping(value = "/saveCyMeetRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<MeetingRecord> saveCyMeetRecord(MeetingRecordBo meetingRecord,HttpServletRequest request,HttpServletResponse response  ) {
			ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			//数据处理
			if(!ServletFileUpload.isMultipartContent(request) && meetingRecord.getProjectId() == null){
				String json = JSONUtils.getBodyString(request);
				meetingRecord = GSONUtil.fromJson(json, MeetingRecordBo.class);
			}
			
			//前端传值
			if(meetingRecord == null || meetingRecord.getProjectId() == null 
					|| meetingRecord.getMeetingDate() == null 
					|| meetingRecord.getMeetingType() == null 
					|| meetingRecord.getMeetingResult() == null ){
				responseBody.setResult(new Result(Status.ERROR,null, "请完善会议信息"));
				return responseBody;
			}
			meetingRecord.setRecordType(RecordType.IDEAS.getType());
			//meetingRecord.setMeetValid((byte)0);
			
			//已有 通过/否决 的会议，不能再添加会议纪要
			MeetingRecord mrQuery = new MeetingRecord();
			mrQuery.setProjectId(meetingRecord.getProjectId());
			//mrQuery.setMeetingType(meetingRecord.getMeetingType());
			mrQuery.setRecordType(RecordType.IDEAS.getType());
			mrQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
			Long mrCount = meetingRecordService.queryCount(mrQuery);
			if(mrCount != null && mrCount.longValue() > 0L){
				responseBody.setResult(new Result(Status.ERROR, "","已有通过的会议，不能再添加会议纪要!"));
				return responseBody;
			}
			mrQuery.setMeetingResult(DictEnum.meetingResult.否决.getCode());
			mrCount = meetingRecordService.queryCount(mrQuery);
			if(mrCount != null && mrCount.longValue() > 0L){
				responseBody.setResult(new Result(Status.ERROR, "","会议被否决，不能再添加会议纪要!"));
				return responseBody;
			}
				
			try {
				//Idea  验证
				Idea idea = new Idea();
				idea = ideaService.queryById(meetingRecord.getProjectId());
				//Result err = errMessage(idea,user,null,"ideaProgress:3"); 
				Result err = errMessage(idea,user,null,DictEnum.IdeaProgress.CYLXH.getCode(),null);   //仅创建人、当前阶段
				if(err!=null && err.getStatus()!=null){
					responseBody.setResult(err);
					return responseBody;
				}
				
				//保存
				SopFile sopFile = null;
				if(meetingRecord.getFkey()!=null){
					if( meetingRecord.getFileLength()==null||meetingRecord.getFname()==null){
						responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
						return responseBody;
					}
					if(meetingRecord.getBucketName()==null){
						meetingRecord.setBucketName(OSSFactory.getDefaultBucketName());
					}		
							
					Map<String,String> nameMap = StrUtils.transFileNames(meetingRecord.getFname());
					sopFile = new SopFile();
					sopFile.setBucketName(meetingRecord.getBucketName());
					sopFile.setFileKey(meetingRecord.getFkey());
					sopFile.setFileLength(meetingRecord.getFileLength());
					sopFile.setFileName(nameMap.get("fileName"));
					sopFile.setFileSuffix(nameMap.get("fileSuffix"));
					
					sopFile.setRecordType(RecordType.IDEAS.getType());
					sopFile.setProjectId(idea.getId());
					sopFile.setProjectProgress(idea.getIdeaProgress());
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
					//sopFile.setFileWorktype(fileWorkType);    //业务分类
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
				}else if(!ServletFileUpload.isMultipartContent(request)){
					sopFile = new SopFile();
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileUid(user.getId());
				}else if(ServletFileUpload.isMultipartContent(request)){
					//调接口上传
					String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
					//Map<String,Object> map = sopFileService.aLiColoudUpload(request, fileKey);
					UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
					//上传成功后
					if(result!=null){
						Map<String,String> nameMap = new HashMap<String,String>();
						String fileName = "";
						if(meetingRecord.getFname()!=null && meetingRecord.getFname().trim().length()>0){
							fileName = meetingRecord.getFname().trim();
							nameMap = StrUtils.transFileNames(fileName);
						}else{
						    nameMap.put("fileName",result.getFileName());
						    nameMap.put("fileSuffix", result.getFileSuffix());
						}
						
						sopFile = new SopFile();
						sopFile.setBucketName(result.getBucketName()==null?OSSFactory.getDefaultBucketName():result.getBucketName()); 
						sopFile.setFileKey(fileKey);  
						sopFile.setFileLength(result.getContentLength());  //文件大小
						sopFile.setFileName(nameMap.get("fileName"));
						sopFile.setFileSuffix(nameMap.get("fileSuffix"));
						
						sopFile.setRecordType(RecordType.IDEAS.getType());
						sopFile.setProjectId(idea.getId());
						sopFile.setProjectProgress(idea.getIdeaProgress());
						sopFile.setFileUid(user.getId());	 //上传人
						sopFile.setCareerLine(user.getDepartmentId());
						sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
						sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
						//sopFile.setFileWorktype(fileWorkType);    //业务分类
						sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
						
					}else{
						responseBody.setResult(new Result(Status.ERROR,null, "上传失败"));
						return responseBody;
					}
				}
				
				Long id = meetingRecordService.addCyMeetRecord(meetingRecord,sopFile);
				responseBody.setId(id);
				responseBody.setResult(new Result(Status.OK, ""));
				
				//ControllerUtils.setRequestParamsForMessageTip(request, idea.getIdeaName(), idea.getId());
				ControllerUtils.setRequestIdeaParamsForMessageTip(request, user,idea.getIdeaName(), idea.getId(),"添加会议记录",UrlNumber.one);
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "创意会议添加失败"));
				logger.error("saveCyMeetRecord 创意会议添加失败 ",e);
			}
			return responseBody;
		}
		
		

		@ResponseBody
		@RequestMapping(value = "/queryCyMeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<MeetingRecordBo> queryCyMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ) {
			
			ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
			
			try {
				User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
				if(!roleIdList.contains(UserConstant.TZJL) && !roleIdList.contains(UserConstant.HHR)
						&& !roleIdList.contains(UserConstant.CEO) && !roleIdList.contains(UserConstant.DSZ) && !roleIdList.contains(UserConstant.YJY)){
					responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
					return responseBody;
				}
				/*if(roleIdList.contains(UserConstant.TZJL)&&!roleIdList.contains(UserConstant.HHR)
						&&query.getProjectId()==null&&query.getUid()==null){
					query.setUid(user.getId());
				}*/
				//query.setUid(user.getId());
				
				//query.setMeetValid((byte) 0);
				Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPage(query, new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()));
				responseBody.setPageList(pageList);
				responseBody.setResult(new Result(Status.OK, ""));
				return responseBody;
				
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
				logger.error("queryCyMeet 查询失败",e);
			}
			
			return responseBody;
		}	
		
		
		
		
		
		
		
		
		
		
		
		//=======================     check has report file    ===============================//
		/**
		 * check has pass meet
		 * @param  ideaid 创意id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ideaCheckHassReport/{ideaid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<Idea> ideaCheckHassReport(HttpServletRequest request,@PathVariable("ideaid") Long ideaid) {
			
			ResponseData<Idea> responseBody = new ResponseData<Idea>();
			//User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			Result result = new Result(Status.OK,null,null);
			try {
				//调研阶段  SopFile中是否有可行性报告
				SopFile file = new SopFile();
				file.setProjectId(ideaid);
				file.setRecordType(RecordType.IDEAS.getType());
				file.setFileValid(1);
				List<SopFile> ideaFileList = sopFileService.queryList(file);
				if(ideaFileList==null || ideaFileList.isEmpty()){
					result.setMessage("not");
				}else{
					result.setMessage("has");
				}
				responseBody.setResult(result);
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "查询失败"));
				logger.error("ideaCheckHassReport 失败",e);
			}
			return responseBody;
		}
		
		//=======================     check has pass / veto meet    ===============================//
		/**
		 * check has pass meet
		 * @param  ideaid 创意id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ideaCheckPassMeet/{ideaid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<Idea> ideaCheckPassMeet(HttpServletRequest request,@PathVariable("ideaid") Long ideaid) {
			
			ResponseData<Idea> responseBody = new ResponseData<Idea>();
			//User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			Result result = new Result(Status.OK,null,null);
			try {
				
				MeetingRecord meet = new MeetingRecord();
				meet.setProjectId(ideaid);
				meet.setMeetingType(DictEnum.meetingType.立项会.getCode());
				meet.setMeetingResult(DictEnum.meetingResult.通过.getCode());
				meet.setRecordType(RecordType.IDEAS.getType());
				Long countN   = meetingRecordService.queryCount(meet);
				if(countN!=null && countN.longValue()>0l){
					result.setMessage("pass");
					responseBody.setResult(result);
					return responseBody;
				}		
				
				meet.setMeetingResult(DictEnum.meetingResult.否决.getCode());
				countN = meetingRecordService.queryCount(meet);
				if(countN!=null && countN.longValue()>0l){
					result.setMessage("vote");
					responseBody.setResult(result);
					return responseBody;
				}	
				
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "查询失败"));
				logger.error("ideaCheckPassMeet 失败",e);
			}
			
			return responseBody;
		}
		
		
		//=======================     check history button can use    ===============================//
		/**
		 * 高管可以随变看，然后就是这个创意属于哪条事业线，所属事业线合伙人，投资经理可以看
		 * @param  ideaid 创意id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ideaCheckHistory/{ideaid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<Idea> ideaCheckHistory(HttpServletRequest request,@PathVariable("ideaid") Long ideaid) {
			
			ResponseData<Idea> responseBody = new ResponseData<Idea>();
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			Result result = new Result(Status.OK,null,null);
			try {
				//Idea  验证
				Idea idea = ideaService.queryById(ideaid);
				if(idea == null || idea.getDepartmentId()==null){
					responseBody.setResult(new Result(Status.ERROR,null, "创意信息错误"));
					return responseBody;
				}else {
					List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
					if(!roleIdList.contains(UserConstant.TZJL) && !roleIdList.contains(UserConstant.HHR)
							&& !roleIdList.contains(UserConstant.CEO) && !roleIdList.contains(UserConstant.DSZ) && !roleIdList.contains(UserConstant.YJY)){
						responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
						return responseBody;
					}
					
					Abandoned abandoned = new Abandoned();
					abandoned.setIdeaId(ideaid);
					Long total = abandonedService.queryCount(abandoned);
					if(total!=null && total.intValue() > 0){
						if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) ){
							result.setMessage("y");
						}else if((roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HHR) ) 
								&& user.getDepartmentId().longValue() == idea.getDepartmentId().longValue()){
								result.setMessage("y");
						}
					}else{
						result.setMessage("n");
					}
				}
				
				responseBody.setResult(result);
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "获取创意信息失败"));
				logger.error("ideaCheckHistory 失败",e);
			}
			
			return responseBody;
		}
		
		
		
}
