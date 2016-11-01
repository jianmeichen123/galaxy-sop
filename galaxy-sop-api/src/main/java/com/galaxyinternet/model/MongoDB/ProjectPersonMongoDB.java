package com.galaxyinternet.model.MongoDB;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class ProjectPersonMongoDB extends BaseEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private Long personId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}