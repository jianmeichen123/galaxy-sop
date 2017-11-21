
package com.galaxyinternet.mongodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.mongodb.dao.InformationFixedTableMGDao;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;

@Service("com.galaxyinternet.mongodb.service.InformationFixedTableMGService")
public class InformationFixedTableMGServiceImpl implements InformationFixedTableMGService {
	
	@Autowired
	private InformationFixedTableMGDao informationFixedTableMGDao;
	
	@Override
	public void save(InformationFixedTableMG project) throws MongoDBException {
		informationFixedTableMGDao.save(project);
	}
	
	@Override
	public void updateById(String id, InformationFixedTableMG t) throws MongoDBException {
		informationFixedTableMGDao.updateById(id, t);
	}
	
	@Override
	public List<InformationFixedTableMG> find(InformationFixedTableMG project) throws MongoDBException {
		return informationFixedTableMGDao.find(project);
	}

	@Override
	public InformationFixedTableMG findOne(InformationFixedTableMG project) throws MongoDBException {
		return informationFixedTableMGDao.findOne(project);
	}

	@Override
	public InformationFixedTableMG findById(String id) throws MongoDBException {
		return informationFixedTableMGDao.findOneById(id);
	}
	
	@Override
	public void deleteById(String id) throws MongoDBException {
		informationFixedTableMGDao.deleteById(id);
	}

	@Override
	public void deleteByCondition(InformationFixedTableMG project) throws MongoDBException {
		informationFixedTableMGDao.deleteByCondition(project);
	}
	
}
