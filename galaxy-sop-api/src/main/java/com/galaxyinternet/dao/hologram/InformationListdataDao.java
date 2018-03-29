package com.galaxyinternet.dao.hologram;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationListdata;

import java.util.List;
import java.util.Map;

public interface InformationListdataDao extends BaseDao<InformationListdata, Long>{

	 double selectTotalMoney(InformationListdata entity);
	 double selectActualMoney(InformationListdata entity);
	 /**
	  * 项目移交修改create_uid
	  */
	 int updateCreateUid(InformationListdata ir);

	List<InformationListdata> searchFor1103(Map<String,Object> projId);

}
