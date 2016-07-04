package com.galaxyinternet.touhou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.touhou.DeliveryDao;
import com.galaxyinternet.dao.touhou.DeliveryFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.service.DeliveryService;


@Service("com.galaxyinternet.touhou.service.DeliveryServiceImpl")
public class DeliveryServiceImpl extends BaseServiceImpl<Delivery> implements DeliveryService {

	@Autowired
	private DeliveryDao deliveryDao;
	
	@Autowired
	private DeliveryFileDao deliveryFileDao;
	
	
	@Override
	protected BaseDao<Delivery, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.deliveryDao;
	}
	
	
	
	@Override
	public Delivery selectDelivery(Long deliveryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long insertDelivery(Delivery delivery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long updateDelivery(Delivery delivery) {
		// TODO Auto-generated method stub
		return null;
	}

	


}

