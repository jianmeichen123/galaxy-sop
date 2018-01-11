package com.galaxyinternet.soptask.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.soptask.SopTaskRecordDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.soptask.SopTaskRecord;
@Repository
public class SopTaskRecordDaoImpl extends BaseDaoImpl<SopTaskRecord, Long> implements SopTaskRecordDao
{

}
