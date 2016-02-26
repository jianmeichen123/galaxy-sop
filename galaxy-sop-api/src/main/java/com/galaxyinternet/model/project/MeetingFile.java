package com.galaxyinternet.model.project;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class MeetingFile extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	

    private Long meetingId;

    private Long documentId;


    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}