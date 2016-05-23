package com.galaxyinternet.resource.service;

import static com.galaxyinternet.utils.ExceptUtils.isNull;
import static com.galaxyinternet.utils.ExceptUtils.throwSopException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.hr.PersonInvestDao;
import com.galaxyinternet.dao.hr.PersonLearnDao;
import com.galaxyinternet.dao.hr.PersonWorkDao;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.ExceptionMessage;
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
		
		
		
		PersonPool personPool = personResumetc.getPersonPool();
		Long personId = personPool.getId();
		isNull(PersonPool.ID,personId);
		isNull(PersonPool.PERSONNAME,personPool.getPersonName());
		Long time = System.currentTimeMillis();
		personPool.setUpdatedTime(time);
		if(personPool.getPersonBirthdayStr() != null){
			try {
				Date date = DateUtil.convertStringToDate(personPool.getPersonBirthdayStr());
				personPool.setPersonBirthday(date);
			} catch (ParseException e) {
				System.err.println(e);
			}
		}
		int count = personPoolDao.updateById(personPool);
		if(count == 0){
			throwSopException(ExceptionMessage.DATA_NOT_EXISTS, "该个人信息不存在");
		}
		PersonInvest personInvest = personResumetc.getPersonInvest();
		if(personInvest != null){
			if(personInvest.getId() == null){
				personInvest.setPersonId(personId);
				personInvest.setCreatedTime(time);
				personInvestDao.insert(personInvest);
			}else {
				personInvest.setUpdatedTime(time);
				personInvestDao.updateById(personInvest);
			}
		}
		List<PersonWork> personWorks = personResumetc.getPersonWork();
		if(personWorks != null && personWorks.size() >0){
			for (PersonWork personWork : personWorks) {
				if(personWork.getBeginWorkStr() != null){
					try {
						Date date = DateUtil.convertStringToDate(personWork.getBeginWorkStr());
						personWork.setBeginWork(date);
					} catch (ParseException e) {
						throwSopException(ExceptionMessage.FIELD_NOT_DATE, "入职时间");
					}
				}
				if(personWork.getId() == null){
					personWork.setPersonId(personId);
					personWork.setCreatedTime(time);
					personWorkDao.insert(personWork);
				}else {
					personWork.setUpdatedTime(time);
					personWorkDao.updateById(personWork);
				}
			}
		}
		List<PersonLearn> personLearns = personResumetc.getPersonLearn();
		if(personLearns != null&& personLearns.size() >0){
			for (PersonLearn personLearn : personLearns) {
				if(personLearn.getOverDateStr()!= null){
					try {
						Date date = DateUtil.convertStringToDate(personLearn.getOverDateStr());
						personLearn.setOverDate(date);
					} catch (ParseException e) {
						throwSopException(ExceptionMessage.FIELD_NOT_DATE, "毕业时间");
					}
				}
				if(personLearn.getId() == null){
					personLearn.setPersonId(personId);
					personLearn.setCreatedTime(time);;
					personLearnDao.insert(personLearn);
				}else {
					personLearn.setUpdatedTime(time);
					personLearnDao.updateById(personLearn);
				}
			}
		}

		return count;
	}

	
}
