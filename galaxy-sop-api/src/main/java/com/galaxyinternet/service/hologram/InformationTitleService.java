package com.galaxyinternet.service.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationTitle;

public interface InformationTitleService extends BaseService<InformationTitle>{

	/**
	 * 查询 parentid 为空的 题， 即顶级目录
	 */
	List<InformationTitle> selectFirstTitle();
	
	
	/**
	 * 根据 code 或 id 查询 本 title
	 */
	InformationTitle selectTitleByPinfo(String pinfoKey);
	
	/**
	 * 根据父id 查询其子集
	 */
	List<InformationTitle> selectChildsByPid(Long pid);
	
	
	/**
	 * 根据 code 或   id 查询其子集
	 */
	List<InformationTitle> selectChildsByPinfo(String pinfo);

	
	/**
	 * 根据 code 、 id 父信息递归查询其下所有子集合
	 */
	InformationTitle selectPchildsByPinfo(String pinfoKey);
	//List<InformationTitle> selectByTlist(List<InformationTitle> tList);

	
	



}
