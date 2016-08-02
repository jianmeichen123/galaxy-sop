package com.galaxyinternet.common.taglib;

import java.util.List;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;

public class FXFunctionTags
{
	public static boolean hasRole(Long roleId)
	{
		return WebUtils.hasRole(roleId);
	}
	public static boolean isCreatedByUser(String recordType, Long id)
	{
		User user = WebUtils.getUserFromSession();
		if(user != null && recordType != null && id != null)
		{
			if(recordType.equalsIgnoreCase("project"))
			{
				ProjectService projectService = WebUtils.getBean(ProjectService.class);
				if(projectService != null)
				{
					Project project = projectService.queryById(id);
					if(project != null && project.getCreateUid() != null)
					{
						return project.getCreateUid().equals(user.getId());
					}
				}
			}
		}
		return false;
	}
	public static boolean inOwnDepart(String recordType, Long id)
	{
		User user = WebUtils.getUserFromSession();
		if(user != null && recordType != null && id != null)
		{
			if(recordType.equalsIgnoreCase("project"))
			{
				ProjectService projectService = WebUtils.getBean(ProjectService.class);
				if(projectService != null)
				{
					Project project = projectService.queryById(id);
					if(project != null && project.getProjectDepartid() != null)
					{
						return project.getProjectDepartid().equals(user.getDepartmentId());
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isDepart(Long depId)
	{
		if(depId != null)
		{
			User user = WebUtils.getUserFromSession();
			if(user != null && user.getDepartmentId() != null)
			{
				return user.getDepartmentId().equals(depId);
			}
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public static boolean isTransfering(Long projectId)
	{
		Cache cache = WebUtils.getBean(Cache.class);
		if(projectId != null && cache != null)
		{
			Object obj = cache.get(SopConstant.CACHE_SCOPE_TRANSFER_PROJECT);
			if(obj != null)
			{
				List<Long> projectIds = (List<Long>)obj;
				return projectIds.contains(projectId);
			}
		}
		return false;
	}
	
}
