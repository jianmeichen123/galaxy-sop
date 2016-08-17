package com.galaxyinternet.grant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.GrantActualBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantActual;
import com.galaxyinternet.service.GrantActualService;


@Controller
@RequestMapping("/galaxy/grant/actual")
public class GrantActualController extends BaseControllerImpl<GrantActual, GrantActualBo> {
	
	private GrantActualService grantActualService;
	
	@Override
	protected BaseService<GrantActual> getBaseService() {
		return this.grantActualService;
	}
	
}
