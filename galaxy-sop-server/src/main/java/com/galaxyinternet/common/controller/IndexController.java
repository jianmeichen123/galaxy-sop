package com.galaxyinternet.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/galaxy")
public class IndexController {
	
	/**
	 * 避免url后边附带sessionId，第一次将user放入session后，通过重定向抹去后边参数
	 * @return
	 */
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect() {
		return "redirect:/galaxy/index";
	}
	
	/**
	 * 跳转到首页-工作桌面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
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
	@RequestMapping(value = "/upp", method = RequestMethod.GET)
	public String updateProject() {
		return "project/update";
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
}
