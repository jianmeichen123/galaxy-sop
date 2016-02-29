package com.galaxyinternet.service;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;

public interface PersonPoolService extends BaseService<PersonPool>{
	
	
	public Long savePersonToProject(PersonPoolBo pool) throws Exception;

}
