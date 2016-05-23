package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.common.Config;

public interface ConfigService extends BaseService<Config> {
	
	
	/**
	 * 为了防止并发添加项目，生成的code一样，必须每次生成就自动加一
	 * @return
	 * @throws Exception
	 */
	public Config createCode() throws Exception;
	
	public Config getByKey(String key, boolean createIfNotExist) throws Exception;
	
}