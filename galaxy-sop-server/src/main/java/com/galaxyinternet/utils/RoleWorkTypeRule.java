package com.galaxyinternet.utils;

public class RoleWorkTypeRule {
	private long roleId;
	private String workType;
	private String isEdit;

	public RoleWorkTypeRule(long roleId, String workType, String isEdit) {
		this.roleId = roleId;
		this.workType = workType;
		this.isEdit = isEdit;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

}