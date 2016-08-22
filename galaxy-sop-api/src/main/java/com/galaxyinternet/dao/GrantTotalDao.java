package com.galaxyinternet.dao;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.GrantTotal;

public interface GrantTotalDao extends BaseDao<GrantTotal, Long>{
	//计算计划总金额
        public Double sumPlanMoney(Long query);
}
