package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;

@Repository("informationDictionaryDao")
public class InformationDictionaryDaoImpl extends BaseDaoImpl<InformationDictionary, Long> implements InformationDictionaryDao{

	
}
