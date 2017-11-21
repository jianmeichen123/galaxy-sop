package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationModelMGDao;
import com.galaxyinternet.mongodb.model.InformationModelMG;

@Service("com.galaxyinternet.mongodb.service.InformationModelMGService")
public class InformationModelMGServiceImpl implements InformationModelMGService {
	
	@Autowired
	private InformationModelMGDao informationModelMGDao;
	
	@Override
	public void save(InformationModelMG project) throws MongoDBException {
		informationModelMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationModelMG t) throws MongoDBException {
		informationModelMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationModelMG> find(InformationModelMG project) throws MongoDBException {
		return informationModelMGDao.find(project);
	}

	@Override
	public InformationModelMG findOne(InformationModelMG project) throws MongoDBException {
		return informationModelMGDao.findOne(project);
	}

	@Override
	public InformationModelMG findById(String id) throws MongoDBException {
		return informationModelMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		informationModelMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationModelMG project) throws MongoDBException {
		informationModelMGDao.deleteByCondition(project);
	}
	
}
