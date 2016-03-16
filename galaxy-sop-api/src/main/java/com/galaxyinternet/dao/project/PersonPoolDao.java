package com.galaxyinternet.dao.project;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.project.PersonPool;

public interface PersonPoolDao extends BaseDao<PersonPool, Long> {

	public Page<PersonPool> selectByPid(PersonPoolBo query,Pageable pageable);
		
}