package com.galaxyinternet.model.project;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class InterviewFile extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
    private Long recordId;

    private Long documentId;


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}