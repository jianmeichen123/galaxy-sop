package com.galaxyinternet.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.common.ConfigDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.service.ConfigService;

@Service("com.galaxyinternet.service.ConfigService")
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements ConfigService {
	
	@Autowired
	private ConfigDao configDao;

	@Override
	protected BaseDao<Config, Long> getBaseDao() {
		return this.configDao;
	}

	@Override
	@Transactional
	public Config createCode() throws Exception {
		Config config = configDao.selectById(1L);
		config.setValue(String.valueOf(Integer.parseInt(config.getValue()) + 1));
		configDao.updateById(config);
		return config;
	}

}
