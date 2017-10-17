package com.galaxyinternet.dao.hologram;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationFile;

public interface InformationFileDao extends BaseDao<InformationFile, Long>{
	 /**
	  * 项目移交修改create_uid
	  */
	 int updateCreateUid(InformationFile ir);
}
