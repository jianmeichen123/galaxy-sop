package com.galaxyinternet.bo.project;

import java.util.List;

import com.galaxyinternet.model.project.PersonPool;

public class PersonPoolBo extends PersonPool{

	private static final long serialVersionUID = 1L;
	
	private Long projectId;
	
	private List<String> ids;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	
}
