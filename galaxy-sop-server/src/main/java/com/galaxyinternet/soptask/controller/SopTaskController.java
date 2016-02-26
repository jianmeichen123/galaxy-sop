package com.galaxyinternet.soptask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.SopTaskService;

@Controller
@RequestMapping("/galaxy/soptask")
public class SopTaskController extends BaseControllerImpl<SopTask, SopTaskBo> {
	final Logger logger = LoggerFactory.getLogger(SopTaskController.class);
	@Autowired
	private SopTaskService sopTaskService;
	//@Autowiredo
	//UserRepository userRepository;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<SopTask> getBaseService() {
		return this.sopTaskService;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<User> login() {
		ResponseData<User> responseBody = new ResponseData<User>();
		User user = new User();
		user.setMobile("dwsdsadsadsafsafca");
		user.setRealName("dsdsadsadas");
		//userRepository.add(user);
		cache.set(user.getMobile(), 100, user);
		
		System.out.println(cache.get(user.getMobile()));
		
		return responseBody;
	}
}
