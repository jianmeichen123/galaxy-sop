package com.galaxyinternet.hologram.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationTitleRelateDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.hologram.InformationTitleRelate;

@Repository("informationTitleRelateDao")
public class InformationTitleRelateDaoImpl extends BaseDaoImpl<InformationTitleRelate, Long> implements InformationTitleRelateDao{

	@Override
	public List<InformationTitle> selectTitleByRelate(Map<String, Object> params) {
		try {
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(getSqlName("selectTitlesByRelate") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据Relate查询titile出错！语句:%s", getSqlName("selectTitlesByRelate")), e);
		}
	}

	
	
	@Override
	public List<InformationTitle> selectTitleGradeByRelate(Map<String, Object> params) {
		try {
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(getSqlName("selectTitlesGradeByRelate") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据Relate查询titile出错！语句:%s", getSqlName("selectTitlesGradeByRelate")), e);
		}
	}

	
}
