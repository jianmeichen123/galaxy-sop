package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class CreateProjectMessageHandler extends AbstractMessageHandler implements MessageHandler
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && "1".equals(message.getMessageType());
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		StringBuffer content = new StringBuffer();
		content.append("添加了项目")
		.append(getProjectNameLink(message));
		message.setContent(content.toString());
		return message;
	}

	@Override
	public int getOrder()
	{
		return 1;
	}
}
