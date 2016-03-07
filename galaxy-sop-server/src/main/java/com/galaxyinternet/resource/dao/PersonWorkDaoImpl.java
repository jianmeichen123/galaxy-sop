package com.galaxyinternet.resource.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hr.PersonWorkDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hr.PersonWork;


@Repository("personWorkDao")
public class PersonWorkDaoImpl  extends BaseDaoImpl<PersonWork, Long> implements PersonWorkDao{

}

