package com.galaxyinternet.progresslog;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.common.ProgressLogDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.common.ProgressLog;
@Repository
public class ProgressLogDaoImpl extends BaseDaoImpl<ProgressLog, Long>implements ProgressLogDao {

}
