package com.galaxyinternet.dao.hologram;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.hologram.InformationTitleRelate;

public interface InformationTitleRelateDao extends BaseDao<InformationTitleRelate, Long>{

	List<InformationTitle> selectTitleByRelate(Map<String, Object> params);

	List<InformationTitle> selectTitleGradeByRelate(Map<String, Object> params);


}
