package com.galaxyinternet.soptask.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.SheduleCommon;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
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

	@Override
	public Page<SopUserSchedule> scheduleListByName(SopUserScheduleBo query, Pageable pageable) {
		Assert.notNull(query);
		try {
			List<SopUserSchedule> contentList= sqlSessionTemplate.selectList("selectByFenye", getParams(query, pageable));
			return new  Page<SopUserSchedule>(contentList, pageable, this.selectCountByName(query));
		} catch (Exception e) {
			throw new DaoException(String.format("模糊查询：%s", getSqlName("selectByFenye")), e);
		}
	}
	@Override
	public Long selectCountByName(SopUserScheduleBo query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName("selectCountByName"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
		}
	}
	
	
	
	

	

}
