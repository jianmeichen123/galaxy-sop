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

	/**
	 * 查询 parentid 为空的 题， 即顶级目录
	 */
	List<InformationTitle> selectFirstTitle();
	
	public List<InformationTitle> selectRelateTitle(InformationTitle query);

}
