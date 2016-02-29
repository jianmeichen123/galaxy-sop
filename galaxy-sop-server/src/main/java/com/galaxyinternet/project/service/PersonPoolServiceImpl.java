package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.dao.project.ProjectPersonDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.PersonPoolService;

@Service("com.galaxyinternet.service.PersonPoolService")
public class PersonPoolServiceImpl extends BaseServiceImpl<PersonPool> implements PersonPoolService{
	
	@Autowired
	private PersonPoolDao personPoolDao;
	@Autowired
	private ProjectPersonDao projectPersonDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	
	@Override
	protected BaseDao<PersonPool, Long> getBaseDao() {
		return this.personPoolDao;
	}

	@Override
	@Transactional
	public Long savePersonToProject(PersonPoolBo pool) throws Exception {
		Long id = personPoolDao.insert(pool);
		ProjectPerson pp = new ProjectPerson();
		pp.setPersonId(id);
		pp.setProjectId(pool.getProjectId());
		pp.setCreatedTime(System.currentTimeMillis());
		projectPersonDao.insert(pp);
		
		SopTask task = new SopTask();
		task.setProjectId(pool.getProjectId());
		task.setTaskName("完善简历");
		//from字典
		task.setTaskType("t-001");
		task.setTaskOrder("tl-001");
		task.setTaskDestination("td-001");
		task.setTaskStatus("ts-001");
		task.setCreatedTime(System.currentTimeMillis());
		sopTaskDao.insert(task);
		return id;
	}

}
