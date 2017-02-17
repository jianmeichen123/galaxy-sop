package com.galaxyinternet.operationMessage;

import java.io.Serializable;
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

import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.operationMessage.OperationType;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
@Component
public class MessageGenerator implements InitializingBean,ApplicationContextAware
{
	private ApplicationContext context;
	private List<MessageHandler> handlers;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.context = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		 Map<String, MessageHandler> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, MessageHandler.class, true, false);
		 if(map != null)
		 {
			 handlers = new ArrayList<MessageHandler>(map.values());
			 OrderComparator.sort(handlers);
		 }
		
	}

	public OperationMessage process(OperationMessage message)
	{
		if(handlers != null)
		{
			for(MessageHandler handler : handlers)
			{
				if(handler.support(message))
				{
					return handler.handle(message);
				}
			}
		}
		return message;
	}
	
	public OperationMessage generate(OperationType type, User user, Map<String, Object> map)
	{
		OperationMessage entity = new OperationMessage();
		entity.setContent(type.getContent());
		if("lijunyang".equals(user.getEmail())){
			entity.setDepartment("产品研发部");
			entity.setRole("SVP");
		}else{
			entity.setDepartment(user.getDepartmentName());
			entity.setRole(user.getRole());
		}
		entity.setOperatorDepartmentId(user.getDepartmentId());
		entity.setOperatorId(user.getId());
		entity.setOperator(user.getRealName());
		Object o = map.get(PlatformConst.REQUEST_SCOPE_USER);
		User u = null;
		if (o != null) {
			u = (User) o;
		} else {
			u = user;
		}
		entity.setBelongDepartmentId(u.getDepartmentId());
		entity.setBelongUid(u.getId());
		entity.setBelongUname(u.getRealName());
		entity.setType(type.getType());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
		entity.setMessageType(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE)));
		entity.setUserData((Serializable) map.get(PlatformConst.REQUEST_SCOPE_USER_DATA));
		Object obj=map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_NUM);
		if(null!=obj){
			boolean flag=(boolean)obj;
			entity.setFlag(flag);
		}
		Object obj2 =map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_ASSISRCOLUMN);
		if(null!=obj2){
			String  assistColumn=(String)obj2;
			entity.setAssistColumn(assistColumn);
		}
		Integer module = type.getModule();
		entity.setModule(module == null ? OperationType.getModule(user.getRoleId()) : module);
		if(StringUtils.isNoneBlank(entity.getMessageType()) && entity.getMessageType().trim().length() >= 2 && "11".equals(entity.getMessageType().substring(0,2))){
			entity.setKeyword(u.getKeyword());
		}
		process(entity);
		
		return entity;
	}
}
