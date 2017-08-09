package com.galaxyinternet.hologram.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.ScoreInfoDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.hologram.InformationScore;
import com.galaxyinternet.model.hologram.ScoreInfo;

@Repository
public class ScoreInfoDaoImpl extends BaseDaoImpl<ScoreInfo, Long> implements ScoreInfoDao
{

	@Override
	public List<InformationScore> selectScore(InformationScore query)
	{
		try
		{
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectList(getSqlName("selectScore"), params);
		} catch (Exception e)
		{
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("selectScore")), e);
		}
	}

	@Override
	public void insertScoreBatch(List<InformationScore> list)
	{
		sqlSessionTemplate.insert(getSqlName("insertScoreBatch"), list);

	}

	@Override
	public void deleteScoreBatch(InformationScore query)
	{
		try
		{
			Map<String, Object> params = BeanUtils.toMap(query);
			sqlSessionTemplate.delete(getSqlName("deleteScoreBatch"), params);
		} catch (Exception e)
		{
			throw new DaoException(String.format("删除对象出错！语句：%s", getSqlName("deleteScoreBatch")), e);
		}

	}

}
