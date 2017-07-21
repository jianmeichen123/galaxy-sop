package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class GradeAutoInfo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	private Long dictId;
	private Integer grade;
	public Long getDictId()
	{
		return dictId;
	}
	public void setDictId(Long dictId)
	{
		this.dictId = dictId;
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
