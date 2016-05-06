package com.galaxyinternet.dao.timer;

import java.util.List;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.timer.PassRate;

public interface PassRateDao extends BaseDao<PassRate, Long>{

	public List<PassRate> selectListById(List<String> idList);
}
