package com.galaxyinternet.model.hologram;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("计算参数")
public class ReportParam
{
	@ApiModelProperty(value="当前页(选项卡)对应ID(information_title_relate.id)",required=true)
	private Long relateId;
	@ApiModelProperty(value="项目ID",required=true)
	private Long projectId;
	@ApiModelProperty(value="报告类型 1：评测报告",required=true)
	private Integer reportType;
	@ApiModelProperty(value="当前报告包含的题目信息",name="items")
	private List<ItemParam> items;
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
	public Integer getReportType()
	{
		return reportType;
	}
	public void setReportType(Integer reportType)
	{
		this.reportType = reportType;
	}
	public List<ItemParam> getItems()
	{
		return items;
	}
	public void setItems(List<ItemParam> items)
	{
		this.items = items;
	}
	@Override
	public String toString()
	{
		return "SingleReportParam [relateId=" + relateId + ", projectId=" + projectId + ", reportType=" + reportType + ", items=" + items + "]";
	}
	
	
}
