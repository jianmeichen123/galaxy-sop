package com.galaxyinternet.common.dao;

import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.common.ConfigDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.common.Config;

@Repository("configDao")
public class ConfigDaoImpl extends BaseDaoImpl<Config, Long> implements ConfigDao  {

	
}
