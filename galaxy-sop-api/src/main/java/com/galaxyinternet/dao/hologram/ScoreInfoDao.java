package com.galaxyinternet.dao.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationScore;
import com.galaxyinternet.model.hologram.ScoreInfo;

public interface ScoreInfoDao extends BaseDao<ScoreInfo, Long>
{
	public List<InformationScore> selectScore(InformationScore query);
	public void insertScoreBatch(List<InformationScore> list);
	public void deleteScoreBatch(InformationScore query);
}
