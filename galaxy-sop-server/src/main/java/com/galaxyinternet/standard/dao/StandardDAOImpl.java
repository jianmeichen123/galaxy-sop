package com.galaxyinternet.standard.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.StandardDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.Standard;
@Repository
public class StandardDAOImpl extends BaseDaoImpl<Standard, Long> implements StandardDao
{

}
