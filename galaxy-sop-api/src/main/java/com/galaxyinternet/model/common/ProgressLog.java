package com.galaxyinternet.model.common;

public class ProgressLog extends PagableRecordEntity {
	private static final long serialVersionUID = 1L;

	private Long uid;
	private String uname;
	private Long userRoleid;
	private String userRole;
	private Long userDepartid;
	private String departName;
	private String operationType;
	private String operationTypeStr;
	private Long relatedId;
	private String relatedName;
	private String operationContent;
	private String operationContentStr;
	private String sopstage;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Long getUserRoleid() {
		return userRoleid;
	}
	public void setUserRoleid(Long userRoleid) {
		this.userRoleid = userRoleid;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Long getUserDepartid() {
		return userDepartid;
	}
	public void setUserDepartid(Long userDepartid) {
		this.userDepartid = userDepartid;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationTypeStr() {
		return operationTypeStr;
	}
	public void setOperationTypeStr(String operationTypeStr) {
		this.operationTypeStr = operationTypeStr;
	}
	public Long getRelatedId() {
		return relatedId;
	}
	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}
	public String getRelatedName() {
		return relatedName;
	}
	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	public String getOperationContentStr() {
		return operationContentStr;
	}
	public void setOperationContentStr(String operationContentStr) {
		this.operationContentStr = operationContentStr;
	}
	public String getSopstage() {
		return sopstage;
	}
	public void setSopstage(String sopstage) {
		this.sopstage = sopstage;
	}
}
