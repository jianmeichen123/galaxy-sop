package com.galaxyinternet.model;

import com.alibaba.fastjson.JSON;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

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
	
	
	//------bo------
	private String createDateStr;
	public String getCreateDateStr() {
		if(createdTime != null){
			DateUtil.longToString(createdTime, "yyyy年MM月dd日");
		}
		return createDateStr;
	}
	
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
