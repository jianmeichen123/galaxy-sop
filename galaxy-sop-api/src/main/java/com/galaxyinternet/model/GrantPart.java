package com.galaxyinternet.model;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class GrantPart extends PagableEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long totalGrantId;
	private String grantName;
	private String grantDetail;
	private Double grantMoney;
	private Long createUid;
	private String createUname;
	
	public Long getTotalGrantId() {
		return totalGrantId;
	}
	public void setTotalGrantId(Long totalGrantId) {
		this.totalGrantId = totalGrantId;
	}
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public String getGrantDetail() {
		return grantDetail;
	}
	public void setGrantDetail(String grantDetail) {
		this.grantDetail = grantDetail;
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
}
