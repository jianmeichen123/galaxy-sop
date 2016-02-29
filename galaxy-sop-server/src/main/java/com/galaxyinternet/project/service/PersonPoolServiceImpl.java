package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.service.PersonPoolService;

@Service("com.galaxyinternet.service.PersonPoolService")
public class PersonPoolServiceImpl extends BaseServiceImpl<PersonPool> implements PersonPoolService{
	
	@Autowired
	private PersonPoolDao personPoolDao;
	
	@Override
	protected BaseDao<PersonPool, Long> getBaseDao() {
		return this.personPoolDao;
	}

}
