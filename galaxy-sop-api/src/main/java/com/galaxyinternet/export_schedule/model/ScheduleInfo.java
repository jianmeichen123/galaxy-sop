package com.galaxyinternet.export_schedule.model;

import java.util.Date;
import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ScheduleInfo extends PagableEntity{
	
	private static final long serialVersionUID = 1L;


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
    
    private int isProject;
    
    private long countVisit;
    
    private String fanceStatus;
    
    private double fanceRate;
    
    private List<Long> createtUids;
    
    /**
     * 查询条件-时间类型：1-季度；2-月；3-周
     */
    private Byte periodType;

    
    public double getFanceRate() {
		return fanceRate;
	}

	public void setFanceRate(double fanceRate) {
		this.fanceRate = fanceRate;
	}

	public long getCountVisit() {
		return countVisit;
	}

	public void setCountVisit(long countVisit) {
		this.countVisit = countVisit;
	}

	public String getFanceStatus() {
		return fanceStatus;
	}

	public void setFanceStatus(String fanceStatus) {
		this.fanceStatus = fanceStatus;
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

	public int getIsProject() {
		return isProject;
	}

	public void setIsProject(int isProject) {
		this.isProject = isProject;
	}

	public Byte getPeriodType() {
		return periodType;
	}

	public void setPeriodType(Byte periodType) {
		this.periodType = periodType;
	}

	public List<Long> getCreatetUids() {
		return createtUids;
	}

	public void setCreatetUids(List<Long> createtUids) {
		this.createtUids = createtUids;
	}
    
    
}