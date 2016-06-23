package com.galaxyinternet.operationMessage.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class MeetMessageHandler extends AbstractMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public int getOrder()
	{
		return 4;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType()) && "4".equals(message.getMessageType().charAt(0));
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// <用户名>为项目<项目名称><添加/编辑>了<周/月/季度>运营会议纪要
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("为项目")
		.append(getProjectNameLink(message))
		.append(message.getContent())
		.append("了会议纪要");
		message.setContent(content.toString());
		return message;
	}

}

