package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;
import java.util.Arrays;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("题目信息")
public class ItemParam
{
	@ApiModelProperty("填写的题目ID(information_title_relate.id)")
	private Long relateId;
	@ApiModelProperty("subId, 一题多文本")
	private Long subId;
	@ApiModelProperty("题目的值")
	private String[] values;
	@ApiModelProperty("题目的分数")
	private BigDecimal score;
	public Long getRelateId()
	{
		return relateId;
	}
	public void setRelateId(Long relateId)
	{
		this.relateId = relateId;
	}
	public Long getSubId()
	{
		return subId;
	}
	public void setSubId(Long subId)
	{
		this.subId = subId;
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
		return "ItemParam [relateId=" + relateId + ", subId=" + subId + ", values=" + Arrays.toString(values) + ", score=" + score + "]";
	}
	
	public String getKey()
	{
		if(subId != null)
		{
			return relateId+"-"+subId;
		}
		return relateId+"";
	}
	
	
}
