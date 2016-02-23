package com.galaxyinternet.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.UserBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.UserService;

@Controller
@RequestMapping("/galaxy/user")
public class UserController extends BaseControllerImpl<User, UserBo> {
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	//@Autowired
	//UserRepository userRepository;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<User> getBaseService() {
		return this.userService;
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<User> login() {
		ResponseData<User> responseBody = new ResponseData<User>();
		User user = new User();
		user.setMobile("15321650029");
		user.setRealName("keifer");
		//userRepository.add(user);
		cache.set(user.getMobile(), 100, user);
		
		System.out.println(cache.get(user.getMobile()));
		
		return responseBody;
	}
}
