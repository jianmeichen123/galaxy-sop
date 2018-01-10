package com.galaxyinternet.model.message;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class SopMessage extends BaseEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String messageType;
	private Integer relatedType;
	private Integer relatedId;
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
	public Integer getRelatedType()
	{
		return relatedType;
	}
	public void setRelatedType(Integer relatedType)
	{
		this.relatedType = relatedType;
	}
	public Integer getRelatedId()
	{
		return relatedId;
	}
	public void setRelatedId(Integer relatedId)
	{
		this.relatedId = relatedId;
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
