package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.ProjectPersonDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.service.ProjectPersonService;


@Service("com.galaxyinternet.service.ProjectPersonService")
public class ProjectPersonServiceImpl extends BaseServiceImpl<ProjectPerson> implements ProjectPersonService {

	@Autowired
	private ProjectPersonDao projectPersonDao;
	
	@Override
	protected BaseDao<ProjectPerson, Long> getBaseDao() {
		return this.projectPersonDao;
	}
	


}