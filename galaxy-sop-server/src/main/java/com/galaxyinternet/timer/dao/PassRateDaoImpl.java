package com.galaxyinternet.timer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.timer.PassRateDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.timer.PassRate;

@Repository("passRateDao")
public class PassRateDaoImpl extends BaseDaoImpl<PassRate, Long> implements PassRateDao  {

	@Override
	public List<PassRate> selectListById(List<String> idList) {
		return sqlSessionTemplate.selectList(getSqlName("selectListById"),idList);
	}

}
