package com.galaxyinternet.hologram.dao;

import java.util.Map;

import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.hologram.InformationListdata;

import org.springframework.stereotype.Repository;

@Repository("informationListdataDao")
public class InformationListdataDaoImpl extends BaseDaoImpl<InformationListdata, Long> implements InformationListdataDao {

	@Override
	
	public double selectTotalMoney(InformationListdata entity) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> params = BeanUtils.toMap(entity);
			return sqlSessionTemplate.selectOne(getSqlName("sumPartMoney"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("sumPartMoney")), e);
		}
		
	}
}
