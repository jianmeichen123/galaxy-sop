package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.FinanceHistoryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.FinanceHistory;

@Repository("financeHistoryDao")
public class FinanceHistoryDaoImpl extends BaseDaoImpl<FinanceHistory, Long> implements FinanceHistoryDao{

	

}
