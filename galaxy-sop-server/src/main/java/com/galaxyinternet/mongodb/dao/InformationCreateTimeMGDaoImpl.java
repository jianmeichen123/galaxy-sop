package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationModelMG;

@Service("com.galaxyinternet.mongodb.dao.InformationCreateTimeMGDao")
public class InformationCreateTimeMGDaoImpl extends MongodbBaseDaoImpl<InformationModelMG, String> implements InformationCreateTimeMGDao {
	
}
