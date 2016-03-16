package com.galaxyinternet.service;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;

public interface PersonPoolService extends BaseService<PersonPool>{
	
	
	public Long savePersonToProject(PersonPoolBo pool) throws Exception;
	
	public Page<PersonPool> queryPageListByPid(PersonPoolBo query, Pageable pageable); 

}
