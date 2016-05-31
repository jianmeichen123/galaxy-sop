package com.galaxyinternet.model.project;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class AppCounts extends PagableEntity{
	private static final long serialVersionUID = 1L;
	
	private Integer projectCounts; //项目总数
	private String projectValuations; //项目总估值
	private Integer Jzproject;
	private Integer Thproject;
	public Integer getProjectCounts() {
		return projectCounts;
	}
	public void setProjectCounts(Integer projectCounts) {
		this.projectCounts = projectCounts;
	}
	public String getProjectValuations() {
		return projectValuations;
	}
	public void setProjectValuations(String projectValuations) {
		this.projectValuations = projectValuations;
	}
	public Integer getJzproject() {
		return Jzproject;
	}
	public void setJzproject(Integer jzproject) {
		Jzproject = jzproject;
	}
	public Integer getThproject() {
		return Thproject;
	}
	public void setThproject(Integer thproject) {
		Thproject = thproject;
	}
	
	
	
	
    
}