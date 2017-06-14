package com.galaxyinternet.project_process.event;

import org.springframework.context.ApplicationEvent;

import com.galaxyinternet.model.project.Project;

public class RejectEvent extends ApplicationEvent
{

	private static final long serialVersionUID = 1L;
	public RejectEvent(Object source)
	{
		super(source);
	}
	
	public Project getProject()
	{
		return (Project)getSource();
	}


}
