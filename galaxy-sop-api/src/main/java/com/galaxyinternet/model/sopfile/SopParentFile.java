package com.galaxyinternet.model.sopfile;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.model.common.PagableRecordEntity;

public class SopParentFile extends PagableRecordEntity  {
	public Long getInterviewRecordId() {
		return interviewRecordId;
	}

	public void setInterviewRecordId(Long interviewRecordId) {
		this.interviewRecordId = interviewRecordId;
	}

	public Long getMeetingRecordId() {
		return meetingRecordId;
	}

	public void setMeetingRecordId(Long meetingRecordId) {
		this.meetingRecordId = meetingRecordId;
	}

	/**
     * 文件大小
     */
    protected Long fileLength;
  
    /**
     * 档案阿里云存储KEY
     */
    protected String fileKey;
 
    /**
     * 档案BackName
     */
    protected String bucketName;
    /**
     * 文档名称
     */
    protected String fileName;
    
    /**
     * 项目ID
     */
    protected Long projectId;
    
    /**
     * 文档后缀
     */
    protected String fileSuffix;
    
    /**
     * 业务分类
     */
    protected String fileWorktype;
    /**
     * 业务分类描述
     */
    protected String fWorktype;
    
    /**
     * 访谈ID
     */
    private Long interviewRecordId;
    
    /**
     * 会有ID
     */
    private Long meetingRecordId;
    
    
    /**
     * 编辑人
     */
    protected Long editUser;

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getFileWorktype() {
		return fileWorktype;
	}

	 public void setFileWorktype(String fileWorktype) {
    	 this.fileWorktype = fileWorktype == null ? null : fileWorktype.trim();
         if(fileWorktype != null){
 			this.fWorktype = DictEnum.fileWorktype.getNameByCode(fileWorktype);
 		}
    }

	public String getfWorktype() {
		return fWorktype;
	}

	public void setfWorktype(String fWorktype) {
		this.fWorktype = fWorktype;
	}

	public Long getEditUser() {
		return editUser;
	}

	public void setEditUser(Long editUser) {
		this.editUser = editUser;
	}
	 
	 
    
    
    
}
