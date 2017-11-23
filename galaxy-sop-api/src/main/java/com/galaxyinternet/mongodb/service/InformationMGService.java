package com.galaxyinternet.mongodb.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.mongodb.model.InformationDataMG;

public interface InformationMGService extends BaseService<InformationDataMG> {
	public void save(InformationDataMG data) throws CloneNotSupportedException;
	

}
