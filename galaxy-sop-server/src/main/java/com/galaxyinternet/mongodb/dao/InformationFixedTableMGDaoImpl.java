package com.galaxyinternet.mongodb.dao;


import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.impl.MongodbBaseDaoImpl;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;

@Service("com.galaxyinternet.mongodb.dao.InformationFixedTableMGDao")
public class InformationFixedTableMGDaoImpl extends MongodbBaseDaoImpl<InformationFixedTableMG, String> implements InformationFixedTableMGDao {
	
}
