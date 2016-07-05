package com.galaxyinternet.service;

import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.Delivery;

public interface DeliveryService extends BaseService<Delivery> {
	
	public Delivery selectDelivery(Long deliveryId);
	
	public Long insertDelivery(Delivery delivery);
	
	public Long updateDelivery(Delivery delivery);

	public void delDeliveryById(Long deliverid);
	
	public Page<DeliveryBo> queryDeliveryPageList(DeliveryBo query, PageRequest pageRequest);

	
}
