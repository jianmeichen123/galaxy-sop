package com.galaxyinternet.model.soptask;

import java.io.Serializable;

public class TaskParams implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long[] ids;
	private Long fromUserId;
	private Long targetUserId;
	private String reason;
	public Long[] getIds()
	{
		return ids;
	}
	public void setIds(Long[] ids)
	{
		this.ids = ids;
	}
	public Long getFromUserId()
	{
		return fromUserId;
	}
	public void setFromUserId(Long fromUserId)
	{
		this.fromUserId = fromUserId;
	}
	public Long getTargetUserId()
	{
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId)
	{
		this.targetUserId = targetUserId;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	

}
