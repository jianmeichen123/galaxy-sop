package com.galaxyinternet.operationMessage.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.handler.MessageHandler;
import com.galaxyinternet.model.operationMessage.OperationMessage;
@Component
public class MeetSchedulingMessageHandler implements MessageHandler
{
	private static final long serialVersionUID = 1L;
	
	/**
		11.1	进入CEO评审会会议排期
		11.2	进入立项会会议排期
		11.3	进入投决会会议排期
	*/		
	private String ceo_schedul_type = "11.1";
	private String lxh_schedul_type = "11.2";
	private String tjh_schedul_type = "11.3";
	
	@Override
	public int getOrder()
	{
		return 11;
	}

	@Override
	public boolean support(OperationMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType()) &&message.getMessageType().length()>1 && "11".equals(message.getMessageType().substring(0, 2));
	}

	@Override
	public OperationMessage handle(OperationMessage message)
	{
		// 项目 Utter绝对潮流  的  立项会   已被安排于   3月12日  16:00举行
		StringBuffer content = new StringBuffer();
		content.append("项目")
		.append(ControllerUtils.getProjectNameLink(message));
		if(message.getMessageType().equals(ceo_schedul_type)){
			content.append("的CEO评审会");
		}else if(message.getMessageType().equals(lxh_schedul_type)){
			content.append("的立项会");
		}else if(message.getMessageType().equals(tjh_schedul_type)){
			content.append("的投决会");
		}
		if(message.getKeyword().contains("cancle")){
			content.append("原已被安排于");
			content.append(message.getKeyword().replace("cancle", ""));
			content.append("的会议已取消");
		}
		if(message.getKeyword().contains("update")){
			String timestr = message.getKeyword().replace("update", "");
			if(StringUtils.isNotEmpty(timestr)){
				String time[] = timestr.split(",");
				content.append("原已被安排于");
				content.append(time[0]);
				content.append("的会议已重新被安排");
				content.append("，将于"+time[1]+"进行");
			}
			
		}
		if(message.getKeyword().contains("insert")){
			content.append("已被安排于");
			content.append(message.getKeyword().replace("insert", ""));
			content.append("举行");
		}
		if(!"operate".equals(message.getKeyword())){
			message.setContent(content.toString());
		}
		return message;
	}

}

