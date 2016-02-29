package com.galaxyinternet.model.soptask;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class SopTask extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     private Long projectId;
     private String taskName;
     private String taskType;
     private String taskOrder;
     private Date taskDeadline;
     private String taskDestination;
     private Long taskReceiveUid;
     private String createUname;
     private String projectName;
     public String getCreateUname() {
		return createUname;
	}
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(String taskOrder) {
		this.taskOrder = taskOrder;
	}
	public Date getTaskDeadline() {
		return taskDeadline;
	}
	public void setTaskDeadline(Date taskDeadline) {
		this.taskDeadline = taskDeadline;
	}
	public String getTaskDestination() {
		return taskDestination;
	}
	public void setTaskDestination(String taskDestination) {
		this.taskDestination = taskDestination;
	}
	public Long getTaskReceiveUid() {
		return taskReceiveUid;
	}
	public void setTaskReceiveUid(Long taskReceiveUid) {
		this.taskReceiveUid = taskReceiveUid;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String taskStatus;
     private String remark;   
    
}
