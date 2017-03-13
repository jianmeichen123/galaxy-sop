package com.galaxyinternet.service.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;

public interface InformationDictionaryService extends BaseService<InformationDictionary>{

	/**
	 * 根据title.id 查询 value 集合
	 */
	List<InformationDictionary> selectValuesByTid(Long tid);

	/**
	 * 根据 code 、 id title 信息查询子 title value 信息集合
	 */
	InformationTitle selectValuesByTinfo(String pinfoKey);


	/**
	 * 根据 code 、 id  的 title 信息  递归查询 各 title下的 value
	 */
	InformationTitle selectTitlesValues(String pinfoKey);

}
