package com.galaxyinternet.sopfile.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.sopfile.SopFile;

@Repository("sopFileDao")
public class SopFileDaoImpl extends BaseDaoImpl<SopFile, Long> implements SopFileDao  {

	
}
