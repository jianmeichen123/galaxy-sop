package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.MeetingRecord;

public class MeetingRecordBo  extends MeetingRecord{
	private static final long serialVersionUID = 1L;

	private String proName; //项目名称 - 会议记录
	private String furi;
	private String fname;
	
	private Long uid; //项目创建人
	private String startTime;
	private String endTime;
	private String proNameCode;
	private int inpro; //是否项目内查看 1：是
	
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
		if (startTime != null && startTime.length() == 10) {
			startTime = startTime + " 00:00:00";
		}
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		if (endTime != null && endTime.length() == 10) {
			endTime = endTime + " 23:59:59";
		}
		this.endTime = endTime;
	}
	public String getProNameCode() {
		return proNameCode;
	}
	public void setProNameCode(String proNameCode) {
		this.proNameCode = proNameCode;
	}
	public String getFuri() {
		return furi;
	}
	public void setFuri(String furi) {
		this.furi = furi;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public int getInpro() {
		return inpro;
	}
	public void setInpro(int inpro) {
		this.inpro = inpro;
	}
	

}