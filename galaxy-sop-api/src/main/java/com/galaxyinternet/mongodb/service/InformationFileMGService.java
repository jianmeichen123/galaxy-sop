package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationFileMG;

public interface InformationFileMGService {
	
	public void save(InformationFileMG project) throws MongoDBException;
	
	public void updateById(String id, InformationFileMG t) throws MongoDBException;
	
	public List<InformationFileMG> find(InformationFileMG project) throws MongoDBException;
	
	public InformationFileMG findOne(InformationFileMG project) throws MongoDBException;
	
	public InformationFileMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationFileMG project) throws MongoDBException;

}
