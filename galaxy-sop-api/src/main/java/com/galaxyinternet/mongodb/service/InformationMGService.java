package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationDataMG;

public interface InformationMGService {
	
	public void save(InformationDataMG project) throws MongoDBException;
	
	public void updateById(String id, InformationDataMG t) throws MongoDBException;
	
	public List<InformationDataMG> find(InformationDataMG project) throws MongoDBException;
	
	public InformationDataMG findOne(InformationDataMG project) throws MongoDBException;
	
	public InformationDataMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationDataMG project) throws MongoDBException;

}
