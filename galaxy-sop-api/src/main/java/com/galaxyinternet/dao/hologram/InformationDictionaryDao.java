package com.galaxyinternet.dao.hologram;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;

public interface InformationDictionaryDao extends BaseDao<InformationDictionary, Long>{

	List<InformationDictionary> selectValues(Map<String, Object> params);

}
