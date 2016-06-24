package com.galaxyinternet.operationMessage.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class MeetMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 *  4.1	添加/编辑内评会会议纪要
		4.2	添加/编辑CEO评审会会议纪要
		4.3	添加/编辑立项会会议纪要
		4.4	添加/编辑投决会会议纪要	
	 */
	private String lph_message_type = "4.1";
	private String ceo_message_type = "4.2";
	private String lxh_message_type = "4.3";
	private String tjh_message_type = "4.3";
	
	@Override
	public int getOrder()
	{
		return 4;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType()) && "4".equals(message.getMessageType().charAt(0));
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// <用户名>为项目<项目名称><添加/编辑>了<周/月/季度>运营会议纪要
		StringBuffer content = new StringBuffer();
		content.append(message.getOperator())
		.append("为项目")
		.append(ControllerUtils.getProjectNameLink(message))
		.append(message.getContent());
		if(message.getMessageType().equals(lph_message_type)){
			content.append("了内评会会议纪要");
		}else if(message.getMessageType().equals(ceo_message_type)){
			content.append("了CEO评审会会议纪要");
		}else if(message.getMessageType().equals(lxh_message_type)){
			content.append("了立项会会议纪要");
		}else if(message.getMessageType().equals(tjh_message_type)){
			content.append("了投决会会议纪要");
		}
		message.setContent(content.toString());
		return message;
	}

}

