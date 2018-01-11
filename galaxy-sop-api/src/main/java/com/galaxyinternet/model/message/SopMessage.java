package com.galaxyinternet.model.message;

import com.galaxyinternet.model.common.RecordEntity;

public class SopMessage extends RecordEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String messageType;
	private Long createdId;
	private Long updatedId;
	private Integer isPublished = 0;
	private Long publishedTime;
	private Integer retryTimes = 0;
	private Integer isValid = 1;
	private Integer isDeleted = 0;
	public String getMessageType()
	{
		return messageType;
	}
	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}
	public Long getCreatedId()
	{
		return createdId;
	}
	public void setCreatedId(Long createdId)
	{
		this.createdId = createdId;
	}
	public Long getUpdatedId()
	{
		return updatedId;
	}
	public void setUpdatedId(Long updatedId)
	{
		this.updatedId = updatedId;
	}
	public Integer getIsPublished()
	{
		return isPublished;
	}
	public void setIsPublished(Integer isPublished)
	{
		this.isPublished = isPublished;
	}
	public Long getPublishedTime()
	{
		return publishedTime;
	}
	public void setPublishedTime(Long publishedTime)
	{
		this.publishedTime = publishedTime;
	}
	public Integer getRetryTimes()
	{
		return retryTimes;
	}
	public void setRetryTimes(Integer retryTimes)
	{
		this.retryTimes = retryTimes;
	}
	public Integer getIsValid()
	{
		return isValid;
	}
	public void setIsValid(Integer isValid)
	{
		this.isValid = isValid;
	}
	public Integer getIsDeleted()
	{
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted)
	{
		this.isDeleted = isDeleted;
	}
	
}
