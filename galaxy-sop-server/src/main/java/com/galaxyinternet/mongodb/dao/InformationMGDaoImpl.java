package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationDataMG;

@Service("com.galaxyinternet.mongodb.dao.InformationMGDao")
public class InformationMGDaoImpl extends MongodbBaseDaoImpl<InformationDataMG, String> implements InformationMGDao {
	
}
