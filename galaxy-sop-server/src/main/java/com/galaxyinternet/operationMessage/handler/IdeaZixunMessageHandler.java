package com.galaxyinternet.operationMessage.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class IdeaZixunMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	
	public static final String idea_zixun_type = "18";
	
	@Override
	public int getOrder()
	{
		return 18;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType()) && message.getMessageType().startsWith(idea_zixun_type);
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// <用户名>添加/编辑/删除项目资讯<创意资讯编码>
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append(message.getContent())
		.append(ControllerUtils.getIdeaZixunCodeLink(message));
		
		message.setContent(content.toString());
		return message;
	}

}

