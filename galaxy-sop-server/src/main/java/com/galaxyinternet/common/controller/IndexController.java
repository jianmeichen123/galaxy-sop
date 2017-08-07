package com.galaxyinternet.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.IndexConfigBo;
import com.galaxyinternet.bo.UserBo;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.resource.PlatformResource;
import com.galaxyinternet.model.sopIndex.IndexConfig;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.IndexConfigService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.sopfile.controller.SopFileController;
import com.galaxyinternet.template.controller.SopTemplateController;


@Controller
@RequestMapping("/galaxy")
public class IndexController extends BaseControllerImpl<User, UserBo>{
	final Logger logger = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private IndexConfigService indexConfigService;
	
	@Autowired
	Cache cache;
	@Autowired
	private SopFileController fileController;
	@Autowired
	private SopTemplateController templateController;
	@Autowired
	private ProjectService projectService;
	
	private String serverUrl;
	
	
	
	/**
	 * 避免url后边附带sessionId，第一次将user放入session后，通过重定向抹去后边参数
	 * @return
	 */
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(HttpServletRequest request) {
		String sessionId = request.getParameter(Constants.SESSOPM_SID_KEY);
		request.getSession().setAttribute(Constants.SESSION_USER_KEY, cache.get(sessionId));
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
//		if(roleIdList != null && (roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.HHR))){
//			//serverUrl = getServerUrl();
//			String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
//			return "redirect:/galaxy/report/platform?"+params;
//		}else 
			if(roleIdList != null && roleIdList.contains(UserConstant.GLY)){
			String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
			return "redirect:" + serverUrl +"platform/galaxy/user?"+params;
		}else if(roleIdList != null && roleIdList.contains(UserConstant.DAGLY)){
			String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
			return "redirect:/galaxy/sopFile/toFileList?"+params;
		}else{
			return "redirect:/galaxy/index";
		}
		
	}
	
	/**
	 * 跳转到首页-工作桌面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		List<IndexConfig> models = new ArrayList<>();
		User user = (User) getUserFromSession(request);
		List<PlatformResource> resList = user.getAllResourceToUser();
		Map<String,PlatformResource> resMap = new HashMap<>();
		for(PlatformResource item : resList)
		{
			if("5".equals(item.getResourceType())) //是否桌面组件
			{
				resMap.put(item.getResourceMark(), item);
			}
		}
		
		List<IndexConfig> list = indexConfigService.queryAll();
		for(IndexConfig item: list)
		{
			if(item.getResourceCode() != null && resMap.containsKey(item.getResourceCode()))
			{
				models.add(item);
			}
		}
		
		logger.debug(GSONUtil.toJson(models));
		request.setAttribute("modules", models);
		return "desktop/index";
	}
	
	/**
	 * 跳转到首页-工作桌面
	 * @return
	 */
	@RequestMapping(value = "/deleteAlert", method = RequestMethod.GET)
	public String deleteAlert(HttpServletRequest request) {
		return "project/tanchuan/tipsAppr";
	}
	
	/**
	 * 跳转到添加项目页面
	 * @return
	 */
	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public String addProject(HttpServletRequest request) {
		request.getSession().removeAttribute("businessPlan");
		return "project/add";
	}
	
	/**
	 * 跳转到修改项目页面
	 * @return
	 */
	@RequestMapping(value = "/upp/{pid}", method = RequestMethod.GET)
	public String updateProject(@PathVariable("pid") Long pid, HttpServletRequest request) {
		request.setAttribute("pid", pid);
		return "project/update";
	}
	
	/**
	 * 高管到项目查询
	 * @return
	 */
	@RequestMapping(value = "/cpl", method = RequestMethod.GET)
	public String catProjectList() {
		return "manager/list";
	}
	
