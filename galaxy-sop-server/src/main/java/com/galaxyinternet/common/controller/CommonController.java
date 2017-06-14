package com.galaxyinternet.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
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
import com.galaxyinternet.model.resource.PlatformResource;
import com.galaxyinternet.model.user.Menus;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.ResourceService;
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
	@Autowired
	private ResourceService resourceService;
	
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
		
		List<Menus> tabs = getUserMenus(request);
		//选中菜单
		Long selectedMenuId = getSelectMenuId(request);
		if(selectedMenuId != null)
		{
			selected = selectedMenuId.intValue();
		}
		
		Header header = new Header();
		header.setAttachment(selected);
		responseBody.setHeader(header);
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
	 * 查询事业线
	 * @version 2016-06-21
	 */
	@ResponseBody
	@RequestMapping(value = "/getCareerlineListByRole", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Department> getCareerlineListByRole(HttpServletRequest request) {
		ResponseData<Department> responseBody = new ResponseData<Department>();
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		
		Department query = new Department();
		query.setType(1);
		if( roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.CEO)){
		}else if(roleIdList.contains(UserConstant.HHR)){
			query.setId(user.getDepartmentId());
		}
		
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
		user.setStatus("0");
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
	
	private List<Menus> getUserMenus(Long userId, Long parentId, String companyId, String params)
	{
		List<Menus> menus = new ArrayList<Menus>();
		List<PlatformResource> list = resourceService.queryUserMenus(userId, parentId,companyId);
		if(list != null && list.size() >0)
		{
			for(PlatformResource res : list)
			{
				String url = null;
				if(StringUtils.isNotEmpty(res.getResourceUrl()))
				{
					String product = StringUtils.isNotEmpty(res.getProductMark()) ? res.getProductMark()+"/" : "" ;
					String reqUrl=serverUrl+product+res.getResourceUrl();

					if(reqUrl.indexOf("?")==-1){
						url = reqUrl+"?"+params;
					}else{
						url = reqUrl+"&"+params;
					}
				}
				Integer level = res.getParentId() != null && res.getParentId().intValue() > 0 ? 1 : 0;
				Integer navNum = NumberUtils.isNumber(res.getStyle()) ? Integer.valueOf(res.getStyle()) : null;
				Menus menu = new Menus(res.getId(), level, navNum, res.getResourceName(), url);
				
				List<Menus> subMenus = getUserMenus(userId,res.getId(),companyId,params);
				if(subMenus != null && subMenus.size()>0 )
				{
					for(Menus node : subMenus)
					{
						menu.addNode(node);
					}
				}
				menus.add(menu);
			}
		}
		return menus;
	}
	/**
	 * 用户菜单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Menus> getUserMenus(HttpServletRequest request)
	{
		User user = (User) getUserFromSession(request);
		List<Menus> tabs = null;
		HttpSession session = request.getSession();
		if(session != null)
		{
			String key = Constants.SESSION_USER_MENUS_KEY+getSessionId(request);
			if(session.getAttribute(key) != null)
			{
				tabs = (List<Menus>)session.getAttribute(key);
			}
			else
			{
				String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request)+ "&"+SopConstant.REQUEST_SCOPE_ATTR_IS_MENU +"=true";
				tabs = getUserMenus(user.getId(), 0L, user.getCompanyId(), params);
				session.setAttribute(key, tabs);
			}
		}
		return tabs;
	}
	/**
	 * 选中菜单
	 * @param request
	 * @return
	 */
	private Long getSelectMenuId(HttpServletRequest request)
	{
		PlatformResource res = null;
		String referer = request.getHeader("Referer");
		String key = Constants.USER_LAST_ACCESS_MENU+getSessionId(request);
		HttpSession session = request.getSession();
		//从其他地方跳转（非菜单）
		if(StringUtils.isNotEmpty(referer) && !referer.contains(SopConstant.REQUEST_SCOPE_ATTR_IS_MENU))
		{
			if(session.getAttribute(key) != null)
			{
				res = (PlatformResource)session.getAttribute(key);
				if(!"index".equals(res.getResourceMark()))
				{
					return res.getId();
				}
			}
		}

		User user = (User) getUserFromSession(request);
		List<PlatformResource> userRes = new ArrayList<>(user.getAllResourceToUser());
		Collections.sort(userRes, new Comparator<PlatformResource>(){
			@Override
			public int compare(PlatformResource o1, PlatformResource o2)
			{
				String url1 = StringUtils.isBlank(o1.getResourceUrl()) ? "" : o1.getResourceUrl();
				String url2 = StringUtils.isBlank(o2.getResourceUrl()) ? "" : o2.getResourceUrl();
				return url2.length() - url1.length();
			}
		});
		
		res = getResourceByUrl(userRes, referer);
		do
		{
			if(res != null && "1".equals(res.getResourceType()))
			{
				if("index".equals(res.getProductMark()))
				{
					session.removeAttribute(key);
				}
				else
				{
					session.setAttribute(key, res);
				}
				return res.getId();
			}
			if(res != null)
			{
				res = getResourceById(userRes, res.getParentId());
			}
		}
		while(res != null);
		return null;
	}
	private PlatformResource getResourceByUrl(List<PlatformResource> userRes, String url)
	{
		PlatformResource res = null;
		for(PlatformResource r : userRes)
		{
			if(!StringUtils.isBlank(r.getResourceUrl()) && url.contains(r.getResourceUrl()))
			{
				res = r;
				break;
			}
		}
		
		return res;
	}
	private PlatformResource getResourceById(List<PlatformResource> userRes, Long id)
	{
		PlatformResource res = null;
		for(PlatformResource r : userRes)
		{
			if(r.getId().equals(id))
			{
				res = r;
				break;
			}
		}
		return res;
	}
}
