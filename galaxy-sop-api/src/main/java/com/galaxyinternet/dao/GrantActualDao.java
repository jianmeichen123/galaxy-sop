package com.galaxyinternet.dao;

import java.util.Map;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.GrantActual;

public interface GrantActualDao extends BaseDao<GrantActual, Long>{
	
	/**
	 * 查看实际注资信息
	 */
	Map<String, Object> lookActualDetail(Long actualId);
	
	double sumBelongToActualMoney(Long partId);
}
