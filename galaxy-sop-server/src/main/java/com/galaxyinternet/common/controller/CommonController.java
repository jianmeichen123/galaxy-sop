package com.galaxyinternet.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.UserBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Header;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.Menus;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.RoleUtils;

@Controller
@RequestMapping("/galaxy/common")
public class CommonController extends BaseControllerImpl<User, UserBo>{
	
	final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	private String serverUrl;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DictService dictService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserService userService;
	
	/**
	 * 动态生成左边菜单项列表
	 * @author yangshuhua
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/menu/{selected}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Menus> menu(@PathVariable("selected") int selected, HttpServletRequest request){
		
		serverUrl = getServerUrl();
		
		ResponseData<Menus> responseBody = new ResponseData<Menus>();
		Header header = new Header();
		header.setAttachment(selected);
		responseBody.setHeader(header);
		String p = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String contextPath = request.getContextPath();
		String u = null;
		if(contextPath == null || "".equals(contextPath.trim())){
			//rl.substring(0, url.indexOf(p) + 1);
			u = url.substring(0, url.indexOf(p) + 1);
		}else{
			u = url.substring(0, url.indexOf(contextPath) + contextPath.length() + 1);
		}
		
		User user = (User) getUserFromSession(request);
		
		List<Menus> tabs = new ArrayList<Menus>();
		String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
		tabs.add(new Menus(1L, 0, 1, "工作桌面", u + "galaxy/index?" + params));
		tabs.add(new Menus(2L, 0, 2, "待办任务", u + "galaxy/soptask?" + params));
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		
		if(roleIdList.contains(UserConstant.TZJL)){
			tabs.add(new Menus(5L, 0, 3, "创投项目", u + "galaxy/mpl?" + params));
			tabs.add(new Menus(6L, 0, 16,"访谈记录", u + "galaxy/project/progress/interView?" + params));
			tabs.add(new Menus(7L, 0, 15,"会议纪要", u + "galaxy/project/progress/meetView?" + params));
			tabs.add(new Menus(14L, 0, 18,"项目文档", u + "galaxy/sopFile/toFileList?" + params));
			tabs.add(new Menus(13L, 0, 17,"项目模板", u + "galaxy/template?" + params));			
			tabs.add(new Menus(21L, 0, 10, "项目创意", u + "galaxy/idea?" + params));		
			
			
			tabs.add(new Menus(8L, 0, 13,"绩效考核", u + "galaxy/kpireport/touserkpi?" + params));
		}
		
		if(roleIdList.contains(UserConstant.HRZJ) || roleIdList.contains(UserConstant.HRJL)
				|| roleIdList.contains(UserConstant.CWZJ) || roleIdList.contains(UserConstant.CWJL)
				|| roleIdList.contains(UserConstant.FWZJ) || roleIdList.contains(UserConstant.FWJL)){
			tabs.add(new Menus(9L, 0, 19,"尽调报告", u + "galaxy/soptask?flag=jz&"+ params));
		}
		
		if(roleIdList.contains(UserConstant.HRZJ) || roleIdList.contains(UserConstant.HRJL)){
			tabs.add(new Menus(10L, 0, 20,"完善简历", u + "galaxy/soptask?flag=jl&"+ params));
			tabs.add(new Menus(14L, 0, 18,"项目文档", u + "galaxy/sopFile/toFileList?" + params));
			tabs.add(new Menus(13L, 0, 17,"项目模板", u + "galaxy/template?" + params));
		}
		if(roleIdList.contains(UserConstant.CWZJ) || roleIdList.contains(UserConstant.CWJL)){
			tabs.add(new Menus(11L, 0, 24,"付款凭证", u + "galaxy/soptask?flag=pz&"+ params));
			tabs.add(new Menus(14L, 0, 18,"项目文档", u + "galaxy/sopFile/toFileList?" + params));
			tabs.add(new Menus(13L, 0, 17,"项目模板", u + "galaxy/template?" + params));
		}
		
		if(roleIdList.contains(UserConstant.FWZJ) || roleIdList.contains(UserConstant.FWJL)){
			tabs.add(new Menus(12L, 0, 25,"股权交割", u + "galaxy/soptask?flag=gq&"+ params));
			tabs.add(new Menus(14L, 0, 18,"项目文档", u + "galaxy/sopFile/toFileList?" + params));
			tabs.add(new Menus(13L, 0, 17,"项目模板", u + "galaxy/template?" + params));
		}
		
		
		
		
		
		//档案管理员
		if(roleIdList.contains(17L)){
			tabs.clear();
			tabs.add(new Menus(14L, 0,18, "档案管理", u + "galaxy/sopFile/toFileList?" + params));
		}
		
		//管理员
		if(roleIdList.contains(16L)){
			tabs.clear();
			tabs.add(new Menus(15L, 0, "用户管理", serverUrl + "platform/galaxy/user?" + params));
			tabs.add(new Menus(16L, 0, "数据字典", serverUrl + "platform/galaxy/dict/index?" + params));
		}
		
		//高管
		if(roleIdList.contains(UserConstant.HHR) || roleIdList.contains(1L) || roleIdList.contains(2L)){
			tabs.clear();
			tabs.add(new Menus(1L, 0, 1,"工作桌面", u + "galaxy/report/platform?" + params));
			//tabs.add(new Menus(3L, 0,8, "消息提醒", serverUrl +"sop/galaxy/operationMessage/index?"+params));
			tabs.add(new Menus(5L, 0, 3, "创投项目", u + "galaxy/mpl?" + params));
			
			//tabs.add(new Menus(22L, 0, 11, "数据简报", u + "galaxy/report/dataBriefing?" + params));
			//tabs.add(new Menus(6L, 0, 12, "项目分析", u + "galaxy/report/projectAnalysis?" + params));
			//tabs.add(new Menus(7L, 0, 13,"绩效考核", u + "galaxy/report/kpi?" + params));
			
			tabs.add(new Menus(22L, 0, 11, "数据简报", u + "galaxy/kpireport/toDataBriefing?" + params));
			tabs.add(new Menus(6L, 0, 12, "项目分析", u + "galaxy/kpireport/paprojectlist?" + params));
			tabs.add(new Menus(8L, 0, 13,"绩效考核", u + "galaxy/kpireport/touserkpi?" + params));
			
			
			
			tabs.add(new Menus(11L, 0,14, "投后运营", "javascript:void(0);")
					.addNode(new Menus(8L, 1, "投后项目跟踪", u + "galaxy/report/afterInvestTrack?" + params))
					.addNode(new Menus(9L, 1, "投后业务运营", u + "galaxy/report/afterInvestBusiness?" + params))
					.addNode(new Menus(10L, 1, "投后企业财报", u + "galaxy/report/afterInvestFinace?" + params)));
			tabs.add(new Menus(21L, 0, 10, "项目创意", u + "galaxy/idea?" + params));
		}
		//董事长秘书      CEO秘书
		if(roleIdList.contains(UserConstant.DMS) ||roleIdList.contains(UserConstant.CEOMS)){
			tabs.clear();
			tabs.add(new Menus(1L, 0, 1,"工作桌面", u + "galaxy/index?" + params));
			tabs.add(new Menus(18L, 0, 21,"立项会", u + "galaxy/lxh?" + params));
			tabs.add(new Menus(19L, 0, 22,"投决会", u + "galaxy/tjh?" + params));
			tabs.add(new Menus(20L, 0, 23,"CEO评审会", u + "galaxy/psh?" + params));								
		}
		tabs.add(new Menus(25L, 0, 24,"测试页面(项目分析)", u + "galaxy/charts/analysis/toProjectAnalysis?" + params));
		tabs.add(new Menus(26L, 0, 25,"测试页面(数据简报)", u + "galaxy/charts/briefing/toDataBriefing?" + params));
	    responseBody.setEntityList(tabs);
		return responseBody;
	}

	@Override
	protected BaseService<User> getBaseService() {
		return null;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	@Value("${project.server.url}")
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@ResponseBody
	@RequestMapping(value="/judgeRole/{projectId}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<User> judgeRole(HttpServletRequest request,@PathVariable String projectId){
		ResponseData<User> responseBody = new ResponseData<User>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		if(StringUtils.isBlank(projectId)){
			responseBody.setResult(new Result(Status.ERROR,"传入的projectId为空"));
		}
		try {
			Result result = null;
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
					.getId());
			Project query = new Project();
			//1为高管
			if(RoleUtils.isGaoGuan(roleIdList)){
				result = new Result(Status.OK,"");
				result.setMessage("show");
				responseBody.setResult(result);	
				return responseBody;
			}else if(RoleUtils.isTZJL(roleIdList)){
				//当为投资经理时候本人
				query.setId(Long.parseLong(projectId));
				query.setCreateUid(user.getId());
			}else if(RoleUtils.isHHR(roleIdList)){
				//当为合伙人时本事业线下
				query.setId(Long.parseLong(projectId));
				query.setProjectDepartid(user.getDepartmentId());
			}else if(RoleUtils.isHRJL(roleIdList) || RoleUtils.isHRZJ(roleIdList) || RoleUtils.isCWJL(roleIdList) || RoleUtils.isCWZJ(roleIdList)|| RoleUtils.isFWJL(roleIdList)||RoleUtils.isFWZJ(roleIdList)){
				//当为人财法不显示
				result = new Result(Status.OK,"");
				result.setMessage("hide");
				responseBody.setResult(result);	
				return responseBody;
			}else if(RoleUtils.isMs(roleIdList)){
				//秘书不显示
				result = new Result(Status.OK,"");
				result.setMessage("hide");
				responseBody.setResult(result);	
				return responseBody;
			}
			List<Project> project = projectService.queryList(query);
			result = new Result(Status.OK,"");
			if(project.isEmpty()){
				result.setMessage("hide");
			}else{
				result.setMessage("show");
			}
			responseBody.setResult(result);	
		} catch (DaoException e) {
			responseBody.setResult(new Result(Status.ERROR,"系统异常"));
		}
		return responseBody;
		
	}
	
	/**
	 * 查询数据字典的子集
	 * @version 2016-06-21
	 */
	@ResponseBody
	@RequestMapping(value = "/getDictionaryList/{parentCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Dict> getDictionaryList(@PathVariable("parentCode") String parentCode,HttpServletRequest request) {
		ResponseData<Dict> responseBody = new ResponseData<Dict>();
		List<Dict> dictList = dictService.selectByParentCode(parentCode);
		responseBody.setEntityList(dictList);
		responseBody.setResult(new Result(Status.OK, null, "获取字典项成功！"));
		return responseBody;
	}
	
