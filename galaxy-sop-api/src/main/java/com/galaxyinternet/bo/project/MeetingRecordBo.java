package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.MeetingRecord;

public class MeetingRecordBo  extends MeetingRecord{
	private static final long serialVersionUID = 1L;

	private String proName; //项目名称 - 会议记录
	
	private Long uid; //项目创建人
	private String startTime;
	private String endTime;
	private String proNameCode;
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
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
	public String getProNameCode() {
		return proNameCode;
	}
	public void setProNameCode(String proNameCode) {
		this.proNameCode = proNameCode;
	}
	
	
	

}