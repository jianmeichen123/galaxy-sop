package com.galaxyinternet.grant.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.GrantPartDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.GrantPart;

@Repository("grantPartDao")
public class GrantPartDaoImpl extends BaseDaoImpl<GrantPart, Long> implements GrantPartDao{

}
