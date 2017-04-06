package com.galaxyinternet.hologram.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galaxyinternet.dao.hologram.InformationFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationFile;
import com.galaxyinternet.service.hologram.InformationFileService;

@Service("informationFileService")
public class InformationFileServiceImpl extends BaseServiceImpl<InformationFile> implements InformationFileService{

	@Autowired
	private InformationFileDao informationFileDao;
	
	@Override
	protected BaseDao<InformationFile, Long> getBaseDao() {
		return this.informationFileDao;
	}

}
