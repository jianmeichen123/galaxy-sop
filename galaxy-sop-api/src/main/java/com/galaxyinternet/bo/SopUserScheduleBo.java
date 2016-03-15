package com.galaxyinternet.bo;

import java.util.Date;

import com.galaxyinternet.model.soptask.SopUserSchedule;

public class SopUserScheduleBo extends SopUserSchedule{

	private static final long serialVersionUID = 1L;
	private String timeTask;// 业务对象中扩展的字段
	
	private Integer count;
	
	private String itemDateStr;
	
    
	
	
	public String getItemDateStr() {
		return itemDateStr;
	}
	public void setItemDateStr(String itemDateStr) {
		this.itemDateStr = itemDateStr;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getTimeTask() {
		return timeTask;
	}
	public void setTimeTask(String timeTask) {
		this.timeTask = timeTask;
	}
	
	
}
