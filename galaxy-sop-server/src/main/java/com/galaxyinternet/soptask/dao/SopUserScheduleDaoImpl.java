package com.galaxyinternet.soptask.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.soptask.SopUserSchedule;

@Repository("sopUserScheduleDao")
public class SopUserScheduleDaoImpl extends BaseDaoImpl<SopUserSchedule, Long>implements SopUserScheduleDao{

}
