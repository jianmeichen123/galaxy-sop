package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationScoreMG;

@Service("com.galaxyinternet.mongodb.dao.InformationScoreMGDao")
public class InformationScoreMGDaoImpl extends MongodbBaseDaoImpl<InformationScoreMG, String> implements InformationScoreMGDao {
	
}
