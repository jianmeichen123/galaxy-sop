package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.ReportExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/galaxy/test")
public class TestController{
	final Logger logger = LoggerFactory.getLogger(TestController.class);


	@Autowired
	private CacheOperationService cacheOperationService;

	@Autowired
	private ReportExportService reportExportService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/laobanTest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
		return "seven_report/hologram/test/laoban_test";
	}
//勿删
	@RequestMapping(value = "/writeStand")
	public String writeStand(HttpServletRequest request) {
		return "project/sopinfo/tab_writeStand";
	}
	 // /galaxy/test/jtym1
	@RequestMapping(value = "/jtym1")
	public String platformTest(HttpServletRequest request) {
		return "project/infoenter/infoList";
	}
	@RequestMapping(value = "/jtym2")
	public String platformTest2(HttpServletRequest request) {
		return "project/infoenter/info";
	}
	@RequestMapping(value = "/infoJsp")
	public String platformTest5(HttpServletRequest request) {
		return "project/infoenter/infoJsp";
	}

	@RequestMapping(value = "/infoDJsp")
	public String platformTestD(HttpServletRequest request) {
		return "project/infoenter/infoDJsp";
	}
		
	@RequestMapping(value = "/searchResult")
	public String platformTest4(HttpServletRequest request) {
		return "project/search";
	} 
	@RequestMapping(value = "/systemNotice")
	public String platformTest123(HttpServletRequest request) {
		return "systemNotice/notice";
	}
	@RequestMapping(value = "/operateAanalysis")
	public String operateAanalysis(HttpServletRequest request) {
		return "project/sopinfo/operateAanalysis";
	}
 //健康度3個tab 
	//运营 会议
	@RequestMapping(value = "/health_meet")
	public String health_meet(HttpServletRequest request) {
		return "project/sopinfo/health/health_meet";
	}

	@RequestMapping(value = "/health_change")
	public String health_change(HttpServletRequest request) {
		return "project/sopinfo/health/health_change";
	}

	@RequestMapping(value = "/health_record")
	public String health_record(HttpServletRequest request) {
		return "project/sopinfo/health/health_record";
	}

}