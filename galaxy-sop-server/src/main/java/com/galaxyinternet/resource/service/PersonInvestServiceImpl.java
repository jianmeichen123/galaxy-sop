package com.galaxyinternet.resource.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.personInvestBo;
import com.galaxyinternet.bo.project.personLearnBo;
import com.galaxyinternet.bo.project.personWorkBo;
import com.galaxyinternet.dao.hr.PersonInvestDao;
import com.galaxyinternet.dao.hr.PersonLearnDao;
import com.galaxyinternet.dao.hr.PersonWorkDao;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hr.PersonInvest;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonResumetc;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.service.PersonInvestService;

@Service("com.galaxyinternet.service.PersonInvestService")
public class PersonInvestServiceImpl extends BaseServiceImpl<PersonInvest> implements PersonInvestService{
	
	@Autowired
	private PersonInvestDao personInvestDao;

	@Autowired
	private PersonLearnDao personLearnDao;
	
	@Autowired
	private PersonPoolDao personPoolDao;
	
	
	@Autowired
	private PersonWorkDao personWorkDao;

	@Override
	protected BaseDao<PersonInvest, Long> getBaseDao() {
		// 
		return this.personInvestDao;
	}

	@Transactional
	@Override
	public int WanShan(PersonResumetc personResumetc) {
		
		
		PersonInvest personInvest = personResumetc.getPersonInvest();
		
		PersonPool personPool = personResumetc.getPersonPool();
		personPool.setId(personResumetc.getPersonId());
		List<PersonWork> personWorks = personResumetc.getPersonWork();
		List<PersonLearn> personLearns = personResumetc.getPersonLearn();
		
		personPoolDao.updateById(personPool);
		Long personId = personPool.getId();
		if(personId == null){
			return 0;
		}
		if(personInvest.getId() == null){
			personInvest.setPersonId(personId);
			personInvestDao.insert(personInvest);
		}else {
			personInvestDao.updateById(personInvest);
		}
		for (PersonWork personWork : personWorks) {
			if(personWork.getId() == null){
				personWork.setPersonId(personId);
				personWorkDao.insert(personWork);
			}else {
				personWorkDao.updateById(personWork);
			}
		}
		for (PersonLearn personLearn : personLearns) {
			if(personLearn.getId() == null){
				personLearn.setPersonId(personId);
				personLearnDao.insert(personLearn);
			}else {
				personLearnDao.updateById(personLearn);
			}
		}

		return 0;
	}

	
}
