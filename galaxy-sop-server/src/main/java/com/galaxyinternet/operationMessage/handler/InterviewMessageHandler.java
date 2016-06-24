package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class InterviewMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public int getOrder()
	{
		return 3;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && "3".equals(message.getMessageType());
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// <用户名>为项目<项目名称><添加/编辑>了<周/月/季度>运营会议纪要
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("为项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append(message.getContent())
		.append("了访谈记录");
		message.setContent(content.toString());
		return message;
	}

}

