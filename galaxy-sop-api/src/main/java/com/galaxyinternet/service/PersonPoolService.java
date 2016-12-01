package com.galaxyinternet.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;

public interface PersonPoolService extends BaseService<PersonPool>{
	
	public List<PersonPool> selectNoToTask(Map<String,Object> params);
	
	public Page<PersonPool> queryPageListByPid(PersonPoolBo query, Pageable pageable); 
	
	public Long addProjectPerson(PersonPoolBo pool);
	
	public List<PersonPool> selectPersonPoolByPID(Long pid) throws DaoException;

}
