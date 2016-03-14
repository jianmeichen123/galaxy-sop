package com.galaxyinternet.model.project;

import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class MeetingScheduling extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private String meetingType;

    private Integer meetingCount;

    private Date meetingDate;

    private String status;

    private String remark;
    
  //项目名称
  	private String projectName;
  	//投资事业线
  	private String projectCareerline;
  	//投资经理
      private String createUname;
      
      
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

}