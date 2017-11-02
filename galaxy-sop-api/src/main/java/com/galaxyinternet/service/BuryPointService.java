package com.galaxyinternet.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BuryPointService {
	
	public String HttpClientburyPoint(Map<String,String> params,HttpServletRequest request);
	
}