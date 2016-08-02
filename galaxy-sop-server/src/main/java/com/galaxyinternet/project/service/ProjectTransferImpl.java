package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.project.ProjectTransferDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.service.ProjectTransferService;


@Service("com.galaxyinternet.service.ProjectTransferService")
public class ProjectTransferImpl extends BaseServiceImpl<ProjectTransfer> implements ProjectTransferService {

	@Autowired
	private ProjectTransferDao projectTransferDao;
	@Override
	protected BaseDao<ProjectTransfer, Long> getBaseDao() {
		return this.projectTransferDao;
	}
	
}