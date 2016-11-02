package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.Project;

public interface ProjectService {
	
	public void save(Project project) throws MongoDBException;
	
	public void updateById(String id, Project t) throws MongoDBException;
	
	public List<Project> find(Project project) throws MongoDBException;
	
	public Project findOne(Project project) throws MongoDBException;
	
	public Project findById(String id) throws MongoDBException;

}
