package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.JointDeliveryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.JointDelivery;

@Repository("jointDeliveryDao")
public class JointDeliveryDaoImpl extends BaseDaoImpl<JointDelivery, Long> implements JointDeliveryDao{

}
