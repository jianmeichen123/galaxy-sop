package com.galaxyinternet.ideaZixun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.idea.IdeaZixunDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.service.IdeaZixunService;


@Service("com.galaxyinternet.service.IdeaZixunService")
public class IdeaZixunServiceImpl extends BaseServiceImpl<IdeaZixun> implements IdeaZixunService{

	@Autowired
	private IdeaZixunDao ideaZixunDao;
	
	@Override
	protected BaseDao<IdeaZixun, Long> getBaseDao() {
		return this.ideaZixunDao;
	}


	
	
}