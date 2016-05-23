package com.galaxyinternet.timer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.timer.PassRateBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.service.PassRateService;

@Controller
@RequestMapping("/galaxy/timer")
public class TimerController extends BaseControllerImpl<PassRate, PassRateBo> {

	final Logger logger = LoggerFactory.getLogger(TimerController.class);
	
	@Autowired
	private PassRateService passRateService;

	@Override
	protected BaseService<PassRate> getBaseService() {
		return this.passRateService;
	}
}
