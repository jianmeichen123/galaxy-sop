package com.galaxyinternet.hologram.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationTitleService;

@Service("com.galaxyinternet.service.hologram.InformationTitleService")
public class InformationTitleServiceImpl extends BaseServiceImpl<InformationTitle> implements InformationTitleService{

	@Autowired
	private InformationTitleDao informationTitleDao;
	
	@Override
	protected BaseDao<InformationTitle, Long> getBaseDao() {
		return this.informationTitleDao;
	}

	
	
}
