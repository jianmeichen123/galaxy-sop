package com.galaxyinternet.HumanResourse.dao;

import org.springframework.stereotype.Repository;




import com.galaxyinternet.dao.HR.PersonInvestDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.HR.personInvest;


@Repository("PersonInvestDao")
public class PersonInvestDaoImpl  extends BaseDaoImpl<personInvest, Long> implements PersonInvestDao{

	
}

