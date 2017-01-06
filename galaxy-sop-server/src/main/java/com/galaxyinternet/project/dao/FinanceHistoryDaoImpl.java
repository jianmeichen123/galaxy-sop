package com.galaxyinternet.project.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.FinanceHistoryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.project.FinanceHistory;

@Repository("financeHistoryDao")
public class FinanceHistoryDaoImpl extends BaseDaoImpl<FinanceHistory, Long> implements FinanceHistoryDao{

	@Override
	public String getFHStr(Long projectId) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		map.put("projectId", projectId.toString());
		try {
			return sqlSessionTemplate.selectOne(getSqlName("getFHStr"),map);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象信息出错！语句：%s", getSqlName(getSqlName("getFHStr"))), e);
		}
	}

	

}
