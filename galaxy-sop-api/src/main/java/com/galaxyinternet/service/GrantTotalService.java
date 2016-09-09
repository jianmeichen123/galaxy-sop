package com.galaxyinternet.service;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantTotal;

public interface GrantTotalService extends BaseService<GrantTotal>{
	public Map<String,Object> setApprProcess(Long query);
	 public Double sumProjectToActualMoney(Long query);
}
