package com.galaxyinternet.service;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantActual;

public interface GrantActualService extends BaseService<GrantActual>{
	
	/**
	 * 查看实际拨款信息
	 */
	Map<String, Object> lookActualDetail(Long actualId);
	
	
	/**
	 * 计算实际拨款金额的总和
	 */
	double calculateBelongToActualMoney(Long partId);
}
