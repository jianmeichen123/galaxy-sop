package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationModelMG;

public interface InformationModelMGService {
	
	public void save(InformationModelMG project) throws MongoDBException;
	
	public void updateById(String id, InformationModelMG t) throws MongoDBException;
	
	public List<InformationModelMG> find(InformationModelMG project) throws MongoDBException;
	
	public InformationModelMG findOne(InformationModelMG project) throws MongoDBException;
	
	public InformationModelMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationModelMG project) throws MongoDBException;

}
