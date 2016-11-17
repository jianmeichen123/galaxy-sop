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
	public void updateById(String id, Project t) throws MongoDBException {
		projectDao.updateById(id, t);
	}
	
	@Override
	public List<Project> find(Project project) throws MongoDBException {
		return projectDao.find(project);
	}

	@Override
	public Project findOne(Project project) throws MongoDBException {
		return projectDao.findOne(project);
	}

	@Override
	public Project findById(String id) throws MongoDBException {
		return projectDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		projectDao.deleteById(id);
	}

}
