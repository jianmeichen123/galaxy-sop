package com.galaxyinternet.hologram.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/galaxy/test")
public class TestController{
	final Logger logger = LoggerFactory.getLogger(TestController.class);
	


	@RequestMapping(value = "/laobanTest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
		return "seven_report/hologram/test/laoban_test";
	}


	
	
}