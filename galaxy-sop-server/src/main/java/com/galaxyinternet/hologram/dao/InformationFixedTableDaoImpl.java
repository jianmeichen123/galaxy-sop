package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationFixedTableDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationFixedTable;

@Repository("informationFixedTableDao")
public class InformationFixedTableDaoImpl extends BaseDaoImpl<InformationFixedTable, Long> implements InformationFixedTableDao{

}
