package com.galaxyinternet.operationMessage.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class ProHealthHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 	13.1	添加/编辑健康度为“高于预期”的健康度报告
		13.2	添加/编辑健康度为“正常”的健康度报告
		13.3	添加/编辑健康度为“预警”的健康度报告

	 */
	public static final String high_health = "13.1";
	public static final String normal_health = "13.2";
	public static final String warn_health = "13.3";
	public static final String start_health = "13.4";
	public static final String end_health = "13.5";
	
	@Override
	public int getOrder()
	{
		return 13;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType()) && message.getMessageType().startsWith("13") ;
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// <用户名>将项目<项目名称>的健康度评估为<健康度>。
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("将项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append("的健康度评估为");
		if(message.getMessageType().equals(high_health)){
			content.append("高于预期");
		}else if(message.getMessageType().equals(normal_health)){
			content.append("正常");
		}else if(message.getMessageType().equals(warn_health)){
			content.append("预警");
		}else if(message.getMessageType().equals(start_health)){
			content.append("初始");
		}else if(message.getMessageType().equals(end_health)){
			content.append("清算");
		}
		message.setContent(content.toString());
		return message;
	}

}

