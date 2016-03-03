package com.galaxyinternet.HumanResourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.HR.PersonLearnDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.HR.personLearn;
import com.galaxyinternet.service.PersonLearnService;

@Service("com.galaxyinternet.service.PersonLearnService")
public class PersonLearnServiceImpl extends BaseServiceImpl<personLearn> implements PersonLearnService{
	
	@Autowired
	private PersonLearnDao personLearnDao;

	@Override
	protected BaseDao<personLearn, Long> getBaseDao() {
		// 
		return this.personLearnDao;
	}

}
