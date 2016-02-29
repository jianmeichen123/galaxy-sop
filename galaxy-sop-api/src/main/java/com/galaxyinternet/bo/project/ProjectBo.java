package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.Project;

public class ProjectBo extends Project {
	private static final long serialVersionUID = 1L;

	private String extendFiled;// 业务对象中扩展的字段
	
	private String nameLike;//模糊查询条件匹配

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	
	
  
}