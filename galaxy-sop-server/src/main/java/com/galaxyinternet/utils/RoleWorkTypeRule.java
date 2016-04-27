package com.galaxyinternet.utils;

public class RoleWorkTypeRule {
	/**
	 * 角色ID
	 */
	private long roleId;
	/**
	 * 业务分类
	 */
	private String workType;
	/**
	 * 该角色对该业务分类是否可编辑
	 */
	private String isEdit;
	/**
	 * 该角色对该业务分类是否可编辑
	 */
	private String isShow;

	public RoleWorkTypeRule(long roleId, String workType, String isEdit,String isShow) {
		this.roleId = roleId;
		this.workType = workType;
		this.isEdit = isEdit;
		this.isShow = isShow;
		
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

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	

}