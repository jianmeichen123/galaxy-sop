package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationCreateTimeMG;

public interface InformationCreateTimeMGService{
    public void save(InformationCreateTimeMG project) throws MongoDBException;
	
	public void updateById(String id, InformationCreateTimeMG t) throws MongoDBException;
	
	public List<InformationCreateTimeMG> find(InformationCreateTimeMG project) throws MongoDBException;
	
	public InformationCreateTimeMG findOne(InformationCreateTimeMG project) throws MongoDBException;
	
	public InformationCreateTimeMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationCreateTimeMG project) throws MongoDBException;

}