	/**
	 * 查询事业线
	 * @version 2016-06-21
	 */
	@ResponseBody
	@RequestMapping(value = "/getCareerlineList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Department> getCareerlineList(HttpServletRequest request) {
		ResponseData<Department> responseBody = new ResponseData<Department>();
		User user = (User) getUserFromSession(request);
		
		Department query = new Department();
		query.setType(1);
		List<Department> careerlineList = departmentService.queryList(query);
		for(Department department : careerlineList){
			if(user.getDepartmentId().longValue() == department.getId().longValue()){
				department.setCurrentUser(true);
				break;
			}
		}
		responseBody.setEntityList(careerlineList);
		responseBody.setResult(new Result(Status.OK, null, "获取事业线成功！"));
		return responseBody;
	}
	
	/**
	 * 根据事业线查询相应的投资经理
	 * @version 2016-06-21
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserList/{departmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<User> getUserList(@PathVariable("departmentId") Long departmentId, HttpServletRequest request) {
		ResponseData<User> responseBody = new ResponseData<User>();
		User currentUser = (User) getUserFromSession(request);
		User user = new User();
		List<Long> departmentIds = new ArrayList<Long>();
		if(departmentId.longValue() == 0L){
			Department query = new Department();
			query.setType(1);
			List<Department> careerlineList = departmentService.queryList(query);
			for(Department d : careerlineList){
				departmentIds.add(d.getId());
			}
		}else{
			departmentIds.add(departmentId);
		}
		user.setDepartmentIds(departmentIds);
		List<User> userList = userService.queryList(user);
		List<User> responseUserList = new ArrayList<User>();
		List<Long> uids = userRoleService.selectUserIdByRoleId(UserConstant.TZJL);
		for(User u : userList){
			if(uids.contains(u.getId())){
				if(u.getId().intValue() == currentUser.getId().intValue()){
					u.setCurrentUser(true);
				}
				responseUserList.add(u);
			}
		}
		responseBody.setEntityList(responseUserList);
		responseBody.setResult(new Result(Status.OK, null, "获取投资经理成功！"));
		return responseBody;
	}
	

}
