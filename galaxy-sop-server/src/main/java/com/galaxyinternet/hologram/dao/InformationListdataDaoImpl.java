package com.galaxyinternet.hologram.dao;

import java.util.Map;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.hologram.InformationListdata;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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

	@Override
	public double selectActualMoney(InformationListdata entity) {
		try {
			Map<String, Object> params = BeanUtils.toMap(entity);
			return sqlSessionTemplate.selectOne(getSqlName("sumActualMoney"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("sumActualMoney")), e);
		}
	}

	@Override
	public int updateCreateUid(InformationListdata ir) {
		Assert.notNull(ir);
		try {
			return sqlSessionTemplate.update(getSqlName("updateCreateUid"), ir);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s", getSqlName("updateCreateUid")), e);
		}
	}
}
