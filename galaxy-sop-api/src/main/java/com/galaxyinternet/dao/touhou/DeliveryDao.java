package com.galaxyinternet.dao.touhou;

import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.touhou.Delivery;

public interface DeliveryDao extends BaseDao<Delivery, Long> {

	Page<DeliveryBo> selectDeliveryPageList(DeliveryBo query, PageRequest pageRequest);
	
	
}