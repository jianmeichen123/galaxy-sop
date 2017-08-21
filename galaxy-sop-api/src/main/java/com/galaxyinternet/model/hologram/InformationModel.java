package com.galaxyinternet.model.hologram;

import java.math.BigDecimal;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.GSONUtil;

public class InformationModel extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String projectId;//项目id
	
	private String titleId;//题干id（标签）
	
	private String resultId;//information_result 结果表id
	
	private String value;//所对应的值（数据字典id）
	
	private String remark1;//所对应的值（备注或者描述）
	
	private String remark2;
	
	private String type;//题型（1:文本、2:单选、3:复选、4:级联选择、5:单选带备注、6:复选带备注、7:附件）

	private String titleCode;//题干code
	
	private String tochange; // 前端标识，是否需要操作该条数据
	
	private BigDecimal score1;
	private BigDecimal score2;
	
	private Long reportType;
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	
	public String getTochange() {
		return tochange;
	}

	public void setTochange(String tochange) {
		this.tochange = tochange;
	}

	
	public BigDecimal getScore1()
	{
		return score1;
	}

	public void setScore1(BigDecimal score1)
	{
		this.score1 = score1;
	}

	public BigDecimal getScore2()
	{
		return score2;
	}

	public void setScore2(BigDecimal score2)
	{
		this.score2 = score2;
	}
	

	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}

	
	
	

}
