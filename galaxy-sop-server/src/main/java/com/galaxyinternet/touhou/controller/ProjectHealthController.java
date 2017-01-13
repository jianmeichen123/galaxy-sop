package com.galaxyinternet.touhou.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.touhou.ProjectHealthBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectHealthService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;


/**
 * 投后阶段     项目健康状况
 */

@Controller
@RequestMapping("/galaxy/health")
public class ProjectHealthController extends BaseControllerImpl<ProjectHealth, ProjectHealthBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectHealthController.class);

	@Autowired
	private ProjectHealthService projectHealthService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRoleService userRoleService;
	
	private String tempfilePath;

	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}

	@Override
	protected BaseService<ProjectHealth> getBaseService() {
		return this.projectHealthService;
	}
	
	
	/**
	 * 弹窗   添加健康状况
	 */
	@RequestMapping(value = "/toaddhealth", method = RequestMethod.GET)
	public String toAddHealth(HttpServletRequest request) {
		return "project/tanchuan/statustc";
	}
	
	/**
	 * 弹窗   健康列表
	 */
	@RequestMapping(value = "/tohealthlist", method = RequestMethod.GET)
	public String toHealthList(HttpServletRequest request) {
		String btn=request.getParameter("hiddenBtn");
		request.setAttribute("isEditable", btn);
		return "project/tanchuan/health_case";
	}
	
	
	/**
	 * 添加  健康记录
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/addhealth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectHealth> addHealth(@RequestBody ProjectHealth projectHealth,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<ProjectHealth> responseBody = new ResponseData<ProjectHealth>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(projectHealth == null || projectHealth.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善信息"));
			return responseBody;
		}
		
		try {
			Project project = projectService.queryById(projectHealth.getProjectId());
			projectHealth.setCreatedUid(user.getId()); 
			projectHealth.setUserName(user.getRealName());
			Long id = projectHealthService.insert(projectHealth);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
			
			String messageType = "";
			if(projectHealth.getHealthState() == 1){
				messageType = "13.1";
			}else if(projectHealth.getHealthState() == 2){
				messageType = "13.2";
			}else if(projectHealth.getHealthState() == 3){
				messageType = "13.3";
			}else if(projectHealth.getHealthState() == 0){
				messageType = "13.4";
			}else if(projectHealth.getHealthState() == 4){
				messageType = "13.5";
			}
			ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), messageType, UrlNumber.one);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "添加失败"));
			logger.error("addHealth 添加失败",e);
		}
		return responseBody;
	}
	
	
	/**
	 *查询  健康列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryhealthpage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectHealth> queryHealthPage(HttpServletRequest request, @RequestBody ProjectHealthBo query) {
		
		ResponseData<ProjectHealth> responseBody = new ResponseData<ProjectHealth>();
		
		try {
			PageRequest pageRequest = new PageRequest();
			Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
			Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
			Direction direction = Direction.DESC;
			String property = "created_time"; //updated_time
			pageRequest = new PageRequest(pageNum,pageSize, direction,property);
			
			Page<ProjectHealth> deliverypage =  projectHealthService.queryPageList(query, pageRequest);
			
			responseBody.setPageList(deliverypage);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,"列表查询失败"));
			logger.error("queryHealthPage 列表查询失败", e);
		}
		return responseBody;
	}
	/**
	 * 首页-高管页面的项目健康度
	 * @param request
	 * @param project
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getHealthyCharts",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> getHealthyCharts(HttpServletRequest request,@RequestBody ProjectBo project){
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}	
		
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		if(!(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.HHR))){ 
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		}
		
		//有搜索条件则不启动默认筛选
		Map<String,Object> params=new HashMap<String,Object>();
		Map<String,Object> map=new HashMap<String,Object>();
		if (roleIdList.contains(UserConstant.HHR)){
			params.put("depId", user.getDepartmentId());
		}
		try {
			map=projectHealthService.gtHealthyChart(params);
			responseBody.setUserData(map);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, "系统出现误不可预知错"));
		}
		return responseBody;
	}
	/**
	 * 弹窗   健康列表
	 */
	@RequestMapping(value = "/toHealthChartDetail", method = RequestMethod.GET)
	public String toHealthChartDetail(HttpServletRequest request) {
		String parameter = request.getParameter("flagUrl");
		if(null!=parameter&&!"".equals(parameter)){
			request.setAttribute("flagUrl", parameter);
		}else{
			request.setAttribute("flagUrl", "");
		}
	
		return "charts/listHealthy";
	}
	
	/**
	 *首页健康度详情
	 */
	@ResponseBody
	@RequestMapping(value = "/getHealthChartGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectHealth> getHealthChartGrid(HttpServletRequest request, @RequestBody ProjectHealthBo query) {
		
		ResponseData<ProjectHealth> responseBody = new ResponseData<ProjectHealth>();
		
        User user = (User) getUserFromSession(request);
		
		//有搜索条件则不启动默认筛选
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		if (roleIdList.contains(UserConstant.HHR)){
			query.setDepId(user.getDepartmentId());
		}
		try {
			PageRequest pageRequest = new PageRequest();
			Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
			Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
			if(null!=query.getFlagUrl()&&!"".equals(query.getFlagUrl())){
				if(query.getFlagUrl().equals("healthHighNum")){
					query.setHealthState((byte)1);
				}else if(query.getFlagUrl().equals("healthGoodNum")){
					query.setHealthState((byte)2);
				}else{
					query.setHealthState((byte)3);
				}
			}
			Direction direction = Direction.DESC;
			String property = "created_time"; //updated_time
			pageRequest = new PageRequest(pageNum,pageSize, direction,property);
			Page<ProjectHealth> deliverypage =  projectHealthService.getHealthChartGrid(query, pageRequest);
			responseBody.setPageList(deliverypage);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,"列表查询失败"));
			logger.error("queryHealthPage 列表查询失败", e);
		}
		return responseBody;
	}
	
}
