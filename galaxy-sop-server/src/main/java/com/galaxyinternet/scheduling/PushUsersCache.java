package com.galaxyinternet.scheduling;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PushUsersCache {
	
    public static final Map<String,List<String>> bindMap = new ConcurrentHashMap<String,List<String>>();
	public static void setCache(String key , List<String> value){
		bindMap.put(key, value);
	}
	public static List<String> getCache(String key){
		return bindMap.get(key);
	}
	
}
