package com.galaxyinternet.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.systemMessage.SystemMessageBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.systemMessage.SystemMessage;
import com.galaxyinternet.service.SystemMessageService;


@Controller
@RequestMapping("/galaxy/systemMessage")
public class SystemMessageController extends BaseControllerImpl<SystemMessage, SystemMessageBo>{
	final Logger logger = LoggerFactory.getLogger(SystemMessageController.class);

	@Autowired
	private SystemMessageService systemMessageService;
	

	@Override
	protected BaseService<SystemMessage> getBaseService() {
		return this.systemMessageService;
	}
	
	
	
	
	
}
