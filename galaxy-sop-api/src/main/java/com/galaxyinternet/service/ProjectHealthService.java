package com.galaxyinternet.service;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.ProjectHealth;

public interface ProjectHealthService extends BaseService<ProjectHealth> {
	public Map<String,Object> gtHealthyChart(Map<String,Object> params);
	

}
