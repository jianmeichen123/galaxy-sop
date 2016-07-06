package com.galaxyinternet.touhou.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.bo.touhou.ProjectHealthBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.ProjectHealthService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


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
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DepartmentService departmentService;

	
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
		return "project/tanchuan/health_case";
	}
	
	
	/**
	 * 添加  健康记录
	 */
	@ResponseBody
	@RequestMapping(value = "/addhealth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> addHealth(@RequestBody ProjectHealth projectHealth,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(projectHealth == null || projectHealth.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善信息"));
			return responseBody;
		}
		
		try {
			projectHealth.setCreatedUid(user.getId()); 
			projectHealth.setUserName(user.getRealName());
			Long id = projectHealthService.insert(projectHealth);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
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
	
	
}
