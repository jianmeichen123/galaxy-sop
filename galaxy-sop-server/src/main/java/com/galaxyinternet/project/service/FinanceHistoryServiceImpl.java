package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.FinanceHistoryDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.FinanceHistory;
import com.galaxyinternet.service.FinanceHistoryService;

@Service("com.galaxyinternet.service.FinanceHistoryService")
public class FinanceHistoryServiceImpl extends BaseServiceImpl<FinanceHistory> implements FinanceHistoryService{
	
	@Autowired
	private FinanceHistoryDao financeHistoryDao;
	
	@Override
	protected BaseDao<FinanceHistory, Long> getBaseDao() {
		return this.financeHistoryDao;
	}
}
