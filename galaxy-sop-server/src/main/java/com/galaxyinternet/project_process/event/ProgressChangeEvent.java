package com.galaxyinternet.project_process.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.model.project.Project;

public class ProgressChangeEvent extends ApplicationEvent
{

	private static final long serialVersionUID = 8650121629972349120L;
	private projectProgress next;
	public ProgressChangeEvent(Project project, projectProgress next)
	{
		super(project);
		this.next = next;
		Assert.notNull(project, "Project can not be null");
		Assert.notNull(next, "Next progress can not be null");
	}
	public Project getProject()
	{
		return (Project)getSource();
	}
	public projectProgress getNext()
	{
		return next;
	}


}
