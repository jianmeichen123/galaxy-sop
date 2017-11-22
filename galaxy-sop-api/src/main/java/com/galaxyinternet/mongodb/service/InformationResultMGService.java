package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationResultMG;

public interface InformationResultMGService {
	
	public void save(InformationResultMG project) throws MongoDBException;
	
	public void updateById(String id, InformationResultMG t) throws MongoDBException;
	
	public List<InformationResultMG> find(InformationResultMG project) throws MongoDBException;
	
	public InformationResultMG findOne(InformationResultMG project) throws MongoDBException;
	
	public InformationResultMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationResultMG project) throws MongoDBException;


}
