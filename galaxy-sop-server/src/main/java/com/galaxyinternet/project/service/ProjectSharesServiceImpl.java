package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.ProjectSharesDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.service.ProjectSharesService;


@Service("com.galaxyinternet.service.ProjectSharesService")
public class ProjectSharesServiceImpl extends BaseServiceImpl<ProjectShares> implements ProjectSharesService {

	@Autowired
	private ProjectSharesDao projectSharesDao;
	
	@Override
	protected BaseDao<ProjectShares, Long> getBaseDao() {
		return this.projectSharesDao;
	}


}