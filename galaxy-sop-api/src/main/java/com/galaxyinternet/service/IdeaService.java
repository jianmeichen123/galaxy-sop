package com.galaxyinternet.service;

import com.galaxyinternet.bo.IdeaBo;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.idea.Idea;

public interface IdeaService extends BaseService<Idea> {
	
	public void createProject(Long id, String projectName) throws BusinessException;
	
	public int updateById(IdeaBo idea);
}
