package com.galaxyinternet.dao.systemMessage;

import java.util.List;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.systemMessage.SystemMessage;

public interface SystemMessageDao extends BaseDao<SystemMessage, Long> {
	
	public List<SystemMessage> selectByIdUserId(SystemMessage query);
	
}