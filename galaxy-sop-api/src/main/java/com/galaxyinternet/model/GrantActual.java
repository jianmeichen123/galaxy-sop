package com.galaxyinternet.model;

import com.alibaba.fastjson.JSON;
import com.galaxyinternet.framework.core.model.PagableEntity;

public class GrantActual extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long partGrantId;
	private Double grantMoney;
	private Long createUid;
	private String createUname;
	
	
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
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
