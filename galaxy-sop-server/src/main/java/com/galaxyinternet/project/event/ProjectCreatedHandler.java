package com.galaxyinternet.project.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationListdataService;

@Component
public class ProjectCreatedHandler implements ApplicationListener<ProjectCreatedEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(ProjectCreatedHandler.class);
	@Autowired
	private Cache cache;
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
		Long depId = project.getProjectDepartid();
		String userName = project.getCreateUname();
		String depName = cache.hget(PlatformConst.CACHE_PREFIX_DEP+depId, "name")+"";
		Object managerId = cache.hget(PlatformConst.CACHE_PREFIX_DEP+depId, "manager");
		String managerName = cache.hget(PlatformConst.CACHE_PREFIX_USER+managerId, "realName")+"";
		
		InformationListdata query = new InformationListdata();
		query.setProjectId(projectId);
		query.setTime(1103L);
		query.setField6(userId+"");
		Long count = listdataService.queryCount(query);
		if(count >0)
		{
			return;
		}
		InformationListdata entity = new InformationListdata();
		entity.setProjectId(projectId);
		entity.setTitleId(1103L);
		entity.setField1(userName);
		entity.setField2("100");
		entity.setField3(depName);
		entity.setField4(managerName);
		entity.setField5("0");
		entity.setField6(userId+"");
		listdataService.insert(entity);
	}
}
