package com.galaxyinternet.common.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.utils.BuryPointRequest;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.BuryPointService;
import com.galaxyinternet.utils.HttpUtils;

@Service("com.galaxyinternet.service.BuryPointService")
public class BuryPointServiceImpl implements BuryPointService {
	
	
	@Override
	public String HttpClientburyPoint(Map<String,String> params,HttpServletRequest request ) {
		String url="/bury/save";
		String urlEncode="application/json; charset=utf-8";
		params.put("softVersion", "");
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		params.put("sessionId", user.getSessionId());
		params.put("userId", user.getId().toString());
			String httpPost = HttpUtils.httpPost(url, params, urlEncode);
		return httpPost;
	}
}
