package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.model.idea.ZixunFinance;

public class IdeaZixunBo extends IdeaZixun {

	private static final long serialVersionUID = 1L;

	private String startTime;
	private String endTime;
	
	private Long beginTimeLong;
	private Long endTimeLong;
	
	
	private String departName;
	private String userName;
	
	private int canEdit;
	
	private List<ZixunFinance> finaceList;
	
	
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public List<ZixunFinance> getFinaceList() {
		return finaceList;
	}
	public void setFinaceList(List<ZixunFinance> finaceList) {
		this.finaceList = finaceList;
	}
	public int getCanEdit() {
		return canEdit;
	}
	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}
	
	
	
	
	
}