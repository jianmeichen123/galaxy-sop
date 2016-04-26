package com.galaxyinternet.model.project;

import java.util.Date;
import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class MeetingScheduling extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private String meetingType;

    private Integer meetingCount;

    private Date meetingDate;

    private String status;

    private String remark;
    //排序字段名称
    private String sortName;
    //排序方式
    private String sortDirection;
    //过滤字段
    private String filterName;
    
    private  List<Long> deptIdList;
    private List<Long> projectIdList;
  //项目名称
  	private String projectName;
  	//投资事业线
  	private String projectCareerline;
  	//投资经理
    private String createUname;
    private String meetingDateStr; 
    
    //排期开始时间
    private Date scheduleStarttime;
    
    //排期结束时间
    private Date scheduleEndtime;
    //排期状态
    private String scheduleStatus;

	public Date getScheduleStarttime() {
		return scheduleStarttime;
	}

	public void setScheduleStarttime(Date scheduleStarttime) {
		this.scheduleStarttime = scheduleStarttime;
	}

	public Date getScheduleEndtime() {
		return scheduleEndtime;
	}

	public void setScheduleEndtime(Date scheduleEndtime) {
		this.scheduleEndtime = scheduleEndtime;
	}

	public String getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public String getMeetingDateStr() {
		return meetingDateStr;
	}

	public void setMeetingDateStr(String meetingDateStr) {
		this.meetingDateStr = meetingDateStr;
	}

	public String getProjectCareerline() {
  		return projectCareerline;
  	}

  	public void setProjectCareerline(String projectCareerline) {
  		this.projectCareerline = projectCareerline;
  	}

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

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType == null ? null : meetingType.trim();
    }

    public Integer getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount(Integer meetingCount) {
        this.meetingCount = meetingCount;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public List<Long> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<Long> projectIdList) {
		this.projectIdList = projectIdList;
	}
	
}