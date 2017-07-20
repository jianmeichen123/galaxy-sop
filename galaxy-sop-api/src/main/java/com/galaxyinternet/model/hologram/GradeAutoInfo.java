package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class GradeAutoInfo extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dictId;
	private Integer processMode;
	private Integer grade;
	public Long getDictId()
	{
		return dictId;
	}
	public void setDictId(Long dictId)
	{
		this.dictId = dictId;
	}
	public Integer getProcessMode()
	{
		return processMode;
	}
	public void setProcessMode(Integer processMode)
	{
		this.processMode = processMode;
	}
	public Integer getGrade()
	{
		return grade;
	}
	public void setGrade(Integer grade)
	{
		this.grade = grade;
	}
	
}
