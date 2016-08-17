package com.galaxyinternet.grant.service;

import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.GrantPartDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.service.GrantPartService;

@Service("com.galaxyinternet.grant.GrantPartService")
public class GrantPartServiceImpl extends BaseServiceImpl<GrantPart> implements GrantPartService {
	
	private GrantPartDao grantPartDao;
	
	@Override
	protected BaseDao<GrantPart, Long> getBaseDao() {
		return this.grantPartDao;
	}

}
