package com.galaxyinternet.hologram.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.hologram.InformationDictionary;

@Repository("informationDictionaryDao")
public class InformationDictionaryDaoImpl extends BaseDaoImpl<InformationDictionary, Long> implements InformationDictionaryDao{

	
	/**
	 * 根据title id查询 value 集合
	 * 
	 */
	@Override
	public List<InformationDictionary> selectValues(Map<String, Object> params) {

		try {
			List<InformationDictionary> contentList = sqlSessionTemplate.selectList(getSqlName("selectValues") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titile查询子code出错！语句:%s", getSqlName("selectValues")), e);
		}
	}
	
}
