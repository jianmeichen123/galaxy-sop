package com.galaxyinternet.idea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.idea.AbandonedDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.Abandoned;
import com.galaxyinternet.service.AbandonedService;
@Service
public class AbandonedServiceImpl extends BaseServiceImpl<Abandoned>implements AbandonedService {

	@Autowired
	private AbandonedDao abandonedDao;
	@Override
	protected BaseDao<Abandoned, Long> getBaseDao() {
		return this.abandonedDao;
	}
	
	
	
}
