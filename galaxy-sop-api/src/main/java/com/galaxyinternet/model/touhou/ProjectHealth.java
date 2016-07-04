package com.galaxyinternet.model.touhou;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ProjectHealth extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private Byte healthState;

    private String rematk;

    private Long createdUid;

    private Long updatedUid;



    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Byte getHealthState() {
        return healthState;
    }

    public void setHealthState(Byte healthState) {
        this.healthState = healthState;
    }

    public String getRematk() {
        return rematk;
    }

    public void setRematk(String rematk) {
        this.rematk = rematk == null ? null : rematk.trim();
    }

    public Long getCreatedUid() {
        return createdUid;
    }

    public void setCreatedUid(Long createdUid) {
        this.createdUid = createdUid;
    }

    public Long getUpdatedUid() {
        return updatedUid;
    }

    public void setUpdatedUid(Long updatedUid) {
        this.updatedUid = updatedUid;
    }

}