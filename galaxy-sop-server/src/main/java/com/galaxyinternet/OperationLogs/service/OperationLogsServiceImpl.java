package com.galaxyinternet.OperationLogs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.operationLog.OperationLogsDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.service.OperationLogsService;

@Service("com.galaxyinternet.service.OperationLogsService")
public class OperationLogsServiceImpl extends BaseServiceImpl<OperationLogs> implements OperationLogsService {

	@Autowired
	private OperationLogsDao operationLogsDao;
	
	
	@Override
	protected BaseDao<OperationLogs, Long> getBaseDao() {
		return this.operationLogsDao;
	}
	
	
	

}
