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

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.ProjectPersonService;
import com.galaxyinternet.service.ProjectService;
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
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		//判断当前用户是否为投资经理
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		if(!roleIdList.contains(UserConstant.HHR) && !roleIdList.contains(UserConstant.TZJL)){
			responseBody.setResult(new Result(Status.ERROR, "没有权限添加项目!"));
			return responseBody;
		}
		project.setProjectCode(String.valueOf(code));
		project.setCreateUid(user.getId());
		project.setCreateUname(user.getNickName());
		//from字典
		project.setProjectProgress("pp-01");
		project.setProjectStatus("ps-01");
		//获取当前登录人的部门信息
		Long did = user.getDepartmentId();
		project.setProjectDepartid(did);
		project.setCreatedTime((new Date()).getTime());
		try {
			long id = projectService.newProject(project);
			if(id > 0){
				responseBody.setResult(new Result(Status.OK,"项目添加成功!"));
				responseBody.setEntity(project);
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
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		
		Project p = projectService.queryById(project.getId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId() != p.getCreateUid()){
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
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(pool.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId() != p.getCreateUid()){
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
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(pool.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId() != p.getCreateUid()){
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
	@RequestMapping(value = "/dpp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPoolBo> deleteProjectPerson(@RequestBody PersonPoolBo pool, HttpServletRequest request) {
		ResponseData<PersonPoolBo> responseBody = new ResponseData<PersonPoolBo>();
		if(pool == null || pool.getId() == null || pool.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(pool.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId() != p.getCreateUid()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限删除该项目的团队成员!"));
			return responseBody;
		}
		
		ProjectPerson pp = new ProjectPerson();
		pp.setPersonId(pool.getId());
		pp.setProjectId(pool.getProjectId());
		int num = projectPersonService.delete(pp);
		
		if(num > 0){
			responseBody.setResult(new Result(Status.OK,"团队成员删除成功!"));
		}
		return responseBody;
	}
	
	
	/**
	 * 查询团队成员列表
	 * @author yangshuhua
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProjectPerson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> queryProjectPerson(PersonPoolBo pb, PageRequest pageable, HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(pb.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(pb.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId() != p.getCreateUid()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限查看该项目的团队成员信息!"));
			return responseBody;
		}
		
		Page<PersonPool> pageList = personPoolService.queryPageList(pb, pageable);
		responseBody.setResult(new Result(Status.OK,"查询成功!"));
		responseBody.setPageList(pageList);
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
		
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
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
}
