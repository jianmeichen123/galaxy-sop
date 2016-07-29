package com.galaxyinternet.operationMessage.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class OperatMeetingMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	private final Map<String,String> map = new HashMap<>();
	public OperatMeetingMessageHandler()
	{
		map.put("12.1", "周运营会议纪要");
		map.put("12.2", "月运营会议纪要");
		map.put("12.3", "季度运营会议纪要");
	}
	

	@Override
	public int getOrder()
	{
		return 12;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && map.containsKey(message.getMessageType());
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		//<用户名>为项目<项目名称><添加/编辑>了<周/月/季度>运营会议纪要
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("为项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append(message.getContent())
		.append("了")
		.append(map.get(message.getMessageType()));
		message.setContent(content.toString());
		return message;
	}

}
