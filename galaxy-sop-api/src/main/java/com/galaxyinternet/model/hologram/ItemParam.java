package com.galaxyinternet.model.hologram;

public class ItemParam
{
	private Long relatedId;
	private String[] values;
	private Integer score;
	public Long getRelatedId()
	{
		return relatedId;
	}
	public void setRelatedId(Long relatedId)
	{
		this.relatedId = relatedId;
	}
	public String[] getValues()
	{
		return values;
	}
	public void setValues(String[] values)
	{
		this.values = values;
	}
	public Integer getScore()
	{
		return score;
	}
	public void setScore(Integer score)
	{
		this.score = score;
	}
	
}
