package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class TaskUrgedMessageHandler implements MessageHandler
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 7.1	催办人事尽调任务
	 */
	private String MESSAGE_TYPE_CBRSJDRW = "7.1";
	/**
	 * 7.2	催办财务尽调任务
	 */
	private String MESSAGE_TYPE_CBCWJDRW = "7.2";
	/**
	 * 7.3	催办法务尽调任务
	 */
	private String MESSAGE_TYPE_CBFWJDRW = "7.3";
	@Override
	public int getOrder()
	{
		return 7;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && (MESSAGE_TYPE_CBRSJDRW.equals(message.getMessageType()) || MESSAGE_TYPE_CBCWJDRW.equals(message.getMessageType()) || MESSAGE_TYPE_CBFWJDRW.equals(message.getMessageType()));
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("催办了项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append("的");
		if(MESSAGE_TYPE_CBRSJDRW.equals(message.getMessageType()))
		{
			content.append(SopConstant.TASK_NAME_RSJD);
		}
		else if(MESSAGE_TYPE_CBCWJDRW.equals(message.getMessageType()))
		{
			content.append(SopConstant.TASK_NAME_CWJD);
		}
		else if(MESSAGE_TYPE_CBFWJDRW.equals(message.getMessageType()))
		{
			content.append(SopConstant.TASK_NAME_FWJD);
		}
		content.append("任务");
		message.setContent(content.toString());
		return message;
	}

}
