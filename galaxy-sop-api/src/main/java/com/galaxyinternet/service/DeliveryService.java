package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.Delivery;

public interface DeliveryService extends BaseService<Delivery> {
	
	public Delivery selectDelivery(Long deliveryId);
	
	public Long insertDelivery(Delivery delivery);
	
	public Long updateDelivery(Delivery delivery);
}
