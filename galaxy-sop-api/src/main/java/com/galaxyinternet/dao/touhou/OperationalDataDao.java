package com.galaxyinternet.dao.touhou;

import com.galaxyinternet.bo.OperationalDataBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.touhou.OperationalData;

public interface OperationalDataDao extends BaseDao<OperationalData, Long> {
	Page<OperationalDataBo> selectOperationalDataPageList(OperationalDataBo query, PageRequest pageRequest);
	
}
