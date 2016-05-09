package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.timer.PassRate;

public class PassRateBo extends PassRate{
	
	private static final long serialVersionUID = 1L;
	
	private List<String> uids;
	public List<String> getUids() {
		return uids;
	}
	public void setUids(List<String> uids) {
		this.uids = uids;
	}
	
	
	
	
}
