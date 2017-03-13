package com.galaxyinternet.model.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class InformationData extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private List<InformationModel> infoModeList;
	
	private List<FixedTableModel> infoFixedTableList;
	
	private List<TableModel>     infoTableModelList;
	
	

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

	public List<FixedTableModel> getInfoFixedTableList() {
		return infoFixedTableList;
	}

	public void setInfoFixedTableList(List<FixedTableModel> infoFixedTableList) {
		this.infoFixedTableList = infoFixedTableList;
	}

	public List<TableModel> getInfoTableModelList() {
		return infoTableModelList;
	}

	public void setInfoTableModelList(List<TableModel> infoTableModelList) {
		this.infoTableModelList = infoTableModelList;
	}
	
	

}
