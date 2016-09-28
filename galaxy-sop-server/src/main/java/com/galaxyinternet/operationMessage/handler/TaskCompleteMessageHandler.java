package com.galaxyinternet.operationMessage.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class TaskCompleteMessageHandler implements MessageHandler
{
	private Map<String,String> map = new HashMap<String,String>();
	public TaskCompleteMessageHandler()
	{
		map.put("9.1", SopConstant.TASK_NAME_RSJD);
		map.put("9.2", SopConstant.TASK_NAME_CWJD);
		map.put("9.3", SopConstant.TASK_NAME_FWJD);
		map.put("9.4", SopConstant.TASK_NAME_GSBG);
		map.put("9.5", SopConstant.TASK_NAME_ZJBF);
		map.put("9.6", SopConstant.TASK_NAME_YWJD);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getOrder()
	{
		return 9;
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
		content.append(message.getOperator())
		.append("完成了项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append("的")
		.append(map.get(message.getMessageType()))
		.append("任务");
		message.setContent(content.toString());
		return message;
	}

}
