package com.galaxyinternet.operationMessage.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.Message.SopMessageDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.message.SopMessage;
@Repository
public class SopMessageDaoImpl extends BaseDaoImpl<SopMessage, Long> implements SopMessageDao
{

}
