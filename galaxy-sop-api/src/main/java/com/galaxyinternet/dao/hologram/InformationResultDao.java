package com.galaxyinternet.dao.hologram;

import java.util.List;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationResult;

public interface InformationResultDao extends BaseDao<InformationResult, Long>{
	List<InformationResult> selectResultByRelate(InformationResult ir);
}
