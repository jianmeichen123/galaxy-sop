package com.galaxyinternet.hologram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationFixedTableDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.service.hologram.InformationFixedTableService;

@Service("informationFixedTableService")
public class InformationFixedTableServiceImpl  extends BaseServiceImpl<InformationFixedTable> implements InformationFixedTableService{

	@Autowired
	private InformationFixedTableDao informationFixedTableDao;

	@Override
	protected BaseDao<InformationFixedTable, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.informationFixedTableDao;
	}
	

	
}
