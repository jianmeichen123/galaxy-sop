package com.galaxyinternet.standard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.StandardDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.Standard;
import com.galaxyinternet.service.StandardService;
@Service
public class StandardServiceImpl extends BaseServiceImpl<Standard> implements StandardService
{
	@Autowired
	private StandardDao dao;
	
	@Override
	protected BaseDao<Standard, Long> getBaseDao()
	{
		return dao;
	}

}
