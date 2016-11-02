package com.galaxyinternet.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.project.ProjectShares;

@Document(collection="galaxy.sop.project")
public class Project {
	
	@Id
	private String id;
	/*唯一编码*/
	private String uuid;
	/*项目的创建人ID*/
	private Long uid;
	/*项目的状态*/
	private int status;
	/*项目名称*/
	private String pn;
	/*项目关联的股权结构*/
	private List<ProjectShares> psc;
	/*团队成员的学习经历*/
	private List<PersonLearn> plc;
	
	private String projectType;
	private String projectName;
	private String createDate;
	private Long industryOwn;
	private String financeStatus;
	
	private Integer faFlag;
	private String faName;
	
	private String remark;
	
	private String formatContribution;
	
	private String formatValuations;
	
	private String formatShareRatio;
	
	
	
	public String getId() {
		return id;
	}
	public String getUuid() {
		return uuid;
	}
	public Long getUid() {
		return uid;
	}
	public int getStatus() {
		return status;
	}
	public String getPn() {
		return pn;
	}
	public List<ProjectShares> getPsc() {
		return psc;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public void setPsc(List<ProjectShares> psc) {
		this.psc = psc;
	}
	public List<PersonLearn> getPlc() {
		return plc;
	}
	public void setPlc(List<PersonLearn> plc) {
		this.plc = plc;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getIndustryOwn() {
		return industryOwn;
	}
	public void setIndustryOwn(Long industryOwn) {
		this.industryOwn = industryOwn;
	}
	public String getFinanceStatus() {
		return financeStatus;
	}
	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}
	public Integer getFaFlag() {
		return faFlag;
	}
	public void setFaFlag(Integer faFlag) {
		this.faFlag = faFlag;
	}
	public String getFaName() {
		return faName;
	}
	public void setFaName(String faName) {
		this.faName = faName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFormatContribution() {
		return formatContribution;
	}
	public void setFormatContribution(String formatContribution) {
		this.formatContribution = formatContribution;
	}
	public String getFormatValuations() {
		return formatValuations;
	}
	public void setFormatValuations(String formatValuations) {
		this.formatValuations = formatValuations;
	}
	public String getFormatShareRatio() {
		return formatShareRatio;
	}
	public void setFormatShareRatio(String formatShareRatio) {
		this.formatShareRatio = formatShareRatio;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
}
