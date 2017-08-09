package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;
import java.util.Set;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class InformationScore extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long relateId;
	private Long projectId;
	private BigDecimal score1;
	private BigDecimal score2;
	private Set<Long> ids;
	private Set<Long> relateIds;
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
	public BigDecimal getScore1()
	{
		return score1;
	}
	public void setScore1(BigDecimal score1)
	{
		this.score1 = score1;
	}
	public BigDecimal getScore2()
	{
		return score2;
	}
	public void setScore2(BigDecimal score2)
	{
		this.score2 = score2;
	}
	public Set<Long> getIds()
	{
		return ids;
	}
	public void setIds(Set<Long> ids)
	{
		this.ids = ids;
	}
	public Set<Long> getRelateIds()
	{
		return relateIds;
	}
	public void setRelateIds(Set<Long> relateIds)
	{
		this.relateIds = relateIds;
	}
	
}