	/**
	 * 高管到项目查询
	 * @return
	 */
	@RequestMapping(value = "/sop", method = RequestMethod.GET)
	public String sop() {
		return "manager/stage";
	}
	@RequestMapping(value = "/sop2/{projectId}", method = RequestMethod.GET)
	public ModelAndView sop2(@PathVariable("projectId") Long projectId) 
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("manager/stage2");
		mv.addObject("projectId", projectId);
		return mv;
	}
	
	/**
	 * 高管查看项目详情
	 * @return
	 */
	@RequestMapping(value = "/detail/{pid}", method = RequestMethod.GET)
	public String detailProject(@PathVariable("pid") Long pid, HttpServletRequest request) {
		request.setAttribute("pid", pid);
		return "manager/detail";
	}
	
	/**
	 * 添加团队成员弹出层
	 * @return
	 */
	@RequestMapping(value = "/addperson", method = RequestMethod.GET)
	public String addPerson() {
		return "project/addPerson";
	}
	
	/**
	 * 到我的项目页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mpl", method = RequestMethod.GET)
	public String myproject(HttpServletRequest request){
		String id = request.getParameter("projectId");
		//退回标记
		String backSign = request.getParameter("backSign");
		if(StringUtils.isNotBlank(id)){
			Project project = projectService.queryById(Long.parseLong(id));
			request.setAttribute("pid", id);
			request.setAttribute("pname", project.getProjectName());
		}
		if(StringUtils.isNotBlank(backSign)){
			request.setAttribute("backSign", backSign);
		}else{
			request.setAttribute("backSign", "false");
		}
		return "project/list";
	}
	
	
	/**
	 * 到我的项目页面
	 * @return
	 */
	@RequestMapping(value = "/ips", method = RequestMethod.GET)
	public String inProjectStage() {
		return "project/sop";
	}
	/**
	 * 到添加访谈记录弹出层
	 * @return
	 */
	@RequestMapping(value = "/air", method = RequestMethod.GET)
	public String addInterviewRecord() {
		return "project/sop/air";
	}
	
	/**
	 * 操作成功提示弹出层
	 * @return
	 */
	@RequestMapping(value = "/tip/{type}", method = RequestMethod.GET)
	public String tip(HttpServletRequest request, @PathVariable("type") int type) {
		request.setAttribute("tipType", type);
		return "common/tip";
	}
	
	
	/**
	 * 跳转到LPHtc
	 * @return
	 */
	@RequestMapping(value = "/mr/{pid}", method = RequestMethod.GET)
	public String lphtc(HttpServletRequest request,@PathVariable Long pid) {
		Project pr=projectService.queryById(pid);
		if(pr!=null){
			String time=pr.getCreateDate();
			request.setAttribute("timeStr", time);
		}
		return "project/sop/mr";
	}
	
	
	/**
	 * 弹出上传业务尽职调查报告
	 * @return
	 */
	@RequestMapping(value = "/uywjd", method = RequestMethod.GET)
	public String uywjd() {
		return "project/uploadFile";
	}
	
	/**
	 * 弹出投资意向书弹出层
	 * @return
	 */
	@RequestMapping(value = "/tzyx", method = RequestMethod.GET)
	public String tzyx() {
		return "project/sop/tzyxs";
	}
	/**
	 * 弹出投资意向书弹出层
	 * @return
	 */
	@RequestMapping(value = "/jzdc", method = RequestMethod.GET)
	public String jzdc() {
		return "project/sop/jzdc";
	}
	/**
	 * 弹出投资协议阶段的所有弹出框
	 * @return
	 */
	@RequestMapping(value = "/tzxy", method = RequestMethod.GET)
	public String tzxy() {
		return "project/sop/tzxy";
	}
	/**
	 * 弹出股权转让协议的所有弹出框
	 * @return
	 */
	@RequestMapping(value = "/gqzr", method = RequestMethod.GET)
	public String gqzr() {
		return "project/sop/gqzr";
	}

	/**
	 * 完善简历的弹出层
	 */
	@RequestMapping(value="/resumetcc", method = RequestMethod.GET)
	public String resumetcc(){
		return "/resumetc/resumetc";
	}
	
	
	/**
	 * 到立项会排期列表
	 * @return
	 */
	@RequestMapping(value = "/lxh", method = RequestMethod.GET)
	public String lxh(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		request.setAttribute("roleIdList", roleIdList);
		request.setAttribute("pageType", 0);
		return "shedule/lxh";
	}
	/**
	 * 到投决会排期列表
	 * @return
	 */
	@RequestMapping(value = "/tjh", method = RequestMethod.GET)
	public String tjh(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		request.setAttribute("roleIdList", roleIdList);
		request.setAttribute("pageType", 1);
		return "shedule/lxh";
	}
	/**
	 * 到CEO评审会排期列表
	 * @return
	 */
	@RequestMapping(value = "/psh", method = RequestMethod.GET)
	public String psh(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) getUserFromSession(request);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
		request.setAttribute("roleIdList", roleIdList);
		request.setAttribute("pageType", 2);
		return "shedule/lxh";
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
	@RequestMapping(value="/openEntry/download/{type}/{id}", method = RequestMethod.GET)
	public void downloadEntry(@PathVariable String type, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response)
	{
		if("file".equals(type))
		{
			fileController.download(id, request, response);
		}
		else if("template".equals(type))
		{
			SopTemplate query = new SopTemplate();
			query.setId(id);
			templateController.download(query, request, response);
		}
		else
		{
			logger.error("参数错误， type ="+type+", id="+id);
		}
	}
	
	/**
	 * 团队成员的弹出层
	 */
	@RequestMapping(value="/personTab/{id}", method = RequestMethod.GET)
	public String personTab(@PathVariable Long id, HttpServletRequest request){
		
		User user = (User) getUserFromSession(request);
		boolean flag = false;
		if(id != null){
			Project pro = projectService.queryById(id);
			if(pro.getCreateUid().longValue() == user.getId().longValue()){
				flag = true;
			}
		}
		request.setAttribute("flag", flag);
		request.setAttribute("pid", id);
		request.setAttribute("projectId", id);
		return "project/sopinfo/tab_person";
	}
	/**
	 * 显示桌面模块
	 * @param moudle
	 * @return
	 */
	@RequestMapping(value="/desktop/{moudle}")
	public String showMoudle(@PathVariable String moudle)
	{
		return "desktop/"+moudle;
	}

	
}
