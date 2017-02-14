package com.galaxyinternet.dao.touhou;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.touhou.OperationalData;

public interface OperationalDataDao extends BaseDao<OperationalData, Long> {
	Page<OperationalData> selectOperationalDataPageList(OperationalData query, PageRequest pageRequest);
	
}
