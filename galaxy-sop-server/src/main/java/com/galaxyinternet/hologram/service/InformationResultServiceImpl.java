package com.galaxyinternet.hologram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.service.hologram.InformationResultService;

@Service("informationResultService")
public class InformationResultServiceImpl extends BaseServiceImpl<InformationResult> implements InformationResultService{

	@Autowired
	private InformationResultDao informationResultDao;
	
	@Override
	protected BaseDao<InformationResult, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.informationResultDao;
	}

}
