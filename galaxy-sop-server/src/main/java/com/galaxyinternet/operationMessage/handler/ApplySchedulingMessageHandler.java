package com.galaxyinternet.operationMessage.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class ApplySchedulingMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  10.1	申请CEO评审会议排期
		10.3	申请投决会会议排期	
	*/
	public static final String ceo_apply_type = "10.1";
	public static final String tjh_apply_type = "10.3";
	
	@Override
	public int getOrder()
	{
		return 10;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType())&&message.getMessageType().length()>1 && "10".equals(message.getMessageType().substring(0,2));
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// <用户名>为项目<项目名称><添加/编辑>了<周/月/季度>运营会议纪要
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("为项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append(message.getContent());
		message.setContent(content.toString());
		return message;
	}

}

