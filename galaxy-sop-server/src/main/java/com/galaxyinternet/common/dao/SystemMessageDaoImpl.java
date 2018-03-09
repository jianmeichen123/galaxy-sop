package com.galaxyinternet.common.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.systemMessage.SystemMessageUserDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.systemMessage.SystemMessageUser;

@Repository("systemMessageUserDao")
public class SystemMessageDaoImpl extends BaseDaoImpl<SystemMessageUser, Long> implements SystemMessageUserDao  {

	
}
