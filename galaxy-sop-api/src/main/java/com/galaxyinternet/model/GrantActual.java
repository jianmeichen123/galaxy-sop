package com.galaxyinternet.model;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.sopfile.SopFile;

public class GrantActual extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long partGrantId;
	private Double grantMoney;
	private Long createUid;
	private String createUname;
	
	private String actualTime;
	
	public Long getPartGrantId() {
		return partGrantId;
	}
	public void setPartGrantId(Long partGrantId) {
		this.partGrantId = partGrantId;
	}
	public Double getGrantMoney() {
		return grantMoney;
	}
	public void setGrantMoney(Double grantMoney) {
		this.grantMoney = grantMoney;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public String getCreateUname() {
		return createUname;
	}
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	
	
	public String getActualTime() {
		return actualTime;
	}
	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}


	//以下为显示字段
	/**
	 * 协议名称(totalGrant  grantName)
	 */
	private String protocolName;
	
	/**
	 * 计划注资时间(grantPart grantDetail)
	 */
	private String planGrantTime;
	
	/**
	 * 计划注资金额(grantPart grantMoney)
	 */
	private Double planGrantMoney;
	
	/**
	 * 剩余注资金额
	 */
	private Double surplusGrantMoney;
	
  	

	/**
	 * 投资方    总投资计划 granttotal - investors
	 */
	private String investors;
	/**
	 * 目标公司       project - projectCompany
	 */
	private String projectCompany;
	
	/**
	 * 实际投资     
	 */
	private Double finalContribution;
	/**
	 * 股权占比                   
	 */
	private Double finalShareRatio;
	/**
	 * 加速服务费占比   
	 */
	private Double serviceCharge;
	/**
	 * 实际估值
	 */
	private Double finalValuations;
	
	//多附件
	private List<SopFile> files; 
    private List<Long> fileIds;
	
    
	public String getProtocolName() {
		return protocolName;
	}
	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	public String getPlanGrantTime() {
		return planGrantTime;
	}
	public void setPlanGrantTime(String planGrantTime) {
		this.planGrantTime = planGrantTime;
	}
	public Double getPlanGrantMoney() {
		return planGrantMoney;
	}
	public void setPlanGrantMoney(Double planGrantMoney) {
		this.planGrantMoney = planGrantMoney;
	}
	public Double getSurplusGrantMoney() {
		return surplusGrantMoney;
	}
	public void setSurplusGrantMoney(Double surplusGrantMoney) {
		this.surplusGrantMoney = surplusGrantMoney;
	}
	
	
	public String getInvestors() {
		return investors;
	}
	public void setInvestors(String investors) {
		this.investors = investors;
	}
	public String getProjectCompany() {
		return projectCompany;
	}
	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}
	public Double getFinalContribution() {
		return finalContribution;
	}
	public void setFinalContribution(Double finalContribution) {
		this.finalContribution = finalContribution;
	}
	public Double getFinalShareRatio() {
		return finalShareRatio;
	}
	public void setFinalShareRatio(Double finalShareRatio) {
		this.finalShareRatio = finalShareRatio;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Double getFinalValuations() {
		return finalValuations;
	}
	public void setFinalValuations(Double finalValuations) {
		this.finalValuations = finalValuations;
	}
	public List<SopFile> getFiles() {
		return files;
	}
	public void setFiles(List<SopFile> files) {
		this.files = files;
	}
	public List<Long> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
