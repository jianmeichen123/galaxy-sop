package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.soptask.SopTask;

public class SopTaskBo extends SopTask {

	private static final long serialVersionUID = 1L;
	private String createUname;// 业务对象中扩展的字段
	private String projectName;//业务扩展字段---项目名称
	private List<String>  ids;//业务扩展字段---项目ids

	public String getCreateUname() {
		return createUname;
	}

	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
