package com.galaxyinternet.touhou.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.touhou.DeliveryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.touhou.Delivery;


@Repository("deliveryDao")
public class DeliveryDaoImpl extends BaseDaoImpl<Delivery, Long> implements DeliveryDao {
	

}