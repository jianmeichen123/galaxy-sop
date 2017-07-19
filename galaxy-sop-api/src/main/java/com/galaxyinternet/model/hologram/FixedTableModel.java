package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.PagableEntity;

import io.swagger.annotations.ApiModelProperty;

public class FixedTableModel extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String titleId;

    private String rowNo;//行号

    private String colNo;//列号

    private String type;//类型

    private String value;//值
    @ApiModelProperty("值ID(如果有)")
    private Long valueId;

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getColNo() {
		return colNo;
	}

	public void setColNo(String colNo) {
		this.colNo = colNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getValueId()
	{
		return valueId;
	}

	public void setValueId(Long valueId)
	{
		this.valueId = valueId;
	}


	
    
	
	

}
