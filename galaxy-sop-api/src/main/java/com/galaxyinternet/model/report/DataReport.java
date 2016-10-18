package com.galaxyinternet.model.report;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class DataReport extends PagableEntity {
	
	private static final long serialVersionUID = 1L;
	protected String startTime;
	protected String endTime;
	protected String createUname;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateUname() {
		return createUname;
	}
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	
	
}
