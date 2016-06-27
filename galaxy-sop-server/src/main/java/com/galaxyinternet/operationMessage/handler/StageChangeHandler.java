package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;

@Component
public class StageChangeHandler implements MessageHandler {

	private static final long serialVersionUID = 1L;
	public static final String _6_1_ = "6.1";
	public static final String _6_2_ = "6.2";
	public static final String _6_3_ = "6.3";
	public static final String _6_4_ = "6.4";
	public static final String _6_5_ = "6.5";
	public static final String _6_6_ = "6.6";
	public static final String _6_7_ = "6.7";
	public static final String _6_8_ = "6.8";
	public static final String _6_9_ = "6.9";
	public static final String _6_10_ = "6.10";

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean support(OperationMessage message) {
		return message != null && (_6_2_.equals(message.getMessageType()) 
				|| _6_3_.equals(message.getMessageType())
				|| _6_4_.equals(message.getMessageType()) 
				|| _6_5_.equals(message.getMessageType())
				|| _6_6_.equals(message.getMessageType()) 
				|| _6_7_.equals(message.getMessageType())
				|| _6_8_.equals(message.getMessageType()) 
				|| _6_9_.equals(message.getMessageType())
				|| _6_10_.equals(message.getMessageType()));
	}

	@Override
	public OperationMessage handle(OperationMessage message) {
		StringBuffer content = new StringBuffer();
		content.append("项目");
		content.append(ControllerUtils.getProjectNameLink(message));
		if(message.getMessageType().equals(_6_2_)){
			content.append("进入内部评审阶段");
		}else if(message.getMessageType().equals(_6_3_)){
			content.append("进入CEO评审阶段");
		}else if(message.getMessageType().equals(_6_4_)){
			content.append("进入立项会阶段");
		}else if(message.getMessageType().equals(_6_5_)){
			content.append("进入投资意向书阶段");
		}else if(message.getMessageType().equals(_6_6_)){
			content.append("进入尽职调查阶段");
		}else if(message.getMessageType().equals(_6_7_)){
			content.append("进入投资决策会阶段");
		}else if(message.getMessageType().equals(_6_8_)){
			content.append("进入投资协议阶段");
		}else if(message.getMessageType().equals(_6_9_)){
			content.append("进入股权交割阶段");
		}else if(message.getMessageType().equals(_6_10_)){
			content.append("进入投后运营阶段");
		}
		message.setContent(content.toString());
		return message;
	}

}
