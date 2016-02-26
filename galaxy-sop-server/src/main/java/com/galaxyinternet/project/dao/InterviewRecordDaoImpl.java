package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.InterviewRecord;


@Repository("interviewRecordDao")
public class InterviewRecordDaoImpl extends BaseDaoImpl<InterviewRecord, Long> implements InterviewRecordDao {


}
