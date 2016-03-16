package com.galaxyinternet.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.bo.UserBo;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.UserRoleService;


@Controller
@RequestMapping("/galaxy")
public class IndexController extends BaseControllerImpl<User, UserBo> {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	Cache cache;
	
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
		if(roleIdList != null && (roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.CEO))){
			String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
			return "redirect:http://fx.qa.galaxyinternet.com/report/galaxy/report/platform?"+params;
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
		return "index";
	}
	
	/**
	 * 跳转到添加项目页面
	 * @return
	 */
	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public String addProject() {
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
	 */
	@RequestMapping(value = "/mpl", method = RequestMethod.GET)
	public String myproject() {
		return "project/list";
	}
	/**
	 * 到我的项目页面
	 * @return
	 */
	@RequestMapping(value = "/ips", method = RequestMethod.GET)
	public String inProjectStage() {
		return "project/stage";
	}
	/**
	 * 到添加访谈记录弹出层
	 * @return
	 */
	@RequestMapping(value = "/air", method = RequestMethod.GET)
	public String addInterviewRecord() {
		return "project/air";
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
	 * 到添加投决会弹出层
	 * @return
	 */
	@RequestMapping(value = "/voto", method = RequestMethod.GET)
	public String addmeetingRecord() {
		return "project/voto";
	}
	
	
	/**
	 * 跳转到LPHtc
	 * @return
	 */
	@RequestMapping(value = "/mr", method = RequestMethod.GET)
	public String lphtc(HttpServletRequest request) {
		return "project/mr";
	}
	
	/**
	 * 跳转到ceopstc
	 * @return
	 */
	@RequestMapping(value = "/ceopstc", method = RequestMethod.GET)
	public String ceopstc(HttpServletRequest request) {
		return "project/CEOPStc";
	}
	
	/**
	 * 跳转到LxHtc
	 * @return
	 */
	@RequestMapping(value = "/lxhtc", method = RequestMethod.GET)
	public String lxhtc() {
		return "project/LXHtc";
	}
	
	/**
	 * 跳转到TJHtc
	 * @return
	 */
	@RequestMapping(value = "/tjhtc", method = RequestMethod.GET)
	public String tjhtc() {
		return "project/TJHtc";
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
		return "project/progress/tzyxs";
	}
	/**
	 * 弹出投资意向书弹出层
	 * @return
	 */
	@RequestMapping(value = "/jzdc", method = RequestMethod.GET)
	public String jzdc() {
		return "project/progress/jzdc";
	}
	/**
	 * 弹出投资协议阶段的所有弹出框
	 * @return
	 */
	@RequestMapping(value = "/tzxy", method = RequestMethod.GET)
	public String tzxy() {
		return "project/progress/tzxy";
	}
	/**
	 * 弹出股权转让协议的所有弹出框
	 * @return
	 */
	@RequestMapping(value = "/gqzr", method = RequestMethod.GET)
	public String gqzr() {
		return "project/progress/gqzr";
	}

	/**
	 * 完善简历的弹出层
	 */
	@RequestMapping(value="/resumetcc", method = RequestMethod.GET)
	public String resumetcc(){
		return "/resumetc/resumetc";
	}
	
	@Override
	protected BaseService<User> getBaseService() {
		return null;
	}
}
