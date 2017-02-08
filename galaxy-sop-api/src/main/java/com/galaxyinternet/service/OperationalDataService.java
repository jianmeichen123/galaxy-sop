package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.OperationalData;

public interface OperationalDataService extends BaseService<OperationalData>{
	public Long insertOperationalData(OperationalData operationalData);
	public OperationalData selectOperationalDataById(Long operationalDataId);
	public void deleteOperationalDataById(Long operationalDataId);
}
