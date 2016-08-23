package com.galaxyinternet.dao;

import java.util.List;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.GrantPart;

public interface GrantPartDao extends BaseDao<GrantPart, Long>{
	
	double sumBelongToPartMoney(Long totalId);
	
	List<GrantPart> selectHasActualMoney(GrantPart part);
	
}
