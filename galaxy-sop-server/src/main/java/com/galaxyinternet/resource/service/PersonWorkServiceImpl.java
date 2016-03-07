package com.galaxyinternet.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hr.PersonWorkDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.service.PersonWorkService;

@Service("com.galaxyinternet.service.PersonWorkService")
public class PersonWorkServiceImpl extends BaseServiceImpl<PersonWork> implements PersonWorkService{
	
	@Autowired
	private PersonWorkDao personWorkDao;

	@Override
	protected BaseDao<PersonWork, Long> getBaseDao() {
		// 
		return this.personWorkDao;
	}

}
