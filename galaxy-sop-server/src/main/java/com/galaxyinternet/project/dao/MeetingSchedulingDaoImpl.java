package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.MeetingScheduling;


@Repository("meetingSchedulingDao")
public class MeetingSchedulingDaoImpl extends BaseDaoImpl<MeetingScheduling, Long> implements MeetingSchedulingDao {


}