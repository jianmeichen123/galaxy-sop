package com.galaxyinternet.dao.hologram;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationTitle;

import java.util.List;
import java.util.Map;

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


	/**
	 * 根据 title id 查询results
	 */
	public List<InformationTitle> selectTitleOfResults(Map<String, Object> params);
	/**
	 * 根据 title id 查询results,  统计 title 记录数
	 */
	public Integer selectCountForTitleOfResults(Map<String, Object> params);
	public Integer selectCountForTitleOfListdata(Map<String, Object> params);
	public Integer selectCountForTitleOfFixedTable(Map<String, Object> params);
	public Integer selectCountForTitleOfFile(Map<String, Object> params);


}
