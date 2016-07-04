package com.galaxyinternet.touhou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.touhou.ProjectHealthDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.service.ProjectHealthService;

@Service("com.galaxyinternet.touhou.service.ProjectHealthServiceImpl")
public class ProjectHealthServiceImpl extends BaseServiceImpl<ProjectHealth> implements ProjectHealthService {

	@Autowired
	private ProjectHealthDao projectHealthDao;
	
	@Override
	protected BaseDao<ProjectHealth, Long> getBaseDao() {
		return this.projectHealthDao;
	}


}