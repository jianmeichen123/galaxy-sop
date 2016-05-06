package com.galaxyinternet.model.timer;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class PassRate extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	private Double rate;
	
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
