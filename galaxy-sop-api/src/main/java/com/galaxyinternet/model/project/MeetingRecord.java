package com.galaxyinternet.model.project;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class MeetingRecord  extends BaseEntity{
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private Date meetingDate;

    private String meetingType;

    private String meetingNotes;


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType == null ? null : meetingType.trim();
    }

    public String getMeetingNotes() {
        return meetingNotes;
    }

    public void setMeetingNotes(String meetingNotes) {
        this.meetingNotes = meetingNotes == null ? null : meetingNotes.trim();
    }

}