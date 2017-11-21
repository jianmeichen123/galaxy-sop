package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationListdataMGDao;
import com.galaxyinternet.mongodb.model.InformationListdataMG;

@Service("com.galaxyinternet.mongodb.service.InformationListdataMGService")
public class InformationListdataMGServiceImpl implements InformationListdataMGService {
	
	@Autowired
	private InformationListdataMGDao informationListdataMGDao;
	
	@Override
	public void save(InformationListdataMG project) throws MongoDBException {
		informationListdataMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationListdataMG t) throws MongoDBException {
		informationListdataMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationListdataMG> find(InformationListdataMG project) throws MongoDBException {
		return informationListdataMGDao.find(project);
	}

	@Override
	public InformationListdataMG findOne(InformationListdataMG project) throws MongoDBException {
		return informationListdataMGDao.findOne(project);
	}

	@Override
	public InformationListdataMG findById(String id) throws MongoDBException {
		return informationListdataMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		informationListdataMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationListdataMG project) throws MongoDBException {
		informationListdataMGDao.deleteByCondition(project);
	}
	
}
