package com.galaxyinternet.service.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;

public interface InformationDictionaryService extends BaseService<InformationDictionary>{

	
	/**
	 * 根据 value.pid  查询其下级  value 集合
	 */
	List<InformationDictionary> selectValuesByVpid(Long pid);
	
	/**
	 * 根据 title.id  查询其  value 集合
	 */
	List<InformationDictionary> selectValuesByTid(Long tid);

	/**
	 * 根据title 的  id 或 code ，查询 title及其value集合信息
	 */
	InformationTitle selectValuesByTinfo(String pinfoKey);
	
	
	/**
	 * 根据title的 id或 code ，查询 该title下一级的 title-value
	 */
	List<InformationTitle> selectTsTvalueInfo(Object pinfoKey);
	/**
	 * 运用cache ,根据title的 id或 code ，查询 该title下一级的 title-value
	 */
	List<InformationTitle> selectTsTvalueInfoByCache(Object pinfoKey);

	
	/**
	 * 传入题 id 或  code， 返回该题信息，及该题的下一级的 题及value 信息
	 */
	InformationTitle selectTitleAndTsTvalues(Object pinfoKey);
	/**
	 * 运用cache , 传入题 id 或  code， 返回该题信息，及该题的下一级的 题及value 信息
	 */
	InformationTitle selectTitleAndTsTvaluesByCache(Object pinfoKey);
	
	
	
	/**
	 * 根据  title的id  递归查询 该 title下的各级的 title - value
	 */
	//List<InformationTitle> selectTitlesValues(Long id);
	
	
	/**
	 * 根据title 的  id 或 code ，  递归查询  title 及其下的所有 title - value
	 */
	InformationTitle selectTitlesValues(String pinfoKey);
	InformationTitle selectTitlesValues(InformationTitle info);
	InformationTitle selectTitlesValuesGrade(InformationTitle informationTitle);

	

	
	
	


	


	
	

	
	

}
