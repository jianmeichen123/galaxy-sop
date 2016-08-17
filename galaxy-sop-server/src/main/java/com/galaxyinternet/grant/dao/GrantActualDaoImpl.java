package com.galaxyinternet.grant.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.GrantActualDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.GrantActual;


@Repository("grantActualDao")
public class GrantActualDaoImpl extends BaseDaoImpl<GrantActual, Long> implements GrantActualDao{

}
