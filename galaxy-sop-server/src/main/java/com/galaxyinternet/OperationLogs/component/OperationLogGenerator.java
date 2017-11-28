package com.galaxyinternet.OperationLogs.component;

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
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.OperationLogsService;

@Component
public class OperationLogGenerator implements InitializingBean,ApplicationContextAware
{
	private ApplicationContext applicationContext;
	private List<OperationLogHandler> handlers;
	@Autowired
	private OperationLogsService service;


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
		OperationLogParams params = new OperationLogParams();
		params.setType(type);
		params.setUser(user);
		params.setParams(map);
		params.setRecordType(recordType);
		if(handlers != null && handlers.size() >0)
		{
			for(OperationLogHandler handler : handlers)
			{
				if(handler.support(params))
				{
					List<OperationLogs> list = handler.handle(params);
					service.insertInBatch(list);
					break;
				}
			}
		}
	}
}
