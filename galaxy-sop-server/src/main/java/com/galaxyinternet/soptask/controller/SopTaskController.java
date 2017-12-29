package com.galaxyinternet.soptask.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.annotation.GalaxyResource;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.dictEnum.DictEnum.fileWorktype;
import com.galaxyinternet.common.dictEnum.DictEnum.projectType;
import com.galaxyinternet.common.dictEnum.DictEnum.taskStatus;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.ExceptionMessage;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.OperationLogsService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserService;

@Controller
@RequestMapping("/galaxy/soptask")
public class SopTaskController extends BaseControllerImpl<SopTask, SopTaskBo> {
	final Logger logger = LoggerFactory.getLogger(SopTaskController.class);
	@Autowired
	private SopTaskService sopTaskService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PersonPoolService personPoolService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Autowired
	SopTaskProcessController taskProcessController;
	
	@Autowired
	private SopFileService sopFileService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private OperationLogsService logService;
	

	@Override
	protected BaseService<SopTask> getBaseService() {
		return this.sopTaskService;
	}
	
	
	/**
	 * 投资经理点击"完善简历"
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/toSureMsg/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> toSureMsg(@PathVariable("pid") Long pid, HttpServletRequest request) {
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		if(pid == null){
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pid);
		List<PersonPool> list = personPoolService.selectNoToTask(params);
		if(list == null || list.isEmpty()){
			responseBody.setResult(new Result(Status.ERROR, null, "没有需要完善信息的成员!"));
			return responseBody;
		}
		try {
			sopTaskService.toSureMsgForPerson(pid, list);
			responseBody.setResult(new Result(Status.ERROR, null, "完善简历任务通知成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "异常，请重试!"));
		}
		return responseBody;
	}
	
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		String flag=request.getParameter("flag");
		ModelAndView mv = new ModelAndView("soptask/tasklist");
		String title = "待办任务";
		if("gq".equals(flag))
		{
			title = "股权交割";
		}
		else if("jz".equals(flag))
		{
			title = "尽调报告";
		}
		else if("pz".equals(flag))
		{
			title = "付款凭证";
		}
		mv.addObject("title", title);
		mv.addObject("flagUrl", flag);
		return mv;
	}
	/**
	 * 弹出页面
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE, LogType.IOSPUSHMESS })
	@RequestMapping(value = "/goClaimtcPage", method = RequestMethod.GET)
	public String goClaimtcPage(HttpServletRequest request)
	{

		// 当前登录人
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		SopTask sopTask = new SopTask();
		Result result = new Result();
		String id = request.getParameter("id");
		if (id != null && !"".equals(id))
		{
			sopTask.setId(Long.parseLong(id));
		}
		sopTask.setTaskStatus(DictEnum.taskStatus.待完工.getCode());

		try
		{
			SopTask queryById = sopTaskService.queryById(Long.parseLong(id));
			UrlNumber urlNum = null;
			String messageType = null;
			switch (queryById.getTaskFlag())
			{
			case 0: // 完善简历
				urlNum = UrlNumber.one;
				break;
			case 2: // 人事尽职调查报告
				urlNum = UrlNumber.two;
				messageType = "8.1";
				break;
			case 3: // 法务尽职调查报告
				urlNum = UrlNumber.five;
				messageType = "8.3";
				break;
			case 4: // 财务尽调报告
				urlNum = UrlNumber.three;
				messageType = "8.2";
				break;
			case 8: // 资金拨付凭证
				messageType = "8.5";
				urlNum = UrlNumber.four;
				break;
			case 9: // 工商变更登记凭证
				messageType = "8.4";
				urlNum = UrlNumber.six;
				break;
			default:
			}
			Project project = projectService.queryById(queryById.getProjectId());
			User manager = userService.queryById(project.getCreateUid());
			sopTask.setAssignUid(user.getId());
			sopTask.setTaskFlag(queryById.getTaskFlag());
			sopTask.setProjectId(queryById.getProjectId());
			sopTaskService.updateById(sopTask);
			request.setAttribute("taskid", id);
			Map<String, Object> params = new HashMap<>();
			params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, project.getProjectName());
			params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, project.getId());
			params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, urlNum.name());
			params.put(PlatformConst.REQUEST_SCOPE_RECORD_ID, id);
			params.put(PlatformConst.REQUEST_SCOPE_USER, manager);
			ControllerUtils.setRequestParamsForMessageTip(request, params);
		} catch (PlatformException e)
		{
			result.addError(e.getMessage());
		} catch (Exception e)
		{
			result.addError("生成任务失败");
			logger.error("生成任务失败", e);
		}
		return "soptask/claimtc";
	}
	/**
	 * 弹出页面
	 */
	@RequestMapping(value = "/doTask",method = RequestMethod.GET)
	public ModelAndView doTask(Long taskId,HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("soptask/taskDetails");
		try {
			SopTask task = sopTaskService.queryById(taskId);
			mv.addObject("taskId", taskId);
			mv.addObject("projectId", task.getProjectId());
			mv.addObject("taskFlag", task.getTaskFlag());
		} catch (Exception e) {
			throw new PlatformException(ExceptionMessage.QUERY_LIST_FAIL.getMessage(),e);
		}
		return mv;
	}

