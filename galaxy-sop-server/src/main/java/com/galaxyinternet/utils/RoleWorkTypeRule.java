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
	
	private String isProveEdit ="false";

	/**
	 * @param roleId   角色ID
	 * @param workType 业务分类
	 * @param isEdit   该角色对该业务分类是否可编辑
	 * @param isShow   该角色对该业务分类是否显示
	 * @param isChangeTask 该角色上传文件时对该业务分类是否改变其任务状态
	 */
	public RoleWorkTypeRule(long roleId, String workType, String isEdit,String isShow,String isChangeTask) {
		this.roleId = roleId;
		this.workType = workType;
		this.isEdit = isEdit;
		this.isShow = isShow;
		this.isChangeTask = isChangeTask;
		
	}
	
	/**
	 * @param roleId   角色ID
	 * @param workType 业务分类
	 * @param isEdit   该角色对该业务分类是否可编辑
	 * @param isShow   该角色对该业务分类是否显示
	 * @param isChangeTask 该角色上传文件时对该业务分类是否改变其任务状态
	 * @param isProveEdit 该角色对该业务分类能否更新签署凭证
	 */
	public RoleWorkTypeRule(long roleId, String workType, String isEdit,String isShow,String isChangeTask,String isProveEdit) {
		this.roleId = roleId;
		this.workType = workType;
		this.isEdit = isEdit;
		this.isShow = isShow;
		this.isChangeTask = isChangeTask;
		this.isProveEdit = isProveEdit;
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

	public String getIsProveEdit() {
		return isProveEdit;
	}

	public void setIsProveEdit(String isProveEdit) {
		this.isProveEdit = isProveEdit;
	}
	
	

}