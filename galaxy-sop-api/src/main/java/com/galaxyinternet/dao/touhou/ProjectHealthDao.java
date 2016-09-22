package com.galaxyinternet.dao.touhou;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.touhou.ProjectHealth;

public interface ProjectHealthDao extends BaseDao<ProjectHealth, Long> {
	
	public List<ProjectHealth> getHealthyChart(Map<String, Object> params) ;
	
	public Page<ProjectHealth> getHealthChartGrid(ProjectHealth query, Pageable pageable);
	
	public Long selectCountChart(ProjectHealth query);
	
	
}