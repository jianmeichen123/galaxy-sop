package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationResultMG;

@Service("com.galaxyinternet.mongodb.dao.InformationResultMGDao")
public class InformationResultMGDaoImpl extends MongodbBaseDaoImpl<InformationResultMG, String> implements InformationResultMGDao {
	
}
