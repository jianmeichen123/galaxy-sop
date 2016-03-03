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
}
