package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.FinanceHistory;

public interface FinanceHistoryService extends BaseService<FinanceHistory>{
	 
	public String getFHStr(Long projectId);

}
