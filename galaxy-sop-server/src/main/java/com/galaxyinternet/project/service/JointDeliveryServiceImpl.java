package com.galaxyinternet.project.service;

import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.JointDelivery;
import com.galaxyinternet.service.JointDeliveryService;

@Service("jointDeliveryService")
public class JointDeliveryServiceImpl extends BaseServiceImpl<JointDelivery> implements JointDeliveryService{

	@Override
	protected BaseDao<JointDelivery, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
