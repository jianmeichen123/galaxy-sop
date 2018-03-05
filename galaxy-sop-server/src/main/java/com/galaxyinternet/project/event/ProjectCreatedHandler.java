package com.galaxyinternet.project.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationListdataService;

@Component
public class ProjectCreatedHandler implements ApplicationListener<ProjectCreatedEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(ProjectCreatedHandler.class);
	@Autowired
	private ProjectService projectService;
	@Autowired
	private InformationListdataService listdataService;

	@Override
	public void onApplicationEvent(ProjectCreatedEvent event)
	{
		Long projectId = event.getProjectId();
		if(projectId == null)
		{
			if(logger.isDebugEnabled())
			{
				logger.debug("No project id");
			}
			return;
		}
		Project project = projectService.queryById(projectId);
		Long userId = project.getCreateUid();
		
		InformationListdata query = new InformationListdata();
		query.setProjectId(projectId);
		query.setTime(1103L);
		query.setField1(userId+"");
		Long count = listdataService.queryCount(query);
		if(count >0)
		{
			return;
		}
		InformationListdata entity = new InformationListdata();
		entity.setProjectId(projectId);
		entity.setTitleId(1103L);
		entity.setField1(userId+"");
		entity.setField2("100");
		entity.setField5("0");
		listdataService.insert(entity);
	}
}
