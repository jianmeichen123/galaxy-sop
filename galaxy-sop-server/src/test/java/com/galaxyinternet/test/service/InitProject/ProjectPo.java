/**
 * 
 */
package com.galaxyinternet.test.service.InitProject;

import java.util.Date;

/**
 * @author zhangchunyuan
 *
 */
public class ProjectPo {
	
	private Date createdTime; //创建时间
	private String projectType;	//项目类型
	private String projectName;	//项目名称
	private String projectProgress;	//项目进程
	private String projectStatus;	//项目状态
	private String createUname;		//投资经理姓名
	private String currencyUnit;	//单位
	private Double projectValuations;	//初始估值 需要根据计划额度和出让股份计算
	private Double projectContribution;		//计划额度
	private Double projectShareRatio;		//出让股份
	private String projectDescribe;		//项目描述
	private String prospectAnalysis;	//竞情分析
	
	private String personName;		//团队成员
	private String personTelephone;	//联系电话
	
	private Date viewDate;	//访谈日期
	private String viewObject;	//访谈对象
	private String viewRecord;	//访谈记录
	
	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}
	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * @return the personTelephone
	 */
	public String getPersonTelephone() {
		return personTelephone;
	}
	/**
	 * @param personTelephone the personTelephone to set
	 */
	public void setPersonTelephone(String personTelephone) {
		this.personTelephone = personTelephone;
	}
	
	/**
	 * @return the viewDate
	 */
	public Date getViewDate() {
		return viewDate;
	}
	/**
	 * @param viewDate the viewDate to set
	 */
	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}
	/**
	 * @return the viewObject
	 */
	public String getViewObject() {
		return viewObject;
	}
	/**
	 * @param viewObject the viewObject to set
	 */
	public void setViewObject(String viewObject) {
		this.viewObject = viewObject;
	}
	/**
	 * @return the viewRecord
	 */
	public String getViewRecord() {
		return viewRecord;
	}
	/**
	 * @param viewRecord the viewRecord to set
	 */
	public void setViewRecord(String viewRecord) {
		this.viewRecord = viewRecord;
	}
	
	
	
	
	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}
	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectProgress
	 */
	public String getProjectProgress() {
		return projectProgress;
	}
	/**
	 * @param projectProgress the projectProgress to set
	 */
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	/**
	 * @return the projectStatus
	 */
	public String getProjectStatus() {
		return projectStatus;
	}
	/**
	 * @param projectStatus the projectStatus to set
	 */
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	/**
	 * @return the createUname
	 */
	public String getCreateUname() {
		return createUname;
	}
	/**
	 * @param createUname the createUname to set
	 */
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	/**
	 * @return the currencyUnit
	 */
	public String getCurrencyUnit() {
		return currencyUnit;
	}
	/**
	 * @param currencyUnit the currencyUnit to set
	 */
	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}
	
	
	/**
	 * @return the projectValuations
	 */
	public Double getProjectValuations() {
		return projectValuations;
	}
	/**
	 * @param projectValuations the projectValuations to set
	 */
	public void setProjectValuations(Double projectValuations) {
		this.projectValuations = projectValuations;
	}
	/**
	 * @return the projectContribution
	 */
	public Double getProjectContribution() {
		return projectContribution;
	}
	/**
	 * @param projectContribution the projectContribution to set
	 */
	public void setProjectContribution(Double projectContribution) {
		this.projectContribution = projectContribution;
	}
	/**
	 * @return the projectShareRatio
	 */
	public Double getProjectShareRatio() {
		return projectShareRatio;
	}
	/**
	 * @param projectShareRatio the projectShareRatio to set
	 */
	public void setProjectShareRatio(Double projectShareRatio) {
		this.projectShareRatio = projectShareRatio;
	}
	/**
	 * @return the projectDescribe
	 */
	public String getProjectDescribe() {
		return projectDescribe;
	}
	/**
	 * @param projectDescribe the projectDescribe to set
	 */
	public void setProjectDescribe(String projectDescribe) {
		this.projectDescribe = projectDescribe;
	}
	/**
	 * @return the prospectAnalysis
	 */
	public String getProspectAnalysis() {
		return prospectAnalysis;
	}
	/**
	 * @param prospectAnalysis the prospectAnalysis to set
	 */
	public void setProspectAnalysis(String prospectAnalysis) {
		this.prospectAnalysis = prospectAnalysis;
	}
	
	
	

}
