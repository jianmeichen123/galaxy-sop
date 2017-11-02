package com.galaxyinternet.common.utils;

import java.util.Date;

import com.galaxyinternet.framework.core.cluster.LeaderSelector;
import com.galaxyinternet.framework.core.id.IdGenerator;

public class BuryPointRequest{
	
	private String buryUrl;
	
	private String softWareVersion;
	
	
	public String getSaveBuryUrl(String url){
		return buryUrl+url;
	};
	
	public String getsoftWareVersion(){
		return softWareVersion;
	};


	public String getBuryUrl() {
		return buryUrl;
	}

	public void setBuryUrl(String buryUrl) {
		this.buryUrl = buryUrl;
	}

	public String getSoftWareVersion() {
		return softWareVersion;
	}

	public void setSoftWareVersion(String softWareVersion) {
		this.softWareVersion = softWareVersion;
	}
	
	public BuryPointRequest(String buryUrl, String softWareVersion)
	{
		super();
		this.buryUrl = buryUrl;
		this.softWareVersion =softWareVersion;
	}

}
