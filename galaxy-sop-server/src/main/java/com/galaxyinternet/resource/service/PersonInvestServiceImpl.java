package com.galaxyinternet.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hr.PersonInvestDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hr.PersonInvest;
import com.galaxyinternet.service.PersonInvestService;

@Service("com.galaxyinternet.service.PersonInvestService")
public class PersonInvestServiceImpl extends BaseServiceImpl<PersonInvest> implements PersonInvestService{
	
	@Autowired
	private PersonInvestDao personInvestDao;

	@Override
	protected BaseDao<PersonInvest, Long> getBaseDao() {
		// 
		return this.personInvestDao;
	}

}
