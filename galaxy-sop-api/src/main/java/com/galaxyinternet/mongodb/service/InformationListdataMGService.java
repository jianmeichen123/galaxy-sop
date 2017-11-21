package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationListdataMG;

public interface InformationListdataMGService {
	
	public void save(InformationListdataMG project) throws MongoDBException;
	
	public void updateById(String id, InformationListdataMG t) throws MongoDBException;
	
	public List<InformationListdataMG> find(InformationListdataMG project) throws MongoDBException;
	
	public InformationListdataMG findOne(InformationListdataMG project) throws MongoDBException;
	
	public InformationListdataMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationListdataMG project) throws MongoDBException;

}
