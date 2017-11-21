package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;

public class ScoreValue
{
	private Long valueId;
	private String value;
	private BigDecimal weight;
	public Long getValueId()
	{
		return valueId;
	}
	public void setValueId(Long valueId)
	{
		this.valueId = valueId;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public BigDecimal getWeight()
	{
		return weight;
	}
	public void setWeight(BigDecimal weight)
	{
		this.weight = weight;
	}

}
