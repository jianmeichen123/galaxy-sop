package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.MeetingScheduling;

public class MeetingSchedulingBo extends MeetingScheduling {
	private static final long serialVersionUID = 1L;
	private String extendFiled;// 业务对象中扩展的字段
	//项目名称
	private String projectName;
	
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

}