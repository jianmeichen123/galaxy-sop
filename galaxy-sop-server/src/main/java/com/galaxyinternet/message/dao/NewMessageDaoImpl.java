package com.galaxyinternet.message.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.Message.NewMessageDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.message.NewMessage;


@Repository("NewMessageDao")
public class NewMessageDaoImpl extends BaseDaoImpl<NewMessage, Long> implements NewMessageDao {

	
	
	}
