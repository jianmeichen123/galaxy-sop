package com.galaxyinternet.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	/**
	 * 跳转到系统消息配置页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tabSystemMessage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
		return "seven_report/hologram/tabInfomation";
	}
     /**
	 *全息图-基本信息模块
	 * @param request
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toBaseInfo", method = RequestMethod.GET)
	public String message(HttpServletRequest request) {
		return "seven_report/hologram/baseInfo";
	}
	
	
	
	
	
	
}
