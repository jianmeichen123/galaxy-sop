package com.galaxyinternet.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/galaxy")
public class IndexController {
	
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String index() {
		return "redirect:/galaxy/index";
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String redirect() {
		return "index";
	}
}
