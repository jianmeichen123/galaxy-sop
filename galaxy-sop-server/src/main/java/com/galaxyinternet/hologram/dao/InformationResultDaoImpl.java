package com.galaxyinternet.hologram.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationResult;

@Repository("InformationResultDao")
public class InformationResultDaoImpl extends BaseDaoImpl<InformationResult, Long> implements InformationResultDao 
{

	@Override
	public void insertInBatch(List<InformationResult> entityList)
	{
		sqlSessionTemplate.insert(getSqlName("insertBatch"), entityList);
	}

	
}
