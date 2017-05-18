package com.galaxyinternet.chart.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/galaxy/report")
public class JumpController{
	final Logger logger = LoggerFactory.getLogger(JumpController.class);
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	/**
	 * 工作平台，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/platform", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
	
		return "platform";
	}
	
	/**
	 * 消息中心，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String message(HttpServletRequest request) {
	
		return "message";
	}
	
	/**
	 * 消息中心，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String projects(HttpServletRequest request) {
	
		return "projects";
	}
	
	/**
	 * 数据简报，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dataBriefing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String dataBriefing(HttpServletRequest request) {
	
		return "dataBriefing";
	}
	
	/**
	 * 项目分析，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectAnalysis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String projectAnalysis(HttpServletRequest request) {
		String forwardProgress = request.getParameter("forwardProgress");
		if(!StringUtils.isBlank(forwardProgress)){
			request.setAttribute("forwardProgress", forwardProgress);
		}
		
		return "projectAnalysis";
	}
	
	/**
	 * 绩效考核，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/kpi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String pfmAppraisal(HttpServletRequest request) {
	
		return "kpi";
	}
	
	/**
	 * 投后运营，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/operation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String operation(HttpServletRequest request) {
	
		return "operation";
	}

	/**
	 * 项目分析－项目数统计－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/paprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String paprojectlist(HttpServletRequest request) {
	
		return "common/paprojectlist";
	}
	
	/**
	 * 项目分析－过会率统计－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ghlprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String ghlprojectlist(HttpServletRequest request) {
	
		return "common/ghlprojectlist";
	}
	
	/**
	 * 项目分析－投决率统计－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tjlprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String tjlprojectlist(HttpServletRequest request) {
	
		return "common/tjlprojectlist";
	}
	
	/**
	 * 绩效考核－团队绩效－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deptkpiprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deptkpiprojectlist(HttpServletRequest request) {
	
		return "common/deptkpiprojectlist";
	}

	/**
	 * 投后运营－项目跟踪－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/afterInvestTrack", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String afterInvestTrack(HttpServletRequest request) {
	
		return "afterinvest/tracking";
	}
	
	/**
	 * 投后运营－业务运营－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/afterInvestBusiness", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String afterInvestBusiness(HttpServletRequest request) {
	
		return "afterinvest/business";
	}
	
	/**
	 * 投后运营－企业财报－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/afterInvestFinace", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String afterInvestFinace(HttpServletRequest request) {
	
		return "afterinvest/finace";
	}
	
	/**
	 * 投后运营－项目查看，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/catprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String catprojectlist(HttpServletRequest request) {
	
		return "project/list";
	}
	
	/**
	 * 公用弹出层页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/popupList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String popupList(HttpServletRequest request) {
	
		return "common/popupList";
	}
	
	/**
	 * 日程跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/popupMeetingList/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String popupMeetingList(@PathVariable("type") String type,HttpServletRequest request) {
		request.setAttribute("type", type);
		return "sheduleMeeting";
	}
	
	
	/**
	 * 日程跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/visitChart", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String visitChart(HttpServletRequest request) {
		return "report/visit/visitChart";
	}
}