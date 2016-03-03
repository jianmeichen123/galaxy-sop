package com.galaxyinternet.HumanResourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.HR.PersonInvestDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.HR.personInvest;
import com.galaxyinternet.service.PersonInvestService;

@Service("com.galaxyinternet.service.PersonInvestService")
public class PersonInvestServiceImpl extends BaseServiceImpl<personInvest> implements PersonInvestService{
	
	@Autowired
	private PersonInvestDao personInvestDao;

	@Override
	protected BaseDao<personInvest, Long> getBaseDao() {
		// 
		return this.personInvestDao;
	}

}
