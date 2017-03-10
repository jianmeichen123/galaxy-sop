package com.galaxyinternet.hologram.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.service.hologram.InformationListdataRemarkService;

@Service("informationListdataRemarkService")
public class InformationListdataRemarkServiceImpl extends BaseServiceImpl<InformationListdataRemark> implements InformationListdataRemarkService{

	@Autowired
	private InformationListdataRemarkDao informationListdataRemarkDao;
	
	@Override
	protected BaseDao<InformationListdataRemark, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.informationListdataRemarkDao;
	}

}
