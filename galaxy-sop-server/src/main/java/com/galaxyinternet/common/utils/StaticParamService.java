package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


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
		
		if(roleMessageTypeMap != null && !roleMessageTypeMap.isEmpty() && roleIdList!=null){
			
			if(roleIdList.size()==1){
				typelist = roleMessageTypeMap.get(roleIdList.get(0));
			}else{
				Set<String> typeSet = new TreeSet<String>();
				for(Long role:roleIdList){
					if(roleMessageTypeMap.containsKey(role)){
						typelist = roleMessageTypeMap.get(role);
						typeSet.addAll(typelist);
					}
				}
				typelist = new ArrayList<String>(typeSet);
			}
		}
		
		return typelist;
	}
}
