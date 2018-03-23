package com.galaxyinternet.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.systemMessage.SystemMessageDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.systemMessage.SystemMessage;
import com.galaxyinternet.service.SystemMessageService;

@Service("com.galaxyinternet.service.SystemMessageService")
public class SystemMessageServiceImpl extends BaseServiceImpl<SystemMessage> implements SystemMessageService {
	
	@Autowired
	private SystemMessageDao systemMessageDao;

	@Override
	protected BaseDao<SystemMessage, Long> getBaseDao() {
		return this.systemMessageDao;
	}

	
}
