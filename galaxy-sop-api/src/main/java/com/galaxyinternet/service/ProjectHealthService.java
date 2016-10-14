package com.galaxyinternet.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.ProjectHealth;

public interface ProjectHealthService extends BaseService<ProjectHealth> {
	
	public Map<String,Object> gtHealthyChart(Map<String,Object> params);
	
	public Page<ProjectHealth> getHealthChartGrid(ProjectHealth query, Pageable pageable);
	

}
