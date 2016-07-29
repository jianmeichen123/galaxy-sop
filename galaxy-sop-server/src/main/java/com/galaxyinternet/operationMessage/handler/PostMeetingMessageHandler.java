package com.galaxyinternet.operationMessage.handler;

import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;

public class PostMeetingMessageHandler implements MessageHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean support(OperationMessage message) {
		// TODO Auto-generated method stub
		return message.getMessageType().startsWith("12.");
	}

	@Override
	public OperationMessage handle(OperationMessage message) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
