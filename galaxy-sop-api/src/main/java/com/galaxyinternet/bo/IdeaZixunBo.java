package com.galaxyinternet.bo;

import com.galaxyinternet.model.idea.IdeaZixun;

public class IdeaZixunBo extends IdeaZixun {

	private static final long serialVersionUID = 1L;

	private String beginTime;
	private String endTime;
	
	private Long beginTimeLong;
	private Long endTimeLong;
	
	
	private String departName;
	private String userName;
	
	
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getBeginTimeLong() {
		return beginTimeLong;
	}
	public void setBeginTimeLong(Long beginTimeLong) {
		this.beginTimeLong = beginTimeLong;
	}
	public Long getEndTimeLong() {
		return endTimeLong;
	}
	public void setEndTimeLong(Long endTimeLong) {
		this.endTimeLong = endTimeLong;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	
}