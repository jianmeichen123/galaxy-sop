package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class InformationModel extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String projectId;//父id
	
	private String titleId;//题干id（标签）	
	
	private String value;//所对应的值（数据字典id）
	
	private String remark;//所对应的值（备注或者描述）
	
	private String type;//题型（1:文本、2:单选、3:复选、4:级联选择、5:单选带备注、6:复选带备注、7:附件）

	private String titleCode;//题干code
	
	private String valueCode;//值code（如果只文本框，该code为空）
	
	private String subCode;//如果是表格，则有一个表格编号
	
	private String subName;//如果是表格，则有一个表格描述

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	
	
	
	

}
