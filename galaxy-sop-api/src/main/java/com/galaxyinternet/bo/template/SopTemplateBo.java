package com.galaxyinternet.bo.template;

import com.galaxyinternet.model.template.SopTemplate;

public class SopTemplateBo extends SopTemplate {
	private static final long serialVersionUID = 1L;
	private Long[] ids;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
}
