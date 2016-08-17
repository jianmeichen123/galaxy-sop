package com.galaxyinternet.grant;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.GrantTotalDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.GrantTotal;


@Repository("grantTotalDao")
public class GrantTotalDaoImpl extends BaseDaoImpl<GrantTotal, Long> implements GrantTotalDao{

}
