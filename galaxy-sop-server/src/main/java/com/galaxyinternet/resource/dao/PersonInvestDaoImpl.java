package com.galaxyinternet.resource.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hr.PersonInvestDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hr.PersonInvest;


@Repository("personInvestDao")
public class PersonInvestDaoImpl  extends BaseDaoImpl<PersonInvest, Long> implements PersonInvestDao{

	
}

