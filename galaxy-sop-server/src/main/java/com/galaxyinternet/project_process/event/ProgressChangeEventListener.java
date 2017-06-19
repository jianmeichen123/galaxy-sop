package com.galaxyinternet.project_process.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.galaxyinternet.project_process.event.handlers.ProgressChangeHandler;
/**
 * 项目流程：<br>
 * 接触访谈 -> 内部评审 -> CEO评审 -> 立项会 -> 会后商务谈判 -> 投资意向书（投资） -> 尽职调查 -> 投决会 -> 投资协议 -> 股权交割<br>
 * 接触访谈 -> 内部评审 -> CEO评审 -> 立项会 -> 会后商务谈判 -> 投资协议（闪投） -> 尽职调查 -> 投决会 -> 股权交割
 * @author wangsong
 *
 */
@Component
public class ProgressChangeEventListener implements ApplicationListener<ProgressChangeEvent>, InitializingBean, ApplicationContextAware
{
	private static final Logger logger = LoggerFactory.getLogger(RejectEventListener.class);
	private ApplicationContext context;
	private List<ProgressChangeHandler> handlers;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		context = applicationContext;
	}
	@Override
	public void onApplicationEvent(ProgressChangeEvent event)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug(String.format("项目推进，PID=%s, next=%s", event.getProject().getId(), event.getNext()));
		}
		if(handlers != null && handlers.size() > 0)
		{
			for(ProgressChangeHandler handler : handlers)
			{
				if(handler.support(event))
				{
					handler.handler(event);
					break;
				}
			}
		}
		
	}
	@Override
	public void afterPropertiesSet() throws Exception
	{
		 Map<String, ProgressChangeHandler> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ProgressChangeHandler.class, true, false);
		 if(map != null)
		 {
			 handlers = new ArrayList<ProgressChangeHandler>(map.values());
		 }
		
	}
}
