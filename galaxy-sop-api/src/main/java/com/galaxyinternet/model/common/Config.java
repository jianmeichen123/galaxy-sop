package com.galaxyinternet.model.common;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class Config extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	
	private String pcode;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
}
