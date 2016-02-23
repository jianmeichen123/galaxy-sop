package com.galaxyinternet.bo;

import com.galaxyinternet.model.user.User;

public class UserBo extends User {

	private static final long serialVersionUID = 1L;
	private String extendFiled;// 业务对象中扩展的字段

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

}
