package com.galaxyinternet.service.hologram;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationData;

public interface InformationDataService extends BaseService<InformationData>
{
	public void save(InformationData data);
}
