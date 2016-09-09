package com.galaxyinternet.dao.project;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.project.PersonPool;

public interface PersonPoolDao extends BaseDao<PersonPool, Long> {

	public Page<PersonPool> selectByPid(PersonPoolBo query,Pageable pageable);
	
	public List<PersonPool> selectNoToTask(Map<String,Object> params);
	
	/**
	 * 根据名称查询项目ID
	 * @param personPool
	 * @return
	 */
	public List<PersonPool> selectProjectIdByPersonName(PersonPool personPool);
		
}