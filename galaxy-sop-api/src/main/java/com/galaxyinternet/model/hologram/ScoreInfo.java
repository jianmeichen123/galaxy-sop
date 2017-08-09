package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class ScoreInfo extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pk;
	private Long relateId;
	private Long subId;
	private String code;
	private Long parentId;
	private Long projectId;
	private Integer reportType;
	private BigDecimal scoreMax;
	private Integer processMode;
	private Integer scoreType;
	private Long scoreId;
	private BigDecimal score;
	private List<ScoreAutoInfo> autoList;
	private List<ScoreValue> valueList;
	private Set<Long> ids;
	public String getPk()
	{
		return pk;
	}
	public void setPk(String pk)
	{
		this.pk = pk;
	}
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
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public Long getParentId()
	{
		return parentId;
	}
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
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
	public BigDecimal getScoreMax()
	{
		return scoreMax;
	}
	public void setScoreMax(BigDecimal scoreMax)
	{
		this.scoreMax = scoreMax;
	}
	public Integer getProcessMode()
	{
		return processMode;
	}
	public void setProcessMode(Integer processMode)
	{
		this.processMode = processMode;
	}
	public Integer getScoreType()
	{
		return scoreType;
	}
	public void setScoreType(Integer scoreType)
	{
		this.scoreType = scoreType;
	}
	public Long getScoreId()
	{
		return scoreId;
	}
	public void setScoreId(Long scoreId)
	{
		this.scoreId = scoreId;
	}
	public BigDecimal getScore()
	{
		return score;
	}
	public void setScore(BigDecimal score)
	{
		this.score = score;
	}
	public List<ScoreAutoInfo> getAutoList()
	{
		return autoList;
	}
	public void setAutoList(List<ScoreAutoInfo> autoList)
	{
		this.autoList = autoList;
	}
	public List<ScoreValue> getValueList()
	{
		return valueList;
	}
	public void setValueList(List<ScoreValue> valueList)
	{
		this.valueList = valueList;
	}
	public Set<Long> getIds()
	{
		return ids;
	}
	public void setIds(Set<Long> ids)
	{
		this.ids = ids;
	}
}
