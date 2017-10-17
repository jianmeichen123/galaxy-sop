package com.galaxyinternet.dao.hologram;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationListdata;

public interface InformationListdataDao extends BaseDao<InformationListdata, Long>{

	 double selectTotalMoney(InformationListdata entity);
	 double selectActualMoney(InformationListdata entity);
	 /**
	  * 项目移交修改create_uid
	  */
	 int updateCreateUid(InformationListdata ir);
}
