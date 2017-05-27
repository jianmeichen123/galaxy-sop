package com.galaxyinternet.export_schedule.dao;
import org.springframework.stereotype.Repository;

import com.galaxyinternet.export_schedule.model.ScheduleContacts;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;

@Repository("ScheduleContactsDao")
public class ScheduleContactsDaoImpl extends BaseDaoImpl<ScheduleContacts, Long> implements ScheduleContactsDao{

}
	