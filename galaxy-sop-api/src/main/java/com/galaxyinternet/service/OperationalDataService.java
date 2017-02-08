package com.galaxyinternet.service;

import com.galaxyinternet.bo.OperationalDataBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.touhou.OperationalData;

public interface OperationalDataService extends BaseService<OperationalData>{
	public Long insertOperationalData(OperationalData operationalData);
	public OperationalData selectOperationalDataById(Long operationalDataId);
	public void deleteOperationalDataById(Long operationalDataId);
	public Page<OperationalDataBo> queryOperationalDataPageList(OperationalDataBo query, PageRequest pageRequest);

}
