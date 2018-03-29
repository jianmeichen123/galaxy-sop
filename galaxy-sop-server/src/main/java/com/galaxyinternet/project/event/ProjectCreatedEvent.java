package com.galaxyinternet.project.event;

import org.springframework.context.ApplicationEvent;

public class ProjectCreatedEvent extends ApplicationEvent
{

	private static final long serialVersionUID = 1L;
	public ProjectCreatedEvent(Long projectId)
	{
		super(projectId);
	}
	
	public Long getProjectId()
	{
		return (Long)getSource();
	}


}
