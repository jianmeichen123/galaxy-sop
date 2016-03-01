package com.galaxyinternet.model.user;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class Menus extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String menuName;
	private String url;
	
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
