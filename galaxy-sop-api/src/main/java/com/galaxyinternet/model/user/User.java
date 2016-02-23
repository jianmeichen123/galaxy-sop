package com.galaxyinternet.model.user;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String realName;
	private String email;
	private String mobile;
	private String telephone;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
