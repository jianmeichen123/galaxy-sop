package com.galaxyinternet.common.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.BuryPointService;
import com.galaxyinternet.utils.HttpUtils;

@Service("com.galaxyinternet.service.BuryPointService")
public class BuryPointServiceImpl implements BuryPointService {
	private String serverUrl; 
	
	private String softWareVesion;
	
	@Override
	public String HttpClientburyPoint(Map<String,String> params,HttpServletRequest request ) {
		String url=serverUrl+"/bury/save";
		String urlEncode="application/json; charset=utf-8";
		params.put("softVersion", softWareVesion);
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		params.put("sessionId", user.getSessionId());
		params.put("userId", user.getId().toString());
			String httpPost = HttpUtils.httpPost(url, params, urlEncode);
		return httpPost;
	}
	@Value("${galaxy.project.bury.endpoint}")
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@Value("${buryPoint.softWare.vesion}")
	public void setSoftWareVersion(String softWareVesion) {
		this.softWareVesion = softWareVesion;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getSoftWareVersion() {
		return softWareVesion;
	}

}
