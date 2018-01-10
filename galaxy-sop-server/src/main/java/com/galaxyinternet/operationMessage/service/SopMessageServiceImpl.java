package com.galaxyinternet.operationMessage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.Message.SopMessageDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.message.SopMessage;
import com.galaxyinternet.model.operationLog.OperationMessageType;
import com.galaxyinternet.platform.constant.PlatformConst;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(OperationMessageType type, Long userId, Map<String,Object> params)
	{
		if(type == null)
		{
			return;
		}
		String messageType = type.getMessageType();
		byte recordType = (byte)params.get(PlatformConst.REQUEST_SCOPE_MESSAGE_RECORD_TYPE);
		List<Long> recordIds = (List<Long>)params.get(PlatformConst.REQUEST_SCOPE_MESSAGE_RECORD_IDS);
		
		if(recordIds == null || recordIds.size() == 0)
		{
			return;
		}
		SopMessage entity = null;
		Long now = System.currentTimeMillis();
		for(Long recordId : recordIds)
		{
			entity = new SopMessage();
			entity.setMessageType(messageType);
			entity.setRecordType(recordType);
			entity.setRecordId(recordId);
			entity.setCreatedId(userId);
			entity.setCreatedTime(now);
			insert(entity);
		}
		
	}
	
	

}
