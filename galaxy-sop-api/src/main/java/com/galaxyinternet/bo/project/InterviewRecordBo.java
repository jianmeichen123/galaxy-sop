package com.galaxyinternet.bo.project;

import java.util.List;

import com.galaxyinternet.model.project.InterviewRecord;

public class InterviewRecordBo extends InterviewRecord{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String proName;

	
	private Long uid;
	private String startTime; // yyyy-mm-dd
	private String endTime;
	private String proNameCode;
	private int inpro; //是否项目内查看 1：是
	private List<Long> proIdList;
	
	private Integer pageNum;// 页码，默认是第一页
	private Integer pageSize;// 每页显示的记录数，默认是10
	private String direction;// asc,desc
	private String property;// 排序的字段名称

	private Long departId; //部门id
	
	public Integer getPageNum() {
		if(pageNum == null){
			pageNum = 0;
		}
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getPageSize() {
		if(pageSize == null){
			pageSize = 10;
		}
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


	public int getInpro() {
		return inpro;
	}

	public void setInpro(int inpro) {
		this.inpro = inpro;
	}

	private String fkey;
	private String bucketName;
	private Long fileLength;
	
	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public List<Long> getProIdList() {
		return proIdList;
	}
	public void setProIdList(List<Long> proIdList) {
		this.proIdList = proIdList;
	}
	public Long getDepartId() {
		return departId;
	}
	public void setDepartId(Long departId) {
		this.departId = departId;
	}



    
}