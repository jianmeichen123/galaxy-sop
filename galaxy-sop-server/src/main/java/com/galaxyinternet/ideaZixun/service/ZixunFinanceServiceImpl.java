package com.galaxyinternet.ideaZixun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.idea.ZixunFinanceDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.ZixunFinance;
import com.galaxyinternet.service.ZixunFinanceService;


@Service("com.galaxyinternet.service.ZixunFinanceService")
public class ZixunFinanceServiceImpl extends BaseServiceImpl<ZixunFinance> implements ZixunFinanceService{
	
	@Autowired
	private ZixunFinanceDao zixunFinanceDao;

	 
	@Override
	protected BaseDao<ZixunFinance, Long> getBaseDao() {
		return this.zixunFinanceDao;
	}


	
}