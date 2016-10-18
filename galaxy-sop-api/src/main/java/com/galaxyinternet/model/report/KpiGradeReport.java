package com.galaxyinternet.model.report;

public class KpiGradeReport extends DataReport {
	
	/**
	 * 事业线ID
	 */
	private String departmentId;
	/**
	 * 事业线名称(1)
	 */
	private String departmentName;
	
	/**
	 * 分数／生成项目(2)
	 */
	private String createProjectSource;
	
	/**
	 * 分数/通过CEO评审(3)
	 */
	private String ceoReviewSource;
	/**
	 * 分数/通过立项会(4)
	 */
	private String projectMettingSource;
	/**
	 * 总分数(5)
	 */
	private String totalSource;
	/**
	 * 过会率/CEO评审(6)
	 */
	private String ceoReviewRate;
	/**
	 * 过会率/立项会(7)
	 */
	private String projectMettingRate;
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getCreateProjectSource() {
		return createProjectSource;
	}
	public void setCreateProjectSource(String createProjectSource) {
		this.createProjectSource = createProjectSource;
	}
	public String getCeoReviewSource() {
		return ceoReviewSource;
	}
	public void setCeoReviewSource(String ceoReviewSource) {
		this.ceoReviewSource = ceoReviewSource;
	}
	public String getProjectMettingSource() {
		return projectMettingSource;
	}
	public void setProjectMettingSource(String projectMettingSource) {
		this.projectMettingSource = projectMettingSource;
	}
	public String getTotalSource() {
		return totalSource;
	}
	public void setTotalSource(String totalSource) {
		this.totalSource = totalSource;
	}
	public String getCeoReviewRate() {
		return ceoReviewRate;
	}
	public void setCeoReviewRate(String ceoReviewRate) {
		this.ceoReviewRate = ceoReviewRate;
	}
	public String getProjectMettingRate() {
		return projectMettingRate;
	}
	public void setProjectMettingRate(String projectMettingRate) {
		this.projectMettingRate = projectMettingRate;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
