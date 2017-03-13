package com.galaxyinternet.model.hologram;

import java.util.Set;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class InformationResult extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String titleId;

    private String contentChoose;

    private String contentDescribe;

    private String projectId;

    private String isValid;

    private String createTime;

    private String createId;

    private String updateTime;

    private String updateId;
    
    private Set<String> titleIds;

    public Set<String> getTitleIds()
	{
		return titleIds;
	}

	public void setTitleIds(Set<String> titleIds)
	{
		this.titleIds = titleIds;
	}

	public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId == null ? null : titleId.trim();
    }

    public String getContentChoose() {
        return contentChoose;
    }

    public void setContentChoose(String contentChoose) {
        this.contentChoose = contentChoose == null ? null : contentChoose.trim();
    }

    public String getContentDescribe() {
        return contentDescribe;
    }

    public void setContentDescribe(String contentDescribe) {
        this.contentDescribe = contentDescribe == null ? null : contentDescribe.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId == null ? null : createId.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId == null ? null : updateId.trim();
    }
}