package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationResultMGDao;
import com.galaxyinternet.mongodb.model.InformationResultMG;
import com.galaxyinternet.mongodb.model.InformationScoreMG;


@Service("com.galaxyinternet.mongodb.service.InformationResultMGService")
public class InformationResultMGServiceImpl implements InformationResultMGService {
	
	@Autowired
	private InformationResultMGDao InformationResultMGDao;
	
	@Override
	public void save(InformationResultMG project) throws MongoDBException {
		InformationResultMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationResultMG t) throws MongoDBException {
		InformationResultMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationResultMG> find(InformationResultMG project) throws MongoDBException {
		return InformationResultMGDao.find(project);
	}

	@Override
	public InformationResultMG findOne(InformationResultMG project) throws MongoDBException {
		return InformationResultMGDao.findOne(project);
	}

	@Override
	public InformationResultMG findById(String id) throws MongoDBException {
		return InformationResultMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		InformationResultMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationResultMG project) throws MongoDBException {
		InformationResultMGDao.deleteByCondition(project);
	}
	
}
