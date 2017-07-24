package com.galaxyinternet.service.hologram;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.hologram.InformationTitleRelate;

public interface InformationTitleRelateService extends BaseService<InformationTitleRelate>{

	/**
	 * 根据Relate info, 查询title 信息
	 */
	List<InformationTitle> selectTitleByRelate(Map<String, Object> params);

	/**
	 * 根据父id 查询其子集
	 */
	List<InformationTitle> selectChildsByPid(Long pid);
	List<InformationTitle> selectChildsGradeByPid(Long relateId);
	
	
}
