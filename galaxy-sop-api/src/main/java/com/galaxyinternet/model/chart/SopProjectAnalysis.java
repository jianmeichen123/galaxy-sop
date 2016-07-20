package com.galaxyinternet.model.chart;

import java.io.Serializable;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class SopProjectAnalysis extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  数据库映射
	 */
	/**
	 * 项目阶段
	 */
	private String projectProgress;
	/**
	 * 阶段项目数量 	
	 */
	private Long projectCount;
	/**
	 * 项目总数量
	 */
	private Long totalCount;
	/**
	 * 阶段项目比率 (程序中计算)
	 */
	private String projectRate;
	
	/**
	 * 项目阶段名称
	 */
	private String projectProgressName;
	
	/**
	 * 排序
	 */
	private int sort;
	

	/**
	 * 以下为查询用
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 *  项目类型
	 */
	private String projectType;
	/**
	 * 项目所属事业线
	 */
	private String projectDepartId;
	public String getProjectProgress() {
		return projectProgress;
	}
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	public Long getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(Long projectCount) {
		this.projectCount = projectCount;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getProjectRate() {
		return projectRate;
	}
	public void setProjectRate(String projectRate) {
		this.projectRate = projectRate;
	}
	public String getProjectProgressName() {
		return projectProgressName;
	}
	public void setProjectProgressName(String projectProgressName) {
		this.projectProgressName = projectProgressName;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectDepartId() {
		return projectDepartId;
	}
	public void setProjectDepartId(String projectDepartId) {
		this.projectDepartId = projectDepartId;
	}

	
	
	
	
	

}
