package com.galaxyinternet.operationMessage.handler;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;


@Component
public class MoneyManagerMessageHandler implements MessageHandler {
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 14.1添加/编辑/删除总注资计划
	 */
	public static final String MONEY_ALL = "14.1";
	/**
	 * 14.2添加/编辑/删除分期注资计划
	 */
	public static final String MONEY_SEPARATE_ = "14.2";
	/**
	 * 14.3添加/编辑/删除实际注资信息
	 */
	public static final String MONEY_IMPLEMENT_ = "14.3";


	@Override
	public int getOrder(){
		return 14;
	}

	@Override
	public boolean support(OperationMessage message){
		return message != null && StringUtils.isNotBlank(message.getMessageType()) && message.getMessageType().startsWith("14");
	}

	@Override
	public OperationMessage handle(OperationMessage message){
		/*
		14.1添加/编辑/删除总注资计划：<日期时间>  <用户名>添加/编辑/删除项目<项目名称>的总注资计划。
		14.2添加/编辑/删除分期注资计划：<日期时间>  <用户名>添加/编辑/删除项目<项目名称>的分期注资计划。
		14.3添加/编辑／删除实际注资信息：<日期时间>  <用户名>添加／编辑/删除项目<项目名称>的实际注资信息。
		 */
		
		String title = "";
		if(message.getMessageType().equals(MONEY_ALL)){
			title = "总注资计划";
		}else if(message.getMessageType().equals(MONEY_SEPARATE_)){
			title = "分期注资计划";
		}else if(message.getMessageType().equals(MONEY_IMPLEMENT_)){
			title = "实际注资信息";
		}else
			return null;
		
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append(message.getContent())
		.append("项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append("的")
		.append(title);
		
		message.setContent(content.toString());
		return message;
	}


}
