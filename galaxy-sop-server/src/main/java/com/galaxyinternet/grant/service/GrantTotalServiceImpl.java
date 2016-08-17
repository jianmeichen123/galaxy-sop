package com.galaxyinternet.grant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.GrantTotalDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.service.GrantTotalService;

@Service("com.galaxyinternet.grant.GrantTotalService")
public class GrantTotalServiceImpl extends BaseServiceImpl<GrantTotal> implements GrantTotalService{
	
	@Autowired
	private GrantTotalDao grantTotalDao;
	
	@Override
	protected BaseDao<GrantTotal, Long> getBaseDao() {
		return this.grantTotalDao;
	}

}
