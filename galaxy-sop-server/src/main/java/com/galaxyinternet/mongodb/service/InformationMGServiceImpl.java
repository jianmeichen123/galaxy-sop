package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.mongodb.dao.InformationMGDao;
import com.galaxyinternet.mongodb.model.InformationDataMG;

@Service("com.galaxyinternet.mongodb.service.InformationMGService")
public class InformationMGServiceImpl implements InformationMGService {
	
	@Autowired
	private InformationMGDao InformationMGDao;
	
	@Override
	public void save(InformationDataMG project) throws MongoDBException {
		InformationMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationDataMG t) throws MongoDBException {
		InformationMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationDataMG> find(InformationDataMG project) throws MongoDBException {
		return InformationMGDao.find(project);
	}

	@Override
	public InformationDataMG findOne(InformationDataMG project) throws MongoDBException {
		return InformationMGDao.findOne(project);
	}

	@Override
	public InformationDataMG findById(String id) throws MongoDBException {
		return InformationMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		InformationMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationDataMG project) throws MongoDBException {
		InformationMGDao.deleteByCondition(project);
	}
	
}
