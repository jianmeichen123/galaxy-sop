package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;
import java.util.Arrays;

public class ItemParam
{
	private Long relatedId;
	private String[] values;
	private BigDecimal score;
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
	public BigDecimal getScore()
	{
		return score;
	}
	public void setScore(BigDecimal score)
	{
		this.score = score;
	}
	@Override
	public String toString()
	{
		return "ItemParam [relatedId=" + relatedId + ", values=" + Arrays.toString(values) + ", score=" + score + "]";
	}
	
	
}
