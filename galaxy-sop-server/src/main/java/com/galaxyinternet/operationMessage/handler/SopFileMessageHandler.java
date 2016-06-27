package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopParentFile;

@Component
public class SopFileMessageHandler implements MessageHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String _5_2_ = "5.2";
	public static final String _5_3_ = "5.3";
	public static final String _5_4_ = "5.4";
	//投资协议
	public static final String _5_8_ = "5.8";
	//投资协议签署凭证
	public static final String _5_9_ = "5.9";
	//股权转让协议
	public static final String _5_12_ = "5.12";
	//股权转让协议签署凭证
	public static final String _5_13_ = "5.13";

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean support(OperationMessage message) {
		return message.getMessageType().startsWith("5.");
	}

	@Override
	public OperationMessage handle(OperationMessage message) {
//		OperationMessage [department=人工智能,
//				role=投资经理, type=项目, 
//				projectName=leung, operator=人工智能-投资经理one,
//				content=更新, module=null, projectId=5, operatorId=104]

		SopParentFile sopFile = (SopParentFile) message.getUserData();
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator());
		content.append("为项目");
		content.append(ControllerUtils.getProjectNameLink(message));
		content.append(message.getContent() + "了");
		content.append(sopFile.getfWorktype());
		content.append("《");
		content.append(sopFile.getFileName() + "." + sopFile.getFileSuffix());
		content.append("》");	
		message.setContent(content.toString());
		return message;
	}
	
	
	
}
