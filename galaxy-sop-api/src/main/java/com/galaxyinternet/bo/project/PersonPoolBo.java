package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.PersonPool;

public class PersonPoolBo extends PersonPool{

	private static final long serialVersionUID = 1L;
	
	private Long projectId;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
