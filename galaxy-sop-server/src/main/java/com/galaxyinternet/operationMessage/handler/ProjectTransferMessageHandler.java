package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class ProjectTransferMessageHandler implements MessageHandler
{

	private static final long serialVersionUID = 1L;
	public static final String MESSAGE_TYPE_APPLY = "15.1";
	public static final String MESSAGE_TYPE_REVOKE = "15.2";
	public static final String MESSAGE_TYPE_RECIVICE = "15.3";
	public static final String MESSAGE_TYPE_REFUSE = "15.4";
	@Override
	public int getOrder()
	{
		return 15;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && (
				MESSAGE_TYPE_APPLY.equals(message.getMessageType()) || 
				MESSAGE_TYPE_REVOKE.equals(message.getMessageType()) || 
				MESSAGE_TYPE_RECIVICE.equals(message.getMessageType()) || 
				MESSAGE_TYPE_REFUSE.equals(message.getMessageType())
				);
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator());
		if(MESSAGE_TYPE_APPLY.equals(message.getMessageType()))
		{
			content.append("移交了");
		}
		else if(MESSAGE_TYPE_REVOKE.equals(message.getMessageType()))
		{
			content.append("撤回移交");
		}
		else if(MESSAGE_TYPE_RECIVICE.equals(message.getMessageType()))
		{
			content.append("接收了");
		}else if(MESSAGE_TYPE_REFUSE.equals(message.getMessageType()))
		{
			content.append("拒接了");
		}
		content.append("项目");
		content.append(ControllerUtils.getProjectNameLink(message));
		message.setContent(content.toString());
		return message;
	}

}
