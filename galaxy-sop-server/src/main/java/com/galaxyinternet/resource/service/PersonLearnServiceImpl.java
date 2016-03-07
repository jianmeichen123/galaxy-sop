package com.galaxyinternet.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hr.PersonLearnDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.service.PersonLearnService;

@Service("com.galaxyinternet.service.PersonLearnService")
public class PersonLearnServiceImpl extends BaseServiceImpl<PersonLearn> implements PersonLearnService{
	
	@Autowired
	private PersonLearnDao personLearnDao;

	@Override
	protected BaseDao<PersonLearn, Long> getBaseDao() {
		// 
		return this.personLearnDao;
	}

}
