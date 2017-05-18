package com.galaxyinternet.export_schedule.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.export_schedule.service.ScheduleInfoService;
import com.galaxyinternet.framework.core.service.BaseService;

@Controller
@RequestMapping("/galaxy/visit")
public class ScheduleInfoController extends BaseControllerImpl<ScheduleInfo, ScheduleInfo> {

	@Autowired
	private ScheduleInfoService scheduleInfoService;
	
	@Override
	protected BaseService<ScheduleInfo> getBaseService() {
		// TODO Auto-generated method stub
		return this.scheduleInfoService;
	}
	
   
}
