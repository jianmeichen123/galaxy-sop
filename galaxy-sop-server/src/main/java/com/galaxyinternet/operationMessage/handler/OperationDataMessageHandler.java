package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class OperationDataMessageHandler implements MessageHandler{

	private static final long serialVersionUID = 1L;

	private String add_operation_data = "19.1";
	private String update_operation_data = "19.2";
	private String del_operation_data = "19.3";
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean support(OperationMessage message) {
		// TODO Auto-generated method stub
		return message != null && "19.1".equals(message.getMessageType()) || message != null && "19.2".equals(message.getMessageType())  || message != null && "19.3".equals(message.getMessageType());
			
	}

	@Override
	public OperationMessage handle(OperationMessage message) {
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator());
		if(message.getMessageType().equals(add_operation_data)){
			content.append("添加项目");
		}else if(message.getMessageType().equals(update_operation_data)){
			content.append("编辑项目");
		}else if(message.getMessageType().equals(del_operation_data)){
			content.append("删除项目");
		}
		content.append(ControllerUtils.getProjectNameLink(message));
		content.append("的运营数据");
		message.setContent(content.toString());
		return message;
	}

}
