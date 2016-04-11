package com.galaxyinternet.bo.template;

import java.util.List;

import com.galaxyinternet.model.template.SopTemplate;

public class SopTemplateBo extends SopTemplate {
	private static final long serialVersionUID = 1L;
	private Long[] ids;
	private List<String> fileWorktypes;
	private Long[] departmentIds;

	public Long[] getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(Long[] departmentIds) {
		this.departmentIds = departmentIds;
	}

	public List<String>  getFileWorktypes() {
		return fileWorktypes;
	}

	public void setFileWorktypes(List<String>  fileWorktypes) {
		this.fileWorktypes = fileWorktypes;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
}
