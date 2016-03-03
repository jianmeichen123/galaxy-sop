package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;

public interface ProjectService extends BaseService<Project> {
	
	/**
	 * 添加项目
	 * 对于每个新添加的项目来讲，整个生命周期中所关联的文档已确定，这里需要一并添加
	 */
	public long newProject(Project project) throws Exception;

}