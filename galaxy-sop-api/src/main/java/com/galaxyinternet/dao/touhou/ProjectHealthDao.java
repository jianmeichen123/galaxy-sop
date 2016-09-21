package com.galaxyinternet.dao.touhou;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.touhou.ProjectHealth;

public interface ProjectHealthDao extends BaseDao<ProjectHealth, Long> {
	
	public List<ProjectHealth> getHealthyChart(Map<String, Object> params) ;

	
}