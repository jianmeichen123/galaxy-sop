package com.galaxyinternet.hologram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.service.hologram.InformationDictionaryService;


@Service("com.galaxyinternet.service.hologram.InformationDictionaryService")
public class InformationDictionaryServiceImpl extends BaseServiceImpl<InformationDictionary> implements InformationDictionaryService{

	@Autowired
	private InformationDictionaryDao informationDictionaryDao;
	
	@Override
	protected BaseDao<InformationDictionary, Long> getBaseDao() {
		return this.informationDictionaryDao;
	}

}


