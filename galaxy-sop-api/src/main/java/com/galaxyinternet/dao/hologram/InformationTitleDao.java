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
	public List<InformationTitle> selectTitleOfResultsForRelate(Map<String, Object> params);
	public List<InformationTitle> selectTitleOfFileResults(Map<String, Object> params);

	//写死 type22
	public List<Map<String,Object>> selectResultsAndGradeForType22(Map<String, Object> params);

	/**
	 * 根据 title id 查询results,  统计 title 记录数
	 */
	public Integer selectCountForTitleOfResults(Map<String, Object> params);
	public Integer selectCountForTitleOfResultsByRelate(Map<String, Object> params);
	public Integer selectCountForTitleOfListdata(Map<String, Object> params);
	public Integer selectCountForTitleOfFixedTable(Map<String, Object> params);
	public Integer selectCountForTitleOfFile(Map<String, Object> params);
	public Integer selectCountForRelateOfGrade(Map<String, Object> params);
	public Integer selectCountForRelateOfGrade2(Map<String, Object> params);



}
