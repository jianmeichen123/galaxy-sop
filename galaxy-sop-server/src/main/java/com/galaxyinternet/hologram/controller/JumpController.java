package com.galaxyinternet.hologram.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/galaxy/infomation")
public class JumpController{
	final Logger logger = LoggerFactory.getLogger(JumpController.class);
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	/**
	 * 基本信息全息图-tab页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tabInfomation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 *全息图-基本信息模块
	 * @param request
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toBaseInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String message(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 * 全息图-项目模块
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toProjectInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String projects(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 * 全息图-团队成员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toTeamInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String dataBriefing(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 * 全息图-运营数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toOperateInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String projectAnalysis(HttpServletRequest request) {
		String forwardProgress = request.getParameter("forwardProgress");
		if(!StringUtils.isBlank(forwardProgress)){
			request.setAttribute("forwardProgress", forwardProgress);
		}
		
		return "hologram/";
	}
	
	/**
	 * 全息图-竞争
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toCompeteInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String pfmAppraisal(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 * 全息图-战略以及策略
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toPlanInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String operation(HttpServletRequest request) {
	
		return "hologram/";
	}

	/**
	 * 全息图-财务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toFinanceInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String paprojectlist(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 * 全息图-法务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toJusticeInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String ghlprojectlist(HttpServletRequest request) {
	
		return "hologram/";
	}
	
	/**
	 * 全息图-融资及估值
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toValuationInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String tjlprojectlist(HttpServletRequest request) {
	
		return "hologram/";
	}
}