	/**
	 * @category 根据任务id修改任务状态 1，待认领 2，待处理 3，已完成
	 * @author chenjianmei
	 * @serialData 2016-02-26
	 * @param pageable
	 * @return @PathVariable("taskId") String taskId
	 */
	@ResponseBody
	@RequestMapping(value = "/insertTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> insertTask(@RequestBody SopTask entity, HttpServletRequest request) {
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		Result result = new Result();
		Object ob = request.getSession().getAttribute("sessionUser");
		if (ob == null) {
			responseBody.setResult(new Result(Status.ERROR, "no login status."));
			return responseBody;
		}
		try {
			Long id = sopTaskService.insertsopTask(entity);
			responseBody.setId(id);
			result.setStatus(Status.OK);
		} catch (PlatformException e) {
			result.addError(e.getMessage());
		} catch (Exception e) {
			result.addError("生成任务失败");
			logger.error("生成任务失败", e);
		}
		responseBody.setResult(result);
		return responseBody;

	}

	/**
	 * @category 根据任务id修改任务状态 1，待认领 2，待处理 3，已完成
	 * @author chenjianmei
	 * @serialData 2016-02-26
	 * @param pageable
	 * @return
	 * @PathVariable("taskId") String taskId
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG,LogType.MESSAGE})
	@ResponseBody
	@RequestMapping(value = "/updateTaskStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> updateTaskStatus( @RequestBody SopTask entity,HttpServletRequest request) {
		//当前登录人
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		entity.setAssignUid(user.getId());
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		Result result = new Result();
		UrlNumber um=null;
		try {
		    sopTaskService.updateById(entity);
		    result.setStatus(Status.OK);
		    SopTask po = sopTaskService.queryById(entity.getId());
		    if(po != null && po.getProjectId() != null){
		    	Project project = projectService.queryById(po.getProjectId());
		    	User manager = userService.queryById(project.getCreateUid());
		    	um=UrlNumber.one;
		    	ControllerUtils.setRequestParamsForMessageTip(request,manager, project.getProjectName(), project.getId(),um);
		    }
		} catch (PlatformException e) {
			result.addError(e.getMessage());
		} catch (Exception e) {
			result.addError("修改任务状态失败");
			logger.error("修改任务状态失败", e);
		}
		responseBody.setEntity(entity);
		responseBody.setResult(result);
		return responseBody;
	}

	/**
	 * @category 处理任务，人，财务，法务查询项目详情
	 * @author chenjianmei
	 * @serialData 2016-02-26
	 * @param pageable
	 * @return
	 *@PathVariable("taskId") String taskId
	 */
	@ResponseBody
	@RequestMapping(value = "/selectProjectByTaskId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> selectProjectByTaskId( @PathVariable Long projectid,HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		Result result = new Result();
	    Project queryProjectById = projectService.queryById(projectid);
	   //项目创建者用户ID与当前登录人ID是否一样
		if(queryProjectById == null ){
			responseBody.setResult(new Result(Status.ERROR, "获取项目详情失败!"));
			return responseBody;
		}
	    result.setStatus(Status.OK);
		responseBody.setResult(result);
		responseBody.setEntity(queryProjectById);
		return responseBody;
	}
	
	
	/**
	 * 人、财、法上传文件后"点击提交完成"
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG,LogType.MESSAGE,LogType.IOSPUSHMESS})
	@ResponseBody
	@RequestMapping(value = "/submitTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> submitTask(@RequestBody SopTask entity, HttpServletRequest request)
	{
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		entity.setAssignUid(user.getId());
		SopFile sipFile = new SopFile();
		if (entity.getId() == null)
		{
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必须的参数!"));
			return responseBody;
		}
		SopTask soptask = sopTaskService.queryById(entity.getId());
		if (null == soptask)
		{
			responseBody.setResult(new Result(Status.ERROR, null, "找不到该任务!"));
			return responseBody;
		}
		String fileWorktype = "";
		UrlNumber urlNum = null;
		String messageType = null;
		switch (soptask.getTaskFlag())
		{
		case 0: // 完善简历
			urlNum = UrlNumber.one;
			break;
		case 1: // 表示投资意向书
			fileWorktype = "fileWorktype:5";
			break;
		case 2: // 人事尽职调查报告
			urlNum = UrlNumber.two;
			messageType = "9.1";
			fileWorktype = "fileWorktype:2";
			break;
		case 3: // 法务尽职调查报告
			urlNum = UrlNumber.five;
			messageType = "9.3";
			fileWorktype = "fileWorktype:3";
			break;
		case 4: // 财务尽调报告
			urlNum = UrlNumber.three;
			messageType = "9.2";
			fileWorktype = "fileWorktype:4";
			break;
		case 5: // 业务尽调报告
			urlNum = UrlNumber.seven;
			messageType = "9.6";
			fileWorktype = "fileWorktype:1";
			break;
		case 6: // 投资协议
			fileWorktype = "fileWorktype:6";
			break;
		case 7: // 股权转让协议
			fileWorktype = "fileWorktype:7";
			break;
		case 8: // 资金拨付凭证
			urlNum = UrlNumber.four;
			messageType = "9.5";
			fileWorktype = "fileWorktype:9";
			break;
		case 9: // 工商变更登记凭证
			messageType = "9.4";
			urlNum = UrlNumber.six;
			fileWorktype = "fileWorktype:8";
			break;
		default:
		}
		sipFile.setProjectId(soptask.getProjectId());
		sipFile.setFileWorktype(fileWorktype);
		SopFile queryOne = sopFileService.queryOne(sipFile);
		Project project = projectService.queryById(queryOne.getProjectId());
		if (null != queryOne && !entity.getGiveUp())
		{
			if (queryOne.getFileStatus().equals("fileStatus:1"))
			{
				responseBody.setResult(new Result(Status.ERROR, null, "文件未上传，任务提交失败!"));
				return responseBody;
			}
		}
		try
		{
			User manager = userService.queryById(project.getCreateUid());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, project.getProjectName());
			params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, project.getId());
			params.put(PlatformConst.REQUEST_SCOPE_USER, manager);
			params.put(PlatformConst.REQUEST_SCOPE_RECORD_ID, entity.getId());
			
			if(urlNum != null){
				params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, urlNum.name());
			}
			int r = sopTaskService.submitTask(entity);
			if (r == 1)
			{
				params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, StageChangeHandler._6_10_);
				params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, messageType);
			} else
			{
				params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
			}
			ControllerUtils.setRequestParamsForMessageTip(request, params);
		} catch (Exception e)
		{
			responseBody.setResult(new Result(Status.ERROR, null, "异常，请重试!"));
			return responseBody;
		}
		return responseBody;
	}
	
	
	/**
	 * 当前待办总数
	 * @author zhaoying
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/totalMission",  produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> totalMission(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		SopTaskBo sopTaskBo  = new SopTaskBo();
		//当前登录人
		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_KEY);
		sopTaskBo.setAssignUid(user.getId());
		sopTaskBo.setTaskStatus(SopConstant.TASK_MISSION_STATUS);
		Long total = 0l;
		try {
			total = sopTaskService.selectTotalMission(sopTaskBo);
			map.put("status", Status.OK);
			map.put("total",total);
		} catch (PlatformException e) {
			map.put("message","查询失败");
		} catch (Exception e) {
			map.put("message","查询总数失败");
			logger.error("查询待办任务失败", e);
		}
	
		return map;
	}
	
	/**
	 * 紧急总数
	 * @author zhaoying
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/totalUrgent", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> totalUrgent(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		SopTaskBo sopTaskBo  = new SopTaskBo();
		//当前登录人
		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_KEY);
		sopTaskBo.setAssignUid(user.getId());
		sopTaskBo.setTaskOrder(SopConstant.TASK_URGENT_STATUS);
		Long total = 0l;
		try {
			total = sopTaskService.selectTotalUrgent(sopTaskBo);
			map.put("status", Status.OK);
			map.put("total",total);
		} catch (PlatformException e) {
			map.put("message","查询失败");
		} catch (Exception e) {
			map.put("message","查询总数失败");
			logger.error("查询紧急任务失败", e);
		}
	
		return map;
	}
	
	@RequestMapping(value = "/detail")
	public ModelAndView detail(Long taskId)
	{
		ModelAndView mv = new ModelAndView("soptask/taskDetails");
		mv.addObject("taskId", taskId);
		return mv;
	}
	
	@RequestMapping(value = "/toTaskLog")
	public ModelAndView toTaskLog(Long taskId) 
	{
		ModelAndView mv = new ModelAndView("soptask/taskLog");
		mv.addObject("taskId", taskId);
		return mv;
	}
	
	@RequestMapping(value = "/toTaskMesage")
	public ModelAndView toTaskMesage(Long taskId) 
	{
		ModelAndView mv = new ModelAndView("soptask/taskMesage");
		SopTask task = sopTaskService.queryById(taskId);
		Project project = projectService.queryById(task.getProjectId());
		mv.addObject("task", task);
		mv.addObject("taskId", taskId);
		mv.addObject("projectId", task.getProjectId());
		mv.addObject("taskFlag", task.getTaskFlag());
		Long assignUid = task.getAssignUid();
		String assignUname = (String)cache.hget(PlatformConst.CACHE_PREFIX_USER+assignUid, "realName");
		mv.addObject("assignUname", assignUname);
		String fileWType = null;
		String btnTxt = ""; //按钮信息
		boolean showIgnore = false; //是否可以不提供附件
		boolean isCreated = projectType.创建.getCode().equals(project.getProjectType());
		switch (task.getTaskFlag())
		{
		case 2:
			btnTxt = "上传尽调报告";
			fileWType = fileWorktype.人力资源尽职调查报告.getCode();
			break;
		case 3:
			btnTxt = "上传尽调报告";
			fileWType = fileWorktype.法务尽职调查报告.getCode();
			showIgnore = isCreated;
			break;
		case 4:
			btnTxt = "上传尽调报告";
			fileWType = fileWorktype.财务尽职调查报告.getCode();
			showIgnore = isCreated;
			break;
		case 8:
			btnTxt = "上传资金拨付凭证";
			fileWType = fileWorktype.资金拨付凭证.getCode();
			showIgnore = true;
			break;
		case 9:
			btnTxt = "上传工商转让凭证";
			fileWType = fileWorktype.工商转让凭证.getCode();
			break;

		}
		if(taskStatus.已完成.getCode().equals(task.getTaskStatus()))
		{
			showIgnore = false;
		}
		mv.addObject("showIgnore", showIgnore);
		mv.addObject("btnTxt", btnTxt);
		mv.addObject("fileWorktype", fileWType);
		return mv;
	}
	/**
	 * 全部 - 显示待认领、本人待完成、本人已完成的任务
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTaskBo> searchAll(@RequestBody SopTaskBo sopTaskBo,HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long assignUid = user.getId();
		sopTaskBo.setAssignUid(assignUid);
		return search(sopTaskBo, request);
	}
	/**
	 * 待认领
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list/unclaimed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTaskBo> searchUnclaimed(@RequestBody SopTaskBo sopTaskBo,HttpServletRequest request)
	{
		sopTaskBo.setTaskStatus(taskStatus.待认领.getCode());
		return search(sopTaskBo, request);
	}
	/**
	 * 待完工
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@GalaxyResource(name="task_list_dispose", table="sop_task")
	@ResponseBody
	@RequestMapping(value = "/list/unfinished", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTaskBo> searchUnfinished(@RequestBody SopTaskBo sopTaskBo,HttpServletRequest request)
	{
		sopTaskBo.setTaskStatus(taskStatus.待完工.getCode());
		return search(sopTaskBo, request);
	}
	/**
	 * 已完成
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@GalaxyResource(name="task_list_complete", table="sop_task")
	@ResponseBody
	@RequestMapping(value = "/list/finished", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTaskBo> searchFinished(@RequestBody SopTaskBo sopTaskBo,HttpServletRequest request)
	{
		sopTaskBo.setTaskStatus(taskStatus.已完成.getCode());
		return search(sopTaskBo, request);
	}
	/**
	 * 部门待完工
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@GalaxyResource(name="task_list_dep", table="sop_task")
	@ResponseBody
	@RequestMapping(value = "/list/depUnfinished", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTaskBo> searchDepUnfinished(@RequestBody SopTaskBo sopTaskBo,HttpServletRequest request)
	{
		sopTaskBo.setTaskStatus(taskStatus.待完工.getCode());
		return search(sopTaskBo, request);
	}
	/**
	 * 桌面快捷显示
	 * @param sopTaskBo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/quickSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTaskBo> quickSearch(@RequestBody SopTaskBo sopTaskBo,HttpServletRequest request)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long assignUid = user.getId();
		sopTaskBo.setAssignUid(assignUid);
		String[] taskStatusArray = {taskStatus.待完工.getCode(),taskStatus.待认领.getCode()};
		sopTaskBo.setTaskStatusList(Arrays.asList(taskStatusArray));
		return search(sopTaskBo, request);
	}
	
	public ResponseData<SopTaskBo> search(@RequestBody SopTaskBo sopTaskBo, HttpServletRequest request)
	{
		ResponseData<SopTaskBo> data = new ResponseData<>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Long departmentId = user.getDepartmentId();
		sopTaskBo.setDepartmentId(departmentId);

		Result result = new Result();
		try
		{
			Page<SopTaskBo> list = sopTaskService.tasklist(new PageRequest(sopTaskBo.getPageNum(), sopTaskBo.getPageSize(),Direction.DESC,"created_time"), sopTaskBo, request);
			if (null == list.getContent())
			{
				List<SopTaskBo> SopTaskBoList = new ArrayList<SopTaskBo>();
				list.setTotal((long) 0);
				list.setContent(SopTaskBoList);
			}
			data.setPageList(list);
			result.setStatus(Status.OK);
		} catch (PlatformException e)
		{
			result.addError(e.getMessage());
		} catch (Exception e)
		{
			result.addError("任务列表查询失败");
			logger.error("任务列表查询失败", e);
		}
		data.setResult(result);
		return data;
	}
	@ResponseBody
	@RequestMapping(value = "/{id}/logs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationLogs> searchTaskLogs(@PathVariable(value="id") Long id,@RequestBody PageRequest pageable)
	{
		ResponseData<OperationLogs> data = new ResponseData<OperationLogs>();
		OperationLogs query = new OperationLogs();
		query.setRecordType(RecordType.TASK.getType());
		query.setRecordId(id);
		Page<OperationLogs> page = logService.queryPageList(query, pageable);
		data.setPageList(page);
		return data;
	}
	@RequestMapping(value="/transfer", method = RequestMethod.GET)
	public ModelAndView transfer()
	{
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
	@ResponseBody
	@RequestMapping(value = "/transfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	public ResponseData<SopTaskBo> transfer(HttpServletRequest request, @RequestParam("ids[]") Long[] ids, @RequestParam Long targetUserId)
	{
		ResponseData<SopTaskBo> date = new ResponseData<SopTaskBo>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try
		{
			sopTaskService.transfer(ids, user.getId(), targetUserId, user.getId());
		} catch (Exception e)
		{
			date.getResult().setMessage("移交失败");
			if(logger.isErrorEnabled())
			{
				logger.error("移交任务失败, ids:"+ids+", User:"+user.getId()+", TargerUserId:"+targetUserId, e);
			}
		}
		return date;
	}
	@RequestMapping(value="/giveup", method = RequestMethod.GET)
	public ModelAndView giveup()
	{
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
	@ResponseBody
	@RequestMapping(value = "/giveup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	public ResponseData<SopTaskBo> giveup(HttpServletRequest request, @RequestParam("ids[]") Long[] ids)
	{
		ResponseData<SopTaskBo> date = new ResponseData<SopTaskBo>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try
		{
			sopTaskService.giveup(ids, user.getId());
		} catch (Exception e)
		{
			date.getResult().setMessage("放弃失败");
			if(logger.isErrorEnabled())
			{
				logger.error(String.format("放弃任务失败, ids:%s, User:%s", ids, user.getId()), e);
			}
		}
		return date;
	}
	@RequestMapping(value="/assign", method = RequestMethod.GET)
	public ModelAndView assign()
	{
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
	@ResponseBody
	@RequestMapping(value = "/assign", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	public ResponseData<SopTaskBo> assign(HttpServletRequest request, @RequestParam("ids[]") Long[] ids, @RequestParam Long targetUserId)
	{
		ResponseData<SopTaskBo> date = new ResponseData<SopTaskBo>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try
		{
			sopTaskService.assign(ids, targetUserId, user.getId());
		} catch (Exception e)
		{
			date.getResult().setMessage("指派失败");
			if(logger.isErrorEnabled())
			{
				logger.error(String.format("指派任务失败, ids:%s, User:%s,TargetUserId", ids, user.getId(),targetUserId), e);
			}
		}
		return date;
	}
	
	
}
