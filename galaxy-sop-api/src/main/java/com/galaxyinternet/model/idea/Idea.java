package com.galaxyinternet.model.idea;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class Idea extends PagableEntity 
{
	private static final long serialVersionUID = 1L;
	
	private String ideaName;
	private String ideaCode;
	private Long departmentId;
	private Long createdUid;
	private Long updatedUid;
	private String ideaProgress;
	private String ideaDesc;
	private String ideaSource;
	private Long projectId;
	private Long claimantUid;
	
	private String departmentDesc;
	private String createdUname;
	private String ideaProgressDesc;
	private String peojectName;
	private String claimantUname;
	public String getIdeaName() {
		return ideaName;
	}
	public void setIdeaName(String ideaName) {
		this.ideaName = ideaName;
	}
	public String getIdeaCode() {
		return ideaCode;
	}
	public void setIdeaCode(String ideaCode) {
		this.ideaCode = ideaCode;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getCreatedUid() {
		return createdUid;
	}
	public void setCreatedUid(Long createdUid) {
		this.createdUid = createdUid;
	}
	public Long getUpdatedUid() {
		return updatedUid;
	}
	public void setUpdatedUid(Long updatedUid) {
		this.updatedUid = updatedUid;
	}
	public String getIdeaProgress() {
		return ideaProgress;
	}
	public void setIdeaProgress(String ideaProgress) {
		this.ideaProgress = ideaProgress;
	}
	public String getIdeaDesc() {
		return ideaDesc;
	}
	public void setIdeaDesc(String ideaDesc) {
		this.ideaDesc = ideaDesc;
	}
	public String getIdeaSource() {
		return ideaSource;
	}
	public void setIdeaSource(String ideaSource) {
		this.ideaSource = ideaSource;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getClaimantUid() {
		return claimantUid;
	}
	public void setClaimantUid(Long claimantUid) {
		this.claimantUid = claimantUid;
	}
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	public String getCreatedUname() {
		return createdUname;
	}
	public void setCreatedUname(String createdUname) {
		this.createdUname = createdUname;
	}
	public String getIdeaProgressDesc() {
		return ideaProgressDesc;
	}
	public void setIdeaProgressDesc(String ideaProgressDesc) {
		this.ideaProgressDesc = ideaProgressDesc;
	}
	public String getPeojectName() {
		return peojectName;
	}
	public void setPeojectName(String peojectName) {
		this.peojectName = peojectName;
	}
	public String getClaimantUname() {
		return claimantUname;
	}
	public void setClaimantUname(String claimantUname) {
		this.claimantUname = claimantUname;
	}
	
	
}
