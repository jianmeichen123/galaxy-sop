package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationOperationTimeDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationOperationTime;

@Repository("informationOperationTimeDao")
public class InformationOperationTimeDaoImpl extends BaseDaoImpl<InformationOperationTime, Long> implements InformationOperationTimeDao{

}
