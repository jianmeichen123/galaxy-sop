package com.galaxyinternet.OperationLogs.component;

import java.util.Map;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.user.User;

public class OperationLogParams
{
	private OperationLogType type;
	private User user;
	private Map<String, Object> params;
	private RecordType recordType;
	public OperationLogType getType()
	{
		return type;
	}
	public void setType(OperationLogType type)
	{
		this.type = type;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public Map<String, Object> getParams()
	{
		return params;
	}
	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}
	public RecordType getRecordType()
	{
		return recordType;
	}
	public void setRecordType(RecordType recordType)
	{
		this.recordType = recordType;
	}
	
	
}
