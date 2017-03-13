package com.galaxyinternet.service.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;

public interface InformationDictionaryService extends BaseService<InformationDictionary>{

	/**
	 * 根据 title.id  查询其  value 集合
	 */
	List<InformationDictionary> selectValuesByTid(Long tid);

	/**
	 * 根据title 的  id 或 code ，查询 title及其value集合信息
	 */
	InformationTitle selectValuesByTinfo(String pinfoKey);


	/**
	 * 根据  title的id  递归查询 该 title下的各 title - value
	 */
	List<InformationTitle> selectTitlesValues(Long id);
	
	
	/**
	 * 根据title 的  id 或 code ，  递归查询  title 及其下的所有 title - value
	 */
	InformationTitle selectTitlesValues(String pinfoKey);

	
	

}
