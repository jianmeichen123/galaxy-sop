package com.galaxyinternet.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.systemMessage.SystemMessageDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.systemMessage.SystemMessage;

@Repository("systemMessageDao")
public class SystemMessageDaoImpl extends BaseDaoImpl<SystemMessage, Long> implements SystemMessageDao  {

	@Override
	public List<SystemMessage> selectByIdUserId(SystemMessage query) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
