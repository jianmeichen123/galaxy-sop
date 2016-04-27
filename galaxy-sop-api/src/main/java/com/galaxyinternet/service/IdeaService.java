package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.idea.Idea;

public interface IdeaService extends BaseService<Idea> {
	public void createProject(Long id, String projectName) throws BusinessException;
}
