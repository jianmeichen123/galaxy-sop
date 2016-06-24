package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class CreateProjectMessageHandler implements MessageHandler
{

	private String add_project = "1";
	private String update_project = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && "1".equals(message.getMessageType()) || message != null && "2".equals(message.getMessageType());
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator());
		if(message.getMessageType().equals(add_project)){
			content.append("添加了项目");
		}else if(message.getMessageType().equals(update_project)){
			content.append("编辑了项目");
		}
		content.append(ControllerUtils.getProjectNameLink(message));
		message.setContent(content.toString());
		return message;
	}

	@Override
	public int getOrder()
	{
		return 1;
	}
}
