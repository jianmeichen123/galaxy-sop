package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationScoreMGDao;
import com.galaxyinternet.mongodb.model.InformationScoreMG;


@Service("com.galaxyinternet.mongodb.service.InformationScoreMGService")
public class InformationScoreMGServiceImpl implements InformationScoreMGService {
	
	@Autowired
	private InformationScoreMGDao informationScoreMGDao;
	
	@Override
	public void save(InformationScoreMG project) throws MongoDBException {
		informationScoreMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationScoreMG t) throws MongoDBException {
		informationScoreMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationScoreMG> find(InformationScoreMG project) throws MongoDBException {
		return informationScoreMGDao.find(project);
	}

	@Override
	public InformationScoreMG findOne(InformationScoreMG project) throws MongoDBException {
		return informationScoreMGDao.findOne(project);
	}

	@Override
	public InformationScoreMG findById(String id) throws MongoDBException {
		return informationScoreMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		informationScoreMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationScoreMG project) throws MongoDBException {
		informationScoreMGDao.deleteByCondition(project);
	}
	
}
