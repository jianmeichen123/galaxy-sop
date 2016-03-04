package com.galaxyinternet.service;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.template.SopTemplate;

public interface SopTemplateService extends BaseService<SopTemplate> {
	public Map<String,Object> getRelatedData();
}
