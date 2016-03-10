package com.galaxyinternet.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping(value = "/lphtc", method = RequestMethod.GET)
	public String lphtc(HttpServletRequest request) {
		return "project/LPHtc";
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
}
