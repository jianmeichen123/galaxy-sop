package com.galaxyinternet.model.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class GradeInfo extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long relateId;
	private Long parentId;
	private Integer reportType;
	private Integer scoreMax;
	private Integer processMode;
	private Integer scoreType;
	private List<GradeAutoInfo> autoList;
	public Long getRelateId()
	{
		return relateId;
	}
	public void setRelateId(Long relateId)
	{
		this.relateId = relateId;
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
	public Integer getProcessMode()
	{
		return processMode;
	}
	public void setProcessMode(Integer processMode)
	{
		this.processMode = processMode;
	}
	public List<GradeAutoInfo> getAutoList()
	{
		return autoList;
	}
	public void setAutoList(List<GradeAutoInfo> autoList)
	{
		this.autoList = autoList;
	}
	public Integer getScoreType()
	{
		return scoreType;
	}
	public void setScoreType(Integer scoreType)
	{
		this.scoreType = scoreType;
	}
	
	
}
