package com.galaxyinternet.model.touhou;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ProjectHealth extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private Byte healthState;
    private String healthStateStr;

    private String rematk;

    private String userName;
    
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
    	if(healthState!=null){
			if(healthState == 1){
				healthStateStr = "高于预期";
			}else if(healthState == 2){
				healthStateStr = "正常";
			}else if(healthState == 3){
				healthStateStr = "健康预警";
			}
		}
        this.healthState = healthState;
    }

    public String getRematk() {
        return rematk;
    }

    public void setRematk(String rematk) {
        this.rematk = rematk == null ? null : rematk.trim();
    }


    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getHealthStateStr() {
		return healthStateStr;
	}

	public void setHealthStateStr(String healthStateStr) {
		this.healthStateStr = healthStateStr;
	}

}