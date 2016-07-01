package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class StaticParamService {
	private  Map<Long,Map<String,List<String>>> roleMessageType;
	
	public Map<Long,Map<String,List<String>>> getRoleMessageType() {
		return roleMessageType;
	}

	public void setRoleMessageType(Map<Long,Map<String,List<String>>> roleMessageType) {
		this.roleMessageType = roleMessageType;
	}
	
	public static Map<String,List<String>> getRoleTypeList(List<Long> roleIdList,StaticParamService paraService){
		Map<String,List<String>> typelist = null;
		
		Map<Long,Map<String,List<String>>> roleMessageTypeMap = paraService.getRoleMessageType();
		
		if(roleMessageTypeMap != null && !roleMessageTypeMap.isEmpty() && roleIdList!=null){
			
			if(roleIdList.size()==1){
				typelist = roleMessageTypeMap.get(roleIdList.get(0));
			}else{
				
				Set<String> typeSetInAll = new TreeSet<String>();
				Set<String> typeSetInPer = new TreeSet<String>();
				Set<String> typeSetInPat = new TreeSet<String>();
				
				for(Long role:roleIdList){
					if(roleMessageTypeMap.containsKey(role)){
						typelist = roleMessageTypeMap.get(role);
						
						if(typelist.get("inAll")!= null && !typelist.get("inAll").isEmpty()){
							typeSetInAll.addAll(typelist.get("inAll"));
						}
						if(typelist.get("inPer")!= null && !typelist.get("inPer").isEmpty()){
							typeSetInPer.addAll(typelist.get("inPer"));
						}
						if(typelist.get("inPat")!= null && !typelist.get("inPat").isEmpty()){
							typeSetInPat.addAll(typelist.get("inPat"));
						}
					}
				}
				typelist.put("inAll", new ArrayList<String>(typeSetInAll));
				typelist.put("inPer", new ArrayList<String>(typeSetInPer));
				typelist.put("inPat", new ArrayList<String>(typeSetInPat));
			}
		}
		return typelist;
	}
}
