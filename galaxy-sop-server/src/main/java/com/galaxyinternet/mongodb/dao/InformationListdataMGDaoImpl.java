package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationListdataMG;

@Service("com.galaxyinternet.mongodb.dao.InformationListdataMGDao")
public class InformationListdataMGDaoImpl extends MongodbBaseDaoImpl<InformationListdataMG, String> implements InformationListdataMGDao {
	
}
