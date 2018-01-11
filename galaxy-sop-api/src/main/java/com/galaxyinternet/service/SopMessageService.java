package com.galaxyinternet.service;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.message.SopMessage;
import com.galaxyinternet.model.operationLog.OperationMessageType;

public interface SopMessageService extends BaseService<SopMessage>
{
	public void add(OperationMessageType type, Long userId, Map<String,Object> params);
}
