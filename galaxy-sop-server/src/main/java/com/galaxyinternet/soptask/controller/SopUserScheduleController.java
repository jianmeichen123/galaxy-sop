package com.galaxyinternet.soptask.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.service.SopUserScheduleService;

public class SopUserScheduleController extends BaseControllerImpl<SopUserSchedule, SopUserScheduleBo> {

	@Autowired
	private SopUserScheduleService sopUserScheduleService;
	
	@Override
	protected BaseService<SopUserSchedule> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleService;
	}

}
