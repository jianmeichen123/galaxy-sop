package com.galaxyinternet.operationMessage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.Message.SopMessageDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.message.SopMessage;
import com.galaxyinternet.service.SopMessageService;
@Service
public class SopMessageServiceImpl extends BaseServiceImpl<SopMessage> implements SopMessageService
{
	@Autowired
	private SopMessageDao dao;

	@Override
	protected BaseDao<SopMessage, Long> getBaseDao()
	{
		return dao;
	}

}
