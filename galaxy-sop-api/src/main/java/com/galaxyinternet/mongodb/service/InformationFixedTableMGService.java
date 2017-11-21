package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;

public interface InformationFixedTableMGService {
	
	public void save(InformationFixedTableMG project) throws MongoDBException;
	
	public void updateById(String id, InformationFixedTableMG t) throws MongoDBException;
	
	public List<InformationFixedTableMG> find(InformationFixedTableMG project) throws MongoDBException;
	
	public InformationFixedTableMG findOne(InformationFixedTableMG project) throws MongoDBException;
	
	public InformationFixedTableMG findById(String id) throws MongoDBException;

	void deleteById(String id) throws MongoDBException;

	void deleteByCondition(InformationFixedTableMG project) throws MongoDBException;

}
