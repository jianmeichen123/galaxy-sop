package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationFileMG;

@Service("com.galaxyinternet.mongodb.dao.InformationFileMGDao")
public class InformationFileMGDaoImpl extends MongodbBaseDaoImpl<InformationFileMG, String> implements InformationFileMGDao {
	
}
