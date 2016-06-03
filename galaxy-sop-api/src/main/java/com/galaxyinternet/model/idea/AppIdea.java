package com.galaxyinternet.model.idea;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class AppIdea extends PagableEntity 
{
	private static final long serialVersionUID = 1L;
/*	@NotEmpty(message="创意名称不能为空")
	private String ideaName;
	@NotEmpty(message="创意编码不能为空")*/
	private String ideaProgresss;  //创意阶段
	private String ipCode;			//创意阶段code
	private String projectName;     // 项目名
	private String projectProgress; // 项目阶段
	private String projectCode; //项目阶段的code
	private String projectDtName;//项目动态操作人
	private String dTime;//操作时间
	private String dtWork;//操作事项
	private String dtcaoZuo;//操作是什么
	private String meetResult; // 会议结果
	private String meetCode;//会议结果的code
	private String meetTime;// 会议时间
	private String meetNote;//会议纪要
	private String fileName;//文件名
	private String personName; //上传人
	private String fileTime;//文件上传时间
	private String fileKey;//文件key
	private String projectId;//项目id
	
	
	
	public String getIdeaProgresss() {
		return ideaProgresss;
	}
	public void setIdeaProgresss(String ideaProgresss) {
		this.ideaProgresss = ideaProgresss;
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectProgress() {
		return projectProgress;
	}
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getMeetResult() {
		return meetResult;
	}
	public void setMeetResult(String meetResult) {
		this.meetResult = meetResult;
	}
	public String getMeetTime() {
		return meetTime;
	}
	public void setMeetTime(String meetTime) {
		this.meetTime = meetTime;
	}
	public String getMeetNote() {
		return meetNote;
	}
	public void setMeetNote(String meetNote) {
		this.meetNote = meetNote;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileTime() {
		return fileTime;
	}
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getIpCode() {
		return ipCode;
	}
	public void setIpCode(String ipCode) {
		this.ipCode = ipCode;
	}
	public String getMeetCode() {
		return meetCode;
	}
	public void setMeetCode(String meetCode) {
		this.meetCode = meetCode;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getProjectDtName() {
		return projectDtName;
	}
	public void setProjectDtName(String projectDtName) {
		this.projectDtName = projectDtName;
	}
	public String getdTime() {
		return dTime;
	}
	public void setdTime(String dTime) {
		this.dTime = dTime;
	}
	public String getDtWork() {
		return dtWork;
	}
	public void setDtWork(String dtWork) {
		this.dtWork = dtWork;
	}
	public String getDtcaoZuo() {
		return dtcaoZuo;
	}
	public void setDtcaoZuo(String dtcaoZuo) {
		this.dtcaoZuo = dtcaoZuo;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	
	
	

	
}
