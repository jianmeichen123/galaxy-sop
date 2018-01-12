package com.galaxyinternet.operationMessage.component;

import java.util.HashMap;
import java.util.Map;

public class MessageResponse
{
	private String status;
	private Map<String, Object> map = new HashMap<>();
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Map<String, Object> getMap()
	{
		return map;
	}
	public void setMap(Map<String, Object> map)
	{
		this.map = map;
	}
	@Override
	public String toString()
	{
		return "MessageResponse [status=" + status + ", map=" + map + "]";
	}
	
	
	
}
