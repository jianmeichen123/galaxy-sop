package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.MeetingRecord;


@Repository("meetingRecordDao")
public class MeetingRecordDaoImpl extends BaseDaoImpl<MeetingRecord, Long> implements MeetingRecordDao {


}