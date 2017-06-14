package com.galaxyinternet.project_process.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.model.project.Project;
@Component
public class ProgressChangeEventListener implements ApplicationListener<ProgressChangeEvent>
{

	@Override
	public void onApplicationEvent(ProgressChangeEvent event)
	{
		process(event.getProject(), event.getNext());
		
	}
	
	private void process(Project project, projectProgress next)
	{
		
	}

}
