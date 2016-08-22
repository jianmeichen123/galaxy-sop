package com.galaxyinternet.grant.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.dao.GrantActualDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.GrantActual;


@Repository("grantActualDao")
public class GrantActualDaoImpl extends BaseDaoImpl<GrantActual, Long> implements GrantActualDao{

	@Override
	public Map<String, Object> lookActualDetail(Long actualId) {
		Assert.notNull(actualId);
		try {
			return sqlSessionTemplate.selectOne(getSqlName("lookActualDetail"), actualId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(String.format("查询实际拨款信息出错！语句：%s", getSqlName("lookActualDetail")), e);
		}
	}

}
