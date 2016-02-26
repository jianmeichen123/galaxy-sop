package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.MeetingFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.MeetingFile;


@Repository("meetingFileDao")
public class MeetingFileDaoImpl extends BaseDaoImpl<MeetingFile, Long> implements MeetingFileDao {


}