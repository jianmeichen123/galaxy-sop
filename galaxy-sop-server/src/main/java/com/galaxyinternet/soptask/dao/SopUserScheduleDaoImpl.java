package com.galaxyinternet.soptask.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.soptask.SopUserSchedule;

@Repository("sopUserScheduleDao")
public class SopUserScheduleDaoImpl extends BaseDaoImpl<SopUserSchedule, Long>implements SopUserScheduleDao{

	@Override
	public List<SopUserSchedule> selectSopUserScheduleByTime(Map<String,Object> map) {
		// TODO Auto-generated method stub
		try {
			List<SopUserSchedule> list=sqlSessionTemplate.selectList(getSqlName("selectByTime"), map);
		    return list;
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("selectByTime")), e);
		}
	}

	

}
