package com.galaxyinternet.grant.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.GrantPartDao;
import com.galaxyinternet.dao.GrantTotalDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
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

	@Override
	public Map<String,Object> setApprProcess(Long query) {
		Map<String,Object> map=new HashMap<String, Object>();
		// TODO Auto-generated method stub
		Double sumPlanMoney = grantTotalDao.sumPlanMoney(query);
		Double sumProjectToActualMoney = grantTotalDao.sumProjectToActualMoney(query);
		map.put("sumPlanMoney", sumPlanMoney);
		map.put("sumActualMoney", sumProjectToActualMoney);
		return map;
	}
	 public Double sumProjectToActualMoney(Long query){
		 Double sumProjectToActualMoney = grantTotalDao.sumProjectToActualMoney(query);
		 return sumProjectToActualMoney;
	 }
}
