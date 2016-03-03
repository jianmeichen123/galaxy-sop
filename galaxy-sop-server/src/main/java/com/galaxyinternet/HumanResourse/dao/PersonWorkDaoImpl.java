package com.galaxyinternet.HumanResourse.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.HR.PersonWorkDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.HR.personWork;


@Repository("PersonWorkDao")
public class PersonWorkDaoImpl  extends BaseDaoImpl<personWork, Long> implements PersonWorkDao{

}

