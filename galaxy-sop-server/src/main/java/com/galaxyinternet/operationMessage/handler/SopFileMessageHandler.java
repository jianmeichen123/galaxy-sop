package com.galaxyinternet.operationMessage.handler;

import org.springframework.stereotype.Component;

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
		String currentDate = DateUtil.longToString(System.currentTimeMillis());
		StringBuffer content = new StringBuffer();
		content.append(currentDate + " ");
		content.append(message.getOperator());
		content.append("为项目");
		content.append(message.getProjectName());
		content.append(message.getContent() + "了");
		content.append(sopFile.getfWorktype());
		content.append("《");
		content.append(sopFile.getFileName() + "." + sopFile.getFileSuffix());
		content.append("》");	
		message.setContent(content.toString());
		return message;
	}
	
	
	
}
