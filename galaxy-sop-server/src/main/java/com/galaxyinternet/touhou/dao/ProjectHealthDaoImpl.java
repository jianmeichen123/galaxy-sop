package com.galaxyinternet.touhou.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.touhou.ProjectHealthDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.touhou.ProjectHealth;


@Repository("projectHealthDao")
public class ProjectHealthDaoImpl extends BaseDaoImpl<ProjectHealth, Long> implements ProjectHealthDao {

	@Override
	public List<ProjectHealth> getHealthyChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName("getHealthyCharts"),params);
}
}