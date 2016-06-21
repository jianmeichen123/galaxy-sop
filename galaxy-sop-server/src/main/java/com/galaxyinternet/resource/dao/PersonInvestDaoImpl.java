package com.galaxyinternet.resource.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.galaxyinternet.dao.hr.PersonInvestDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.hr.PersonInvest;


@Repository("personInvestDao")
public class PersonInvestDaoImpl  extends BaseDaoImpl<PersonInvest, Long> implements PersonInvestDao{

	@Transactional
	public int updateByIdForNull(PersonInvest entity) {
		Assert.notNull(entity);
		try {
			return sqlSessionTemplate.update(getSqlName("updateByIdForNull"), entity);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s", getSqlName("updateByIdForNull")), e);
		}
	}
	
}

