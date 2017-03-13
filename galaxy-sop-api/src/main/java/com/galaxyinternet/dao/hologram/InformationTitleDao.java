package com.galaxyinternet.dao.hologram;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationTitle;

public interface InformationTitleDao extends BaseDao<InformationTitle, Long>{

	/**
	 * 根据 code 、 id 父信息查询子code集合
	 * 
	 */
	List<InformationTitle> selectChildsByPid(Map<String, Object> params);

}
