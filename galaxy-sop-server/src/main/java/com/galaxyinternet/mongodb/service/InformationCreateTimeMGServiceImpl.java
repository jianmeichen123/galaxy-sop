package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationCreateTimeMGDao;
import com.galaxyinternet.mongodb.model.InformationCreateTimeMG;

@Service("com.galaxyinternet.mongodb.service.InformationCreateTimeMGService")
public class InformationCreateTimeMGServiceImpl implements InformationCreateTimeMGService {
	
	@Autowired
	private InformationCreateTimeMGDao InformationCreateTimeMGDao;
	
	@Override
	public void save(InformationCreateTimeMG project) throws MongoDBException {
		InformationCreateTimeMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationCreateTimeMG t) throws MongoDBException {
		InformationCreateTimeMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationCreateTimeMG> find(InformationCreateTimeMG project) throws MongoDBException {
		return InformationCreateTimeMGDao.find(project);
	}

	@Override
	public InformationCreateTimeMG findOne(InformationCreateTimeMG project) throws MongoDBException {
		return InformationCreateTimeMGDao.findOne(project);
	}

	@Override
	public InformationCreateTimeMG findById(String id) throws MongoDBException {
		return InformationCreateTimeMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		InformationCreateTimeMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationCreateTimeMG project) throws MongoDBException {
		InformationCreateTimeMGDao.deleteByCondition(project);
	}
	
}
