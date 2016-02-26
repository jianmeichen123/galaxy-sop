package com.galaxyinternet.soptask.dao;

import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.soptask.SopTask;

@Repository("sopTaskDao")
public class SopTastDaoImpl extends BaseDaoImpl<SopTask, Long>implements SopTaskDao {
}
