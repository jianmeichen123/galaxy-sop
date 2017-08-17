package com.galaxyinternet.dao.hologram;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationListdata;

public interface InformationListdataDao extends BaseDao<InformationListdata, Long>{

	 double selectTotalMoney(InformationListdata entity);
}
