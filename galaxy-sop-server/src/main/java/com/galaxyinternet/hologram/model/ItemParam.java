package com.galaxyinternet.hologram.model;

public class ItemParam
{
	private Long relatedId;
	private Long projectId;
	private String[] value;
	public Long getRelatedId()
	{
		return relatedId;
	}
	public void setRelatedId(Long relatedId)
	{
		this.relatedId = relatedId;
	}
	public Long getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
	public String[] getValue()
	{
		return value;
	}
	public void setValue(String[] value)
	{
		this.value = value;
	}
}
