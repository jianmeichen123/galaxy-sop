package com.galaxyinternet.model.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class InformationData extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private List<InformationModel> infoModeList;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<InformationModel> getInfoModeList() {
		return infoModeList;
	}

	public void setInfoModeList(List<InformationModel> infoModeList) {
		this.infoModeList = infoModeList;
	}
	
	
	
	

}
