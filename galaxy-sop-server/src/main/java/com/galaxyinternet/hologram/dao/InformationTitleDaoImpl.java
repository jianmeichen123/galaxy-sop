package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationTitle;

@Repository("informationTitleDao")
public class InformationTitleDaoImpl extends BaseDaoImpl<InformationTitle, Long> implements InformationTitleDao{

	
}
