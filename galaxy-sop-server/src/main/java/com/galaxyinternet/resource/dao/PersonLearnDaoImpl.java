package com.galaxyinternet.resource.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hr.PersonLearnDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hr.PersonLearn;



@Repository("personLearnDao")
public class PersonLearnDaoImpl  extends BaseDaoImpl<PersonLearn, Long> implements PersonLearnDao{

}

