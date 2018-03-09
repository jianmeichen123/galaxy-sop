package com.galaxyinternet.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.systemMessage.SystemMessageUserBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.systemMessage.SystemMessageUser;
import com.galaxyinternet.service.SystemMessageUserService;


@Controller
@RequestMapping("/galaxy/systemMessageUser")
public class SystemMessageUserController extends BaseControllerImpl<SystemMessageUser, SystemMessageUserBo>{
	final Logger logger = LoggerFactory.getLogger(SystemMessageUserController.class);

	@Autowired
	private SystemMessageUserService systemMessageUserService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Override
	protected BaseService<SystemMessageUser> getBaseService() {
		return this.systemMessageUserService;
	}
}
