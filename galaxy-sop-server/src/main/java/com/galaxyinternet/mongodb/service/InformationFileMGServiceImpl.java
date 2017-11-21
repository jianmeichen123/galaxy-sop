package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationFileMGDao;
import com.galaxyinternet.mongodb.model.InformationFileMG;

@Service("com.galaxyinternet.mongodb.service.InformationFileMGService")
public class InformationFileMGServiceImpl implements InformationFileMGService {
	
	@Autowired
	private InformationFileMGDao informationFileMGDao;
	
	@Override
	public void save(InformationFileMG project) throws MongoDBException {
		informationFileMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationFileMG t) throws MongoDBException {
		informationFileMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationFileMG> find(InformationFileMG project) throws MongoDBException {
		return informationFileMGDao.find(project);
	}

	@Override
	public InformationFileMG findOne(InformationFileMG project) throws MongoDBException {
		return informationFileMGDao.findOne(project);
	}

	@Override
	public InformationFileMG findById(String id) throws MongoDBException {
		return informationFileMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		informationFileMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationFileMG project) throws MongoDBException {
		informationFileMGDao.deleteByCondition(project);
	}
	
}
