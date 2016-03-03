package com.galaxyinternet.HumanResourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.HR.PersonWorkDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.HR.personWork;
import com.galaxyinternet.service.PersonWorkService;

@Service("com.galaxyinternet.service.PersonWorkService")
public class PersonWorkServiceImpl extends BaseServiceImpl<personWork> implements PersonWorkService{
	
	@Autowired
	private PersonWorkDao personWorkDao;

	@Override
	protected BaseDao<personWork, Long> getBaseDao() {
		// 
		return this.personWorkDao;
	}

}
