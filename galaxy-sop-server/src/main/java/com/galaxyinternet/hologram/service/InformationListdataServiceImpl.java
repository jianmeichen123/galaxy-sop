package com.galaxyinternet.hologram.service;

import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.service.hologram.InformationListdataRemarkService;
import com.galaxyinternet.service.hologram.InformationListdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("informationListdataService")
public class InformationListdataServiceImpl extends BaseServiceImpl<InformationListdata> implements InformationListdataService {

	@Autowired
	private InformationListdataDao informationListdataDao;
	
	@Override
	protected BaseDao<InformationListdata, Long> getBaseDao() {

		return this.informationListdataDao;
	}

}
