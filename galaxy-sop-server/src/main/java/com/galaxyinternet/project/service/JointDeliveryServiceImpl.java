package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.JointDeliveryDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.JointDelivery;
import com.galaxyinternet.service.JointDeliveryService;

@Service("com.galaxyinternet.service.jointDeliveryService")
public class JointDeliveryServiceImpl extends BaseServiceImpl<JointDelivery> implements JointDeliveryService{

	@Autowired
	private JointDeliveryDao jointDeliveryDao;
	
	@Override
	protected BaseDao<JointDelivery, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.jointDeliveryDao;
	}

}
