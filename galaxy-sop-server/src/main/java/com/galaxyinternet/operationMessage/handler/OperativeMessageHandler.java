package com.galaxyinternet.operationMessage.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class OperativeMessageHandler implements MessageHandler
{
	private Map<String,String> map = new HashMap<String,String>();
	public OperativeMessageHandler()
	{
		map.put("16.1", "需要填写上月运营数据。");
		map.put("16.2", "需要于每月1日开始填写上月的运营数据。");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public int getOrder()
	{
		return 16;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && map.containsKey(message.getMessageType());
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		StringBuffer content = new StringBuffer();
		content.append("项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append(map.get(message.getMessageType()));
		message.setContent(content.toString());
		return message;
	}

}
