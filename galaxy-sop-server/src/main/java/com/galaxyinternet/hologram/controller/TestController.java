package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.common.utils.DocExportUtil;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.ReportExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/galaxy/test")
public class TestController{
	final Logger logger = LoggerFactory.getLogger(TestController.class);


	@Autowired
	private CacheOperationService cacheOperationService;

	@Autowired
	private ReportExportService reportExportService;

	@RequestMapping(value = "/laobanTest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
		return "seven_report/hologram/test/laoban_test";
	}


	public static final String temp1 = "qxbg-hb-temp.xml"; //横板
	public static final String temp2 = "qxbg-zh-temp.xml"; //综合
	public static final String tempath = "/template";  //  模板地址

	@RequestMapping("/down/{pid}")
	public void downDoc(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),pid,"NO");

		DocExportUtil docExportUtil = new DocExportUtil(tempath, temp1, request, response);
		docExportUtil.createDocOut(map);
	}



}