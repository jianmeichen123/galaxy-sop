package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class ScoreAutoInfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	private Long dictId;
	private BigDecimal grade;
	public Long getDictId()
	{
		return dictId;
	}
	public void setDictId(Long dictId)
	{
		this.dictId = dictId;
	}
	public BigDecimal getGrade()
	{
		return grade;
	}
	public void setGrade(BigDecimal grade)
	{
		this.grade = grade;
	}
	
}
