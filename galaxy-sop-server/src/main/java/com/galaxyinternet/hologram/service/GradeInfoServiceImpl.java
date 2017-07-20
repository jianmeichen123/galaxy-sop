package com.galaxyinternet.hologram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.hologram.dao.GradeInfoDaoImpl;
import com.galaxyinternet.hologram.model.ItemParam;
import com.galaxyinternet.model.hologram.GradeInfo;
import com.galaxyinternet.service.hologram.GradeInfoService;

@Service
public class GradeInfoServiceImpl extends BaseServiceImpl<GradeInfo> implements GradeInfoService
{
	@Autowired
	private GradeInfoDaoImpl gradeInfoDao;
	/**
	 * 计算题目分数
	 * @param param
	 * @return
	 */
	public Integer calculateItemScore(ItemParam param)
	{
		int score = 0;
		GradeInfo info = gradeInfoDao.selectById(param.getRelatedId());
		if(info != null)
		{
			
		}
		
		return score;
	}
	@Override
	protected BaseDao<GradeInfo, Long> getBaseDao()
	{
		return gradeInfoDao;
	}
}
