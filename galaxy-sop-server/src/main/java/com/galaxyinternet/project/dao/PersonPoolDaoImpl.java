package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.PersonPool;

@Repository("personPoolDao")
public class PersonPoolDaoImpl extends BaseDaoImpl<PersonPool, Long> implements PersonPoolDao{

}
