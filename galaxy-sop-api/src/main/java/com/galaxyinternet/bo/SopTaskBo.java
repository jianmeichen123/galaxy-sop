package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.framework.core.model.Pagable;
import com.galaxyinternet.model.soptask.SopTask;

public class SopTaskBo extends  SopTask implements Pagable{

	private static final long serialVersionUID = 1L;
	private String createUname;// 业务对象中扩展的字段
	private String projectName;//业务扩展字段---项目名称
	private List<String>  ids;//业务扩展字段---项目ids
	private String taskDeadlineformat;
	private String caozuo;
	private List<String> taskStatusList; //任务状态  完成、待完成
	private String nameLike;
	private String statusFlag;
	private String caozuohtml;
	private int hours;
	private String orderRemark;
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

	public List<String> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<String> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

	public String getTaskDeadlineformat() {
		return taskDeadlineformat;
	}

	public void setTaskDeadlineformat(String taskDeadlineformat) {
		this.taskDeadlineformat = taskDeadlineformat;
	}

	public String getCaozuo() {
		return caozuo;
	}

	public void setCaozuo(String caozuo) {
		this.caozuo = caozuo;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getCaozuohtml() {
		return caozuohtml;
	}
	
	public void setCaozuohtml(String caozuohtml) {
		this.caozuohtml = caozuohtml;
	}

	public int getHours() {
		return hours;
	}
	
	protected Integer pageSize;
	protected Integer pageNum;

	@Override
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public Integer getPageSize() {
		return this.pageSize;
	}

	@Override
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;

	}

	@Override
	public Integer getPageNum() {
		return this.pageNum;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	
}
