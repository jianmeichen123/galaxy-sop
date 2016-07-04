package com.galaxyinternet.touhou.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.touhou.DeliveryFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.touhou.DeliveryFile;


@Repository("deliveryFileDao")
public class DeliveryFileDaoImpl extends BaseDaoImpl<DeliveryFile, Long> implements DeliveryFileDao {

	
}