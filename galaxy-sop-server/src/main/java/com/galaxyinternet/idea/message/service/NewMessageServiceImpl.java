package com.galaxyinternet.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.Message.NewMessageDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.message.NewMessage;
import com.galaxyinternet.service.NewMessageService;


@Service("com.galaxyinternet.service.NewMessageService")
public class NewMessageServiceImpl extends BaseServiceImpl<NewMessage> implements NewMessageService {

	@Autowired
	private NewMessageDao newMessageDao;
	
	@Override
	protected BaseDao<NewMessage, Long> getBaseDao() {
		return this.newMessageDao;
	}

	}