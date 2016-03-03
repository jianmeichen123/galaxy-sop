package com.galaxyinternet.HumanResourse.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.HR.PersonLearnDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.HR.personLearn;



@Repository("PersonLearnDao")
public class PersonLearnDaoImpl  extends BaseDaoImpl<personLearn, Long> implements PersonLearnDao{

}

