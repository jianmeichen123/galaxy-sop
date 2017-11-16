package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.Project;

@Service("com.galaxyinternet.mongodb.dao.ProjectDao")
public class ProjectDaoImpl extends MongodbBaseDaoImpl<Project, String> implements ProjectDao {
	
}
