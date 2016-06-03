package com.galaxyinternet.model.timer;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class PassRate extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private Double rate;
	private Integer rateType;
	
	private List<String> uids;
	
	
	public List<String> getUids() {
		return uids;
	}
	public void setUids(List<String> uids) {
		this.uids = uids;
	}
	public Integer getRateType() {
		return rateType;
	}
	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
}
