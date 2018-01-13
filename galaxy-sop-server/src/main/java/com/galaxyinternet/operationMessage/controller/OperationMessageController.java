package com.galaxyinternet.operationMessage.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.bo.OperationMessageBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.service.OperationMessageService;

@Controller
@RequestMapping("/galaxy/operationMessage")
public class OperationMessageController extends BaseControllerImpl<OperationMessage, OperationMessageBo> {
	
	final Logger logger = LoggerFactory.getLogger(OperationMessageController.class);
	
	@Autowired
	private OperationMessageService operationMessageService;
	
	@Override
	protected BaseService<OperationMessage> getBaseService() {
		return this.operationMessageService;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "operationMessage/index";
	}
}