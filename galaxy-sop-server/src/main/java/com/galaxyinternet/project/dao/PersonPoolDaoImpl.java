package com.galaxyinternet.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.project.PersonPool;

@Repository("personPoolDao")
public class PersonPoolDaoImpl extends BaseDaoImpl<PersonPool, Long> implements PersonPoolDao{


	@Override
	public Page<PersonPool> selectByPid(com.galaxyinternet.bo.project.PersonPoolBo query, Pageable pageable) {
		Assert.notNull(query);
		try {
			List<PersonPool> contentList= sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
			return new  Page<PersonPool>(contentList, pageable, this.selectCount(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据pids查询：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	@Override
	public List<PersonPool> selectNoToTask(Map<String,Object> params) {
		try {
			List<PersonPool> contentList= sqlSessionTemplate.selectList(getSqlName("selectNoToTask"), params);
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据pids查询：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}
	
	
	/**
	 * 根据名称查询项目ID
	 * @param personPool
	 * @return
	 */
	public List<PersonPool> selectProjectIdByPersonName(PersonPool personPool){
		List<PersonPool> personPoolList = null;
		try {
			Map<String, Object> params = BeanUtils.toMap(personPool);
			personPoolList = sqlSessionTemplate.selectList(getSqlName("selectProjectIdByPersonName"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("根据personName查询：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
		return personPoolList;
	}
	
	

}
