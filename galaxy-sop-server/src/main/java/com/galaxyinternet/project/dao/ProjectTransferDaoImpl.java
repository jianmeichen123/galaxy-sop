package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.ProjectTransferDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.ProjectTransfer;


@Repository("projectTransferDao")
public class ProjectTransferDaoImpl extends BaseDaoImpl<ProjectTransfer, Long> implements ProjectTransferDao {


}