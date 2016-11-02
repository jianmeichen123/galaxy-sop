package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.ProjectDao;
import com.galaxyinternet.mongodb.model.Project;

@Service("com.galaxyinternet.mongodb.service.ProjectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public void save(Project project) throws MongoDBException {
		projectDao.save(project);
	}

	@Override
	public List<Project> find(Project project) throws MongoDBException {
		return projectDao.find(project);
	}

	@Override
	public Project findOne(Project project) throws MongoDBException {
		return projectDao.findOne(project);
	}

}
