package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.InterviewRecord;

public class InterviewRecordBo extends InterviewRecord{

	private static final long serialVersionUID = 1L;
	
	private String proName;
	private String fkey;
	private String fname;
	
	private Long uid;
	private String startTime;
	private String endTime;
	private String proNameCode;
	private int inpro; //是否项目内查看 1：是
	
	private Integer pageNum;// 页码，默认是第一页
	private Integer pageSize;// 每页显示的记录数，默认是10
	
	private String ftgk;
	

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

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
		if (startTime != null && startTime.length() == 10) {
			startTime = startTime + " 00:00:00";
		}
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		if (endTime != null && endTime.length() == 10) {
			endTime = endTime + " 23:59:59";
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getProNameCode() {
		return proNameCode == null ? null : proNameCode.trim();
	}

	public void setProNameCode(String proNameCode) {
		 this.proNameCode = proNameCode == null ? null : proNameCode.trim();
	}




	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
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

	public String getFtgk() {
		return ftgk;
	}

	public void setFtgk(String ftgk) {
		this.ftgk = ftgk;
	}

	
	

    
}