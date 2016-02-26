package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.InterviewFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.InterviewFile;

@Repository("interviewFileDao")
public class InterviewFileDaoImpl extends BaseDaoImpl<InterviewFile, Long> implements InterviewFileDao {


}