package com.galaxyinternet.soptask.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.bo.SheduleCommon;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.query.Query;
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

	@Override
	public List<SheduleCommon> selectScheduleListByDate(Map<String,Object> map){
		// TODO Auto-generated method stub
		try {
			
			List<SheduleCommon> lists=sqlSessionTemplate.selectList("selectListByDate", map);
		    System.out.println(lists);
			return lists;
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("selectListByDate")), e);
		}
		
	}

	@Override
	public List<SopUserSchedule> selectSopUserScheduleDesc(
			Map<String, Object> map) {
	    try {
	    	List<SopUserSchedule> list=sqlSessionTemplate.selectList(getSqlName("select"), map);
			return list;
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("select")), e);
		}
	}

	

}
