package com.galaxyinternet.common;

import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.model.operationLog.UrlNumber;

public class SopResult extends Result{

	private static final long serialVersionUID = 1L;
	
	private UrlNumber number;
	
	public SopResult(){}
	public SopResult(Status status, Object message){
		super(status, message);
	}
	public SopResult(Status status, String errorCode, Object message){
		super(status, errorCode, message);
	}
	public SopResult(Status status, String errorCode, Object message, UrlNumber number){
		super(status, errorCode, message);
		this.number = number;
	}

	public UrlNumber getNumber() {
		return number;
	}

	public void setNumber(UrlNumber number) {
		this.number = number;
	}
}
