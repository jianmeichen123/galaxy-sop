package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationScoreMG;

public interface InformationScoreMGService {
	
	public void save(InformationScoreMG project) throws MongoDBException;
	
	public void updateById(String id, InformationScoreMG t) throws MongoDBException;
	
	public List<InformationScoreMG> find(InformationScoreMG project) throws MongoDBException;
	
	public InformationScoreMG findOne(InformationScoreMG project) throws MongoDBException;
	
	public InformationScoreMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationScoreMG project) throws MongoDBException;


}
