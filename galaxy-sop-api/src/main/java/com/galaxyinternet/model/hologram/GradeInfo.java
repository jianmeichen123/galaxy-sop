package com.galaxyinternet.model.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class GradeInfo extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long relatedId;
	private Long parentId;
	private Integer reportType;
	private Integer scoreMax;
	private List<GradeAutoInfo> autoList;
	public Long getRelatedId()
	{
		return relatedId;
	}
	public void setRelatedId(Long relatedId)
	{
		this.relatedId = relatedId;
	}
	public Long getParentId()
	{
		return parentId;
	}
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}
	public Integer getReportType()
	{
		return reportType;
	}
	public void setReportType(Integer reportType)
	{
		this.reportType = reportType;
	}
	public Integer getScoreMax()
	{
		return scoreMax;
	}
	public void setScoreMax(Integer scoreMax)
	{
		this.scoreMax = scoreMax;
	}
	public List<GradeAutoInfo> getAutoList()
	{
		return autoList;
	}
	public void setAutoList(List<GradeAutoInfo> autoList)
	{
		this.autoList = autoList;
	} 
	
	
	
	
}
