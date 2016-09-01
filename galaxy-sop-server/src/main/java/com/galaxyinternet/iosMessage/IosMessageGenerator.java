package com.galaxyinternet.iosMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;

import com.galaxyinternet.iosMessage.handleInter.IosMessageHandler;
import com.galaxyinternet.iosMessage.operType.IosMeaageOperation;
import com.galaxyinternet.model.iosMessage.IosMessage;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
@Component
public class IosMessageGenerator implements InitializingBean,ApplicationContextAware
{
	private ApplicationContext context;
	private List<IosMessageHandler> iosHandlers;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.context = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		 Map<String, IosMessageHandler> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IosMessageHandler.class, true, false);
		 if(map != null)
		 {
			 iosHandlers = new ArrayList<IosMessageHandler>(map.values());
			 OrderComparator.sort(iosHandlers);
		 }
		
	}

	public IosMessage process(IosMessage message){
		boolean hasHandle = true;
		if(iosHandlers != null) {
			for(IosMessageHandler handler : iosHandlers) {
				if(handler.support(message)) {
					hasHandle = false;
					message = handler.handle(message);
				}
			}
		}
		return hasHandle?null:message;
	}
	
	public IosMessage generate(IosMeaageOperation type, User user, Map<String, Object> map) throws Exception
	{
		IosMessage entity = new IosMessage();
		
		entity.setOperator(user.getRealName());
		
		User u = map.get(PlatformConst.REQUEST_SCOPE_USER)==null?user:(User)map.get(PlatformConst.REQUEST_SCOPE_USER) ;
		String messageType = String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE));
		if(StringUtils.isNoneBlank(messageType) && messageType.startsWith("11")){
			if(u.getKeyword()==null){
				throw new Exception("user中keyword信息缺失");
			}
			entity.setKeyword(u.getKeyword());
		}
		entity.setMessageType(messageType);
		entity.setTitle(type.getTitle());
		entity.setContent(type.getContent());
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		process(entity);
		
		return entity;
	}
}
