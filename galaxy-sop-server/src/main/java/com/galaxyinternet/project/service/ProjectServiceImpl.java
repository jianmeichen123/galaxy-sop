package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.ProjectService;


@Service("com.galaxyinternet.service.projectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	
	@Override
	protected BaseDao<Project, Long> getBaseDao() {
		return this.projectDao;
	}


}