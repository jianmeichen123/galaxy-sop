package com.galaxyinternet.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.systemMessage.SystemMessageUserDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.systemMessage.SystemMessageUser;
import com.galaxyinternet.service.SystemMessageUserService;

@Service("com.galaxyinternet.service.SystemMessageUserService")
public class SystemMessageUserServiceImpl extends BaseServiceImpl<SystemMessageUser> implements SystemMessageUserService {
	
	@Autowired
	private SystemMessageUserDao systemMessageDao;

	@Override
	protected BaseDao<SystemMessageUser, Long> getBaseDao() {
		return this.systemMessageDao;
	}

	
}
