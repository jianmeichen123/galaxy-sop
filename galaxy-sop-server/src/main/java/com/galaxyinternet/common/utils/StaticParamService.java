package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class StaticParamService {
	private  Map<Long,List<String>> roleMessageType;
	
	public Map<Long, List<String>> getRoleMessageType() {
		return roleMessageType;
	}

	public void setRoleMessageType(Map<Long, List<String>> roleMessageType) {
		this.roleMessageType = roleMessageType;
	}
	
	public static List<String> getRoleTypeList(List<Long> roleIdList,StaticParamService paraService){
		List<String> typelist = null;
		
		Map<Long,List<String>> roleMessageTypeMap = paraService.getRoleMessageType();
		
		if(roleMessageTypeMap != null && !roleMessageTypeMap.isEmpty()){
			Set<String> typeSet = new HashSet<String>();
			if(roleIdList!=null){
				for(Long role:roleIdList){
					if(roleMessageTypeMap.containsKey(role)){
						typelist = roleMessageTypeMap.get(role);
						typeSet.addAll(typelist);
					}
				}
			}
			typelist = new ArrayList<String>(typeSet);
		}
		
		return typelist;
	}
}
