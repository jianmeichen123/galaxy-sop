package com.galaxyinternet.model.project;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class InterviewRecord extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
    private Long projectId;

    private Date viewDate;

    private String viewTarget;

    private String viewNotes;


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public String getViewTarget() {
        return viewTarget;
    }

    public void setViewTarget(String viewTarget) {
        this.viewTarget = viewTarget == null ? null : viewTarget.trim();
    }

    public String getViewNotes() {
        return viewNotes;
    }

    public void setViewNotes(String viewNotes) {
        this.viewNotes = viewNotes == null ? null : viewNotes.trim();
    }

}