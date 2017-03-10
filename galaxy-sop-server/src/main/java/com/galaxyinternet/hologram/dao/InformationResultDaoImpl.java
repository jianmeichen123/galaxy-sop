package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationResult;

@Repository("InformationResultDao")
public class InformationResultDaoImpl extends BaseDaoImpl<InformationResult, Long> implements InformationResultDao {

}
