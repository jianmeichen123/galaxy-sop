package com.galaxyinternet.grant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.GrantPartDao;
import com.galaxyinternet.dao.GrantTotalDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.service.GrantTotalService;

@Service("com.galaxyinternet.grant.GrantTotalService")
public class GrantTotalServiceImpl extends BaseServiceImpl<GrantTotal> implements GrantTotalService{
	
	@Autowired
	private GrantTotalDao grantTotalDao;
	
	@Override
	protected BaseDao<GrantTotal, Long> getBaseDao() {
		return this.grantTotalDao;
	}
	
	@Autowired
	private GrantPartDao grantPartDao;

	@Override
	@Transactional
	public void removeGrantTotal(Long tid) {
		grantTotalDao.deleteById(tid);
		GrantPart part = new GrantPart();
		part.setTotalGrantId(tid);
		grantPartDao.delete(part);
	}

}
