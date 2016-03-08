package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.MeetingScheduling;

public class MeetingSchedulingBo extends MeetingScheduling {
	private static final long serialVersionUID = 1L;
	private String extendFiled;// 业务对象中扩展的字段
	//项目名称
	private String projectName;
	//投资事业线
	private String projectCareerline;
	//投资经理
    private String createUname;
    
    
	public String getProjectCareerline() {
		return projectCareerline;
	}

	public void setProjectCareerline(String projectCareerline) {
		this.projectCareerline = projectCareerline;
	}

	public String getCreateUname() {
		return createUname;
	}

	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}

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