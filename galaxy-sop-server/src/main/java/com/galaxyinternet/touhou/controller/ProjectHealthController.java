package com.galaxyinternet.touhou.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.touhou.ProjectHealthBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.cache.Cache;
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
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.ProjectHealthService;
import com.galaxyinternet.service.ProjectService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;


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
	
	private String tempfilePath;
	
	@Autowired
	private Cache cache;

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
	@ApiOperation("添加/编辑项目健康度")
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/addhealth", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectHealth> addHealth(@ApiParam(name = "projectHealth", value = "项目信息", required = true) 
	@RequestBody ProjectHealth projectHealth,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<ProjectHealth> responseBody = new ResponseData<ProjectHealth>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(projectHealth == null || projectHealth.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善信息"));
			return responseBody;
		}
		try {
			Date date= new Date();
			Project project = projectService.queryById(projectHealth.getProjectId());
			projectHealth.setUpdatedUid(user.getId());
			projectHealth.setUpdatedTime(date.getTime());
				if(null==projectHealth.getId()){
					projectHealth.setCreatedUid(user.getId()); 
					projectHealth.setCreatedTime(date.getTime());
					projectHealth.setUserName(user.getRealName());
					Long id = projectHealthService.insert(projectHealth);
					responseBody.setResult(new Result(Status.OK,"添加成功"));
					responseBody.setId(id);
					ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), UrlNumber.one);
				}else{
					int updateById = projectHealthService.updateById(projectHealth);
					if(updateById>0){
						responseBody.setResult(new Result(Status.OK, "编辑成功"));
						responseBody.setId(new Long(updateById));	
					}else{
						responseBody.setResult(new Result(Status.OK, "编辑失败"));
					}
					
					ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), UrlNumber.two);
				}
			     return responseBody;
			} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("Health 操作失败",e);
		}
		return responseBody;
	}
	
	/**
	 * 查询  健康记录
	 */
	@ApiOperation("查看项目健康度")
	@ResponseBody
	@RequestMapping(value = "/getDetail/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectHealth> getDetail(@ApiParam(name = "id", value = "健康度id", required = true) @PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<ProjectHealth> responseBody = new ResponseData<ProjectHealth>();
		try {
			ProjectHealth projectHealth = projectHealthService.queryById(id);
			if(null==projectHealth){
				responseBody.setResult(new Result(Status.ERROR, "记录不存在"));
			}else{
				responseBody.setEntity(projectHealth);
				responseBody.setResult(new Result(Status.OK, "获取健康信息成功"));	
			}
			return responseBody;
			} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("get Health 操作失败",e);
		}
		return responseBody;
	}
	
	/**
	 * 删除  健康记录
	 */
	@ApiOperation("删除项目健康度")
	@ResponseBody
	@RequestMapping(value = "/deleteDetail/{id}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectHealth> deleteDetail(@ApiParam(name = "id", value = "健康度id", required = true)@PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<ProjectHealth> responseBody = new ResponseData<ProjectHealth>();
		try {
			ProjectHealth projectHealth=new ProjectHealth();
			ProjectHealth queryOne = projectHealthService.queryById(id);
			projectHealth.setId(id);
			projectHealth.setIsDelete(1);
			if(null!=queryOne){
				int deleteById = projectHealthService.updateById(projectHealth);
				if(deleteById>0){
					responseBody.setId(id);
					responseBody.setResult(new Result(Status.OK,"删除成功"));
					return responseBody;
				}else{
					responseBody.setResult(new Result(Status.OK,"删除失败"));
					return responseBody;
				}
			}else{
				responseBody.setResult(new Result(Status.OK,"记录不存在"));
				return responseBody;
			}
			
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("Health 操作失败",e);
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
	@ApiOperation("查询项目健康度列表（带参）")
	@ResponseBody
	@RequestMapping(value="/getHealthyCharts",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> getHealthyCharts(HttpServletRequest request,@RequestBody ProjectBo project){
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}	
		
		List<Long> roleIdList = user.getRoleIds();
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
		
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
	
         //从缓存获取当前用户的所有角色
        ShardedJedis jedis = null;
        jedis = cache.getJedis();
        Set<String> smembers = jedis.smembers(PlatformConst.CACHE_USER_ROLEIDS+user.getId());
		List<String> roleIdList = new ArrayList<String>(smembers);
	     //$$$$$$$$$$$$$$$$$$$$$$
		//有搜索条件则不启动默认筛选
		if(roleIdList.contains(UserConstant.HHR)){
	    	 query.setDepId(user.getDepartmentId());
	     }
	     if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.HHR)){ 
	    	 if(null!=query.getDepId()){
	    		 query.setDepId(query.getDepId());
	    	 }
		}
	     if(null!=query.getHealthState()){
	    	 query.setHealthState(query.getHealthState());
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
	   public List<String> getUserIdsByDepId(String deptId){
	    	ShardedJedis jedis = null;
	    	jedis = cache.getJedis();
			ShardedJedisPipeline pip = jedis.pipelined();
				Response<Set<String>> resp = pip.smembers(PlatformConst.CACHE_PREFIX_DEP_USERS+deptId);
				Set<String> userIds = resp.get();
				List<String> ls=new ArrayList<String>(userIds); 
				return ls;
		}
		 
}
