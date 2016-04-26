package com.galaxyinternet.model.soptask;

import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class SopTask extends PagableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	 private Long id;
     private Long projectId;
     private String taskName;
     private String taskType;
     private Integer taskFlag;
     private Integer taskOrder;
     private Date taskDeadline;
     private Long departmentId;
     private Long assignUid;
     
     
     
     
     
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
	public Integer getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Integer taskFlag) {
		this.taskFlag = taskFlag;
	}
	public Integer getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(Integer taskOrder) {
		this.taskOrder = taskOrder;
	}
	public Date getTaskDeadline() {
		return taskDeadline;
	}
	public void setTaskDeadline(Date taskDeadline) {
		this.taskDeadline = taskDeadline;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getAssignUid() {
		return assignUid;
	}
	public void setAssignUid(Long assignUid) {
		this.assignUid = assignUid;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}   
    
}
