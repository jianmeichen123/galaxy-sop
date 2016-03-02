package com.galaxyinternet.menu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.MenusBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.user.Menus;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/common")
public class CommonController extends BaseControllerImpl<Menus, MenusBo> {
	
	final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@ResponseBody
	@RequestMapping(value = "/menu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Menus> menu(HttpServletRequest request){
			ResponseData<Menus> responseBody = new ResponseData<Menus>();	
			
			//获取项目根路径
			String p = request.getRequestURI();
			String url = request.getRequestURL().toString();
			String contextPath = request.getContextPath();
			String host = request.getRemoteHost();
			int port = request.getRemotePort();
			String u = null;
			if(contextPath == null || "".equals(contextPath.trim())){
			}else{
				u = url.substring(0, url.indexOf(contextPath) + contextPath.length() + 1);
			}
			
			Object obj = request.getSession().getAttribute(Constants.SESSION_ID_KEY);
			if(obj == null){
				responseBody.setResult(new Result(Status.ERROR, "未登录!"));
				return responseBody;
			}
			User user = (User) obj;
			
			
			
			List<Menus> tabs = new ArrayList<Menus>();
			tabs.add(new Menus("工作界面",""));
			
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.HHR) || roleIdList.contains(UserConstant.TZJL)){
				
			}
			
		    responseBody.setEntityList(tabs);	
			return responseBody;
	}

	@Override
	protected BaseService<Menus> getBaseService() {
	
		return null;
	}

}
