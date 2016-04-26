package com.galaxyinternet.model.message;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class NewMessage  extends PagableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String userName;
	
	private String userRole;
	
	private String operationType;
	
	private Long projectId;
	
	private String projectName;
	
	private String operationContent;
	
	private String careerLine;
	
	private String createDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public String getCareerLine() {
		return careerLine;
	}

	public void setCareerLine(String careerLine) {
		this.careerLine = careerLine;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	 @Override
	    public void setCreatedTime(Long createdTime) {
	    	this.createdTime = createdTime;
	    	if(createdTime != null){
	    		this.createDate = DateUtil.longToString(createdTime);
	    	}
	    }



	
	

}
