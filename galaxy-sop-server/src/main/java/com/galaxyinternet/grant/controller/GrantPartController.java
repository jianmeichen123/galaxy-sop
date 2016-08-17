package com.galaxyinternet.grant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.GrantPartBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.service.GrantPartService;



@Controller
@RequestMapping("/galaxy/grant/part")
public class GrantPartController extends BaseControllerImpl<GrantPart, GrantPartBo> {
	
	private GrantPartService grantPartService;
	
	@Override
	protected BaseService<GrantPart> getBaseService() {
		return this.grantPartService;
	}
	
}
