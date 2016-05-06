package com.galaxyinternet.timer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.timer.PassRateDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.service.PassRateService;


@Service("com.galaxyinternet.service.PassRateService")
public class PassRateServiceImpl extends BaseServiceImpl<PassRate> implements PassRateService {
	
	@Autowired
	private PassRateDao passRateDao;
	
	@Override
	protected BaseDao<PassRate, Long> getBaseDao() {
		return this.passRateDao;
	}
}
