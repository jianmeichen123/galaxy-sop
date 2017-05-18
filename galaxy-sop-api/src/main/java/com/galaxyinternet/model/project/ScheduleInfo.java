package com.galaxyinternet.model.project;

import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ScheduleInfo extends PagableEntity{
	
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long parentId;

    private Byte type;

    private Long nameId;

    private String name;

    private Byte projectType;

    private Long projectId;

    private Date startTime;

    private Date endTime;

    private Byte isAllday;

    private Long wakeupId;

    private String remark;

    private Long createdId;

    private Long updatedId;

    private Long createdTime;

    private Long updatedTime;

    private Byte significance;

    private String callonAddress;

    private Byte isDel;

    private Long callonPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getProjectType() {
        return projectType;
    }

    public void setProjectType(Byte projectType) {
        this.projectType = projectType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getIsAllday() {
        return isAllday;
    }

    public void setIsAllday(Byte isAllday) {
        this.isAllday = isAllday;
    }

    public Long getWakeupId() {
        return wakeupId;
    }

    public void setWakeupId(Long wakeupId) {
        this.wakeupId = wakeupId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    public Long getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(Long updatedId) {
        this.updatedId = updatedId;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Byte getSignificance() {
        return significance;
    }

    public void setSignificance(Byte significance) {
        this.significance = significance;
    }

    public String getCallonAddress() {
        return callonAddress;
    }

    public void setCallonAddress(String callonAddress) {
        this.callonAddress = callonAddress == null ? null : callonAddress.trim();
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    public Long getCallonPerson() {
        return callonPerson;
    }

    public void setCallonPerson(Long callonPerson) {
        this.callonPerson = callonPerson;
    }
}