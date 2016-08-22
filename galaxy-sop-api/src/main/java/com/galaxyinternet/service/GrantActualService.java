package com.galaxyinternet.service;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantActual;

public interface GrantActualService extends BaseService<GrantActual>{
	
	/**
	 * 查看实际拨款信息
	 */
	Map<String, Object> lookActualDetail(Long actualId);

}
