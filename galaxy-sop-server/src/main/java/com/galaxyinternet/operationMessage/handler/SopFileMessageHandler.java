package com.galaxyinternet.operationMessage.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.sopfile.SopParentFile;

@Component
public class SopFileMessageHandler implements MessageHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//投资意向书
	public static final String _5_2_ = "5.2";
	//投资意向书签署凭证
	public static final String _5_3_ = "5.3";
	//业务尽职调查报告
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
		return message.getMessageType().startsWith("5.") || message.getMessageType().equals("6.1");
	}

	@Override
	public OperationMessage handle(OperationMessage message) {
//		OperationMessage [department=人工智能,
//				role=投资经理, type=项目, 
//				projectName=leung, operator=人工智能-投资经理one,
//				content=更新, module=null, projectId=5, operatorId=104]

		SopParentFile sopFile = (SopParentFile) message.getUserData();
		if(sopFile!=null){
			if(message.getMessageType().equals("6.1")){
				
				List<String> messageList = message.getMessageList() == null ? new ArrayList<String>() : message.getMessageList();
				StringBuffer content = new StringBuffer();
				content.append(message.getOperator());
				content.append("为项目");
				content.append(ControllerUtils.getProjectNameLink(message));
				content.append("创建" + "了");
				content.append(sopFile.getfWorktype());
				content.append("《");
				content.append(sopFile.getFileName() + "." + sopFile.getFileSuffix());
				content.append("》");	
				messageList.add(content.toString());
				message.setMessageList(messageList);
			}else{
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
			}	
		}
		return message;
		
	}
	
	
	
}
