package com.galaxyinternet.bo;

import com.galaxyinternet.model.soptask.SopTask;

public class SopTaskBo extends SopTask {

	private static final long serialVersionUID = 1L;
	private String extendFiled;// 业务对象中扩展的字段

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

}
