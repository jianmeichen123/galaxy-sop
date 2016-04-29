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
	 * 该角色对该业务分类是否显示
	 */
	private String isShow;
	
	/**
	 * 该角色上传文件时对该业务分类是否改变其任务状态
	 */
	private String isChangeTask;

	public RoleWorkTypeRule(long roleId, String workType, String isEdit,String isShow,String isChangeTask) {
		this.roleId = roleId;
		this.workType = workType;
		this.isEdit = isEdit;
		this.isShow = isShow;
		this.isChangeTask = isChangeTask;
		
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

	public String getIsChangeTask() {
		return isChangeTask;
	}

	public void setIsChangeTask(String isChangeTask) {
		this.isChangeTask = isChangeTask;
	}
	
	

}