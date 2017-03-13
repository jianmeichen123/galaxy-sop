package com.galaxyinternet.hologram.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.hologram.InformationTitle;

@Repository("informationTitleDao")
public class InformationTitleDaoImpl extends BaseDaoImpl<InformationTitle, Long> implements InformationTitleDao{

	
	/**
	 * 根据父信息  code 、 id 查询子 code 集合
	 * 
	 */
	@Override
	public List<InformationTitle> selectChildsByPid(Map<String, Object> params) {

		try {
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(getSqlName("selectChildsByPid") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titile查询子code出错！语句:%s", getSqlName("selectChildsByPid")), e);
		}
	}

	
}
