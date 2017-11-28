package com.galaxyinternet.OperationLogs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

import com.galaxyinternet.OperationLogs.component.OperationLogHandler;
import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.dao.operationLog.OperationLogsDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.OperationLogsService;

@Service("com.galaxyinternet.service.OperationLogsService")
public class OperationLogsServiceImpl extends BaseServiceImpl<OperationLogs> implements OperationLogsService,InitializingBean,ApplicationContextAware
{
	private ApplicationContext applicationContext;
	private List<OperationLogHandler> handlers;

	@Autowired
	private OperationLogsDao operationLogsDao;

	@Override
	protected BaseDao<OperationLogs, Long> getBaseDao()
	{
		return this.operationLogsDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
		
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		Map<String, OperationLogHandler> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, OperationLogHandler.class, true, false);
		 if(map != null)
		 {
			 handlers = new ArrayList<OperationLogHandler>(map.values());
			 OrderComparator.sort(handlers);
		 }
		
	}
	
	public void generate(OperationLogType type, User user, Map<String, Object> map, RecordType recordType)
	{
		
	}
	

}
