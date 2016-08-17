package com.galaxyinternet.grant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.GrantTotalBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.service.GrantTotalService;



@Controller
@RequestMapping("/galaxy/grant/total")
public class GrantTotalController extends BaseControllerImpl<GrantTotal, GrantTotalBo> {
	
	private GrantTotalService grantTotalService;
	
	@Override
	protected BaseService<GrantTotal> getBaseService() {
		return this.grantTotalService;
	}
	
}
