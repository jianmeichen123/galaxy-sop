package com.galaxyinternet.operationMessage.handler;

import com.galaxyinternet.model.operationMessage.OperationMessage;

public abstract class AbstractMessageHandler
{
	protected String getProjectNameLink(OperationMessage message)
	{
		/*StringBuffer link = new StringBuffer();
		link.append("<a href=\"#\" class=\"blue project_name\" data-project-id=\"")
		.append(message.getProjectId())
		.append("\">")
		.append(message.getProjectName())
		.append("</a>");
		return link.toString();*/
		return "projectname";
	}
}
