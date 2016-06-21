package com.galaxyinternet.common.utils;

import java.util.List;
import java.util.Map;


public class StaticParamService {
	private Map<String,List<String>> roleMessageType;
	
	//List<String> a = staticParamService.getRoleMessageType().get("DSZ");
	
	public Map<String, List<String>> getRoleMessageType() {
		return roleMessageType;
	}

	public void setRoleMessageType(Map<String, List<String>> roleMessageType) {
		this.roleMessageType = roleMessageType;
	}
	
}
