package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;
import java.util.Arrays;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("题目信息")
public class ItemParam
{
	@ApiModelProperty("填写的题目ID(information_title_relate.id)")
	private Long relatedId;
	@ApiModelProperty("题目的值")
	private String[] values;
	@ApiModelProperty("题目的分数")
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
