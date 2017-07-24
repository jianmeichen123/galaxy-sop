package com.galaxyinternet.model.hologram;

import java.util.List;

public class ReportParam
{
	private Long relateId;
	private Long projectId;
	private int reportType;
	private List<ItemParam> items;
	public Long getRelateId()
	{
		return relateId;
	}
	public void setRelateId(Long relateId)
	{
		this.relateId = relateId;
	}
	public Long getProjectId()
	{
		return projectId;
	}
	public void setProjectId(Long projectId)
	{
		this.projectId = projectId;
	}
	public int getReportType()
	{
		return reportType;
	}
	public void setReportType(int reportType)
	{
		this.reportType = reportType;
	}
	public List<ItemParam> getItems()
	{
		return items;
	}
	public void setItems(List<ItemParam> items)
	{
		this.items = items;
	}
	@Override
	public String toString()
	{
		return "SingleReportParam [relateId=" + relateId + ", projectId=" + projectId + ", reportType=" + reportType + ", items=" + items + "]";
	}
	
	
}
